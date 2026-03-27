import java.util.Random;

public class DivQuestion extends Question {

    public DivQuestion(Difficulty difficulty) {
        super(difficulty);
        operator = "÷";
        generateDivisionQuestion();
    }

    private void generateDivisionQuestion() {
        Random rand = new Random();

        int answer;
        int divisor;

        switch (difficulty) {
            case EASY:
                answer = rand.nextInt(10) + 1;
                divisor = rand.nextInt(10) + 1;
                break;
            case MEDIUM:
                answer = rand.nextInt(15) + 1;
                divisor = rand.nextInt(12) + 1;
                break;
            case HARD:
                answer = rand.nextInt(20) + 1;
                divisor = rand.nextInt(15) + 1;
                break;
            default:
                answer = 1;
                divisor = 1;
        }

        num2 = divisor;
        correctAnswer = answer;
        num1 = correctAnswer * num2;
    }

    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}