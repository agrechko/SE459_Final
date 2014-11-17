package sensors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamException;

import objectsdto.CellData;
import objectsdto.Coord;

public class SampleFloorplan {

    Logger logger = Logger.getLogger("main");
    FloorPlan sample;

    public SampleFloorplan(final String f) {
        try {
            sample = new FloorPlan(f);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Reading sameple floorplan xml file: ", e);
        }
    }

    public final CellData getcell(final int x, final int y) {
        return sample.get(new Coord(x, y));
    }

    public final Set<Coord> sampleSet() {
        return sample.gridSet();
    }

    public final void read() {
        try {
            sample.read();
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.log(Level.SEVERE, "Reading sameple floorplan xml file: ", e);
        }

    }

}
