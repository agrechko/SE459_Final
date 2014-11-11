package utils;

public enum Path {
	
	UNKNOWN(0), OPEN(1), OBSTACLE(2), STAIRS(4);
	private int value;
	
	private Path(int value) {
		this.value = value;
	}
	
	public static void main(String arg[]) {
		Path p = Path.OBSTACLE;
		System.out.println(p);
		if (p.value == 2) {
			System.out.println(p + " = 2");
		}
	}
}



