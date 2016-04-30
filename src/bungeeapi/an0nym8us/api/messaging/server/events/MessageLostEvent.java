package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;

/** Called when message has been lost due to net communiction issues
 * @author An0nym8us
 *
 */
public class MessageLostEvent implements MessagingEvent
{
	public String channel;
	public String serverFrom;
	public String serverTo;
	public byte[] data;
	 
	public MessageLostEvent(String channel, String serverFrom, String serverTo, byte[] data)
	{
		this.channel = channel;
		this.serverFrom = serverFrom;
		this.serverTo = serverTo;
		this.data = data;
	}
}
