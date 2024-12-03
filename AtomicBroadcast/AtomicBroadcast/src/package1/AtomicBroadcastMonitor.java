package package1;

public class AtomicBroadcastMonitor<T> implements AtomicBroadcast<T> {

	private T[] buffer;
	private int wi;
	private int B; // velicina buffera
	private int N; // broj consumera
	private int itemsAvailable[];
	private int cnt[];
	private int ri[];

	@SuppressWarnings("unchecked")
	public AtomicBroadcastMonitor(int b, int n) {
		super();
		B = b;
		N = n;
		
		this.buffer = (T[]) new Object[B];
		

		wi = 0;

		itemsAvailable = new int[N];
		ri = new int[N];
		cnt = new int[B];
		for (int i = 0; i < B; i++) {
			cnt[i] = N; // kao da je svako consumovao sve
		}

		for (int i = 0; i < N; i++) {
			ri[i] = 0;
		}

	}

	@Override
	synchronized public void put(T item) {
		while (cnt[wi] < N) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cnt[wi] = 0;

		buffer[wi] = item;
		wi = (wi + 1) % B;

		for (int i = 0; i < N; i++) {
			itemsAvailable[i]++;
		}

		notifyAll();

	}

	@Override
	synchronized public T get(int id) {
		T item;

		while (itemsAvailable[id] == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		itemsAvailable[id]--;
		item = buffer[ri[id]];
		cnt[ri[id]]++;
		if (cnt[ri[id]] == N) {
			notifyAll();
		}
		ri[id] = (ri[id] + 1) % B;

		return item;
	}

}
