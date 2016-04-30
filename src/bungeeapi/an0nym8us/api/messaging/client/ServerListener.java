package an0nym8us.api.messaging.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import an0nym8us.api.events.EventManager;
import an0nym8us.api.messaging.HandshakePacket;
import an0nym8us.api.messaging.Packet;
import an0nym8us.api.messaging.PacketStruct;
import an0nym8us.api.messaging.SocketListener;
import an0nym8us.api.messaging.client.events.ConnectionClosedEvent;
import an0nym8us.api.messaging.client.events.ConnectionClosedEvent.DisconnectReason;
import an0nym8us.api.messaging.client.events.MessageReceivedEvent;
import an0nym8us.api.messaging.client.events.ProxyDisconnectedEvent;
import an0nym8us.api.messaging.client.events.ServerConnectedEvent;
import an0nym8us.api.messaging.server.ProxyListener;

/** Client-sided listener class
 * @author An0nym8us
 *
 */
public class ServerListener extends SocketListener
{
	static ServerListener instance;
	
	public String proxyIP;
	public int proxyPort;
	
	
	public ServerListener(String serverName) throws IllegalAccessException
	{
		this(serverName, "127.0.0.1", ProxyListener.PORT);
	}
	
	public ServerListener(String serverName, String proxyIP, int proxyPort) throws IllegalAccessException
	{
		super(serverName);
		
		this.proxyIP = proxyIP;
		this.proxyPort = proxyPort;
		
		if(instance != null) { throw new IllegalAccessException("ServerListener has already created instance!"); }		
		instance = this;
		
		this.serverName = serverName;
		
		scheduleTask();
	}
	
	public static ServerListener getInstance()
	{
		return instance;
	}
	
	protected void scheduleTask()
	{
		listeningThread.start();
	}
	
	protected void listeningTaskFunc() throws IOException, InterruptedException
	{
		try
		{
			if(socket != null && socket.isConnected()) { socket.close(); }
			
			Connect();
		}
		catch (IOException ex)
		{
			this.listeningThread.sleep(1000);
			
			return;
		}
		
		try
		{
			while(true)
			{
				Packet packet = new Packet(socket.getInputStream(), this.secretKey, this.iv);
				
				if(packet.GetPacketStruct().equals(PacketStruct.Server))
				{
					EventManager.callEvent(new MessageReceivedEvent(packet.GetValue("channel"), packet.GetValue("serverFrom"), packet.GetValue("serverTo"), packet.GetParams()));
				}
				else if(packet.GetPacketStruct().equals(PacketStruct.DuplicatedConnection))
				{
					EventManager.callEvent(new ConnectionClosedEvent(DisconnectReason.DuplicatedConnection));
					
					this.listeningThread.interrupt();
					
					return;
				}
			}
		}
		catch(SocketException ex)
		{
			EventManager.callEvent(new ProxyDisconnectedEvent());
			
			this.listeningThread.sleep(1000);
			
			return;
		}
		catch(Exception ex)
		{
			EventManager.callEvent(new ProxyDisconnectedEvent());
			
			ex.printStackTrace();
			
			this.listeningThread.sleep(1000);
			
			return;
		}
	}
	
	public OutputStream GetOutput()
	{
		try
		{
			return socket.getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	void Connect() throws UnknownHostException, IOException
	{
		socket = new Socket(proxyIP, proxyPort);
		
		HandshakePacket packet = new HandshakePacket(this.secretKey, this.iv, this.serverName);		
		packet.WritePacket(socket.getOutputStream());
		
		EventManager.callEvent(new ServerConnectedEvent());
	}
	
	boolean IsConnected()
	{
		return !(socket == null || !socket.isConnected());
	}
}
