// คลาส Player ใช้เก็บข้อมูลและจัดการสถานะของผู้เล่น
// implements Scorable หมายถึงคลาสนี้ต้องมีเมธอดเกี่ยวกับคะแนนตามที่ interface กำหนด
public class Player implements Scorable {

    // ตัวแปรเก็บข้อมูลของผู้เล่น
    private String name;        // ชื่อผู้เล่น
    private int score;          // คะแนนรวม
    private int correctCount;   // จำนวนข้อที่ตอบถูก
    private int wrongCount;     // จำนวนข้อที่ตอบผิด
    private int combo;          // คอมโบปัจจุบัน
    private int maxCombo;       // คอมโบสูงสุดที่เคยทำได้

    // Constructor สำหรับสร้างผู้เล่นใหม่
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.correctCount = 0;
        this.wrongCount = 0;
        this.combo = 0;
        this.maxCombo = 0;
    }

    // Getter สำหรับคืนค่าชื่อผู้เล่น
    public String getName() {
        return name;
    }

    // Getter สำหรับคืนค่าจำนวนข้อที่ตอบถูก
    public int getCorrectCount() {
        return correctCount;
    }

    // Getter สำหรับคืนค่าจำนวนข้อที่ตอบผิด
    public int getWrongCount() {
        return wrongCount;
    }

    // Getter สำหรับคืนค่าคอมโบปัจจุบัน
    public int getCombo() {
        return combo;
    }

    // Getter สำหรับคืนค่าคอมโบสูงสุด
    public int getMaxCombo() {
        return maxCombo;
    }

    // เมธอดนี้ใช้เมื่อผู้เล่นตอบถูก
    public void addCorrect() {
        // เพิ่มจำนวนข้อที่ตอบถูก
        correctCount++;

        // เพิ่มคอมโบ
        combo++;

        // ถ้าคอมโบปัจจุบันมากกว่าคอมโบสูงสุดเดิม ให้อัปเดตค่า
        if (combo > maxCombo) maxCombo = combo;

        // ระบบเพิ่มคะแนนตามคอมโบ
        // คอมโบ 5 ขึ้นไป = +3 คะแนน
        // คอมโบ 3-4 = +2 คะแนน
        // ต่ำกว่า 3 = +1 คะแนน
        if (combo >= 5) score += 3;
        else if (combo >= 3) score += 2;
        else score += 1;
    }

    // เมธอดนี้ใช้เมื่อผู้เล่นตอบผิด
    public void addWrong() {
        // เพิ่มจำนวนข้อที่ตอบผิด
        wrongCount++;

        // รีเซ็ตคอมโบ
        combo = 0;
    }

    // Override เมธอดจาก interface Scorable
    // ใช้เพิ่มคะแนนทีละ 1
    @Override
    public void addScore() {
        score++;
    }

    // Override เมธอดจาก interface Scorable
    // ใช้คืนค่าคะแนนปัจจุบัน
    @Override
    public int getScore() {
        return score;
    }
}