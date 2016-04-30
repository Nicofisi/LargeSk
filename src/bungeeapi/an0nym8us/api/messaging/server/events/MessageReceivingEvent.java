package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;


/** Called when message has income (before receiving!)
 * @author An0nym8us
 *
 */
@Deprecated
public class MessageReceivingEvent implements MessagingEvent
{
	public String channel;
	public String serverFrom;
	public String serverTo;
	
	public MessageReceivingEvent(String channel, String serverFrom, String serverTo)
	{
		this.channel = channel;
		this.serverFrom = serverFrom;
		this.serverTo = serverTo;
	}
}
