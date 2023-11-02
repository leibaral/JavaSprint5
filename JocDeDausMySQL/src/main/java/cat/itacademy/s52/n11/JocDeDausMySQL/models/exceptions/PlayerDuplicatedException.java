package cat.itacademy.s52.n11.JocDeDausMySQL.models.exceptions;

public class PlayerDuplicatedException extends RuntimeException {
    public PlayerDuplicatedException(String message){
        super(message);
    }

}
