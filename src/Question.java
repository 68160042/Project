import java.util.Random;

// คลาส Question เป็น abstract class
// ใช้เป็นคลาสแม่สำหรับโจทย์คณิตศาสตร์ทุกประเภท
// เช่น บวก ลบ คูณ หาร
public abstract class Question {

    // ตัวแปรพื้นฐานของโจทย์
    protected int num1;              // ตัวเลขตัวที่ 1
    protected int num2;              // ตัวเลขตัวที่ 2
    protected int correctAnswer;     // คำตอบที่ถูกต้อง
    protected String operator;       // เครื่องหมาย เช่น + - × ÷
    protected Difficulty difficulty; // ระดับความยากของโจทย์

    // Constructor ของคลาส Question
    // รับค่า difficulty เพื่อกำหนดระดับความยาก
    public Question(Difficulty difficulty) {
        this.difficulty = difficulty;

        // เรียกเมธอดสุ่มตัวเลขตามระดับความยาก
        generateNumbers();
    }

    // เมธอดสำหรับสุ่มตัวเลขตามระดับความยาก
    private void generateNumbers() {
        Random rand = new Random();

        switch (difficulty) {
            case EASY:
                // ระดับง่าย: สุ่มเลข 1-10
                num1 = rand.nextInt(10) + 1;
                num2 = rand.nextInt(10) + 1;
                break;

            case MEDIUM:
                // ระดับปานกลาง: สุ่มเลข 1-20
                num1 = rand.nextInt(20) + 1;
                num2 = rand.nextInt(20) + 1;
                break;

            case HARD:
                // ระดับยาก: สุ่มเลข 1-50
                num1 = rand.nextInt(50) + 1;
                num2 = rand.nextInt(50) + 1;
                break;
        }
    }

    // abstract method
    // ให้คลาสลูกเป็นคนกำหนดรูปแบบข้อความโจทย์เอง
    public abstract String generateQuestionText();

    // Getter สำหรับคืนค่าคำตอบที่ถูกต้อง
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    // เมธอดสำหรับตรวจว่าคำตอบของผู้ใช้ถูกหรือไม่
    public boolean checkAnswer(int userAnswer) {
        return userAnswer == correctAnswer;
    }
}