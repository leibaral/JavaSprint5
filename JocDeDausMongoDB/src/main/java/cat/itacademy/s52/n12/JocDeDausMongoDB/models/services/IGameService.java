package cat.itacademy.s52.n12.JocDeDausMongoDB.models.services;

import cat.itacademy.s52.n12.JocDeDausMongoDB.models.dto.GameDTO;

import java.util.List;

public interface IGameService {
    GameDTO createGame(String _id);

    void removeGamesByPlayer(String _id);

    List<GameDTO> getGamesListByPlayer(String _id);

}
