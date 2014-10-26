package sensors;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import objectsDTO.CellData;
import objectsDTO.Coord;
import utils.LogFactory;

public class SensorsController 
{
	public FloorPlan memory;
	private SampleFloorplan sample;
	private Logger log;

	public SensorsController(String floorPlanPath) 
	{
		try {
			memory = new FloorPlan();
			sample = new SampleFloorplan(floorPlanPath);
			log = new LogFactory().generateLog("floorplan", Level.INFO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//this method checks if the given location x and y on the sweepers has been visited
	public boolean isVisited(int x, int y)
	{
		//TODO Do not implement this method without discussion. This might be moved under robot
		return false;
	}
	
	//returns the celldata object of current cell of x and y
	public CellData getCell(int x, int y)
	{
		Coord c = new Coord(x, y);
		CellData cd = memory.grid.get(c);
		if ( cd == null ) {
			cd = new CellData(x, y);
//			floorplan.grid.put(c, cd);
//			log.info("Creating new cell object and sensing dirt and surface type");
			System.out.println("Creating new cell object and sensing dirt and surface type");
            cd = sample.getcell(x, y);
		} 
		return cd;		
	}
	
	private void memcheck(int x, int y) {
		Coord c = new Coord(x, y);
		CellData cd = memory.grid.get(c);
		if (cd == null) {
			cd = sample.getcell(x , y);
			System.out.println("Pulling data from sample.xml into memory");
			memory.grid.put(c, cd);
		}
	}
	
	
	//this takes in current cell and it returns the surrounding cells status 1: open, 2: obstical, 4: stairs  
	public int[] getPaths(int x, int y)   
	{
		memcheck(x, y);
		return memory.grid.get(new Coord(x, y)).getPaths();
		
	}
	
	//this sets the surrounding coordinates of the current location  //TODO
	private void setPaths(int x, int y, int[] paths)// Rahmo:I think we should add x and y so we can know where is the current location?
	{
		//TODO	
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
		CellData cd = memory.grid.get(new Coord(x, y));
		cd.setPaths(paths);
		
	}
	
	//this method cleans 1 unit of dirt from the given location
	public void clean(int x, int y)
	{
//		int[] xy = new int[] {x,y};
		CellData cd = memory.grid.get(new Coord(x, y));
//		cd = floorplan.grid.get(xy);
		int CurrentDirt = cd.getDirt();
		int NewDirt = CurrentDirt - 1;
		cd.setDirt(NewDirt);
		
	}
	
	//this method return if the current location is clean or not
	public boolean isClean(int x, int y) //TODO Test this
	{
		memcheck(x, y);
		Coord c = new Coord(x, y);
		CellData cd = memory.grid.get(c);
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
		return memory.grid.get(new Coord(x, y)).getSurface();
	}
	
	//this method sets the surface for current location surface value are 1: bare floor, 2: low carpet, 3: high carpet
	public void setSurface(int x, int y, int surfaceValue)
	{
//		int[] xy = new int[] {x,y};
//		CellData cd = new CellData();
		CellData cd = memory.grid.get(new Coord(x, y));
		cd.setSurface(surfaceValue);
		
	}
	

	public boolean isAllClean() {
		return false;
//          Boolean Status = false; 
//			
//			 for(Coord xy: floorplan.grid.keySet()){
//				CellData cd = floorplan.grid.get(xy);
////				cd = block.getValue();
//				int CurrentDirt = cd.getDirt();
//				if (CurrentDirt == 0 ){
//					Status= true;
//				   }
//				else {
//					Status = false; 
//				     }
//		        }
//		return Status;
	}

	public CellData getChargingStationLocation() {
		CellData cd = null;
		for(Coord xy: memory.grid.keySet()) {
			cd = memory.grid.get(xy);
			if (cd.isChargingStation() == true) {
//				System.out.println(cd);
				return cd;
			} 
		}
		return null;
	}
}
