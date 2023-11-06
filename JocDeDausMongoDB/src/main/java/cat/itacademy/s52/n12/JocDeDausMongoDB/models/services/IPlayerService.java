package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;


import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.PlayerDTO;
import cat.itacademy.s52.n12.JocDeDausMongoDB.models.entities.Player;

import java.util.List;

public interface IPlayerService {


    List<Player> getPlayers();
    PlayerDTO createPlayer(PlayerDTO playerDTO);
    PlayerDTO editPlayer(String id, PlayerDTO playerDTO);
    List<PlayerDTO> getPlayersWithWinRatio();
    String getWinningAverage();
    PlayerDTO getTopLoser();
    PlayerDTO getTopWinner();
}
