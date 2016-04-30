package an0nym8us.api.messaging.client.events;

import an0nym8us.api.events.MessagingEvent;



/** Called when message is sent
 * @author An0nym8us
 *
 */
public class MessageSentEvent implements MessagingEvent
{
	public String channel;
	public String from;
	public String to;
	
	public byte[] data;
	
	public MessageSentEvent(String channel, String from, String to, byte[] data)
	{
		this.channel = channel;
		this.from = from;
		this.to = to;
		this.data = data;
	}
}
