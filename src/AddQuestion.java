//คลาสลูกของQuestion
public class AddQuestion extends Question {
    //กำหนดระดับความยากของโจทย์
    public AddQuestion(Difficulty difficulty) {
        //เรียก constructor ของคลาสแม่ เพื่อสุ่มตัวเลข
        super(difficulty);
        //กำหนดเครื่องหมายของโจทย์ให้เป็นบวก
        operator = "+";
        //คำนวณคำตอบที่ถูกต้องของโจทย์
        correctAnswer = num1 + num2;
    }
    //ใช้สร้างข้อความคำถามที่จะแสดงบนหน้าจอ
    @Override
    public String generateQuestionText() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }
}
