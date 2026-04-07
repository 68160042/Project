import java.util.Random;
//สืบทอดมาจากคลาส Question ใช้สำหรับสร้างโจทย์การหาร
public class DivQuestion extends Question {
    //รับค่า difficulty เพื่อกำหนดระดับความยากของโจทย์
    public DivQuestion(Difficulty difficulty) {
        // เรียก constructor ของคลาสแม่ (Question)
        super(difficulty);
        // กำหนดเครื่องหมายของโจทย์ให้เป็นหาร
        operator = "÷";
        // เรียกเมธอดสำหรับสร้างโจทย์หาร
        generateDivisionQuestion();
    }
    //เมธอดในการสร้างโจทย์การหารโดยทำให้เป็นผลลัพธ์ออกมาเป็นจำนวนเต็มเสมอ
    private void generateDivisionQuestion() {
        // สร้างออบเจกต์ Random สำหรับสุ่มตัวเลข
        Random rand = new Random();

        // ตัวแปร answer เก็บคำตอบที่ถูกต้อง
        // ตัวแปร divisor เก็บตัวหาร
        int answer;
        int divisor;

        // เลือกช่วงตัวเลขตามระดับความยาก
        switch (difficulty) {
            case EASY: // ระดับง่าย: คำตอบ 1-10, ตัวหาร 1-10
                answer = rand.nextInt(10) + 1;
                divisor = rand.nextInt(10) + 1;
                break;
            case MEDIUM: // ระดับปานกลาง: คำตอบ 1-15, ตัวหาร 1-12
                answer = rand.nextInt(15) + 1;
                divisor = rand.nextInt(12) + 1;
                break;
            case HARD: // ระดับยาก: คำตอบ 1-20, ตัวหาร 1-15
                answer = rand.nextInt(20) + 1;
                divisor = rand.nextInt(15) + 1;
                break;
            default:  // ค่าเริ่มต้นกรณีผิดพลาด
                answer = 1;
                divisor = 1;
        }
        // ป้องกันการหารด้วย 0
        if (divisor == 0) divisor = 1;

        // กำหนดตัวหารให้กับ num2
        num2 = divisor;
        // กำหนดคำตอบที่ถูกต้อง
        correctAnswer = answer;
        // คำนวณ num1 เพื่อให้โจทย์หารลงตัวเสมอ
        num1 = correctAnswer * num2;
    }

    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}