package ci.ten.config.exception;

public class multipleUsersException extends RuntimeException {

    public multipleUsersException(){

    }
    public  multipleUsersException(String message){
        super(message);
    }
    public multipleUsersException(String message, Throwable cause){
            super(message, cause);
    }
    public  multipleUsersException(Throwable cause){
        super(cause);
    }

}
