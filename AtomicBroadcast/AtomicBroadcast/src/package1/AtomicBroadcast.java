package package1;

public interface AtomicBroadcast<T> {
	
	public void put(T item);
	public T get(int id);

}
