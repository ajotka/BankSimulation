import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.random.SimGenerator;

final class OkresNiecierpliwosciPoczatek extends BasicSimEvent<Zgloszenie, Object> {
	private Zgloszenie zgloszenieMatka;
	private SimGenerator generator;
	
	OkresNiecierpliwosciPoczatek(Zgloszenie zgloszenieMatka, double odstep) throws SimControlException {
		super(zgloszenieMatka, odstep);
		this.zgloszenieMatka = zgloszenieMatka;
		generator = new SimGenerator();
	}
	
	OkresNiecierpliwosciPoczatek(Zgloszenie zgloszenieMatka) throws SimControlException {
		super(zgloszenieMatka);
		this.zgloszenieMatka = zgloszenieMatka;
		generator = new SimGenerator();
	}
	
	protected void stateChange() throws SimControlException {
		System.out.printf("%10.3f: Klient numer %d sie niecierpliwi (priorytet rowny %d)\n",
			simTime(), zgloszenieMatka.numer(), zgloszenieMatka.priorytet());
		
		zgloszenieMatka.okresNiecierpliwosciKoniec = new OkresNiecierpliwosciKoniec(zgloszenieMatka, generator.normal(15.0, 1.0));
	}
	
	public Object getEventParams() {
		return null;
	}
	
	protected void onInterruption() throws SimControlException {}
	
	protected void onTermination() throws SimControlException {}
}
