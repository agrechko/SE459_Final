package objectsDTO;

public class CellData 
{
	int surface;
	int dirt;
	int[] paths;
	boolean chargingStation;
	int cellX;
	int cellY;

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
}
