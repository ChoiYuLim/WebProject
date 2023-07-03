package business;

import java.util.ArrayList;
import java.util.List;

import model.Score;

public class GlobalMemoryScoreService implements IScoreService {

    private static ArrayList<Score> scores = new ArrayList<Score>();

    @Override
    public void AddScore(Score score) {
        scores.add(score);
    }

    @Override
    public List<Score> GetScoreList() {
        return scores;
    }

    @Override
    public Score GetScoreById(String id) {
        for (Score score : scores) {
            if (score.getUserId().equals(id)) {
                return score;
            }
        }
        return null;
    }
}
