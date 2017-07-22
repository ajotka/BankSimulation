import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.broker.IPublisher;
import dissimlab.random.SimGenerator;
import dissimlab.broker.INotificationEvent;

final class Otoczenie extends BasicSimObj {
	GeneratorZgloszen generator;
	GeneratorAwarii awaria;
	Smo smo;

	Otoczenie(Smo smo) throws SimControlException {
		
		SimGenerator simgenerator;
		simgenerator = new SimGenerator();
		double czasAwarii = simgenerator.normal(5.0, 0.1);
		
		generator = new GeneratorZgloszen(this, 0.0);
		awaria = new GeneratorAwarii(this, czasAwarii);
		this.smo = smo;

	}

	public boolean filter(IPublisher ip, INotificationEvent ine) {
		return false;
	}
	
	public void reflect(IPublisher ip, INotificationEvent ine) {}
}
