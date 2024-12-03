package unisex;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnisexBathroomLock implements UnisexBathroom {

	private int N;
	private Lock lock = new ReentrantLock();
	private Condition toilet = lock.newCondition();

	private int cntM;
	private int cntW;
	private int capacity;

	private int ticket;
	private int next;

	public UnisexBathroomLock(int n) {
		super();
		this.N = n;
		this.cntM = 0;
		this.cntW = 0;
		this.capacity = N;
		this.ticket = 0;
		this.next = 0;
	}

	@Override
	public void m_enter(int id) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			int myTicket = ticket++;
			while (cntW > 0 || cntM > capacity || myTicket != next) {
				toilet.awaitUninterruptibly();
			}
			cntM++;
			next++;
			System.out.println("man " + (id + 1) + " entered");

		} finally {
			lock.unlock();
		}

	}

	@Override
	public void m_exit(int id) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			cntM--;
			System.out.println("man " + (id + 1) + " left");
			toilet.signalAll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void w_enter(int id) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			int myTicket = ticket++;
			while (cntM > 0 || cntW > capacity || myTicket != next) {
				toilet.awaitUninterruptibly();
			}
			cntW++;
			next++;
			System.out.println("woman " + (id + 1) + " entered");

		} finally {
			lock.unlock();
		}
	}

	@Override
	public void w_exit(int id) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			cntW--;
			System.out.println("woman " + (id + 1) + " left");
			toilet.signalAll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void cleaner_enter(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleaner_exit(int id) {
		// TODO Auto-generated method stub

	}

}
