package robot;

import sensors.SensorsController;

public class DirtController
{
	SensorsController sensors;
	int currentPower;//starts at maximum power and counts down to zero which means we ran out of power
	int maxPower;
	int currentDirtCapacity;//starts from maximum dirt capacity and counts down to zero until no empty space is left for dirt
	int maxDirtCapacity;
	
	public void update(int surfaceType)
	{
		currentDirtCapacity--; 
		currentPower = currentPower - surfaceType;
	}
}
