import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.broker.IPublisher;
import dissimlab.broker.INotificationEvent;
import java.util.Comparator;

final class Zgloszenie extends BasicSimObj {
	private final int numer;
	private final double czasNadejscia;
	private final int priorytet;
	private final OkresNiecierpliwosciPoczatek okresNiecierpliwosciPoczatek;
	OkresNiecierpliwosciKoniec okresNiecierpliwosciKoniec;
	final Smo smo;
	
	Zgloszenie(final int numer, final double czasNadejscia, final int priorytet, final Smo smo) throws SimControlException {
		if (czasNadejscia < 0.0) {
			throw new IllegalArgumentException("Zgloszenie - czas nadejscia mniejszy niz 0");
		}
		
		if (priorytet < 1 || priorytet > 10) {
			throw new IllegalArgumentException("Zgloszenie - priorytet spoza zakresu");
		}
		
		if (smo == null) {
			throw new IllegalArgumentException("Zgloszenie - smo rowne null");
		}
		
		this.numer = numer;
		this.czasNadejscia = czasNadejscia;
		this.priorytet = priorytet;
		okresNiecierpliwosciPoczatek = new OkresNiecierpliwosciPoczatek(this);
		this.smo = smo;
	}
	
	static Comparator<Zgloszenie> komparatorFifo() {
		return (z1, z2) -> {
			if (z1.priorytet < z2.priorytet || z1.priorytet == z2.priorytet && z1.numer > z2.numer) {
				return +1;
			}
			else if (z1.priorytet == z2.priorytet && z1.numer == z2.numer) {
				return 0;
			}
			else {
				return -1;
			}
		};
	}
	
	public boolean filter(IPublisher ip, INotificationEvent ine) {
		return false;
	}
	
	public void reflect(IPublisher ip, INotificationEvent ine) {}
	
	int numer() {
		return numer;
	}
	
	double czasNadejscia() {
		return czasNadejscia;
	}
	
	int priorytet() {
		return priorytet;
	}
}
