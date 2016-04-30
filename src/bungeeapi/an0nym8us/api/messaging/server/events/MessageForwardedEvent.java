package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;

/** Called when message has been forwared to other server
 * @author An0nym8us
 *
 */
public class MessageForwardedEvent implements MessagingEvent
{
	public String channel;
	public String serverFrom;
	public String serverTo;
	public byte[] data;
	
	public MessageForwardedEvent(String channel, String serverFrom, String serverTo, byte[] data)
	{
		this.channel = channel;
		this.serverFrom = serverFrom;
		this.serverTo = serverTo;
		this.data = data;
	}
}
