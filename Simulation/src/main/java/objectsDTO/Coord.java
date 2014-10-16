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

	public boolean equals(Coord c) {
		if (c == null) {
		    return false;	
		} else if ((this.x == c.x) && (this.y == c.y)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int hashcode() {
		return ((17 * 31 + x) * 31) + y;
	}
}
