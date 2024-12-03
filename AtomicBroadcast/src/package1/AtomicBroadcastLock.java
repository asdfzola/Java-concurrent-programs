package package1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicBroadcastLock<T> implements AtomicBroadcast<T> {
	private int B;
	private int N;

	private T[] buffer;
	private int wi;
	private int ri[];
	private int cnt[];
	private Lock lock = new ReentrantLock();
	private Condition full = lock.newCondition();
	private Condition empty = lock.newCondition();
	private int[] itemsAvailable;

	
	
	@SuppressWarnings("unchecked")
	public AtomicBroadcastLock(int b, int n) {
		super();
		B = b;
		N = n;
		buffer = (T[]) new Object[B];
		wi = 0;
		ri = new int[N];
		cnt = new int[B];
		itemsAvailable = new int[N];
		for(int i = 0; i < N; i++) {
			ri[i] = 0;
		}
		for(int i = 0; i < B; i++) {
			cnt[i] = N;
		}
	}

	@Override
	public void put(T item) {
		lock.lock();
		try {
			
			if(cnt[wi]<N)
				empty.awaitUninterruptibly();
			buffer[wi] = item;
			cnt[wi] = 0;
			wi = (wi + 1) % B;
			for(int i =0; i<N; i++) {
				itemsAvailable[i]++;
				full.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public T get(int id) {

		lock.lock();
		try {
			if(itemsAvailable[id]==0)
				full.awaitUninterruptibly();
			T item = buffer[ri[id]];
			
			itemsAvailable[id]--;
			cnt[ri[id]]++;
			if (cnt[ri[id]] == N) {
				cnt[ri[id]] = 0;
				empty.signal();
			}

			ri[id] = (ri[id] + 1) % B;

			return item;
		} finally {
			lock.unlock();
		}

	}

}
