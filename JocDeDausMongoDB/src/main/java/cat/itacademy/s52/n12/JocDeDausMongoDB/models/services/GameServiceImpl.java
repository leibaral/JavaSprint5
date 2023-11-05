package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.PlayerDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Game;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Counter;

import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.GamesNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerDuplicatedException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IGameRepository;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IPlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements IGameService, IPlayerService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private CounterService counter;

    //mètodes CONVERTERS:
    private PlayerDTO convertPlayerToDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    private Player convertDTOToPlayer(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }

    private GameDTO convertGameToDTO(Game game) {
        return modelMapper.map(game, GameDTO.class);
    }

    private List<GameDTO> convertGameListToDTO(List<Game> gamesList) {
        return gamesList.stream().map(this::convertGameToDTO).collect(Collectors.toList());
    }
    //fi dels CONVERTERS

    @Override
    public GameDTO createGame(String _id) {
        Player playerBy_id = playerRepository.findBy_id(_id);
        Game newGame = new Game(playerBy_id);
        playerBy_id.addGame(newGame);
        gameRepository.save(newGame);

        return convertGameToDTO(newGame);
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
    public void removeGamesByPlayer(String _id) {
        List<Game> gamesToRemove = ifExistsGames(_id);
        gamesToRemove.clear();
    }

    @Override
    public List<GameDTO> getGamesListByPlayer(String _id) {
        Player player = playerRepository.findBy_id(_id);
        if (player != null) {
            return convertGameListToDTO(ifExistsGames(_id));
        }
        throw new PlayerNotFoundException("Jugador amb ID: " + _id + " no existent");
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            throw new PlayerNotFoundException("Encara no hi ha cap jugador a la base de dades");
        }
        return players;
    }

    @Override
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        PlayerDTO playerValidated = validatePlayerName(playerDTO);
        Player playerSaved = playerRepository.save(convertDTOToPlayer(playerValidated));
        PlayerDTO playerDTOToReturn = convertPlayerToDTO(playerSaved);
        playerDTOToReturn.setWinRate("Cap joc jugat encara");

        return playerDTOToReturn;
    }

    @Override
    public PlayerDTO editPlayer(String _id, PlayerDTO playerDTO) {
        Player playerToUpdate = playerRepository.findBy_id(_id);
        PlayerDTO playerValidated = validatePlayerName(playerDTO);
        playerToUpdate.setPlayerName(playerValidated.getPlayerName());
        Player playerUpdated = playerRepository.save(playerToUpdate);

        return obtainWinRate(playerUpdated);
    }

    @Override
    public List<PlayerDTO> getPlayersWithWinRatio() {
        List<Player> allPlayers = getPlayers();
        List<PlayerDTO> allPlayersDTO = new ArrayList<>();
        allPlayers.forEach(p -> allPlayersDTO.add(obtainWinRate(p)));
        return allPlayersDTO;
    }

    @Override
    public String getWinningAverage() {
        List<PlayerDTO> playersWhoPlayed = playersWhoPlayed();
        if (playersWhoPlayed.isEmpty()) {
            return "No hi ha dades disponibles en aquest moment.";
        }
        double totalWinPercentage = playersWhoPlayed.stream().mapToDouble(p -> Double.parseDouble(p.getWinRate().replace(",", "."))).sum();
        double winningAverage = totalWinPercentage / playersWhoPlayed.size();
        return "Percentatge de jocs guanyats per jugador: " + String.format("%.2f", winningAverage);
    }

    @Override
    public PlayerDTO getTopLoser() {
        List<PlayerDTO> playersWhoPlayed = playersWhoPlayed();
        return playersWhoPlayed.stream()
                .min(Comparator.comparingDouble(p -> Double.parseDouble(p.getWinRate().replace(",", ".")))).get();
    }

    @Override
    public PlayerDTO getTopWinner() {
        List<PlayerDTO> playersWhoPlayed = playersWhoPlayed();
        return playersWhoPlayed.stream()
                .max(Comparator.comparingDouble(p -> Double.parseDouble(p.getWinRate().replace(",", ".")))).get();
    }


    /**
     * Mètode per comprovar si el nom del Player és "ANONIM" o és únic.
     * S'utilitza en els mètodes createPlayer() i editPlayer().
     */
    public PlayerDTO validatePlayerName(PlayerDTO playerDTO) {
        if (playerDTO.getPlayerName()==null || playerDTO.getPlayerName().isEmpty()) {
            playerDTO.setPlayerName("ANONIM");
            playerDTO.set_id(String.valueOf(counter.getNextPlayerSequence()));
            return playerDTO;
        }
        if (playerRepository.existsByPlayerName(playerDTO.getPlayerName())) {
            throw new PlayerDuplicatedException("Escull un altre nom. Aquest ja està en ús.");
        }
        playerDTO.set_id(String.valueOf(counter.getNextPlayerSequence()));
        return playerDTO;
    }

    /**
     * Mètode encarregat de calcular el percentatge de victòries del Player.
     * S'utilitza en el mètode editPlayer() i getPlayersWithWinPercentage().
     */
    public PlayerDTO obtainWinRate(Player player) {
        Player playerValidated = ifExistsPlayer(player);
        PlayerDTO playerDTOToReturn = convertPlayerToDTO(playerValidated);

        if (playerValidated.getGamesList().isEmpty()) {
            playerDTOToReturn.setWinRate("Percentage incalculable: cap joc jugat");
        } else {
            long totalGames = playerValidated.getGamesList().size();
            long totalWins = playerValidated.getGamesList().stream()
                    .filter(g -> g.getResult() == Game.ResultGame.WON_GAME).count();
            double winPercentage = ((double) totalWins / totalGames) * 100.0d;
            playerDTOToReturn.setWinRate(String.format("%.2f", winPercentage));
        }
        return playerDTOToReturn;
    }

    /**
     * Mètode per validar l'existència del Player a la base de dades.
     * S'utilitza en el mètode obtainWinPercentage().
     */
    public Player ifExistsPlayer(Player player) {
        if (playerRepository.existsBy_id(player.get_id())) {
            return player;
        } else {
            throw new PlayerNotFoundException("Jugador no trobat a la base de dades");
        }
    }

    /**
     * Mètode per validar l'existència de games per player a la base de dades.
     * S'utilitza en els mètodes removeGamesByPlayer() i getGamesListByPlayer().
     */
    public List<Game> ifExistsGames(String _id) {
        Player playerById = playerRepository.findBy_id(_id);
        if (playerById.getGamesList().isEmpty()) {
            throw new GamesNotFoundException(playerById.getPlayerName() + " no té cap joc.");
        } else {
            return playerById.getGamesList();
        }
    }

    /**
     * Mètode per obtenir només els jugadors que han jugat.
     * S'utilitza en els mètodes getWinningAverage(), getTopLoser() i getTopWinner().
     */
    public List<PlayerDTO> playersWhoPlayed() {
        List<PlayerDTO> allPlayersDTO = getPlayersWithWinRatio();
        if (gameRepository.isEmpty()) {
            throw new GamesNotFoundException("Cap joc jugat a la base de dades");
        }
        return allPlayersDTO.stream().filter(p -> !p.getWinRate().equals("Cap joc jugat encara")
                && !p.getWinRate().equals("Percentage incalculable: cap joc jugat")).collect(Collectors.toList());
    }

    /**
     * Generem una tirada de qualsevol dau amb un valor aleatori.
     */
    public static short rollDice() {
        Random random = new Random();
        return (short) (random.nextInt(6) + 1); //el "bound" o límit és exclusiu, el zero no.
    }

    /**
     * Obtenim el resultat de la partida,
     * condicionem la regla per guanyar (sumar 7)
     * i assignem l'enum de Game: WON_GAME o LOST_GAME.
     */
    public static Game.ResultGame obtainResult(short dice1, short dice2) {
        if (dice1 + dice2 == 7) {
            return Game.ResultGame.WON_GAME;
        } else {
            return Game.ResultGame.LOST_GAME;
        }
    }

}

