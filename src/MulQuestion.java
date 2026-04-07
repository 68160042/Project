// คลาส MulQuestion สืบทอดมาจากคลาส Question
// ใช้สำหรับสร้างโจทย์การคูณ
public class MulQuestion extends Question {

    // Constructor ของคลาส MulQuestion
    // รับค่า difficulty เพื่อกำหนดระดับความยากของโจทย์
    public MulQuestion(Difficulty difficulty) {
        // เรียก constructor ของคลาสแม่ (Question)
        // เพื่อสุ่มค่า num1 และ num2 ตามระดับความยาก
        super(difficulty);

        // กำหนดเครื่องหมายของโจทย์ให้เป็นการคูณ
        operator = "×";

        // คำนวณคำตอบที่ถูกต้อง
        correctAnswer = num1 * num2;
    }

    // Override เมธอดจากคลาสแม่
    // ใช้สร้างข้อความคำถามที่จะแสดงบนหน้าจอ
    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}