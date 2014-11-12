package objectsDTO;

public class Coord {
	int x;
	int y;
	
	public Coord(int a, int b) {
	    	x = a;
	    	y = b;
	}
	
	public Coord() { }
	
	public void setx(int a) {
		x = a;
	}

	public void sety(int b) {
		y = b;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public boolean equals(Object c) {
		if (c == null) {
		    return false;	
		} else {
			Coord temp = (Coord) c;
			if ((this.x == temp.x) && (this.y == temp.y)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public int hashCode() {
		return ((17 * 31 + x) * 31) + y;
	}
}
