import dissimlab.simcore.SimManager;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;

import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimParameters.SimControlStatus;
import dissimlab.simcore.SimControlException;

class Symulacja {
	private static int dlugosc;
	private static int ileOkienek;
	private static double czasSim;
	private static double pstwo;
	public static SimManager model;

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.out.println("Symulacja [dlugosc] {czas}\n");
			System.out.println("- dlugosc - dlugosc kolejki\n");
			System.out.println("- czas - czas trwania symulacji");
		} else {
			try {
				Odczyt ozp = new Odczyt("Data.txt");
				dlugosc = Integer.parseInt(ozp.pobierzDana());
				ileOkienek = Integer.parseInt(ozp.pobierzDana());
				czasSim = Double.parseDouble(ozp.pobierzDana().replaceAll(" ","."));
				pstwo = Double.parseDouble(ozp.pobierzDana().replaceAll(" ","."));
				
				if(pstwo >= 1) {
					System.out.println("Podane prawdopodobienstwo jest zbyt duze");
				} else {
				
				model = SimManager.getInstance();
				Smo smo = new Smo(dlugosc, ileOkienek);
				Otoczenie otoczenie = new Otoczenie(smo);
				SimControlEvent stop = new SimControlEvent(czasSim,
					SimControlStatus.STOPSIMULATION);
				
				// Badanie czasu trwania eksperymentu - pocz¹tek
				long czst = new Date().getTime();
				
				model.startSimulation();

				// Badanie czasu trwania eksperymentu - koniec
				czst = new Date().getTime() - czst;
				
				// Wyniki
				System.out.println("\n------ WYNIKI ------\n");
				System.out.println("Czas trwania eksperymentu:\t\t\t\t" + czst);
				System.out.println("\nLiczba straconych klientow:\t\t\t\t" + smo.odrzucone());
				
				////////// Ile klientow w kolejce
				double  wynik = new BigDecimal(Statistics
						.arithmeticMean(Smo.MVliczba_klientow)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("\nWartosc srednia ilosci klientow:\t\t\t"
								+ wynik);
				wynik = new BigDecimal(Statistics
						.standardDeviation(Smo.MVliczba_klientow)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("Odchylenie standardowe dla ilosci klientow:\t\t"
								+ wynik);
				wynik = new BigDecimal(Statistics
						.max(Smo.MVliczba_klientow)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("Oczekiwana graniczna ilosc klientow:\t\t\t"
								+ wynik);
				
				////////// Czas obslugi
				wynik = new BigDecimal(Statistics
						.arithmeticMean(smo.MVczasy_obslugi)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("\nWartosc srednia czasu obslugi:\t\t\t\t"
								+ wynik);
				wynik = new BigDecimal(Statistics
						.standardDeviation(smo.MVczasy_obslugi)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("Odchylenie standardowe dla czasu obslugi:\t\t"
								+ wynik);
				wynik = new BigDecimal(Statistics.max(smo.MVczasy_obslugi))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out.println("Oczekiwany graniczny czas obslugi:\t\t\t"
						+ wynik);
				
				////////// Czas oczekiwania na obsluge
				wynik = new BigDecimal(Statistics
						.arithmeticMean(smo.MVczasy_oczekiwania)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("\nWartosc srednia czasu oczekiwania na obsluge:\t\t"
								+ wynik);
				wynik = new BigDecimal(Statistics
						.standardDeviation(smo.MVczasy_oczekiwania)).setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out
						.println("Odchylenie standardowe dla czasu oczekiwania:\t\t"
								+ wynik);
				wynik = new BigDecimal(Statistics.max(smo.MVczasy_oczekiwania))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out.println("Oczekiwany graniczny czas oczekiwania na obsluge:\t"
						+ wynik);
				}	
			}
			catch (SimControlException e) {
				System.out.println(e.toString());
			}
		}
	}
}
