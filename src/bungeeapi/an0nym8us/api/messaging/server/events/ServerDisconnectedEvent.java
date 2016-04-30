package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;

/** Called when server has disconnected from proxy
 * @author An0nym8us
 *
 */
public class ServerDisconnectedEvent implements MessagingEvent
{
	public String serverName;
	
	public ServerDisconnectedEvent(String serverName)
	{
		this.serverName = serverName;
	}
}
