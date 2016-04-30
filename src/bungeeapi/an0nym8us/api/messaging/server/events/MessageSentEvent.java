package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;


/** Called when message has been sent
 * @author An0nym8us
 *
 */
public class MessageSentEvent implements MessagingEvent
{
	public String channel;
	public String serverFrom;
	public String serverTo;	
	public byte[] data;
	
	public MessageSentEvent(String channel, String serverFrom, String serverTo, byte[] data)
	{
		this.channel = channel;
		this.serverFrom = serverFrom;
		this.serverTo = serverTo;
		this.data = data;
	}
}
