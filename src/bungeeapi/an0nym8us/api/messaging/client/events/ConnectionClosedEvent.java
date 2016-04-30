package an0nym8us.api.messaging.client.events;

import an0nym8us.api.events.MessagingEvent;



/** Called when connection is closed by proxy - caused by reason other than connection loss (i.e. connection duplex)
 * @author An0nym8us
 *
 */
public class ConnectionClosedEvent implements MessagingEvent
{
	public enum DisconnectReason
	{
		DuplicatedConnection();
		
		DisconnectReason() { }
	}
	public DisconnectReason reason;
	
	public ConnectionClosedEvent(DisconnectReason reason)
	{
		this.reason = reason;
	}
}
