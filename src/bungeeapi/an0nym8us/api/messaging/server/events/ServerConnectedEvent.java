package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;

/** Called when server has connected to proxy
 * @author An0nym8us
 *
 */
public class ServerConnectedEvent implements MessagingEvent
{
	public String serverName;
	
	public ServerConnectedEvent(String serverName)
	{
		this.serverName = serverName;
	}
}
