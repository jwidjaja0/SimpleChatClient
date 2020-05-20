package sample;

import com.SimpleChat.Messages.Login.LoginFail;
import com.SimpleChat.Messages.Login.SignUpFail;
import com.SimpleChat.Messages.Packet;

public class FailHandler {


    public static String getSignupFailCause(SignUpFail signUpFail){
        String message = "Unknown error";
        int failNumber = signUpFail.getFailCause();
        switch (failNumber){
            case -1:
                message = "Wrong password/Username doesn't exist";
                break;
            case -2:
                message = "Account inactive";
                break;
        }
        return message;
    }
    public static String getLoginFailCause(LoginFail loginFail){
        String message = "Unknown error";
        int failNumber = loginFail.getFailCause();
        switch(failNumber){
            case -1:
                message = "Username does not exist";
                break;
            case -2:
                message = "Wrong password";
                break;
            case -3:
                message = "Inactive account";
                break;
        }
        return message;
    }

}
