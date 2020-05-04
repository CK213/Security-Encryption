public abstract class Cipher
{
    public Cipher() throws Exception
    {
    }
    public abstract byte[] encrypt(byte[] data) throws Exception;
    public abstract void decrypt(byte[] data) throws Exception;
    public abstract byte[] decrypt(byte[] data, int offset) throws Exception;
}