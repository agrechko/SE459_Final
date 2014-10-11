package sensors;

import java.util.ArrayList;

import objectsDTO.CellData;

public class FloorPlan 
{
	ArrayList<ArrayList<CellData>> grid;//the full floor plan grid that is read in from the xml
	ArrayList<ArrayList<CellData>> sweeperMemoryGrid;//the floor plan the sweeper encounters
	
	//floorPlanLocation is the physical drive location
	public FloorPlan(String floorPlanLocation)
	{
		grid = new ArrayList<ArrayList<CellData>>();
	}
	
	//write arraylist to xml to file
	public void writeFloorPlan(ArrayList<ArrayList<CellData>> grid)
	{
		
	}
	
	//prints arraylist to console
	public void printFloorPlan(ArrayList<ArrayList<CellData>> grid)
	{
		
	}
}
