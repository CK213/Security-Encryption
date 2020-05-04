import STS.ClientHandle;
import STS.DH;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import org.junit.Assert.*;

public class c3291914A3 {
    public static void main(String[] args) throws Exception
    {
        //RSA
        RSA rsa = new RSA();
        DataInputStream in = new DataInputStream(System.in);
        String teststring;
        System.out.println("Enter the plain text:");
        teststring = in.readLine();
        System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: "
                + rsa.bytesToString(teststring.getBytes()));
        // encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypting Bytes(RSA): " + rsa.bytesToString(decrypted));
        System.out.println("Decrypted String(RSA): " + new String(decrypted));

    //DES
        in = new DataInputStream(System.in);
        String line;
        System.out.println("Enter the plain text:");
        line = in.readLine();

        byte[] codedtext = new TripleDES().encrypt(line);
        String decodedtext = new TripleDES().decrypt(codedtext);
        // this is a byte array, you'll just see a reference to an array
        System.out.println("Encrypted code(DES): " + codedtext);
        // This correctly shows plain text inputted.
        System.out.println("Original text(DES): " + decodedtext);

        //STS
        String host = "localhost";
        int port = 4444;
        int clientCount = 0;
        Socket socket = new Socket(host, port);
        DH dh = new DH();
        in = new DataInputStream(System.in);
        System.out.println("Enter the plain text:");
        line = in.readLine();
        while(line != null){
            line =  Base64.getEncoder().encodeToString(dh.encrypt(line.getBytes()));
            System.out.println(line);
        }
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();

        ServerSocket server = new ServerSocket(port);
        while(true){
            socket = server.accept();
            new Thread(new ClientHandle(socket, ++clientCount)).start();
        }

        //CTR with AES
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        SecureRandom random = new SecureRandom();
        IvParameterSpec ivSpec = CTR.createCtrIvForAES(1, random);
        Key key2 = CTR.createKeyForAES(256, random);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
        String input = "input1234567";
        cipher.init(Cipher.ENCRYPT_MODE, key2, ivSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());

        cipherText[9] ^= '0' ^ '9';
        cipher.init(Cipher.DECRYPT_MODE, key2, ivSpec);
        byte[] plainText = cipher.doFinal(cipherText);

        System.out.println("plain text(CTR): " + new String(plainText));
    }
}
