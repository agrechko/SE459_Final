package objectsdto;

public class CellData {
	int surface = -1;
	int dirt= -1;
	int[] paths = null;
	Boolean  chargingStation = null;
	int cellX = -1;
	int cellY = -1;
	
	public CellData() { }

	public CellData(int x, int y) {
		cellX = x;
		cellY = y;
	}

	public int getCellX() {
		return cellX;
	}
	public void setCellX(int cellX) {
		this.cellX = cellX;
	}
	public int getCellY() {
		return cellY;
	}
	public void setCellY(int cellY) {
		this.cellY = cellY;
	}
	public int getSurface() {
		return surface;
	}
	public void setSurface(int surface) {
		this.surface = surface;
	}
	public int getDirt() {
		return dirt;
	}
	public void setDirt(int dirt) {
		this.dirt = dirt;
	}
	public int[] getPaths() {
		return paths;
	}
	public void setPaths(int[] paths) {
		int[] temp = paths.clone();
		this.paths = temp;
	}
	public boolean isChargingStation() {
		return chargingStation;
	}
	public void setChargingStation(boolean chargingStation) {
		this.chargingStation = chargingStation;
	}

	public String toString() {
		int x = getCellX();
		int y = getCellY();
//		String s = "x:" + x + " " + " Y: " + y;
		StringBuilder sb = new StringBuilder();
		sb.append("x: " + x + " ");
		sb.append("y: " + y + " ");
		sb.append("ss: " + surface + " ");
		sb.append("ds: " + dirt);
		sb.append("ps: " + paths);
		
		return sb.toString();
	}

	public boolean equals(Object thatObject) {

		if (!(this.getClass().equals(thatObject.getClass()))) {
			return false;
		}
		CellData that = (CellData) thatObject;
		for (int i=0; i < 4; i++) {
			if (this.paths[i] != that.paths[i]) {
				return false;
			}
		}
		
		return ( this.cellX == that.cellX
				&& this.cellY==that.cellY    
				&& this.dirt== that.dirt 				
				&& this.surface==that.surface
				&& this.isChargingStation() == that.isChargingStation() 
				);
	}

	public int hashCode() {
		return ((83 * 31 + cellX) * 31) + cellY;
	}

}
