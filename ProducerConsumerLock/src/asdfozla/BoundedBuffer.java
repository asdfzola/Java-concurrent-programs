package asdfozla;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
	private Lock lock = new ReentrantLock();
	private Condition full = lock.newCondition();
	private Condition empty = lock.newCondition();

	private int B = 2;
	private int N = 3;

	private int[] items;
	private int wi, ri, cnt;

	public BoundedBuffer() {
		this.wi = 0;
		this.ri = 0;
		this.cnt = 0;
		this.items = new int[B];
	}

	void put(int item) throws InterruptedException {
		lock.lock();
		try {
			if (cnt == B)
				empty.awaitUninterruptibly();
			items[wi] = item;
			wi = (wi + 1) % B;
			cnt++;
			if(cnt == B)  
				full.signal();
		} finally {
			lock.unlock();
		}
	}

	int get() throws InterruptedException {
		lock.lock();
		try {
			if (cnt == 0) {
				full.awaitUninterruptibly();
			}

			int item = items[ri];
			ri = (ri + 1) % items.length;
			cnt--;
			empty.signal();
			return item;
		} finally {
			lock.unlock();
		}
	}
}
