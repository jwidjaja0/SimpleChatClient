package sample;

import com.SimpleChat.Messages.Login.SignUpFail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FailHandlerTest {

    @Test
    void testSignUpFail() {
        SignUpFail signUpFail = new SignUpFail(-1);
        String message = FailHandler.getSignupFailCause(signUpFail);
        assertEquals(message, "Username already exist!");
    }
}