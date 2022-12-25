import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Main {

  public static void main(String[] args) throws Exception {

    Main cryptoRSAUtil = new Main();
    String encoded2 = "INcO2tubDerRXtnKAXzTfAPzVWabuUdIOPwk1K6O7Y+Jt1iLItn3ftvjVbuOvAfmlSQsH/tQ7kujktQ00Tk2f9ME/D2lhZcAMggqc8pBdyjfBGzw4/W4se1XVRvb771VYbt69rqVGfyDMRoYrjabRYCSBihNOj9M0Q3w4SaSZC0=";
    String decode = cryptoRSAUtil.decode(encoded2);
    System.out.println(decode);

  }

  public String decode(String toDecode) throws Exception {

    PrivateKey privateKey = loadPrivateKey();

    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(toDecode));
    return new String(bytes);

  }

  private PrivateKey loadPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

    // reading from resource folder
    byte[] privateKeyBytes = getClass().getResourceAsStream("/private_key.pk8").readAllBytes();

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
    return privateKey;
  }

}