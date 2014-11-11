package sensors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import objectsDTO.CellData;
import objectsDTO.Coord;

import java.util.logging.*;

public class SampleFloorplan {
	
	Logger logger = Logger.getLogger("main");
	FloorPlan sample;
	
	public SampleFloorplan(String f) {
		try {
			sample = new FloorPlan(f);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reading sameple floorplan xml file: " + e.toString());
		}
	}

	public CellData getcell(int x, int y) {
		return sample.get(new Coord(x, y));
	}

	public Set<Coord> sampleSet() {
		return sample.gridSet();
	}
	
}
