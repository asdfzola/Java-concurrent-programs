package package1;

public class Consumer extends Thread {
	private AtomicBroadcast<Integer> buffer;
	private int id;

	public Consumer(String name, int id, AtomicBroadcast<Integer> buffer2) {
		super(name);
		this.id = id;
		this.buffer = buffer2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {

			int item = buffer.get(id);

			System.out.println(this.getName() + " consumed " + item);

			try {
				sleep((int) (Math.random() * 3500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
