package code;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: manoj
 * Date: 17/8/11
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */

public class AESImplementation {

  public static SecretKeySpec getKeySpec() throws IOException, NoSuchAlgorithmException {
	byte[] bytes = new byte[16];
	File f = new File("aes_key");
	SecretKey key = null;
	SecretKeySpec spec = null;

	if (f.exists()) {
	  new FileInputStream(f).read(bytes);
	} else {
	   KeyGenerator kgen = KeyGenerator.getInstance("AES");
	   kgen.init(256);
	   key = kgen.generateKey();
	   bytes = key.getEncoded();
	   new FileOutputStream(f).write(bytes);
	}

    System.out.println("======= key |" + bytes + "|");

	spec = new SecretKeySpec(bytes,"AES");
	return spec;
  }
  public static String encrypt(String text) throws Exception {
	SecretKeySpec spec = getKeySpec();
	Cipher cipher = Cipher.getInstance("AES");
	cipher.init(Cipher.ENCRYPT_MODE, spec);
	BASE64Encoder enc = new BASE64Encoder();
    String cipherText = enc.encode(cipher.doFinal(text.getBytes()));
	System.out.println("===== encrypted text:|" + enc.encode(cipher.doFinal(text.getBytes())) + "|");

    return cipherText;
  }

  public static String decrypt(String text) throws Exception {
	SecretKeySpec spec = getKeySpec();
	Cipher cipher = Cipher.getInstance("AES");
	cipher.init(Cipher.DECRYPT_MODE, spec);
	BASE64Decoder dec = new BASE64Decoder();
	System.out.println("======= decrypted text |" + new String(cipher.doFinal(dec.decodeBuffer(text))) + "|");

    return new String(cipher.doFinal(dec.decodeBuffer(text)));

  }

  public static void main(String[] args) throws Exception {
	String mode = "decrypt";
	String text = "manojkumar";

	if (mode.equals("encrypt")) {
      System.out.println("======= given plain text:" + text);
	  encrypt(text);
	} else if (mode.equals("decrypt")) {
      System.out.println("======= text to be decrypted:" + text);
	  decrypt("DnXS8uDwisUPFrxvoMp5xA==");
	}

  }
}