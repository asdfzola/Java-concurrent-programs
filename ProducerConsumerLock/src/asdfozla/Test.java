package asdfozla;

public class Test {
	

	public static void main(String[] args) {
		BoundedBuffer buff = new BoundedBuffer();
		Producer p = new Producer("P", buff);
		p.start();
		for(int i = 0; i < 5; i++) {
			Consumer c = new Consumer("C" + (i+1), i, buff);
			c.start();
		}
		

	}

}
