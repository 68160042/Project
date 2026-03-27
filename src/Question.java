import java.util.Random;

public abstract class Question {
    protected int num1;
    protected int num2;
    protected int correctAnswer;
    protected String operator;
    protected Difficulty difficulty;

    public Question(Difficulty difficulty) {
        this.difficulty = difficulty;
        generateNumbers();
    }

    private void generateNumbers() {
        Random rand = new Random();

        switch (difficulty) {
            case EASY:
                num1 = rand.nextInt(10) + 1;
                num2 = rand.nextInt(10) + 1;
                break;
            case MEDIUM:
                num1 = rand.nextInt(20) + 1;
                num2 = rand.nextInt(20) + 1;
                break;
            case HARD:
                num1 = rand.nextInt(50) + 1;
                num2 = rand.nextInt(50) + 1;
                break;
        }
    }

    public abstract String generateQuestionText();

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean checkAnswer(int userAnswer) {
        return userAnswer == correctAnswer;
    }
}