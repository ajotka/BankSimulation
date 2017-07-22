final class Sekwencja {
	private final int pierwszy, roznica;
	private int nastepny;
	
	private boolean add(int a, int b) {
		long c = (long) a + (long) b;
		return c < 0x80000000 || c > 0x7FFFFFFF;
	}
	
	Sekwencja(final int pierwszy, final int roznica) {
		if (roznica == 0) {
			throw new IllegalArgumentException("Sekwencja - roznica rowna 0");
		}
		
		this.pierwszy = pierwszy;
		this.roznica = roznica;
		nastepny = pierwszy - roznica;
	}
	
	Sekwencja() {
		this(1, 1);
	}
	
	Sekwencja(final int pierwszy) {
		this(pierwszy, 1);
	}
	
	int pierwszy() {
		return pierwszy;
	}
	
	int roznica() {
		return roznica;
	}
	
	int nastepny() {
		if (add(nastepny, roznica)) {
			if (nastepny + roznica != pierwszy) {
				throw new IllegalStateException("Sekwencja - sekwencja zuzyta");
			}
		}
		
		nastepny += roznica;
		return nastepny;
	}
}
