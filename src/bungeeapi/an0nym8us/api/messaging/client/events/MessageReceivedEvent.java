package an0nym8us.api.messaging.client.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import an0nym8us.api.events.MessagingEvent;



/** Called when message has been received
 * @author An0nym8us
 *
 */
public class MessageReceivedEvent implements MessagingEvent
{
	public String channel;
	public String serverTo;
	public String serverFrom;
	public String[] params;
	
	public MessageReceivedEvent(String channel, String serverFrom, String serverTo, String... params)
	{
		this.channel = channel;
		this.serverFrom = serverFrom;
		this.serverTo = serverTo;
		this.params = params;
	}
	
	public byte[] GetData()
	{
		ByteArrayDataOutput outStream = ByteStreams.newDataOutput();
		
		for(String s : params)
		{
			outStream.writeUTF(s);
		}
			
		byte[] array = outStream.toByteArray();
			
		outStream = ByteStreams.newDataOutput();
		
		outStream.write(array);
			
		return outStream.toByteArray();
	}
}
