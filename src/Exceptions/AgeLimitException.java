package Exceptions;

public class AgeLimitException extends Exception{
    private String message;
    public AgeLimitException(String message){
        super(message);
    }
}
