import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.random.SimGenerator;

public class GeneratorAwarii extends BasicSimEvent<Otoczenie, Object> {
	private final SimGenerator simGenerator;
	private Otoczenie otoczenieMatka;
	
	public GeneratorAwarii(Otoczenie otoczenieMatka, double czasAwarii) throws SimControlException {
		super(otoczenieMatka, czasAwarii);
		simGenerator = new SimGenerator();
	}
	
	protected void stateChange() throws SimControlException {
		otoczenieMatka = getSimObj();
		
		int nrOkienka = Math.abs(simGenerator.nextInt(otoczenieMatka.smo.ileOkienek));
		double czasNaprawy = simGenerator.normal(5.0, 0.1);
		
		if (otoczenieMatka.smo.okienkoWolne(nrOkienka) == false) {
			
			System.out.printf("%10.3f: AWARIA okienka %d - Klient odchodzi od okienka\n", simTime(), nrOkienka+1);
			Zgloszenie z = otoczenieMatka.smo.usun();
			otoczenieMatka.smo.obslugaKoniec = new ObslugaKoniec(otoczenieMatka.smo, 0, z, nrOkienka, true);
			
			otoczenieMatka.smo.naprawaAwarii = new Naprawa(otoczenieMatka.smo, czasNaprawy, nrOkienka);
			
		} else if (otoczenieMatka.smo.okienkoWolne(nrOkienka) == true) {
			
			System.out.printf("%10.3f: AWARIA okienka %d - Brak klientow\n", simTime(), nrOkienka+1);
			otoczenieMatka.smo.zwolnijZablokuj(false, nrOkienka);

			otoczenieMatka.smo.naprawaAwarii = new Naprawa(otoczenieMatka.smo, czasNaprawy, nrOkienka);
		}

		double czasAwarii = simGenerator.normal(5.0, 0.1);
		setRepetitionPeriod(czasAwarii);
	}
	
	public Object getEventParams() {
		return null;
	}
	
	protected void onInterruption() throws SimControlException {}
	
	protected void onTermination() throws SimControlException {}

}
