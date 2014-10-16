package sensors;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import objectsDTO.CellData;
import objectsDTO.Coord;

public class SensorsController 
{
	FloorPlan floorplan;
	public SensorsController(String floorPlanPath) throws IOException  
	{
		floorplan = new FloorPlan(floorPlanPath);
	}
	
	//returns the celldata object of current cell of x and y
	public CellData getCell(int x, int y)
	{
		return floorplan.grid.get(new Coord(x, y));
	}
	
	//this takes in current cell and it returns the surrounding cells status 1: open, 2: obstical, 4: stairs  
	public int[] getPaths(int x, int y)   
	{
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
//		cd = floorplan.grid.get(xy);
//		return cd.getPaths();   //TODO Test this
		return floorplan.grid.get(new Coord(x, y)).getPaths();
		
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
	private void setPaths(int x, int y, int[] paths)// Rahmo:I think we should add x and y so we can know where is the current location?
	{
		//TODO	
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
		CellData cd = floorplan.grid.get(new Coord(x, y));
		cd.setPaths(paths);
		
	}
	
	//this method cleans 1 unit of dirt from the given location
	public void clean(int x, int y)
	{
//		int[] xy = new int[] {x,y};
		CellData cd = floorplan.grid.get(new Coord(x, y));
//		cd = floorplan.grid.get(xy);
		int CurrentDirt = cd.getDirt();
		int NewDirt = CurrentDirt - 1;
		cd.setDirt(NewDirt);
		
	}
	
	//this method return if the current location is clean or not
	public boolean isClean(int x, int y) //TODO Test this
	{
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
		CellData cd = floorplan.grid.get(new Coord(x, y));
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
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
//		cd = floorplan.grid.get(new Coord(x, y));
		return floorplan.grid.get(new Coord(x, y)).getSurface();
	}
	
	//this method sets the surface for current location surface value are 1: bare floor, 2: low carpet, 3: high carpet
	public void setSurface(int x, int y, int surfaceValue)
	{
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
		CellData cd = floorplan.grid.get(new Coord(x, y));
		cd.setSurface(surfaceValue);
		
	}
	

	public boolean isAllClean()
	{
          Boolean Status = false; 
			
			 for(Coord xy: floorplan.grid.keySet()){
				CellData cd = floorplan.grid.get(xy);
//				cd = block.getValue();
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

	public CellData getChargingStationLocation() {
		CellData cd = new CellData ();
		for(Coord xy: floorplan.grid.keySet()){
			cd = floorplan.grid.get(xy);
			if (cd.isChargingStation() == true) 
			 {
				return cd;
			  } 
			}
			return cd;
	}
}
