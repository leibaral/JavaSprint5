package cat.itacademy.s52.n11.JocDeDausMySQL.models.exceptions;

public class GamesNotFoundException extends RuntimeException {

    public GamesNotFoundException(String message) {
        super(message);
    }
}
