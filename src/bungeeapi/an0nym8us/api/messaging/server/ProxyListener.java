package an0nym8us.api.messaging.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;

import an0nym8us.api.events.EventManager;
import an0nym8us.api.messaging.HandshakePacket;
import an0nym8us.api.messaging.Listener;
import an0nym8us.api.messaging.Packet;
import an0nym8us.api.messaging.PacketStruct;
import an0nym8us.api.messaging.server.events.DuplicatedServerConnectionTrialEvent;
import an0nym8us.api.messaging.server.events.MessageSentEvent;
import an0nym8us.api.messaging.server.events.ServerConnectedEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.CountingInputStream;

/** Server(proxy)-sided listener, manages incoming traffic
 * @author An0nym8us
 *
 */
public class ProxyListener extends Listener
{
	static final String SERVER_ALL = "ALL";
	static final String SERVER_PROXY = "BUNGEE";
	public static final int PORT = 30001;
	
	protected static ProxyListener instance;
	
	ServerSocket serverSocket;
	List<Server> servers;
	
	int proxyPort;
	
	public ProxyListener() throws IllegalAccessException
	{
		this(PORT);
	}
	
	public ProxyListener(int proxyPort) throws IllegalAccessException
	{
		super(SERVER_PROXY);
		this.serverName = SERVER_PROXY;
		
		this.proxyPort = proxyPort;
		
		if(instance != null) { throw new IllegalAccessException("ProxyListener has already created instance!"); }		
		instance = this;
		
		servers = new ArrayList<Server>();
		
		scheduleTask();
	}
	
	public static ProxyListener GetInstance()
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
			serverSocket = new ServerSocket(proxyPort);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
			listeningThread.interrupt();
			
			return;
		}
		
		while(true)
		{
			try
			{
				Socket socket = serverSocket.accept();
				
				HandshakePacket handshakePacket = new HandshakePacket(socket.getInputStream());
				
				if(GetServer(handshakePacket.serverName) != null)
				{
					EventManager.callEvent(new DuplicatedServerConnectionTrialEvent(handshakePacket.serverName));
					
					Packet disconnectPacket = new Packet(PacketStruct.DuplicatedConnection.packetID);
					
					disconnectPacket.WritePacket(socket.getOutputStream(), handshakePacket.key, handshakePacket.IV);
					
					socket.close();
					
					continue;
				}
				
				servers.add(new Server(handshakePacket.serverName, socket, handshakePacket.key, handshakePacket.IV));
				
				EventManager.callEvent(new ServerConnectedEvent(handshakePacket.serverName));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				continue;
			}
		}
	}
	
	public boolean SendMessage(String channel, String destinationServer, byte[] data) throws IOException
	{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
		
		List<String> list = new ArrayList<String>();
		
		while(dis.available() > 0)
		{
			list.add(dis.readUTF());
		}
		
		return SendMessage(channel, destinationServer, list.toArray(new String[list.size()]));
	}
		
	public boolean SendMessage(String channel, String destinationServer, String... array)	throws IOException
	{
		return ProxyListener.GetInstance().GetServer(destinationServer).SendMessage(channel, destinationServer, array);
	}
	
	public void Dispose()
	{
		for(Server server : servers)
		{
			server.Dispose();
		}
		
		super.Dispose();
	}
	
	public Server GetServer(String serverName)
	{
		for(Server server : servers)
		{
			if(server.GetName().equals(serverName))
			{
				return server;
			}
		}
		
		return null;
	}
}
