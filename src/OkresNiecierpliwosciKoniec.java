import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

final class OkresNiecierpliwosciKoniec extends BasicSimEvent<Zgloszenie, Object> {
	private Zgloszenie zgloszenieMatka;
	
	OkresNiecierpliwosciKoniec(Zgloszenie zgloszenieMatka, double odstep) throws SimControlException {
		super(zgloszenieMatka, odstep);
		this.zgloszenieMatka = zgloszenieMatka;
	}
	
	OkresNiecierpliwosciKoniec(Zgloszenie zgloszenieMatka) throws SimControlException {
		super(zgloszenieMatka);
		this.zgloszenieMatka = zgloszenieMatka;
	}
	
	protected void stateChange() throws SimControlException {
		System.out.printf("%10.3f: Klientowi numer %d skonczyla sie cierpliwosc (priorytet rowny %d)\n",
			simTime(), zgloszenieMatka.numer(), zgloszenieMatka.priorytet());
		
		System.out.printf("%10.3f: Klient numer %d wyszedl\n", simTime(), zgloszenieMatka.numer());
		zgloszenieMatka.smo.usunWybrane(zgloszenieMatka);
	}
	
	protected void onInterruption() throws SimControlException {
		System.out.printf("%10.3f: Klient numer %d - problem z niecierpliwoscia\n",
			simTime(), zgloszenieMatka.numer());
	}
	
	public Object getEventParams() {
		return null;
	}
	
	protected void onTermination() throws SimControlException {}
}
