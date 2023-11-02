package cat.itacademy.s52.n11.JocDeDausMySQL.models.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message){
        super(message);
    }
}
