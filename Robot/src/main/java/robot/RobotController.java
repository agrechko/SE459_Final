package robot;

import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import sensors.SensorsController;

public class RobotController extends Thread {
    // used for testing purposes
    public boolean devModeOn = false;
    // hard coded paths for testing
    public LinkedList<int[]> devpaths;
    public LinkedList<String> devCommands;

    Logger logger = Logger.getLogger("main");

    SensorsController sensors;
    // flag to state if this is the first time exploring is starting
    boolean firstStart = true;
    public int currentState = State.READY_TO_CLEAN.getValue();
    int prevState;
    // starts at maximum power and counts down to zero which means we ran out of
    // power
    int currentPower;
    int maxPower;
    // starts from maximum dirt capacity and counts down to zero until no empty
    // space is left for dirt
    private int currentDirtCapacity;
    int maxDirtCapacity;
    int currentX;
    int currentY;
    int wentBackFrom = -1;
    int currentCleaningApparatus = 0;
    Boolean emptyMe;
    Boolean userInputWaiting;
    int userInputState = -1;
    int userInputCommand;
    //direct path to known dirty cell
    LinkedList<Integer> pathToDirtyCell = new LinkedList<Integer>();
    
    // this is the route that the sweeper took; pop off the stack to return
    // home. 1: x neg, 2: x pos, 3: y pos, 4: y neg
    Stack<Integer> route = new Stack<Integer>();

    public enum State {
        STOP(0), READY_TO_CLEAN(1), CLEANING(2), CHARGING(3), GOING_HOME(4), EXPLORING(
                5), PRINT(6), WAITING_FOR_COMMAND(7), EMPTY_ME(8);

        private int value;

        private State(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    enum CleaningApparatus {
        APP_BARE_FLOOR(0), APP_LOW_CARPET(1), APP_HIGH_CARPET(2);

        private int value;

        private CleaningApparatus(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public RobotController(final SensorsController sensors, final int maxPower,
            final int maxDirtCapacity, final int startX, final int startY) {
        this.sensors = sensors;
        this.currentPower = maxPower;
        this.maxPower = maxPower;
        this.currentDirtCapacity = maxDirtCapacity;
        this.maxDirtCapacity = maxDirtCapacity;
        this.currentX = startX;
        this.currentY = startY;
        emptyMe = false;
        userInputWaiting = false;
    }

    // controlls execution state
    public final void run() {
        while (true) {
            if (currentState != State.WAITING_FOR_COMMAND.getValue()) {
                logger.log(Level.FINE, "power: " + currentPower);
            }

            // user has inputed a command on the other thread
            if (userInputWaiting) {
                userInputWaiting = false;
                prevState = currentState;
                currentState = userInputState;
                System.out.println("Input processed");
            }

            if (State.READY_TO_CLEAN.getValue() == currentState) {
                new ReadyToCleanState().execute(this);
            } else if (State.EXPLORING.getValue() == currentState) {
                new ExploringState().execute(this);
            } else if (State.CLEANING.getValue() == currentState) {
                new CleaningState().execute(this);
            } else if (State.CHARGING.getValue() == currentState) {
                new ChargingState().execute(this);
            } else if (State.GOING_HOME.getValue() == currentState) {
                new GoingHomeState().execute(this);
            } else if (State.STOP.getValue() == currentState) {
                if (userInputState != State.STOP.getValue()) {
                    printStopCommands();
                }
                break;
            } else if (State.PRINT.getValue() == currentState) {
                // print the floor plan in print state and set the current state
                // to what it was before to continue execution
                sensors.PrintMemFloorplan();
                currentState = prevState;
                printAvailableCommands();
            } else if (State.WAITING_FOR_COMMAND.getValue() == currentState) {
                if (devModeOn && !userInputWaiting) {
                    userInput(devCommands.pop());
                }
                if (userInputWaiting) {
                    currentState = userInputState;
                }
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Sleep interupted: ", e);
                }
            } else if (State.EMPTY_ME.getValue() == currentState) {
                new EmptyMeState().execute(this);
            } else {
                logger.log(Level.FINE, "Invalid state reached. Exiting...");
                throw new IllegalStateException();
            }
        }
        // prints out the memory floor plan after the clean cycle run
        sensors.PrintMemFloorplan();
    }

    public final int getCurrentX() {
        return currentX;
    }

    public final int getCurrentY() {
        return currentY;
    }

    public final int getCurrentPower() {
        return currentPower;
    }

    public final int currentCleaningApparatus() {
        if (CleaningApparatus.APP_BARE_FLOOR.getValue() == currentCleaningApparatus()) {
            currentCleaningApparatus = CleaningApparatus.APP_BARE_FLOOR
                    .getValue();
        } else if (CleaningApparatus.APP_LOW_CARPET.getValue() == currentCleaningApparatus()) {
            currentCleaningApparatus = CleaningApparatus.APP_LOW_CARPET
                    .getValue();
        } else if (CleaningApparatus.APP_HIGH_CARPET.getValue() == currentCleaningApparatus()) {
            currentCleaningApparatus = CleaningApparatus.APP_HIGH_CARPET
                    .getValue();
        }
        return currentCleaningApparatus;
    }

    public final int getPowerConsumption(final int floorType) {
        int powerConsumption = 0;
        switch (floorType) {
        case 1:
            powerConsumption = 1;
            break;
        case 2:
            powerConsumption = 2;
            break;
        case 4:
            powerConsumption = 3;
            break;
        default:
            new Exception("unknown floor type encountered");
            break;
        }
        return powerConsumption;
    }

    // return true if the user command has been recognized else false
    public final boolean userInput(final String userCommand) {
        System.out.println("Processing Please Wait...");
        if ("stop".equals(userCommand)) {
            userInputState = State.STOP.getValue();
            userInputWaiting = true;
        } else if ("print".equals(userCommand)) {
            userInputState = State.PRINT.getValue();
            userInputWaiting = true;
        } else if ("empty".equals(userCommand)) {
            if (State.WAITING_FOR_COMMAND.getValue() == currentState) {
                userInputState = State.EMPTY_ME.getValue();
                userInputWaiting = true;
            } else {
                System.out.println("Unable to perform empty action at this time");
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * @return the currentDirtCapacity
     */
    public final int getCurrentDirtCapacity() {
        return currentDirtCapacity;
    }

    /**
     * @param currentDirtCapacity
     *            the currentDirtCapacity to set
     */
    public final void setCurrentDirtCapacity(final int currentDirtCapacity) {
        this.currentDirtCapacity = currentDirtCapacity;
    }

    public final void printAvailableCommands() {
        System.out.println("Available command:");
        System.out.println("Print - prints the current state of the floor plan");
        System.out.println("Stop - stop and reset the current clean cycle");
        System.out.println("Empty - to empty the dirt cargo bay and continue cleaning");
    }

    public final void printStopCommands() {
        System.out.println("Available command:");
        System.out.println("restart - start a new clean cycle");
        System.out.println("Shutdown - turns off the robot");
    }

}
