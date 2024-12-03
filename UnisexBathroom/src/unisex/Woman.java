package unisex;

public class Woman extends Thread {
	private UnisexBathroom bath;
	private int id;

	public Woman(String name, UnisexBathroom bath, int id) {
		super(name);
		this.bath = bath;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			bath.w_enter(id);

			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bath.w_exit(id);
			
			try {
				sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
