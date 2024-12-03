package unisex;

public class Cleaner extends Thread {
	private UnisexBathroom bath;
	private int id;

	public Cleaner(String name, UnisexBathroom bath, int id) {
		super(name);
		this.bath = bath;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			bath.cleaner_enter(id);
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bath.cleaner_exit(id);
		}
	}
}
