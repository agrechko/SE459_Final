package objectsdto;

public class CellData {
    int surface = -1;
    int dirt = -1;
    int[] paths = null;
    Boolean chargingStation = null;
    int cellX = -1;
    int cellY = -1;

    public CellData() {
    }

    public CellData(final int x, final int y) {
        cellX = x;
        cellY = y;
    }

    public final int getCellX() {
        return cellX;
    }

    public final void setCellX(final int cellX) {
        this.cellX = cellX;
    }

    public final int getCellY() {
        return cellY;
    }

    public final void setCellY(final int cellY) {
        this.cellY = cellY;
    }

    public final int getSurface() {
        return surface;
    }

    public final void setSurface(final int surface) {
        this.surface = surface;
    }

    public final int getDirt() {
        return dirt;
    }

    public final void setDirt(final int dirt) {
        this.dirt = dirt;
    }

    public final int[] getPaths() {
        return paths;
    }

    public final void setPaths(final int[] paths) {
        int[] temp = paths.clone();
        this.paths = temp;
    }

    public final boolean isChargingStation() {
        return chargingStation;
    }

    public final void setChargingStation(final boolean chargingStation) {
        this.chargingStation = chargingStation;
    }

    public final String toString() {
        int x = getCellX();
        int y = getCellY();
        // String s = "x:" + x + " " + " Y: " + y;
        StringBuilder sb = new StringBuilder();
        sb.append("x: " + x + " ");
        sb.append("y: " + y + " ");
        sb.append("ss: " + surface + " ");
        sb.append("ds: " + dirt + " ");
        StringBuilder s = new StringBuilder();
        for ( int i: paths) {
        	s.append(i);
        }
        sb.append("ps: " + s);

        return sb.toString();
    }

    public final boolean equals(final Object thatObject) {

        if (!(this.getClass().equals(thatObject.getClass()))) {
            return false;
        }
        CellData that = (CellData) thatObject;
        for (int i = 0; i < 4; i++) {
            if (this.paths[i] != that.paths[i]) {
                return false;
            }
        }

        return (this.cellX == that.cellX && this.cellY == that.cellY
                && this.dirt == that.dirt && this.surface == that.surface && this
                    .isChargingStation() == that.isChargingStation());
    }

    public final int hashCode() {
        return ((83 * 31 + cellX) * 31) + cellY;
    }

}
