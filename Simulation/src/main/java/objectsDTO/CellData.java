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
	
	  public String toString() {
		    // TODO
		
		  int x = getCellX();
		  
				  int y = getCellY();
			  String s = "x:"+x+"\n"+"Y:"+y;
			    return s;
		  }
	  public boolean equals(Object thatObject) {
		    // TODO
			  
			   if (!(this.getClass().equals(thatObject.getClass())))
			 // or if ( !(thatObject instanceof VideoObj) )
				  return false;
			  CellData that = (CellData) thatObject;
			  return ( this.cellX == that.cellX
					  && this.cellY==that.cellY    
					  && this.dirt== that.dirt 
					  && this.paths==that.paths
					  && this.surface==that.surface	  
							  );
		  }

}
