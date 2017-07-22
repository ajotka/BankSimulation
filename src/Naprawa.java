import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimControlException;

final class Naprawa extends BasicSimEvent<Smo, Zgloszenie> {
	private final Smo smoMatka;
	private int nrOkienka;
	
	Naprawa(Smo parent, double odstep, int nrOkienka) throws SimControlException {
		super(parent, odstep);
		this.smoMatka = parent;
		this.nrOkienka = nrOkienka;
	}
	
	Naprawa(Smo smo, SimEventSemaphore semafor) throws SimControlException {
		super(smo, semafor);
		this.smoMatka = smo;
	}
	
	protected void stateChange() throws SimControlException {
		
			smoMatka.zwolnijZablokuj(true, nrOkienka); 
			System.out.printf("%10.3f: Naprawa okienka %d\n", simTime(), nrOkienka+1);
		
		if (smoMatka.stan() > 0) {
			smoMatka.obslugaPoczatek = new ObslugaPoczatek(smoMatka, nrOkienka);
		}
	}
	
	public Object getEventParams() {
		return null;
	}
	
	protected void onInterruption() throws SimControlException {}
	
	protected void onTermination() throws SimControlException {}
}
