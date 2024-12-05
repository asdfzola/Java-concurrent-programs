package unisex;



public class UnisexBathroomMonitor implements UnisexBathroom {
	private int N;
	private int cntM = 0;
	private int cntW = 0;

	public UnisexBathroomMonitor(int n) {
		super();
		N = n;
	}

	@Override
	public synchronized void m_enter(int id) {
		// TODO Auto-generated method stub
		
		while (cntW > 0 || cntM > N) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cntM++;
		System.out.println("man " + id + " entered");
		if(cntM == 1)
			notifyAll();
	}

	@Override
	public synchronized void m_exit(int id) {
		// TODO Auto-generated method stub
		cntM--;
		System.out.println("man " + id + " left");
		notifyAll();
	}

	@Override
	public synchronized void w_enter(int id) {
		// TODO Auto-generated method stub
		
		while (cntM > 0 || cntW > N) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cntW++;
		System.out.println("woman " + id + " entered");
		if(cntW ==1 )
			notifyAll();
	}

	@Override
	public synchronized void cleaner_enter(int id) {
		// TODO Auto-generated method stub
		while ((cntM + cntW) > 0 || cntD > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cntD++;
		System.out.println("Cleaner " + id + " entered");
	}

	@Override
	public synchronized void cleaner_exit(int id) {
		// TODO Auto-generated method stub
		cntD--;
		notifyAll();

	}

}
