package package1;

public class Test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 5;
		int B = 3;
		//AtomicBroadcast<Integer> buffer = new AtomicBroadcastSemaphores<>(B, N);
		//AtomicBroadcast<Integer> buffer = new AtomicBroadcastMonitor<>(B, N);
		AtomicBroadcast<Integer> buffer = new AtomicBroadcastLock<Integer>(B, N);
			Producer p = new Producer("P1", buffer);
			p.start();
			Producer p2 = new Producer("P2", buffer);
			p2.start();
		
		
		for (int i = 0; i < N; i++) {
			Consumer c = new Consumer("C" + (i+1), i, buffer);
			c.start();
		}
		
	}

}
