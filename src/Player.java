public class Player implements Scorable {
    private String name;
    private int score;
    private int correctCount;
    private int wrongCount;
    private int combo;
    private int maxCombo;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.correctCount = 0;
        this.wrongCount = 0;
        this.combo = 0;
        this.maxCombo = 0;
    }

    public String getName() {
        return name;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public int getCombo() {
        return combo;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public void addCorrect() {
        correctCount++;
        combo++;

        if (combo > maxCombo) maxCombo = combo;

        if (combo >= 5) score += 3;
        else if (combo >= 3) score += 2;
        else score += 1;
    }

    public void addWrong() {
        wrongCount++;
        combo = 0;
    }

    @Override
    public void addScore() {
        score++;
    }

    @Override
    public int getScore() {
        return score;
    }
}