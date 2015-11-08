package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by whdinata on 11/8/15.
 */
public class Validator {
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    public static boolean isEmailValid(String email){
        if(email.equals("")){
            return false;
        }

        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean isPhoneNumberValid(String phoneNumber){
        if(phoneNumber.equals("")){
            return false;
        }

        for(int i = 0; i < phoneNumber.length(); i++){
            if(Character.isLetter(phoneNumber.charAt(i))){
                return false;
            }
        }

        return true;
    }

    public static boolean isPasswordValid(String password){
        if(password.equals("")){
            return false;
        }

        Matcher matcher = PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }
}
