package robot;

import sensors.SensorsController;

public class RobotController
{
	SensorsController sensors;
	int currentState = State.READY_TO_CLEAN.getValue();
	int currentPower;
	int currentDirtCapacity;
	
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
	
	public RobotController(SensorsController sensors, DirtController dirtController, int maxPower, int maxDirtCapacity)
	{
		this.sensors = sensors;
		this.currentPower = maxPower;
		this.currentDirtCapacity = maxDirtCapacity;
	}
	
	//controlls execution state
	public void execute()
	{
		while(true)
		{
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
				//not yet implemented
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
	public void setState(State state)
	{
		currentState = state.getValue();
	}
	
}
