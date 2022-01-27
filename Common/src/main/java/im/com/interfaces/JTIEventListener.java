package im.com.interfaces;

import javax.security.auth.callback.Callback;

public interface JTIEventListener<T> extends Callback
{
		public void execute(T result);
}
