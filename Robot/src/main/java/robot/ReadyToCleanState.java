package robot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import objectsdto.CellData;
import robot.RobotController.State;

public class ReadyToCleanState implements RobotStates {
    /**
     * Checks the preconditions for successful clean cycle. If not met then the
     * state becomes stop
     */
    public final void execute(final RobotController robot) {
        // if dirt capacity is <= 0 it means we do not have free space to put
        // dirt
        // if current power is less than 2 it means that we do not have enough
        // power to go half way and back
        // if sensors are null we cannot see the environment
        // if dirt controller is null we cannot clean dirt
        if (robot.getCurrentDirtCapacity() <= 0 || robot.currentPower <= 1
                || robot.sensors == null) {
            robot.currentState = State.STOP.getValue();
        } else {
            // if(!robot.firstStart && !robot.sensors.isAllClean()) {
            // findClosestDirt(robot);
            // }
            robot.currentState = State.EXPLORING.getValue();
        }
    }

    private void findClosestDirt(final RobotController robot) {
        LinkedList<CellData> queque = new LinkedList<CellData>();
        Stack<CellData> dirtPath = new Stack<CellData>();

        // string coordinates of the current cell and the celldata of the parent
        // cell
        HashMap<String, CellData> parent = new HashMap<String, CellData>();
        // key visited cell, value is visited by
        HashMap<String, String> visited = new HashMap<String, String>();
        // string current cell reverse direction pointing to parent which leads
        // to home
        HashMap<String, Integer> directionToParent = new HashMap<String, Integer>();
        boolean found = false;
        CellData cur = null;
        CellData start = robot.sensors.getCell(0, 0);
        queque.add(start);
        parent.put(start.getCellX() + "," + start.getCellY(), null);
        visited.put(start.getCellX() + "," + start.getCellY(), null);

        while (!queque.isEmpty() && !found) {
            cur = queque.poll();
            if (!robot.sensors.isClean(cur.getCellX(), cur.getCellY())) {
                found = true;
                continue;
            }

            int[] paths = robot.sensors
                    .getPaths(cur.getCellX(), cur.getCellY());
            int pathSize = paths.length;
            for (int i = 0; i < pathSize; i++) {
                if (paths[i] == 1) {
                    CellData cell = lookForCellInMemory(robot, cur, i);
                    if (cell != null) {
                        if (!visited.containsKey(cell.getCellX() + ","
                                + cell.getCellY())) {
                            queque.add(cell);
                            parent.put(cell.getCellX() + "," + cell.getCellY(),
                                    cur);
                            visited.put(
                                    cell.getCellX() + "," + cell.getCellY(),
                                    cur.getCellX() + "," + cur.getCellY());
                            directionToParent.put(
                                    cell.getCellX() + "," + cell.getCellY(), i);
                        }
                    }
                }
            }
        }

        robot.pathToDirtyCell = getDirtPathForCell(robot, parent,
                directionToParent, cur);
    }

    // finds the path to the dirty cell
    private LinkedList<Integer> getDirtPathForCell(final RobotController robot,
            final HashMap<String, CellData> parent,
            final HashMap<String, Integer> directionToParent,
            final CellData cell) {
        LinkedList<Integer> dirtPath = new LinkedList<Integer>();

        CellData cur = cell;

        while (parent.get(cur.getCellX() + "," + cur.getCellY()) != null) {
            dirtPath.add(directionToParent.get(cur.getCellX() + ","
                    + cur.getCellY()));
            cur = parent.get(cur.getCellX() + "," + cur.getCellY());
        }
        // removes the last step because exploring logic should pick it up from
        // there
        dirtPath.pollLast();
        return dirtPath;
    }

    // looks for the correct cell data based on paths array index
    private CellData lookForCellInMemory(final RobotController robot,
            final CellData cur, final int i) {
        CellData cell = null;
        switch (i) {
        case 0:
            cell = robot.sensors.getCellMemory(cur.getCellX() + 1,
                    cur.getCellY());
            break;
        case 1:
            cell = robot.sensors.getCellMemory(cur.getCellX() - 1,
                    cur.getCellY());
            break;
        case 2:
            cell = robot.sensors.getCellMemory(cur.getCellX(),
                    cur.getCellY() + 1);
            break;
        case 3:
            cell = robot.sensors.getCellMemory(cur.getCellX(),
                    cur.getCellY() - 1);
            break;
        default:
            throw new IllegalStateException();
        }
        return cell;
    }
}
