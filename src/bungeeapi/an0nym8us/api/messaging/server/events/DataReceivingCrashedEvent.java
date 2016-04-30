package an0nym8us.api.messaging.server.events;

import an0nym8us.api.events.MessagingEvent;

/** Called when data receiving has crashed, not used certainly
 * @author An0nym8us
 *
 */
@Deprecated
public class DataReceivingCrashedEvent implements MessagingEvent
{
	public Exception ex;
	public String serverName;
	
	public DataReceivingCrashedEvent(String serverName, Exception ex)
	{
		this.serverName = serverName;
	}
}
