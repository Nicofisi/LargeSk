package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;

/** Called when server has attemted to proxy while another server has been connected with the same ID
 * @author An0nym8us
 *
 */
public class DuplicatedServerConnectionTrialEvent implements MessagingEvent
{
	public String serverName;
	
	public DuplicatedServerConnectionTrialEvent(String serverName)
	{
		this.serverName = serverName;
	}
}
