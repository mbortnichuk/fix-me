package fixmecore.utils;

import javax.validation.constraints.Null;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static String separator = "|";

    // Encryption Handler
    public static String encrypt(String pswd) {
        final byte[] defBytes = pswd.getBytes();
        try {
            final MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
            md5MessageDigest.reset();
            md5MessageDigest.update(defBytes);
            final byte messageDigest[] = md5MessageDigest.digest();
            final StringBuffer hexstr = new StringBuffer();

            for (final byte el : messageDigest) {
                final String hexStr = Integer.toHexString(0xFF & el);
                if (hexStr.length() == 1) {
                    hexstr.append('0');
                }
                hexstr.append(hexStr);
            }
            pswd = hexstr + "";
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exception");
//            e.printStackTrace();
        }
        return pswd;
    }

    // CheckSum Handler
    public static String createCheckSum(String msg) {
        String chs = "CheckSum=";
        String chVal = "";
        chVal = encrypt(msg);
        return (msg + separator + chs + chVal);
    }

    public static boolean validateCheckSum(String msg) {
        if (msg.contains("|")) {
            String originalMsg = msg.substring(0, msg.lastIndexOf("|"));
            String chVerif = msg.substring(msg.lastIndexOf("=") + 1);
            String sumCode = encrypt(originalMsg);
            return (chVerif.equals(sumCode));
        }
        return false;
    }

    public static boolean isNbr(String strNbr) {
        try {
            double nbr = Double.parseDouble(strNbr);
            if (nbr <= 0) {
                throw new NumberFormatException("Input should be positive numbers");
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Number format or Null Pointer Exception");
            return false;
        }
        return true;
    }

}
