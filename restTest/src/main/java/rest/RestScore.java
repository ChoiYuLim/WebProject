package rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;
import business.GlobalMemoryScoreService;
import business.IScoreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Score;

@WebServlet("/score/*")
public class RestScore extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private IScoreService scoreService = new GlobalMemoryScoreService();

    public void setScoreService(IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all scores
            List<Score> scores = scoreService.GetScoreList();
            response.setContentType(MediaType.APPLICATION_JSON);
            for (Score score : scores) {
                response.getWriter().println(score.getUserId() + " " + score.getScore());
            }

        } else if (pathInfo.startsWith("/add/")) {
            // Add a new score
            String[] parts = pathInfo.split("/");
            if (parts.length == 4) {
                String id = parts[2];
                int scoreValue = Integer.parseInt(parts[3]);
                Score existingScore = scoreService.GetScoreById(id);
                if (existingScore != null) {
                    int currentScore = existingScore.getScore();
                    int newScore = currentScore + scoreValue;
                    existingScore.setScore(newScore);
                } else {
                    scoreService.AddScore(new Score(id, scoreValue));
                }
                response.setContentType(MediaType.APPLICATION_JSON);
                response.getWriter().println("[{\"Result\":\"true\"}]");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
