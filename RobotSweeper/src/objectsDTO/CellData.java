package objectsDTO;

public class CellData 
{
	int surface;
	int dirt;
	int[] paths;
	boolean chargingStation;

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
