package unisex;

import java.util.concurrent.Semaphore;

public class UnisexBathroomSem implements UnisexBathroom {

	private int N; // kapacitet wc-a
	private int cntM;
	private int cntW;
	private Semaphore entry;
	private Semaphore toilet;
	private int ticket;
	private int next;
	private Semaphore mutexW;
	private Semaphore mutexM;
	private Semaphore exclusive;
	private Semaphore using;
	private boolean cleaner;

	public UnisexBathroomSem(int n) {
		super();
		N = n;
		entry = new Semaphore(1);
		toilet = new Semaphore(1);
		mutexW = new Semaphore(1);
		mutexM = new Semaphore(1);
		ticket = 0;
		next = 0;
		cntW = cntM = 0;
		cleaner = false;
		exclusive = new Semaphore(1);
		using = new Semaphore(N);
	}

	@Override
	public void m_enter(int id) {
		// TODO Auto-generated method stub
		entry.acquireUninterruptibly();
		mutexM.acquireUninterruptibly();
		int myTicket = ticket++;
		cntM++;
		if (myTicket != next || cntM == 1) {
			toilet.acquireUninterruptibly();
			// exclusive.acquireUninterruptibly();
		}
		System.out.println("man " + (id + 1) + " entered");

		next++;
		mutexM.release();
		entry.release();
		using.acquireUninterruptibly();
		System.out.println("man " + (id + 1) + " using toilet");

	}

	@Override
	public void m_exit(int id) {
		// TODO Auto-generated method stub
		System.out.println("man " + (id + 1) + " not using toilet anymore");
		using.release();
		mutexM.acquireUninterruptibly();
		System.out.println("man " + (id + 1) + " left");
		cntM--;
		if (cntM == 0) {
			toilet.release();
			// exclusive.release();
		}
		mutexM.release();
	}

	@Override
	public void w_enter(int id) {
		// TODO Auto-generated method stub
		entry.acquireUninterruptibly();
		mutexW.acquireUninterruptibly();
		int myTicket = ticket++;
		cntW++;
		if (myTicket != next || cntW == 1) {
			toilet.acquireUninterruptibly();
			// exclusive.acquireUninterruptibly();
		}
		System.out.println("woman " + (id + 1) + " entered");

		next++;
		mutexW.release();
		entry.release();
		using.acquireUninterruptibly();
		System.out.println("woman " + (id + 1) + " using toilet");
	}

	@Override
	public void w_exit(int id) {
		// TODO Auto-generated method stub
		System.out.println("woman " + (id + 1) + " not using toilet anymore");
		using.release();
		mutexW.acquireUninterruptibly();
		System.out.println("woman " + (id + 1) + " left");
		cntW--;
		if (cntW == 0) {
			toilet.release();
			// exclusive.release();
		}
		mutexW.release();
	}

	@Override
	public void cleaner_enter(int id) {
		// TODO Auto-generated method stub
		if (cntW > 0 || cntM > 0) {
			toilet.acquireUninterruptibly();
		} else {
			cleaner = true;
			exclusive.acquireUninterruptibly();

		}

	}

	@Override
	public void cleaner_exit(int id) {
		// TODO Auto-generated method stub
		cleaner = false;
		exclusive.release();
		toilet.release();
	}

}
