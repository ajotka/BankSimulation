import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.random.SimGenerator;

class ObslugaPoczatek extends BasicSimEvent<Smo, Zgloszenie> {
	private final Smo smoMatka;
	private final SimGenerator generator;
	private int nrOkienka;
	
	ObslugaPoczatek(Smo smoMatka, double odstep, int nrOkienka) throws SimControlException {
		super(smoMatka, odstep);
		this.smoMatka = smoMatka;
		generator = new SimGenerator();
		this.nrOkienka = nrOkienka;
	}
	
	ObslugaPoczatek(Smo smoMatka, int nrOkienka) throws SimControlException {
		super(smoMatka);
		this.smoMatka = smoMatka;
		generator = new SimGenerator();
		this.nrOkienka = nrOkienka;
	}
	
	protected void stateChange() throws SimControlException {
		if (smoMatka.stan() > 0) {
			smoMatka.zwolnijZablokuj(false, nrOkienka);
			Zgloszenie z = smoMatka.usun();
			//z.okresNiecierpliwosciKoniec.interrupt();
			double czasObslugi = generator.normal(9.0, 1.0);
			System.out.printf("%10.3f: Klient numer %d podszedl do okienka %d(priorytet rowny %d)\n",
				simTime(), z.numer(), nrOkienka+1, z.priorytet());
			
			smoMatka.MVczasy_obslugi.setValue(czasObslugi);
			smoMatka.obslugaKoniec = new ObslugaKoniec(smoMatka, czasObslugi, z, nrOkienka, false);
		}
	}
	
	public Object getEventParams() {
		return null;
	}
	
	protected void onInterruption() throws SimControlException {}
	
	protected void onTermination() throws SimControlException {}
}
