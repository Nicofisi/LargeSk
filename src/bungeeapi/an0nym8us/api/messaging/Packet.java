package an0nym8us.api.messaging;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.CountingInputStream;


/** Supports packet manipulation
 * @author An0nym8us
 *
 */
/**
 * @author An0nym8us
 *
 */
/**
 * @author An0nym8us
 *
 */
/**
 * @author An0nym8us
 *
 */
/**
 * @author An0nym8us
 *
 */
/**
 * @author An0nym8us
 *
 */
public class Packet
{
	public int packetID;
	public String[] data;
	
	public Packet(int packetID)
	{
		this.packetID = packetID;
		data = null;
	}
	
	public Packet(int packetID, String... data)
	{
		this.packetID = packetID;
		this.data = data;
	}
	
	public Packet(int packetID, byte[] data) throws IOException
	{
		this.packetID = packetID;
		
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));	
		
		List<String> list = new ArrayList<String>();
		
		while(dis.available() > 0)
		{
			list.add(dis.readUTF());
		}
		
		this.data = list.toArray(new String[list.size()]);
	}
	
	
	/** Reads packet from given stream
	 * @param input Given stream
	 * @param sKey Secret Key
	 * @param iv IV
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public Packet(InputStream input, SecretKey sKey, byte[] iv) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		this(input, sKey, iv, true);
	}
	
	
	/** Reads packet from given stream
	 * @param input Given stream
	 * @param sKey Secret key
	 * @param iv IV
	 * @param crypt If true, then packet is decrypted using given key
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public Packet(InputStream input, SecretKey sKey, byte[] iv, boolean crypt) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		Packet packet = Packet.ReadPacket(input, sKey, iv, crypt);
		
		this.data = packet.data;
		this.packetID = packet.packetID;
	}
	
	public static Packet ReadPacket(InputStream input, SecretKey sKey, byte[] iv) throws InvalidKeyException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		return ReadPacket(input, sKey, iv, true);
	}
	
	public static Packet ReadPacket(InputStream input, SecretKey sKey, byte[] iv, boolean crypt) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		try
		{
			DataInputStream dis = new DataInputStream(input);	
			
			int length = dis.readInt();
			
			byte[] data = new byte[length];	
			dis.readFully(data);
			
			if(crypt) { data = CryptoStance.Decrypt(sKey, iv, data); }
			
			dis = new DataInputStream(new ByteArrayInputStream(data));
			int packetID = dis.readInt();
			byte[] newData = new byte[length - 4];
			dis.readFully(newData);
			
			return new Packet(packetID, newData);
		}
		catch(EOFException ex)
		{
			return null;
		}
	}
	
	public boolean WritePacket(OutputStream output, SecretKey sKey, byte[] iv)
	{
		return WritePacket(output, sKey, iv, true);
	}
	
	public boolean WritePacket(OutputStream output, SecretKey sKey, byte[] iv, boolean crypt)
	{
		try
		{
			ByteArrayDataOutput outStream = ByteStreams.newDataOutput();
			
			outStream.writeInt(packetID);
			
			if(data != null)
			{
				for(String s : data)
				{
					outStream.writeUTF(s);
				}
			}
			
			DataOutputStream dos = new DataOutputStream(output);
			dos.writeInt(outStream.toByteArray().length);			
			dos.write(crypt ? CryptoStance.Encrypt(sKey, iv, outStream.toByteArray()) : outStream.toByteArray());
		}
		catch(Exception ex)
		{
			return false;
		}
		
		return true;
	}
	
	
	/** Returns param value by given param key using PacketStruct patterns
	 * @param param
	 * @return
	 */
	public String GetValue(String param)
	{
		return data[GetPacketStruct().GetParamIndex(param)];
	}
	
	
	/** Returns packet data packed into byte array
	 * @return
	 */
	public byte[] GetData()
	{
		return Packet.GetData(data);
	}
	
	public byte[] GetData(int startIndex, int endIndex)
	{
		return Packet.GetData(new ArrayList<String>(Arrays.asList(data)).subList(startIndex, endIndex).toArray(new String[endIndex - startIndex]));
	}
	
	public static byte[] GetData(String... data)
	{
		ByteArrayDataOutput outStream = ByteStreams.newDataOutput();
		
		for(String s : data)
		{
			outStream.writeUTF(s);
		}
			
		byte[] array = outStream.toByteArray();
			
		outStream = ByteStreams.newDataOutput();
		
		outStream.write(array);
			
		return outStream.toByteArray();
	}
	
	@SuppressWarnings("This method could throw NullPointerException, if current packet type is NOT defined in PacketStruct enumerable! Use on your own.")
	public String[] GetParams()
	{
		return new ArrayList<String>(Arrays.asList(data)).subList(PacketStruct.GetPacketByID(packetID).params.length, data.length).toArray(new String[data.length - PacketStruct.GetPacketByID(packetID).params.length]);
	}
	
	public PacketStruct GetPacketStruct()
	{
		return PacketStruct.GetPacketByID(packetID);
	}
}
