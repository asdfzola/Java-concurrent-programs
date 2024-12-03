package unisex;

public class Man extends Thread {

	private UnisexBathroom bath;
	private int id;

	public Man(String name, UnisexBathroom bath, int id) {
		super(name);
		this.bath = bath;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			bath.m_enter(id);

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bath.m_exit(id);
			
			try {
				sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
