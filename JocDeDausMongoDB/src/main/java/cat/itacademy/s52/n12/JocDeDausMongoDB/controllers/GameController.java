package cat.itacademy.s52.n12.JocDeDausMongoDB.controllers;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.GamesNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.Messages;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.services.GameServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    @Autowired
    private final GameServiceImpl gameService;


    /**
     * POST /games/{id}/game : un jugador/a específic realitza una tirada dels daus.
     */
    @PostMapping(value = "/{id}/game", produces = "application/json")
    @Operation(summary = "Nova jugada", description = "Afegeix una jugada del jugador X a la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while creating the game",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<GameDTO> newGame(@Parameter(description = "La ID del jugador que llença els daus")
                                           @PathVariable("id") String id) {

        return new ResponseEntity<>(gameService.createGame(String.valueOf(id)), HttpStatus.CREATED);

    }


    /**
     * DELETE /games/{id}/games: elimina les tirades del jugador/a.
     */
    @DeleteMapping(value = "/{id}/games", produces = "application/json")
    @Operation(summary = "Esborra totes les jugades d'un jugador", description = "Esborra totes les jugades d'un jugador de la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games removed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "404", description = "Games not found by player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while deleting the player's games history",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<Messages> deleteGames(@Parameter(description = "La ID del jugador a qui esborrar les jugades")
                                                @PathVariable("id") String id, WebRequest request) throws Exception {
        try {
            gameService.removeGamesByPlayer(String.valueOf(id));
            return ResponseEntity.noContent().build();
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while deleting games history", e.getCause());
        }
    }

    /**
     * GET /games/{id}/games: retorna el llistat de jugades per un jugador/a.
     */
    @GetMapping(value = "/{id}/games", produces = "application/json")
    @Operation(summary = "Llistat de jugades per a un jugador", description = "Llistat de totes les jugades d'un jugador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games list retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "No games for this player yet",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while retrieving the games by player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<List<GameDTO>> getAllGamesByPlayer(@Parameter(description = "La ID del jugador de qui veure les jugades")
                                                             @PathVariable("id") String id, WebRequest request) throws Exception {
        try {
            return new ResponseEntity<>(gameService.getGamesListByPlayer(String.valueOf(id)), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while retrieving the games by player from the database", e.getCause());
        }
    }
}
