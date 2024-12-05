package unisex;

public class Test {
	public static void main(String[] args) {
		int N = 3;
		//UnisexBathroom bath = new UnisexBathroomSem(N);
		//UnisexBathroom bath = new UnisexBathroomLock(N);
		UnisexBathroom bath = new UnisexBathroomMonitor(N);

		for (int i = 0; i < 6; i++) {
			Man m = new Man("M" + (i + 1), bath, i);
			m.start();
		}
		for (int i = 0; i < 6; i++) {
			Woman w = new Woman("W" + (i + 1), bath, i);
			w.start();
		}
		
		Cleaner c = new Cleaner("Cleaner 1", bath, 1);
		//c.start();
	}

}
