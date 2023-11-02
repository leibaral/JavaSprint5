package cat.itacademy.s52.n12.JocDeDausMongoDB.controllers;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.PlayerDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.GamesNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.Messages;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerDuplicatedException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.services.IGameService;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.services.IPlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/players")
public class GameController {

    private final IPlayerService playerService;
    private final IGameService gameService;

    @Autowired
    public GameController (IPlayerService playerService, IGameService gameService) {
        super();
        this.playerService = playerService;
        this.gameService = gameService;
    }

    /**
     * POST: /players: crea un jugador/a.
     */
    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    @Operation(summary = "Crear nou jugador", description = "Afegeix un nou jugador a la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "406", description = "Player's name not valid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while creating the player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) throws Exception {

        try {
            return new ResponseEntity<>(playerService.createPlayer(playerDTO), HttpStatus.CREATED);
        } catch (PlayerDuplicatedException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while creating the player", e.getCause());
        }
    }

    /**
     * PUT /players: modifica el nom del jugador/a.
     */
    @PutMapping(value = "/update/{player_id}", produces = "application/json", consumes = "application/json")
    @Operation(summary = "Edita el nom d'un jugador", description = "Actualitza un jugador a la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "406", description = "Player's name not valid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while updating the player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<PlayerDTO> updatePlayer(@Parameter(description = "La ID del jugador a editar")
                                                  @PathVariable String player_id, @RequestBody PlayerDTO playerDTO) throws Exception {

        try {
            return new ResponseEntity<>(playerService.editPlayer(player_id, playerDTO), HttpStatus.OK);
        } catch (PlayerNotFoundException | PlayerDuplicatedException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while updating the player", e.getCause());
        }
    }


    /**
     * POST /players/{id}/game : un jugador/a específic realitza una tirada dels daus.
     */
    @PostMapping(value = "/{player_id}/game", produces = "application/json")
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
                                           @PathVariable String player_id) throws Exception {

        try {
            return new ResponseEntity<>(gameService.createGame(player_id), HttpStatus.CREATED);
        } catch (PlayerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while creating the game", e.getCause());
        }
    }


    /**
     * DELETE /players/{id}/games: elimina les tirades del jugador/a.
     */
    @DeleteMapping(value = "/{player_id}/games", produces = "application/json")
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
                                                @PathVariable String player_id, WebRequest request) throws Exception {
        try {
            gameService.removeGamesByPlayer(player_id);
            return ResponseEntity.noContent().build();
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while deleting games history", e.getCause());
        }
    }


    /**
     * GET /players/: retorna el llistat de tots els jugadors/es del sistema amb el seu percentatge mitjà d’èxits.
     */
    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "Llistat de jugadors i % d'encerts", description = "Llistat de tots els jugadors i els seus % d'èxit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players list retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Any players in database yet",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while retrieving players from database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() throws Exception {
        try {
            return new ResponseEntity<>(playerService.getPlayersWithWinRatio(), HttpStatus.OK);
        } catch (PlayerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Unable to retrieve any player with 1 game at least from the database", e.getCause());
        }
    }


    /**
     * GET /players/{player_id}/games: retorna el llistat de jugades per un jugador/a.
     */
    @GetMapping(value = "/{player_id}/games", produces = "application/json")
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
                                                             @PathVariable String player_id, WebRequest request) throws Exception {
        try {
            return new ResponseEntity<>(gameService.getGamesListByPlayer(player_id), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while retrieving the games by player from the database", e.getCause());
        }
    }

    /**
     * GET /players/ranking: retorna el ranking mig de tots els jugadors/es del sistema. És a dir, el percentatge mitjà d’èxits.
     */
    @GetMapping(value = "/ranking", produces = "application/json")
    @Operation(summary = "Percentatge promig d'èxits", description = "Obté el percentatge promig d'èxits de la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players average wins retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Messages.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "404", description = "Games not found in the database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while retrieving the average wins from the database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<Messages> getWinAverage(WebRequest request) throws Exception {

        try {
            return new ResponseEntity<>(new Messages(HttpStatus.OK.value(), LocalDateTime.now(), playerService.getWinningAverage(), request.getDescription(false)), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while retrieving the average wins from the database", e.getCause());
        }
    }


    /**
     * GET /players/ranking/loser: retorna el jugador/a amb pitjor percentatge d’èxit.
     */
    @GetMapping(value = "/ranking/loser", produces = "application/json")
    @Operation(summary = "Obté el jugador amb pitjors resultats", description = "Extreu el jugador amb pitjors resultats de la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Most losing player retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "404", description = "Games not found in the database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while retrieving the most losing player from the database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<PlayerDTO> getLoser() throws Exception {

        try {
            return new ResponseEntity<>(playerService.getTopLoser(), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while retrieving the most losing player from the database", e.getCause());
        }
    }


    /**
     * GET /players/ranking/winner: retorna el jugador amb millor percentatge d’èxit.
     */
    @GetMapping(value = "/ranking/winner", produces = "application/json")
    @Operation(summary = "Obté el jugador amb millors resultats", description = "Extreu el jugador amb millors resultats de la base de dades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Most winner player retrieved successfully", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "404", description = "Games not found in the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Messages.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while retrieving the most winner player from the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Messages.class))})})
    public ResponseEntity<PlayerDTO> getWinner() throws Exception {

        try {
            return new ResponseEntity<>(playerService.getTopWinner(), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Internal Server Error while retrieving the most winner player from the database", e.getCause());
        }
    }


}
