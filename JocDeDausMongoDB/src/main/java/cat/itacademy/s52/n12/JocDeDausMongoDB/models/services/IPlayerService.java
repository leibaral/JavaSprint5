package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;


import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.PlayerDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;

import java.util.List;

public interface IPlayerService {
    Player getPlayerById(String playerID);
    List<Player> getPlayers();
    PlayerDTO createPlayer(PlayerDTO playerDTO);
    PlayerDTO editPlayer(String playerID, PlayerDTO playerDTO);
    List<PlayerDTO> getPlayersWithWinRatio();
    String getWinningAverage();
    PlayerDTO getTopLoser();
    PlayerDTO getTopWinner();
}
