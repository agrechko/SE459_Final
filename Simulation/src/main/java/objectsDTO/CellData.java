package objectsDTO;

public class CellData 
{
	int surface;
	int dirt;
	int[] paths;
	Boolean  chargingStation;
	int cellX;
	int cellY;
	
	public CellData(int x, int y) {
		cellX = x;
		cellY = y;
		surface = -1;
		dirt = -1;
		paths = null;
		chargingStation = null;
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
		this.paths = paths;
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
		sb.append("x: " + x);
		sb.append("y: " + y);
		sb.append("ss: " + surface);
		sb.append("ds: " + dirt);
		return sb.toString();
	}

	public boolean equals(Object thatObject) {
		// TODO

		if (!(this.getClass().equals(thatObject.getClass()))) {
			// or if ( !(thatObject instanceof VideoObj) )
			return false;
		}
		CellData that = (CellData) thatObject;
		return ( this.cellX == that.cellX
				&& this.cellY==that.cellY    
				&& this.dirt== that.dirt 
				&& this.paths==that.paths
				&& this.surface==that.surface	  
				);
	}

}
