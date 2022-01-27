package im.com.interfaces;

public interface JTIEventSignaler
{
	void addEventListener(Object type,  JTIEventListener listener, Boolean once);

	void dispatch(Object type, Object args);

	void removeListener(Object type);

	void removeListener(Object type, JTIEventListener listener);
}
