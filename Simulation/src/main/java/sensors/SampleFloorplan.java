package sensors;

import java.io.IOException;
import java.util.HashMap;

import objectsDTO.CellData;
import objectsDTO.Coord;

import java.util.logging.Level;
import java.util.logging.*;

public class SampleFloorplan {
	
	Logger logger = Logger.getLogger("main");
	FloorPlan sample;
	
	public SampleFloorplan(String f) {
		try {
			sample = new FloorPlan(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public CellData getcell(int x, int y) {
		return sample.grid.get(new Coord(x, y));
	}
	
	public HashMap<Coord, CellData> getgrid() {
		return sample.grid;
	}

}
