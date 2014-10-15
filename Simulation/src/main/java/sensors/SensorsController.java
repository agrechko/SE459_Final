package sensors;

import java.io.IOException;
import java.util.Random;

import objectsDTO.CellData;

public class SensorsController 
{
	FloorPlan floorplan;
	public SensorsController(String floorPlanPath) throws IOException //TODO 
	{
		floorplan = new FloorPlan(floorPlanPath);
	}
	
	//returns the celldata object of current cell of x and y
	public CellData getCell(int x, int y)
	{
		int[] coordinator = new int[] {x,y};
		CellData cd = new CellData();
		cd = floorplan.grid.get(coordinator);
		return cd;
	}
	
	//this takes in current cell and it returns the surrounding cells status 1: open, 2: obstical, 4: stairs  
	public int[] getPaths(int x, int y)
	{
		Random gen = new Random();
		int i = gen.nextInt(4);
		if(i == 0)
			return new int[]{1,2,2,2};
		else if(i == 1)
			return new int[]{2,1,2,2};
		else if(i == 2)
			return new int[]{2,2,1,2};
		else
			return new int[]{2,2,2,1};
		
	}
	
	//this sets the surrounding coordinates of the current location 
	private void setPaths(int[] paths)
	{
		
	}
	
	//this method cleans 1 unit of dirt from the given location
	public void clean(int x, int y)
	{
		
	}
	
	//this method return if the current location is clean or not
	public boolean isClean(int x, int y)
	{
		return true;
	}
	
	//this method returns an int 1: bare floor, 2: low carpet, 4: high carpet
	public int getSurface(int x, int y)
	{
		return 0;
	}
	
	//this method sets the surface for current location surface value are 1: bare floor, 2: low carpet, 3: high carpet
	public void setSurface(int x, int y, int surfaceValue)
	{
		
	}
	
	//TODO
	public boolean isAllClean()
	{
		return false;
	}
	
	public CellData getChargingStationLocation()
	{
		CellData cell = new CellData();
		cell.setCellX(0);
		cell.setCellY(0);
		cell.setChargingStation(true);
		return cell;
	}
	
}
