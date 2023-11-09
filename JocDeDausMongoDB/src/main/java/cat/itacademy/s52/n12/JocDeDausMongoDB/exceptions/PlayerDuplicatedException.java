package cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions;

public class PlayerDuplicatedException extends RuntimeException {
    public PlayerDuplicatedException(String message){
        super(message);
    }

}
