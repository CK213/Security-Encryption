import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class CTR{

        public static SecretKey createKeyForAES(int bitLength, SecureRandom random)
                throws NoSuchAlgorithmException, NoSuchProviderException {
            KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");

            generator.init(128, random);

            return generator.generateKey();
        }

        public static IvParameterSpec createCtrIvForAES(int messageNumber, SecureRandom random) {
            byte[] ivBytes = new byte[16];
            random.nextBytes(ivBytes);
            ivBytes[0] = 1;
            ivBytes[1] = 2;
            ivBytes[2] = 3;
            ivBytes[3] = 4;

            for (int i = 0; i != 7; i++) {
                ivBytes[8 + i] = 0;
            }
            ivBytes[15] = 1;
            return new IvParameterSpec(ivBytes);
        }

    }