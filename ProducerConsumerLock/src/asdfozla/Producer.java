package asdfozla;

public class Producer extends Thread {

	public BoundedBuffer buffer;

	public Producer(String name, BoundedBuffer buffer) {
		super(name);
		this.buffer = buffer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		while (true) {
			int item = (int) (Math.random()*100) + 1;
			try {
				buffer.put(item);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(getName() + " produced " + item);
			try {
				sleep((int) Math.random() * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
