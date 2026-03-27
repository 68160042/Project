public class SubQuestion extends Question {

    public SubQuestion(Difficulty difficulty) {
        super(difficulty);
        operator = "-";

        if (num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        correctAnswer = num1 - num2;
    }

    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}