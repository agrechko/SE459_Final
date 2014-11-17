package objectsdto;

public class Coord {
    int x;
    int y;

    public Coord(final int a, final int b) {
        x = a;
        y = b;
    }

    public Coord() {
    }

    public final void setx(final int a) {
        x = a;
    }

    public final void sety(final int b) {
        y = b;
    }

    public final int getx() {
        return x;
    }

    public final int gety() {
        return y;
    }

    public final boolean equals(final Object c) {
        return !(c == null);
    }

    public final int hashCode() {
    	int s = 17;
    	int a = (31 * x) + x + 11;
    	int b = (41 * y) + y + 29;
        return (s + a + b);	
    }
}
