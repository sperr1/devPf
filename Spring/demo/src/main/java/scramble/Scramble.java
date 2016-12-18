package scramble;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Base64.*;

//shoving all the crypto functionality into this class
public class Scramble {


	private Cipher c;
	private SecretKeySpec s;
	
	//DEADBEEF seems to be a garbage key for raisn's, look up how to pad 
	public Scramble() throws NoSuchAlgorithmException, NoSuchPaddingException{
		this.c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		this.s = new SecretKeySpec(Integer.toString(0xDEADBEEF).getBytes(), 0, Integer.toString(0xDEADBEEF).length(), "AES"); //figure out how to shove DEADBEEF key in
	}
	
	//to be honest, the two following functions could likely be combined into a single large branching function
	
	//convert a string to base64
	public String toBase64(String input){
		String out = input;
		Encoder e = Base64.getEncoder();
		String out64 = e.encodeToString(out.getBytes());
		return out64;
	}
	
	//convert base64 to string (using UTF-8 encoding)
	public String toNormal(String input) throws UnsupportedEncodingException{
		String out64 = input;
		Decoder d = Base64.getDecoder();
		byte[] b = d.decode(out64);
		String out = new String(b, "UTF-8");
		return out;
	}
	
	//this was once two separate functions, but then I just combined the two. I expect it to blow up as is, though.
	public String doAES(String input, int mode) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
		if(mode == 0){
			this.c.init(Cipher.ENCRYPT_MODE, this.s, c.getParameters());
			System.out.println("ENCRYPTING");
		}else if(mode == 1){
			this.c.init(Cipher.DECRYPT_MODE, this.s, c.getParameters());
			System.out.println("DECRYPTING");
		}else{
			throw new Error("invalid mode.");
		}
		byte[] ciphertext = this.c.doFinal(input.getBytes());
		String outCipher = new String(ciphertext, "UTF-8");
		return outCipher;
	}
	
	
	//random assorted testing of stuff
	public static void main (String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		Scramble s = new Scramble();
		String bStr = s.toBase64("Shazbot000"); //whatever we're encrypting needs to be padded out to a multiple of 16
		System.out.println(bStr+", "+bStr.length());
		String nStr = s.toNormal(s.toBase64("Shazbot123456789"));
		System.out.println(nStr+", "+nStr.length());
		String hStr = Integer.toHexString(Integer.valueOf(0xDEADBEEF));
		System.out.println(hStr.getBytes().length);
		for(int i = hStr.getBytes().length -1 ; i>=0; i--){
			System.out.print(hStr.getBytes()[i] + " ");
		}
		System.out.println();
		String cT = s.doAES(bStr, 0);
		System.out.println(cT);
		String dT = s.doAES(cT, 1);
		System.out.println(dT);
	}
}
