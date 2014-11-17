package sensors;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import objectsdto.CellData;
import objectsdto.Coord;

public class SensorsController {
    public FloorPlan memory;
    private SampleFloorplan sample;
    Logger logger = Logger.getLogger("main");

    public SensorsController(final String floorPlanPath) {
        try {
            memory = new FloorPlan();
            sample = new SampleFloorplan(floorPlanPath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in sensor setup: ", e);
        }
    }

    public final void setup() {
        sample.read();
    }

    public final void PrintMemFloorplan() {
        memory.write();
    }

    // this method checks if the given location x and y on the sweepers has been
    // visited
    public final boolean isVisited(final int x, final int y) {
        // TODO Do not implement this method without discussion. This might be
        // moved under robot
        return false;
    }

    // returns the celldata object of current cell of x and y
    public final CellData getCell(final int x, final int y) {
        Coord c = new Coord(x, y);
        CellData cd = memory.get(c);
        if (cd == null) {
            cd = new CellData(x, y);
            // log.info("Creating new cell object and sensing dirt and surface type");
            // System.out.println("Creating new cell object and sensing dirt and surface type");
            cd = sample.getcell(x, y);
            memory.put(c, cd);
        }
        return cd;
    }

    private void memcheck(final int x, final int y) {
        Coord c = new Coord(x, y);
        CellData cd = memory.get(c);
        if (cd == null) {
            cd = sample.getcell(x, y);
            logger.log(Level.FINE, "Pulling data from sample.xml into memory");
            memory.put(c, cd);
        }
    }

    // this takes in current cell and it returns the surrounding cells status 1:
    // open, 2: obstical, 4: stairs
    public final int[] getPaths(final int x, final int y) {
        memcheck(x, y);
        return memory.get(new Coord(x, y)).getPaths();

    }

    // this sets the surrounding coordinates of the current location
    private void setPaths(final int x, final int y, final int[] paths) {
        CellData cd = memory.get(new Coord(x, y));
        cd.setPaths(paths);
    }

    // this method cleans 1 unit of dirt from the given location
    public final void clean(final int x, final int y) {
        // memcheck(x, y);
        CellData cd = memory.get(new Coord(x, y));
        int CurrentDirt = cd.getDirt();
        int NewDirt = CurrentDirt - 1;
        cd.setDirt(NewDirt);

    }

    // this method return if the current location is clean or not
    public final boolean isClean(final int x, final int y) {
        memcheck(x, y);
        Coord c = new Coord(x, y);
        CellData cd = memory.get(c);
        int CurrentDirt = cd.getDirt();
        return CurrentDirt == 0;
    }

    // this method returns an int 1: bare floor, 2: low carpet, 4: high carpet
    public final int getSurface(final int x, final int y) {

        return memory.get(new Coord(x, y)).getSurface();
    }

    // this method sets the surface for current location surface value are 1:
    // bare floor, 2: low carpet, 3: high carpet
    public final void setSurface(final int x, final int y,
            final int surfaceValue) {

        CellData cd = memory.get(new Coord(x, y));
        cd.setSurface(surfaceValue);

    }

    public final boolean isAllClean() {
        for (Coord s : sample.sampleSet()) {
            CellData cd = memory.get(s);
            if (cd == null) {
                return false;
            } else if (cd.getDirt() > 0) {
                return false;
            } else {
            }
        }
        return true;
    }

    public final CellData getChargingStationLocation() {
        CellData cd = null;
        for (Coord xy : memory.gridSet()) {
            cd = memory.get(xy);
            if (cd.isChargingStation() == true) {

                return cd;
            }
        }
        return null;
    }

    // only knows about information in memory
    public final CellData getCellMemory(final int x, final int y) {
        Coord c = new Coord(x, y);
        CellData cd = memory.get(c);
        return cd;
    }
}
