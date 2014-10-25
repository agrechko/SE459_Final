package sensors;

import java.io.IOException;

import objectsDTO.CellData;
import objectsDTO.Coord;

public class SampleFloorplan {
	
	FloorPlan sample;
	
	public SampleFloorplan(String f) {
		try {
			sample = new FloorPlan(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CellData getcell(int x, int y) {
		return sample.grid.get(new Coord(x, y));
	}
}
