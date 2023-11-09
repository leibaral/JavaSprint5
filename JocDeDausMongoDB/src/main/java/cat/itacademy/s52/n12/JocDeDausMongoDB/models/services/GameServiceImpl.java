package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Game;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;

import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.GamesNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IGameRepository;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IPlayerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {

    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IGameRepository gameRepository;

    //mètodes CONVERTERS:
    private GameDTO convertGameToDTO(Game game) {
        Optional<Player> player = playerRepository.findById(game.getPlayerId());
        String playerName = player.map(Player::getPlayerName).orElse("Jugador desconegut");

        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
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
        if (!playerOptional.isPresent()){
            throw new PlayerNotFoundException("Jugador amb ID: " + id + " no trobat");
        }
        Game newGame = new Game(id);
        List<Game> oldList = playerOptional.get().getGamesList();
        oldList.add(newGame);
        newGame = gameRepository.save(newGame);
        playerOptional.get().setGamesList(oldList);
        playerRepository.save(playerOptional.get());
        return convertGameToDTO(newGame);

    }

    @Override
    public void removeGamesByPlayer(String id) {
        List<Game> gamesToRemove = ifExistsGames(id);
        gameRepository.findAll()
                .stream()
                .filter(game -> gamesToRemove.contains(game))
                .forEach(game -> gameRepository.delete(game));

        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Jugador amb ID: " + id + " no trobat"));
        player.getGamesList().clear();
        playerRepository.save(player);

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
            List<Game> gamesList = playerById.get().getGamesList();
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

