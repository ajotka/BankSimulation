import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.broker.INotificationEvent;

final class Smo extends BasicSimObj {
	private static KolejkaI kolejka;
	private boolean[] okienkoWolne;
	ObslugaPoczatek obslugaPoczatek;
	ObslugaKoniec obslugaKoniec;
	Naprawa naprawaAwarii;
	private Utylizator utylizator;
	public int ileOkienek;
	
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public static MonitoredVar MVliczba_klientow;
    public MonitoredVar MVutracone;
	
	Smo(int dlugosc, int ileOkienek) throws SimControlException {
		
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar();
        MVczasy_oczekiwania = new MonitoredVar();
        MVliczba_klientow = new MonitoredVar();
        MVutracone = new MonitoredVar();
		
		if (dlugosc <= 0) {
			throw new IllegalArgumentException("Smo - dlugosc mniejsza niz 1");
		}
		
		this.ileOkienek = ileOkienek;
		okienkoWolne = new boolean[ileOkienek];
		
		kolejka = Kolejka.stworz("KOLEJKA_FIFO_OGR_PR", dlugosc);
		for ( int i=0; i < ileOkienek; i++ ) {
			okienkoWolne[i] = true;
		}
		utylizator = new Utylizator();
	}
	
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}
	
	public void reflect(IPublisher publisher, INotificationEvent event) {}
	
	boolean okienkoWolne(int nrOkienka) {
		return okienkoWolne[nrOkienka];
	}
	
	boolean okienkoAwaria(int nrOkienka) {
		return okienkoWolne[nrOkienka] = false;
	}
	
	boolean okienkoNaprawa(int nrOkienka) {
		return okienkoWolne[nrOkienka] = true;
	}
	
	void zwolnijZablokuj(boolean zablokujZwolnij, int nrOkienka) {
		this.okienkoWolne[nrOkienka] = zablokujZwolnij;
	}
	
	static boolean kolejkaPelna() {
		return kolejka.kolejkaPelna();
	}
	
	int stan() {
		return kolejka.stan();
	}
	
	static void wstaw(Zgloszenie zgloszenie) {
		MVliczba_klientow.setValue(kolejka.stan());
		kolejka.wstaw(zgloszenie);
	}
	
	Zgloszenie usun() {
		MVliczba_klientow.setValue(kolejka.stan());
		return kolejka.usun();
	}
	
	void usunWybrane(Zgloszenie zgloszenie) {
		kolejka.usunWybrane(zgloszenie);
		MVliczba_klientow.setValue(kolejka.stan());
		utylizator.zapamietaj();
	}
	
	int odrzucone() {
		return utylizator.odrzucone();
	}
}
