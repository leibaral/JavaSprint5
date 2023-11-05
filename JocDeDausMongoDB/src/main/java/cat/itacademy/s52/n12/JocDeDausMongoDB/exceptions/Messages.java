package cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Messages {

    @Schema(description = "Http Status", example = "201")
    private int statusCode;

    @Schema(description = "Date and time", example = "2023-10-18T18:13:21.274+00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Description message", example = "Player created and saved successfully into the database")
    private String textMessage;

    @Schema(description = "Uri path", example = "uri=/players/add")
    private String description;

    public Messages(String textMessage) {
        this.textMessage = textMessage;
    }

}
