package an0nym8us.api.messaging;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


/** Class which is providing easy-access to cryptography features
 * @author An0nym8us
 *
 */
public class CryptoStance
{
	public static SecretKey key;
	public static byte[] IV;
	
	public static SecretKey GenerateKey()
	{
		try
		{
			KeyGenerator kg = KeyGenerator.getInstance("AES"); 
			SecretKey k = kg.generateKey();
			return k;
		}
		catch(NoSuchAlgorithmException ex)
		{
			return null;
		}
	}
	
	public static byte[] GenerateIV()
	{
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		
		return iv;
	}
	
	public static byte[] Encrypt(SecretKey key, byte[] iv, byte[] data) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{ 
		  Cipher c = Cipher.getInstance("AES/CFB8/NoPadding"); 
		  c.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv)); 
		  
		  return c.doFinal(data);
	}
	
	public static byte[] Decrypt(SecretKey key, byte[] iv, byte[] data) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		  Cipher c = Cipher.getInstance("AES/CFB8/NoPadding"); 
		  c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv)); 
		  
		  return c.doFinal(data);
	}
}
