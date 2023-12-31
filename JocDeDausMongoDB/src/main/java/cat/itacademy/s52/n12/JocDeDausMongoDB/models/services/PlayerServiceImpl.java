package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.GamesNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerDuplicatedException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.exceptions.PlayerNotFoundException;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.PlayerDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Game;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IGameRepository;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.repositories.IPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IGameRepository gameRepository;


    //mètodes CONVERTERS:
    private PlayerDTO convertPlayerToDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    private Player convertDTOToPlayer(PlayerDTO playerDTO) {
        playerDTO.setRegistration(LocalDateTime.now());
        // Obtenir l'últim jugador de la col·lecció
        Player lastPlayer = playerRepository.findFirstByOrderByIdDesc();
        // Calcular el nou identificador
        int nextId = (lastPlayer != null) ? Integer.parseInt(lastPlayer.getId()) + 1 : 1;
        // Assignar el nou identificador al jugador
        playerDTO.setId(String.valueOf(nextId));
        Player player = modelMapper.map(playerDTO, Player.class);
        return player;
    }

    //fi dels CONVERTERS


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
    public PlayerDTO editPlayer(String id, PlayerDTO playerDTO) {

        PlayerDTO playerValidated = validatePlayerName(playerDTO);
        Optional<Player> playerToUpdate = playerRepository.findById(id);
        if (playerToUpdate.isPresent()){
            Player player = playerToUpdate.get();
            player.setPlayerName(playerValidated.getPlayerName());
            playerRepository.save(player);
            return obtainWinRate(id);
        }
        throw new PlayerNotFoundException("Jugador amb ID: " + id + " no trobat");

    }

    @Override
    public List<PlayerDTO> getPlayersWithWinRatio() {
        List<Player> allPlayers = getPlayers();
        List<PlayerDTO> allPlayersDTO = new ArrayList<>();
        for (Player player : allPlayers) {
            PlayerDTO playerDTO = obtainWinRate(player.getId());
            allPlayersDTO.add(playerDTO);
        }
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
        // validem que no es posi cap nom
        if (playerDTO.getPlayerName()==null || playerDTO.getPlayerName().isEmpty()) {
            playerDTO.setPlayerName("ANONIM");
            return playerDTO;
        }
        // invalidem que el nom es pugui repetir (no es pot)
        if (playerRepository.existsByPlayerName(playerDTO.getPlayerName())) {
            throw new PlayerDuplicatedException("Escull un altre nom. Aquest ja està en ús.");
        } else {
        // si el nom que es posa no és igual a cap dels que hi ha, validem playerDTO
            return playerDTO;
        }
    }

    /**
     * Mètode encarregat de calcular el percentatge de victòries del Player.
     * S'utilitza en el mètode editPlayer() i getPlayersWithWinPercentage().
     */
    public PlayerDTO obtainWinRate(String id) {
        Optional<Player> playerOptional = playerRepository.findById(id);

        if (!playerOptional.isPresent()) {
            throw new PlayerNotFoundException("Jugador amb ID: " + id + " no trobat.");
        }
        Player playerValidated = playerOptional.get();
        PlayerDTO playerDTOToReturn = convertPlayerToDTO(playerValidated);

        List<Game> gamesList = gameRepository.findByPlayerId(id);

        if (gamesList.isEmpty()) {
            playerDTOToReturn.setWinRate("Percentage incalculable: cap joc jugat");
        } else {
            long totalGames = gamesList.size();
            long totalWins = gamesList.stream()
                    .filter(g -> g.getResult() == Game.ResultGame.WON_GAME).count();
            double winPercentage = ((double) totalWins / totalGames) * 100.0d;
            playerDTOToReturn.setWinRate(String.format("%.2f", winPercentage));
        }
        return playerDTOToReturn;
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

}

