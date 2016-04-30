package an0nym8us.api.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager
{
	private static class RegisteredListener
	{
		private MessagingListener listener;
		private boolean isRegistered;
		
		public RegisteredListener(MessagingListener listener)
		{
			this.listener = listener;
			
			isRegistered = true;
		}
		
		public boolean IsRegistered()
		{
			return isRegistered;
		}
		
		public void Unregister()
		{
			isRegistered = false;
		}
		
		public MessagingListener GetListener()
		{
			return listener;
		}
	}
	
	static
	{
		
	}
	
	private static volatile List<RegisteredListener> handlers = new ArrayList<RegisteredListener>();
	
	
	
	public static void registerListener(MessagingListener listener)
	{
		synchronized (handlers)
		{
			handlers.add(new RegisteredListener(listener));
			
			return;
		}
	}
	
	public static void unregisterListener(MessagingListener listener)
	{
		synchronized (handlers)
		{
			for(int i = 0; i < handlers.size(); i++)
			{
				if(handlers.get(i).GetListener().equals(listener))
				{
					handlers.get(i).Unregister();
					
					return;
				}
			}
		}
	}
	
	public static void unregisterAllListeners()
	{
		synchronized (handlers)
		{
			for(int i = 0; i < handlers.size(); i++)
			{
				handlers.get(i).Unregister();
			}
		}
	}
	
	public static void callEvent(MessagingEvent event)
	{
		try
		{
			synchronized(handlers)
			{				
				for(int i = 0; i < handlers.size(); i++)
				{
					if(!handlers.get(i).IsRegistered()) { continue; }
					
					Method[] methods = handlers.get(i).GetListener().getClass().getDeclaredMethods();
					
					for(Method method : methods)
					{
						if(method.isAnnotationPresent(EventHandler.class))
						{
							if(method.getParameterTypes()[0].equals(event.getClass()))
							{
								method.invoke(handlers.get(i).GetListener(), event);
							}
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
