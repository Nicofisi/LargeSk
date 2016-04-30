package an0nym8us.api.messaging;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;


/** Provides support for handshake sending/receiving
 * @author An0nym8us
 *
 */
public class HandshakePacket
{
	public SecretKey key;
	public byte[] IV;
	public String serverName;
	public int packetID = 0x00;
	
	public HandshakePacket(SecretKey key, byte[] IV, String serverName)
	{
		this.key = key;
		this.IV = IV;
		this.serverName = serverName;
	}
	
	public HandshakePacket(InputStream input) throws IOException
	{
		DataInputStream dis = new DataInputStream(input);	
		
		int length = dis.readInt();
		
		byte[] data = new byte[length];	
		dis.readFully(data);
		
		dis = new DataInputStream(new ByteArrayInputStream(data));
		int packetID = dis.readInt();
		int secretLength = dis.readInt();
		int ivLength = dis.readInt();
		
		byte[] key = new byte[secretLength];
		this.IV = new byte[ivLength];
		
		dis.read(key, 0, key.length);
		dis.read(IV, 0, IV.length);
		this.serverName = dis.readUTF();
		
		this.key = new SecretKeySpec(key, 0, key.length, "AES");
	}
	
	public boolean WritePacket(OutputStream output)
	{
		try
		{
			ByteArrayDataOutput outStream = ByteStreams.newDataOutput();			
			outStream.writeInt(packetID);
			outStream.writeInt(key.getEncoded().length);
			outStream.writeInt(IV.length);
			outStream.write(key.getEncoded());
			outStream.write(IV);
			outStream.writeUTF(serverName);
			
			DataOutputStream dos = new DataOutputStream(output);
			dos.writeInt(outStream.toByteArray().length);			
			dos.write(outStream.toByteArray());
		}
		catch(Exception ex)
		{
			return false;
		}
		
		return true;
	}
}
