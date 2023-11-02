package cat.itacademy.s52.n11.JocDeDausMySQL.models.services;

import cat.itacademy.s52.n11.JocDeDausMySQL.models.dto.PlayerDTO;
import cat.itacademy.s52.n11.JocDeDausMySQL.models.entities.Player;

import java.util.List;

public interface IPlayerService {
    Player getPlayerById(Long playerID);
    List<Player> getPlayers();
    PlayerDTO createPlayer(PlayerDTO playerDTO);
    PlayerDTO editPlayer(Long playerID, PlayerDTO playerDTO);
    List<PlayerDTO> getPlayersWithWinRatio();
    String getWinningAverage();
    PlayerDTO getTopLoser();
    PlayerDTO getTopWinner();
}
