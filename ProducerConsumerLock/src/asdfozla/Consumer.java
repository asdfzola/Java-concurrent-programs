package asdfozla;

public class Consumer extends Thread {
	private BoundedBuffer buff;
	private int id;
	public Consumer(String name, int id, BoundedBuffer buff) {
		super(name);
		this.buff = buff;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		while (true) {
			int item = 0;
			try {
				item = buff.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(this.getName() + " Consumed " + item);
			try {
				sleep((int) Math.random() * 8500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
