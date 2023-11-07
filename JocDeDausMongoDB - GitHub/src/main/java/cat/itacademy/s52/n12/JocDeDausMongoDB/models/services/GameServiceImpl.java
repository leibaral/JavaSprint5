package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Game;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;

import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.GamesNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IGameRepository;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IPlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IGameRepository gameRepository;

    //mètodes CONVERTERS:
    private GameDTO convertGameToDTO(Game game) {
        Optional<Player> player = playerRepository.findById(game.getPlayerId());
        String playerName = player.map(Player::getPlayerName).orElse("Jugador desconegut");

        GameDTO gameDTO = new GameDTO();
        gameDTO.set_id(game.get_id());
        gameDTO.setDice1(game.getDice1());
        gameDTO.setDice2(game.getDice2());
        gameDTO.setResult(game.getResult());
        gameDTO.setPlayerName(playerName);
        return gameDTO;
    }

    private List<GameDTO> convertGameListToDTO(List<Game> gamesList) {
        return gamesList.stream().map(this::convertGameToDTO).collect(Collectors.toList());
    }
    //fi dels CONVERTERS

    @Override
    public GameDTO createGame(String id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Game newGame = new Game(id);
            newGame = gameRepository.save(newGame);
            return convertGameToDTO(newGame);
        } else {
            throw new PlayerNotFoundException("Jugador amb ID: " + id + " no trobat");
        }
    }


    /**
     * Anotació @Transactional indispensable perquè el mètode elimini tots els games del player especificat de la base de dades.
     * És necessària perquè l'eliminació de tots els jocs de l'historial de jocs d'un jugador és una operació que pot afectar a
     * múltiples registres en la base de dades.
     * Utilitzant l'anotació garantim que l'operació d'eliminar es realitzi de manera atòmica, que significa que
     * es compromet o es desfà en cas que es produeixi una excepció.
     * Així s'evita que es faci una eliminació parcial o incompleta que podria deixar la base de dades en un estat inconsistent.
     * Per tant, és indispensable perquè l'eliminació es dugui a terme de manera segura i consistent en la base de dades.
     */
    @Override
    @Transactional
    public void removeGamesByPlayer(String id) {
        List<Game> gamesToRemove = ifExistsGames(id);
        gamesToRemove.clear();
    }

    @Override
    public List<GameDTO> getGamesListByPlayer(String id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player != null) {
            return convertGameListToDTO(ifExistsGames(id));
        }
        throw new PlayerNotFoundException("Jugador amb ID: " + id + " no existent");
    }

    /**
     * Mètode per validar l'existència de games per player a la base de dades.
     * S'utilitza en els mètodes removeGamesByPlayer() i getGamesListByPlayer().
     */
    public List<Game> ifExistsGames(String id) {
        Optional<Player> playerById = playerRepository.findById(id);
        if (playerById.isPresent()) {
            List<Game> gamesList = gameRepository.findByPlayerId(id);
            if (gamesList.isEmpty()) {
                throw new GamesNotFoundException("El jugador amb ID" + id + " no té cap joc.");
            } else {
                return gamesList;
            }
        } else {
            throw new PlayerNotFoundException("Jugador amb ID: " + id + " no trobat");
        }
    }

}

