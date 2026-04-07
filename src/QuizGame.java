import java.util.Random;

public class QuizGame {

    // ข้อมูลหลักของเกม
    private Player player;
    private Difficulty difficulty;
    private int totalQuestions;
    private int currentQuestionNumber;
    private Question currentQuestion;
    private long startTime;
    private long endTime;

    // Constructor กำหนดจำนวนข้อเริ่มต้น = 5
    public QuizGame(Player player, Difficulty difficulty) {
        this(player, difficulty, 5);
    }

    // Constructor หลัก
    public QuizGame(Player player, Difficulty difficulty, int totalQuestions) {
        this.player = player;
        this.difficulty = difficulty;
        this.totalQuestions = totalQuestions;
        this.currentQuestionNumber = 1;
    }

    // เริ่มเกมและสุ่มโจทย์ข้อแรก
    public void startGame() {
        startTime = System.currentTimeMillis();
        currentQuestion = generateRandomQuestion();
    }

    // สุ่มประเภทของโจทย์
    public Question generateRandomQuestion() {
        Random rand = new Random();
        int type = rand.nextInt(4);

        switch (type) {
            case 0: return new AddQuestion(difficulty);
            case 1: return new SubQuestion(difficulty);
            case 2: return new MulQuestion(difficulty);
            default: return new DivQuestion(difficulty);
        }
    }

    // รับคำตอบของผู้เล่นและตรวจคำตอบ
    public boolean submitAnswer(int answer) {
        boolean correct = currentQuestion.checkAnswer(answer);

        if (correct) player.addCorrect();
        else player.addWrong();

        if (currentQuestionNumber >= totalQuestions) {
            endTime = System.currentTimeMillis();
        }

        currentQuestionNumber++;

        if (!isGameOver()) currentQuestion = generateRandomQuestion();

        return correct;
    }

    // ตรวจสอบว่าเกมจบหรือยัง
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

    // คำนวณเวลาที่ใช้เล่นเกม
    public double getElapsedSeconds() {
        long current = isGameOver() ? endTime : System.currentTimeMillis();
        return (current - startTime) / 1000.0;
    }

    // ประเมินผลการเล่นตามเปอร์เซ็นต์คะแนน
    public String getResultLevel() {
        double percent = (player.getCorrectCount() * 100.0) / totalQuestions;
        if (percent >= 90) return "Excellent";
        else if (percent >= 75) return "Very Good";
        else if (percent >= 60) return "Good";
        else if (percent >= 40) return "Fair";
        else return "Need Practice";
    }

    // คืนค่าเปอร์เซ็นต์คะแนน
    public double getScorePercent() {
        return (player.getCorrectCount() * 100.0) / totalQuestions;
    }
}