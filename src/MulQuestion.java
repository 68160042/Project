public class MulQuestion extends Question {

    public MulQuestion(Difficulty difficulty) {
        super(difficulty);
        operator = "×";
        correctAnswer = num1 * num2;
    }

    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}