package an0nym8us.api.messaging;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import an0nym8us.api.events.EventManager;
import an0nym8us.api.messaging.client.events.MessageSentEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public abstract class SocketListener extends Listener
{
	public Socket socket;
	public SecretKey secretKey;
	protected byte[] iv;
	
	public SocketListener(String serverName)
	{
		super(serverName);
		secretKey = CryptoStance.GenerateKey();
		iv = CryptoStance.GenerateIV();
	}
	
	public SocketListener(String serverName, SecretKey secretKey, byte[] iv)
	{
		super(serverName);
		
		this.secretKey = secretKey;
		this.iv = iv;
	}
	
	@Override
	public void Dispose()
	{
		super.Dispose();
		
		try
		{
			this.socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
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
	
	public boolean SendMessage(String channel, String destinationServer, String... array) throws IOException
	{
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		list.add(0, channel);
		list.add(1, this.serverName);
		list.add(2, destinationServer);
		
		Packet packet = new Packet(PacketStruct.Server.packetID, list.toArray(new String[list.size()]));
		
		EventManager.callEvent(new MessageSentEvent(channel, this.serverName, destinationServer, packet.GetData()));
		return packet.WritePacket(socket.getOutputStream(), secretKey, iv);
	}
}
