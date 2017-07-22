import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimControlException;

final class ObslugaKoniec extends BasicSimEvent<Smo, Zgloszenie> {
	private final Smo smoMatka;
	private int nrOkienka;
	private boolean czyAwaria;
	
	ObslugaKoniec(Smo parent, double odstep, Zgloszenie zgloszenie, int nrOkienka, boolean czyAwaria) throws SimControlException {
		super(parent, odstep, zgloszenie);
		this.smoMatka = parent;
		this.nrOkienka = nrOkienka;
		this.czyAwaria = czyAwaria;
	}
	
	ObslugaKoniec(Smo smo, SimEventSemaphore semafor, Zgloszenie zgloszenie) throws SimControlException {
		super(smo, semafor, zgloszenie);
		this.smoMatka = smo;
	}
	
	protected void stateChange() throws SimControlException {
		if (czyAwaria == true) {

			System.out.printf("%10.3f: Klient numer %d wrocil do kolejki (priorytet rowny 10)\n", simTime(), transitionParams.numer());
			Zgloszenie	z = new Zgloszenie(transitionParams.numer(), simTime(), 10,	this.smoMatka);
			if(!Smo.kolejkaPelna()){
				Smo.wstaw(z);
			}
			
		} else if (czyAwaria == false) {
			smoMatka.zwolnijZablokuj(true, nrOkienka);
			SimGenerator generator = new SimGenerator();
			double pstwo = Math.abs(generator.nextDouble());
			//double pstwo = Math.random();
			if(pstwo <= 0.5){
				System.out.printf("%10.3f: Klient numer %d obsluzony - wyszedl\n", simTime(), transitionParams.numer());
			} else {
				System.out.printf("%10.3f: Klient numer %d obsluzony - wrocil do kolejki (priorytet rowny 10)\n", simTime(), transitionParams.numer());
				Zgloszenie	z = new Zgloszenie(transitionParams.numer(), simTime(), 10,	this.smoMatka);
				if(!Smo.kolejkaPelna()){
					Smo.wstaw(z);
				}
			} 
		}
		
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
