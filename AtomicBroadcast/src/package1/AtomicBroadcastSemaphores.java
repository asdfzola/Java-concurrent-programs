package package1;

import java.util.concurrent.Semaphore;

public class AtomicBroadcastSemaphores<T> implements AtomicBroadcast<T> {

	private int B;
	private int N;

	private T[] buffer;
	private int wi;
	private int cnt[];
	private int readindex[];
	private Semaphore full[];
	private Semaphore empty;
	private Semaphore mutex[];
	private Semaphore mutexP;

	@SuppressWarnings("unchecked")
	public AtomicBroadcastSemaphores(int b, int n) {
		super();
		B = b;
		N = n;
		this.buffer = (T[]) new Object[B];
		this.wi = 0;
		this.cnt = new int[B];
		this.readindex = new int[N];
		this.mutex = new Semaphore[B];
		this.full = new Semaphore[N];
		for (int i = 0; i < B; i++) {
			mutex[i] = new Semaphore(1);
		}

		for (int i = 0; i < N; i++) {
			full[i] = new Semaphore(0);
		}
		this.empty = new Semaphore(B);
		this.mutexP = new Semaphore(1);

	}

	@Override
	public void put(T item) {
		empty.acquireUninterruptibly();
		mutexP.acquireUninterruptibly();
		buffer[wi] = item;
		wi = (wi + 1) % B;
		mutexP.release();
		
		for (int i = 0; i < full.length; i++) {
			full[i].release();
		}

	}

	@Override
	public T get(int id) {
		if (id < 0 || id > N)
			return null;

		full[id].acquireUninterruptibly();
		T item = buffer[readindex[id]];
		mutex[readindex[id]].acquireUninterruptibly();
		cnt[readindex[id]]++;
		if (cnt[readindex[id]] == N) {
			cnt[readindex[id]] = 0;
			empty.release();
		}

		mutex[readindex[id]].release();
		readindex[id] = (readindex[id] + 1) % B;

		return item;
	}

}
