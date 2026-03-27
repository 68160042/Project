public class LeaderboardEntry {
    private String playerName;
    private String difficulty;
    private int totalQuestions;
    private int score;
    private double time;
    private int maxCombo;

    public LeaderboardEntry(String playerName, String difficulty, int totalQuestions,
                            int score, double time, int maxCombo) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.time = time;
        this.maxCombo = maxCombo;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public double getTime() {
        return time;
    }

    public int getMaxCombo() {
        return maxCombo;
    }
}