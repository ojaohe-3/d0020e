package Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static final String SALT = "RQD-I`&KMDJQQ]=B5=VKp!5FG+.d(!<:##>S*eTox@9ad>~O)|n3pA%0a}n+oPJr";
    /**
     * Hash input given string
     * @param input input string, string will be salted
     * @return Hashed string
     */
    public static String generateHash(String input) throws NoSuchAlgorithmException {
        input = SALT+input;
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
            throw e;
        }

        return hash.toString();
    }
}

