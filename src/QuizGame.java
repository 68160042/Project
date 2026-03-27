import java.util.Random;

public class QuizGame {
    private Player player;
    private Difficulty difficulty;
    private int totalQuestions;
    private int currentQuestionNumber;
    private Question currentQuestion;
    private long startTime;
    private long endTime;

    public QuizGame(Player player, Difficulty difficulty) {
        this(player, difficulty, 5);
    }

    public QuizGame(Player player, Difficulty difficulty, int totalQuestions) {
        this.player = player;
        this.difficulty = difficulty;
        this.totalQuestions = totalQuestions;
        this.currentQuestionNumber = 1;
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
        currentQuestion = generateRandomQuestion();
    }

    public Question generateRandomQuestion() {
        Random rand = new Random();
        int type = rand.nextInt(4);

        if (type == 0) {
            return new AddQuestion(difficulty);
        } else if (type == 1) {
            return new SubQuestion(difficulty);
        } else if (type == 2) {
            return new MulQuestion(difficulty);
        } else {
            return new DivQuestion(difficulty);
        }
    }

    public boolean submitAnswer(int answer) {
        boolean correct = currentQuestion.checkAnswer(answer);

        if (correct) {
            player.addCorrect();
        } else {
            player.addWrong();
        }

        currentQuestionNumber++;

        if (!isGameOver()) {
            currentQuestion = generateRandomQuestion();
        } else {
            endTime = System.currentTimeMillis();
        }

        return correct;
    }

    public boolean isGameOver() {
        return currentQuestionNumber > totalQuestions;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public Player getPlayer() {
        return player;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public double getElapsedSeconds() {
        long current = isGameOver() ? endTime : System.currentTimeMillis();
        return (current - startTime) / 1000.0;
    }

    public String getResultLevel() {
        double percent = (player.getCorrectCount() * 100.0) / totalQuestions;

        if (percent >= 90) return "Excellent";
        else if (percent >= 75) return "Very Good";
        else if (percent >= 60) return "Good";
        else if (percent >= 40) return "Fair";
        else return "Need Practice";
    }

    public double getScorePercent() {
        return (player.getCorrectCount() * 100.0) / totalQuestions;
    }
}