package sample;

public class FailHandler {

    public static String handleFail(int failNumber){
        String message = "Unknown error";
        switch (failNumber){
            case -1:
                message = "Username already exist!";
        }

        return message;
    }

}
