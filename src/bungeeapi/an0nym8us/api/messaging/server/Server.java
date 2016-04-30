package an0nym8us.api.messaging.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKey;

import an0nym8us.api.events.EventManager;
import an0nym8us.api.messaging.Packet;
import an0nym8us.api.messaging.PacketStruct;
import an0nym8us.api.messaging.SocketListener;
import an0nym8us.api.messaging.server.events.MessageForwardedEvent;
import an0nym8us.api.messaging.server.events.MessageLostEvent;
import an0nym8us.api.messaging.server.events.MessageReceivedEvent;
import an0nym8us.api.messaging.server.events.MessageSentEvent;
import an0nym8us.api.messaging.server.events.ServerDisconnectedEvent;



/** Secondary-level listener, handles one connection (one object per one connected server)
 * @author An0nym8us
 *
 */
public class Server extends SocketListener
{
	public Server(String serverName, Socket socket, SecretKey key, byte[] iv) throws IOException
	{
		super(serverName, key, iv);
		
		this.socket = socket;
		
		scheduleTask();
	}
	
	protected void scheduleTask()
	{
		listeningThread.start();
	}
	
	protected void listeningTaskFunc() throws IOException, InterruptedException
	{
		try
		{
			while(true)
			{
				Packet packet = new Packet(socket.getInputStream(), this.secretKey, this.iv);
				
				if(packet.GetPacketStruct().equals(PacketStruct.Server))
				{
					if(packet.GetValue("serverTo").equals(ProxyListener.SERVER_ALL))
					{
						for(Server s : ProxyListener.GetInstance().servers)
						{
							if(!s.serverName.equals(this.serverName))
							{				
								SendMessage(packet.GetValue("channel"), s.serverName, packet.GetData(3, packet.data.length));
							}
						}
						
						EventManager.callEvent(new MessageForwardedEvent(packet.GetValue("channel"), this.serverName, packet.GetValue("serverTo"), packet.GetData()));
					}
					else if(packet.GetValue("serverTo").equals(ProxyListener.SERVER_PROXY))
					{				
						EventManager.callEvent(new MessageSentEvent(packet.GetValue("channel"), this.serverName, packet.GetValue("serverTo"), packet.GetData()));
						EventManager.callEvent(new MessageReceivedEvent(packet.GetValue("channel"), this.serverName, packet.GetValue("serverTo"), packet.GetParams()));
					}
					else
					{
						try
						{
							SendMessage(packet.GetValue("channel"), packet.GetValue("serverTo"), packet.GetData(3, packet.data.length));
						}
						catch(NullPointerException ex)
						{
							EventManager.callEvent(new MessageLostEvent(packet.GetValue("channel"), this.serverName, packet.GetValue("serverTo"), packet.GetData()));
						}
						
						EventManager.callEvent(new MessageForwardedEvent(packet.GetValue("channel"), this.serverName, packet.GetValue("serverTo"), packet.GetData()));
					}
				}
			}
		}
		catch(Exception ex)
		{
			EventManager.callEvent(new ServerDisconnectedEvent(this.serverName));
			
			ProxyListener.GetInstance().servers.remove(this);
			this.listeningThread.interrupt();
			
			return;
		}
		catch(Throwable ex)
		{
			EventManager.callEvent(new ServerDisconnectedEvent(this.serverName));
			
			ProxyListener.GetInstance().servers.remove(this);
			this.listeningThread.interrupt();
			
			return;
		}
	}
	
	@Override
	public boolean SendMessage(String channel, String destinationServer, String... array) throws IOException
	{
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		list.add(0, channel);
		list.add(1, this.serverName);
		list.add(2, destinationServer);
		
		Packet packet = new Packet(PacketStruct.Server.packetID, list.toArray(new String[list.size()]));
		
		EventManager.callEvent(new MessageSentEvent(channel, this.serverName, destinationServer, packet.GetData()));
		return packet.WritePacket(ProxyListener.GetInstance().GetServer(destinationServer).socket.getOutputStream(), ProxyListener.GetInstance().GetServer(destinationServer).secretKey, ProxyListener.GetInstance().GetServer(destinationServer).iv);
	}
	
	public OutputStream GetOutput()
	{
		try {
			return socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
