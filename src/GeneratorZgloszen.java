import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.random.SimGenerator;
import java.util.Random;

public class GeneratorZgloszen extends BasicSimEvent<Otoczenie, Object> {
	private final Sekwencja numery;
	private final Random generatorPriorytetow;
	private final SimGenerator simGenerator;
	private Otoczenie otoczenieMatka;
	
	public GeneratorZgloszen(Otoczenie otoczenieMatka, double odstep) throws SimControlException {
		super(otoczenieMatka, odstep);
		numery = new Sekwencja();
		generatorPriorytetow = new Random();
		simGenerator = new SimGenerator();
	}
	
	protected void stateChange() throws SimControlException {
		otoczenieMatka = getSimObj();
		
		if (Smo.kolejkaPelna()) {
			System.out.printf("Kolejka pelna - nowy klient odrzucony\n");
			
			return;
		}
		Zgloszenie	z = new Zgloszenie(numery.nastepny(), simTime(), generatorPriorytetow.nextInt(10) + 1,
			otoczenieMatka.smo);
		
		Smo.wstaw(z);
		System.out.printf("%10.3f: Klient numer %d dodany do kolejki (priorytet rowny %d)\n",
			simTime(), z.numer(), z.priorytet());
		
		int nrOkienka = Math.abs(simGenerator.nextInt(otoczenieMatka.smo.ileOkienek));
		//System.out.println(nrOkienka);
		
		if (otoczenieMatka.smo.stan() == 1) {
			if ( otoczenieMatka.smo.okienkoWolne(nrOkienka) ) {
				otoczenieMatka.smo.obslugaPoczatek = new ObslugaPoczatek(otoczenieMatka.smo, nrOkienka);
			}
		}
		
		double odstep = simGenerator.normal(1.0, 0.1);
		otoczenieMatka.smo.MVczasy_oczekiwania.setValue(odstep);
		setRepetitionPeriod(odstep);
	}
	
	public Object getEventParams() {
		return null;
	}
	
	protected void onInterruption() throws SimControlException {}
	
	protected void onTermination() throws SimControlException {}

}
