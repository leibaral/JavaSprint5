package cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message){
        super(message);
    }
}
