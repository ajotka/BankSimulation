final class Zegar {
	private final long start;
	
	Zegar() {
		start = System.currentTimeMillis();
	}
	
	double czas() {
		return (System.currentTimeMillis() - start) / 1000.0;
	}
}
