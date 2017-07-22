import java.util.PriorityQueue;

import java.util.Comparator;

final class Kolejka implements KolejkaI {
	private final PriorityQueue<Zgloszenie> bufor;
	private final static Comparator<Zgloszenie> komparator;
	private final int dlugosc;
	
	static KolejkaI stworz(final String typKolejki, final int dlugosc) {
		return new Kolejka(dlugosc);
	}
	
	static {
		komparator = Zgloszenie.komparatorFifo();
	}
	
	Kolejka(final int dlugosc) {
		if (dlugosc <= 0) {
			throw new IllegalArgumentException("Dlugosc mniejsza niz 1");
		}
		
		bufor = new PriorityQueue<>(dlugosc, komparator);
		this.dlugosc = dlugosc;
	}
	
	public int dlugosc() {
		return dlugosc;
	}
	
	public boolean kolejkaPelna() {
		return bufor.size() == dlugosc;
	}
	
	public boolean kolejkaPusta() {
		return bufor.isEmpty();
	}
	
	public int stan() {
		return bufor.size();
	}
	
	public void wstaw(final Zgloszenie zgloszenie) {
		if (kolejkaPelna()) {
			throw new IllegalStateException("Kolejka pelna");
		} else
		
		bufor.add(zgloszenie);
	}
	
	public Zgloszenie nastepne() {
		if (kolejkaPusta()) {
			System.out.println("Kolejka pusta");
			Symulacja.model.stopSimulation();
		}
		
		return bufor.peek();
	}
	
	public Zgloszenie usun() {
		if (kolejkaPusta()) {
			System.out.println("Kolejka pusta");
			Symulacja.model.stopSimulation();
		}
		
		return bufor.poll();
	}
	
	public void usunWybrane(final Zgloszenie zgloszenie) {
		if (kolejkaPusta()) {
			System.out.println("Kolejka pusta - brak klientow na dzis");
			Symulacja.model.stopSimulation();
		} else
		
		bufor.remove(zgloszenie);
	}
}
