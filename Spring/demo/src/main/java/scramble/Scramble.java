package scramble;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.*;

//shoving all the crypto functionality into this class
public class Scramble {


	private static Cipher c;
	private static SecretKeySpec sks;
	private static MessageDigest md = null;
	private static byte[] sha;
	private static final String KEY = "DEADBEEF";

	private static int hasInit = 0;
	//DEADBEEF seems to be a garbage key for raisn's, look up how to pad 

	//should only run once; hasInit acts as a singleton switch of sorts
	public static void init(){
		try{
			if(Scramble.hasInit==0){
				c = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				Scramble.sha= DatatypeConverter.parseHexBinary(Scramble.KEY);
				Scramble.md = MessageDigest.getInstance("SHA-1");
				sha = Scramble.md.digest(sha);
				sha = Arrays.copyOf(sha, 16);
				Scramble.sks = new SecretKeySpec(sha, "AES");
				Scramble.hasInit = 1;
			}
		}catch (Exception e){
			System.out.println("Error: "+e.getMessage());
		}
	}
	//encodes a string to base64 (mode = 0) or decodes to UTF-8 (mode = 1)
	public static String codec(String input, int mode){
		try{
			String out = input;
			if(mode==0)
			{
				Encoder e = Base64.getEncoder();
				String out64 = e.encodeToString(out.getBytes());
				return out64;
			}
			else if(mode==1){
				Decoder d = Base64.getDecoder();
				byte[] b = d.decode(out);
				String outUTF = new String(b, "UTF-8");
				return outUTF;
			}else{
				throw new Exception("Invalid mode ("+mode+").");
			}
		}catch (Exception e)
		{
			System.out.println("Error: "+e.toString());
		}
		return null;
	}

	
	//since we're using a predetermined key and thus have some fixed constants to design around (namely the key is a hex value), some sloppy code will ensue.
	//AFAIK the only way to really get AES working is to do some b64 tomfoolery to ensure the text is a multiple of 16 bytes, while also SHA-1 hashing the key
	//to get a 16 byte version in a consistent fashion. 
	public static String doAES(String input, int mode) throws Exception{
		Scramble.init();
		
		if(mode == 0){
			c.init(Cipher.ENCRYPT_MODE, Scramble.sks, c.getParameters());
			System.out.println("ENCRYPTING");
			byte[] encode = c.doFinal(input.getBytes("UTF-8"));
			String encryptedText = Base64.getEncoder().encodeToString(encode);
			return encryptedText;
		}else if(mode == 1){
			c.init(Cipher.DECRYPT_MODE, Scramble.sks, c.getParameters());
			System.out.println("DECRYPTING");
			byte[] decode = Base64.getDecoder().decode(input);
			String decryptedText = new String(c.doFinal(decode));
			return decryptedText;
		}else{
			throw new Exception("Invalid mode ("+mode+").");
		}
	}


	//random assorted testing of stuff
	public static void main (String[] args){
		try{
			String e = Scramble.doAES("Shazbot", 0);
			System.out.println(e);
			String d = Scramble.doAES(e, 1);
			System.out.println(d);
		}catch(Exception ex){
			System.out.println("ERROR: "+ex.toString());
		}
	}
}
