package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;

import java.util.List;

public interface IGameService {
    GameDTO createGame(String playerID);

    void removeGamesByPlayer(String playerID);

    List<GameDTO> getGamesListByPlayer(String playerID);

}
