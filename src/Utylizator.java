final class Utylizator {
	private int odrzucone;
	
	Utylizator() {}
	
	void zapamietaj() {
		++odrzucone;
	}
	
	int odrzucone() {
		return odrzucone;
	}
}
