package robot;

import java.util.LinkedList;
import java.util.Stack;

import sensors.SensorsController;

public class RobotController
{
	public boolean devModeOn = false;//used for testing purposes
	public LinkedList<int[]> devpaths;//hard coded paths for testing
	
	SensorsController sensors;
	DirtController dirtController;
	int currentState = State.READY_TO_CLEAN.getValue();
	int currentPower;//starts at maximum power and counts down to zero which means we ran out of power
	int maxPower;
	int currentDirtCapacity;//starts from maximum dirt capacity and counts down to zero until no empty space is left for dirt
	int maxDirtCapacity;
	int currentX;
	int currentY;
	int wentBackFrom = -1;
	
	//this is the route that the sweeper took; pop off the stack to return home. 1: x neg, 2: x pos, 3: y pos, 4: y neg 
	Stack<Integer> route = new Stack<Integer>();
	
	enum State{
		STOP(0),
		READY_TO_CLEAN(1), 
		CLEANING(2),
		CHARGING(3),
		GOING_HOME(4),
		EXPLORING(5);
		
		private int value;
		
        private State(int value) {
                this.value = value;
        }
        
        public int getValue()
        {
        	return value;
        }
	}
	
	public RobotController(SensorsController sensors, DirtController dirtController, int maxPower, int maxDirtCapacity, int startX, int startY)
	{
		this.sensors = sensors;
		this.currentPower = maxPower;
		this.maxPower = maxPower;
		this.currentDirtCapacity = maxDirtCapacity;
		this.maxDirtCapacity = maxDirtCapacity;
		this.dirtController = dirtController;
		this.currentX = startX;
		this.currentY = startY;
	}
	
	//controlls execution state
	public void execute() throws Exception
	{
		while(true)
		{
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
		}	
	}
	
	//change robot state
	public void setState(int state)
	{
		currentState = state;
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
}
