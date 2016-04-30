package an0nym8us.api.messaging;

import java.io.IOException;

/**	Native listener class
 * @author An0nym8us
 *
 */
public abstract class Listener
{
	public static final String SERVER_ALL = "ALL";
	public static final String SERVER_PROXY = "BUNGEE";
	public static int PORT = 30001;
	
	protected String serverName;
	protected Thread listeningThread;
	
	public Listener(String serverName)
	{
		this.serverName = serverName;
		
		listeningThread = new Thread()
		{
			public void run()
			{
				while(!listeningThread.isInterrupted())
				{
					try
					{
						listeningTaskFunc();
					}
					catch (IOException e)
					{
						System.out.print("Thread interrupted - IOException");
						
						break;
					}
					catch(InterruptedException ex)
					{
						break;
					}
				}
			}
		};
	}
	
	public void Dispose()
	{
		listeningThread.interrupt();
	}
	
	public String GetName()
	{
		return serverName;
	}
	
	protected abstract void scheduleTask();
	protected abstract void listeningTaskFunc() throws IOException, InterruptedException;
}
