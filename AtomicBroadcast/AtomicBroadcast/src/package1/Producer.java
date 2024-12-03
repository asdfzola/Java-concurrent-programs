package package1;

public class Producer extends Thread {
	private AtomicBroadcast<Integer> buffer;

	public Producer(String name, AtomicBroadcast<Integer> buffer) {
		super(name);
		this.buffer = buffer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			int item = (int) (Math.random() * 100) + 1;

			buffer.put(item);
			System.out.println(this.getName() + " produced " + item);
			try {
				sleep((int) (Math.random() * 2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
