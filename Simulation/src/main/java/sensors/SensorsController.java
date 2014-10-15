package sensors;

import java.io.IOException;
import java.util.Map;
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
		int[] coordinator = new int[] {x,y};
		CellData cd = new CellData();
		cd = floorplan.grid.get(coordinator);
		return cd.getPaths();   //TODO Need to make sure of the principle of this...
		
//		
//		Random gen = new Random();
//		int i = gen.nextInt(4);
//		if(i == 0)
//			return new int[]{1,2,2,2};
//		else if(i == 1)
//			return new int[]{2,1,2,2};
//		else if(i == 2)
//			return new int[]{2,2,1,2};
//		else
//			return new int[]{2,2,2,1};
		
	}
	
	//this sets the surrounding coordinates of the current location  //TODO
	private void setPaths(int[] paths)  // I add x and y so I can know where is the current location?
	{
	//TODO	
		
	}
	
	//this method cleans 1 unit of dirt from the given location
	public void clean(int x, int y)
	{
		int[] coordinator = new int[] {x,y};
		CellData cd = new CellData();
		cd = floorplan.grid.get(coordinator);
		 int CurrentDirt = cd.getDirt();
		 int NewDirt = CurrentDirt - 1;
		 cd.setDirt(NewDirt);
		
	}
	
	//this method return if the current location is clean or not
	public boolean isClean(int x, int y)
	{
		int[] coordinator = new int[] {x,y};
		CellData cd = new CellData();
		cd = floorplan.grid.get(coordinator);
		int CurrentDirt = cd.getDirt();
		if (CurrentDirt == 0 ){
		return true;
		}
		else {
			return false; 
		}
	}
	
	//this method returns an int 1: bare floor, 2: low carpet, 4: high carpet
	public int getSurface(int x, int y)
	{
		int[] coordinator = new int[] {x,y};
		CellData cd = new CellData();
		cd = floorplan.grid.get(coordinator);
		return cd.getSurface();
	}
	
	//this method sets the surface for current location surface value are 1: bare floor, 2: low carpet, 3: high carpet
	public void setSurface(int x, int y, int surfaceValue)
	{
		
		int[] coordinator = new int[] {x,y};
		CellData cd = new CellData();
		cd = floorplan.grid.get(coordinator);
		cd.setSurface(surfaceValue);
		
	}
	

	public boolean isAllClean()
	{
          Boolean Status = false; 
			
			 for(Map.Entry<int[], CellData> block  : floorplan.grid.entrySet()){
				CellData cd = new CellData ();
				cd = block.getValue();
				
				int CurrentDirt = cd.getDirt();
				if (CurrentDirt == 0 ){
					Status= true;
				   }
				else {
					Status = false; 
				     }
		        }
	return Status;
}
		//TODO
	public CellData getChargingStationLocation()
	{
		CellData cd = new CellData();
		cd.setCellX(0);
		cd.setCellY(0);
		cd.setChargingStation(true);
		return cd;
	}
	
}
