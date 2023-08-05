package in.sudha.utils;


public class PwdUtils {

	public static String generateRandomPwd() {
		
		  int len = 6;
	        String string = "abcdefghijklmnopqrstuvwxyz1234567890";
	        String result = "";
	        
	        // Setting the Loop
	        for (int i = 0; i < len; i++) {
	            // Generate Password
	            result += string.charAt((int) Math.ceil(Math.random() * string.length() - 1));
	        }
	        
	        // Results
		return result;
		
	}
}
