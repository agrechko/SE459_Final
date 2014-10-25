package robot;

import java.util.LinkedList;
import java.util.Stack;

import sensors.SensorsController;

public class RobotController extends Thread
{
	public boolean devModeOn = false;//used for testing purposes
	public LinkedList<int[]> devpaths;//hard coded paths for testing
	
	SensorsController sensors;
	int currentState = State.READY_TO_CLEAN.getValue();
	int prevState;
	int currentPower;//starts at maximum power and counts down to zero which means we ran out of power
	int maxPower;
	int currentDirtCapacity;//starts from maximum dirt capacity and counts down to zero until no empty space is left for dirt
	int currentX;
	int currentY;
	int wentBackFrom = -1;
	int currentCleaningApparatus = 0;
	Boolean emptyMe;
	Boolean userInputWaiting;
	int userInputState;
	
	//this is the route that the sweeper took; pop off the stack to return home. 1: x neg, 2: x pos, 3: y pos, 4: y neg 
	Stack<Integer> route = new Stack<Integer>();
	
	enum State{
		STOP(0),
		READY_TO_CLEAN(1), 
		CLEANING(2),
		CHARGING(3),
		GOING_HOME(4),
		EXPLORING(5),
		PRINT(6);
		
		private int value;
		
        private State(int value) {
                this.value = value;
        }
        
        public int getValue()
        {
        	return value;
        }
	}
	
	enum CleaningApparatus{
		APP_BARE_FLOOR(0),
		APP_LOW_CARPET(1),
		APP_HIGH_CARPET(2);
		
		private int value;
		
        private CleaningApparatus(int value) {
                this.value = value;
        }
        
        public int getValue()
        {
        	return value;
        }
	}
	
	public RobotController(SensorsController sensors, int maxPower, int maxDirtCapacity, int startX, int startY)
	{
		this.sensors = sensors;
		this.currentPower = maxPower;
		this.maxPower = maxPower;
		this.currentDirtCapacity = maxDirtCapacity;
		this.currentX = startX;
		this.currentY = startY;
		emptyMe = false;
		userInputWaiting = false;
	}
	
	//controlls execution state
	public void run()
	{
		while(true)
		{
			//user has inputed a command on the other thread
			if(userInputWaiting)
			{
				userInputWaiting = false;
				prevState = currentState;
				currentState = userInputState;
			}
			
			System.out.println("power: " + currentPower);
			if(State.READY_TO_CLEAN.getValue() == currentState)
			{
				new ReadyToCleanState().execute(this);
			}
			else if(State.EXPLORING.getValue() == currentState)
			{
				new ExploringState().execute(this);
			}
			else if(State.CLEANING.getValue() == currentState)
			{
				new CleaningState().execute(this);
			}
			else if(State.CHARGING.getValue() == currentState)
			{
				new ChargingState().execute(this);
			}
			else if(State.GOING_HOME.getValue() == currentState)
			{
				new GoingHomeState().execute(this);
			}
			else if (State.STOP.getValue() == currentState)
			{
				new StopState().execute(this);
				break;
			}
			else if(State.PRINT.getValue() == currentState)
			{
				//print the floor plan in print state and set the current state to what it was before to continue execution
				currentState = prevState;
			}
			else
			{
				System.out.println("Invalid state reached. Exiting...");
				break;
			}
		}	
	}
	
	public int getCurrentX(){
		return currentX;
	}
	public int getCurrentY(){
		return currentY;
	}
	public int getCurrentPower(){
		return currentPower;
	}
	
	public int getPowerConsumption(int floorType)
	{
		int powerConsumption = 0;
		switch(floorType){
		case 1: 
			powerConsumption = 1;
			break;
		case 2:
			powerConsumption = 2;
			break;
		case 4:
			powerConsumption = 3;
			break;
		}
		return powerConsumption;
	}
	
	//return true if the user command has been recognized else false
	public boolean userInput(String userCommand)
	{
		if(userCommand.equals("stop"))
		{
			userInputState = State.STOP.getValue();
			userInputWaiting = true;
		}
		else if(userCommand.equals("print"))
		{
			userInputState = State.PRINT.getValue();
			System.out.println("print is not implemented yet");
		}
		else
		{
			return false;
		}
		
		return true;
	}
}
