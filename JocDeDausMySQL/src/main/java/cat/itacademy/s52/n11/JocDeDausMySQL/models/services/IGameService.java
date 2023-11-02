package cat.itacademy.s52.n11.JocDeDausMySQL.models.services;

import cat.itacademy.s52.n11.JocDeDausMySQL.models.dto.GameDTO;

import java.util.List;

public interface IGameService {
    GameDTO createGame(Long playerID);

    void removeGamesByPlayer(Long playerID);

    List<GameDTO> getGamesListByPlayer(Long playerID);

}
