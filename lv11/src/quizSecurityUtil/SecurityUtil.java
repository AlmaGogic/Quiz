package quizSecurityUtil;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {
	
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String candidate, String hashed) {
		return BCrypt.checkpw(candidate, hashed);
	}
	public static String Hash(String string) {
		return BCrypt.hashpw(string, BCrypt.gensalt());
	}
	
	public static boolean checkHash(String candidate, String hashed) {
		return BCrypt.checkpw(candidate, hashed);
	}
}
