package business;

import java.util.List;

import model.Score;

public interface IScoreService {
    public List<Score> GetScoreList();

    public void AddScore(Score score);
}
