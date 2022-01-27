package im.com.interfaces;

public interface JTIPool<T extends JTIPoolObject>
{
	 public T get();

     public void put(T item);
 
}
