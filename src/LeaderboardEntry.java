// คลาส LeaderboardEntry ใช้เก็บข้อมูลผลการแข่งขันของผู้เล่น 1 คน
public class LeaderboardEntry {

    // ตัวแปรเก็บข้อมูลของผู้เล่น
    private String playerName;     // ชื่อผู้เล่น
    private String difficulty;     // ระดับความยาก
    private int totalQuestions;    // จำนวนข้อทั้งหมด
    private int score;             // คะแนนที่ได้
    private double time;           // เวลาที่ใช้ในการเล่น
    private int maxCombo;          // คอมโบสูงสุดที่ทำได้

    // Constructor สำหรับกำหนดค่าข้อมูลเริ่มต้น
    public LeaderboardEntry(String playerName, String difficulty, int totalQuestions,
                            int score, double time, int maxCombo) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.time = time;
        this.maxCombo = maxCombo;
    }

    // Getter สำหรับคืนค่าชื่อผู้เล่น
    public String getPlayerName() {
        return playerName;
    }

    // Getter สำหรับคืนค่าระดับความยาก
    public String getDifficulty() {
        return difficulty;
    }

    // Getter สำหรับคืนค่าจำนวนข้อทั้งหมด
    public int getTotalQuestions() {
        return totalQuestions;
    }

    // Getter สำหรับคืนค่าคะแนน
    public int getScore() {
        return score;
    }

    // Getter สำหรับคืนค่าเวลาที่ใช้
    public double getTime() {
        return time;
    }

    // Getter สำหรับคืนค่าคอมโบสูงสุด
    public int getMaxCombo() {
        return maxCombo;
    }
}