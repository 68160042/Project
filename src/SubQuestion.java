// คลาส SubQuestion ใช้สำหรับสร้างโจทย์ "ลบ"
// สืบทอดมาจากคลาสแม่ Question
public class SubQuestion extends Question {

    // Constructor รับระดับความยากของโจทย์
    public SubQuestion(Difficulty difficulty) {
        // เรียก constructor ของคลาสแม่ เพื่อสุ่มตัวเลขตาม difficulty
        super(difficulty);

        // กำหนดเครื่องหมายเป็นลบ
        operator = "-";

        // ถ้า num1 น้อยกว่า num2 ให้สลับค่า
        // เพื่อไม่ให้คำตอบติดลบ
        if (num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        // คำนวณคำตอบที่ถูกต้อง
        correctAnswer = num1 - num2;
    }

    // Override เมธอดจากคลาสแม่
    // ใช้สร้างข้อความโจทย์สำหรับแสดงบนหน้าจอ
    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}