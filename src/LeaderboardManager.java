import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// คลาส LeaderboardManager ใช้จัดการข้อมูลอันดับผู้เล่น
public class LeaderboardManager {

    // เก็บข้อมูลอันดับผู้เล่นทั้งหมดไว้ใน List
    // static = ใช้ร่วมกันทั้งโปรแกรม
    private static final List<LeaderboardEntry> leaderboard = new ArrayList<>();

    // เมธอดสำหรับเพิ่มข้อมูลผู้เล่นลง Leaderboard
    public static void addEntry(LeaderboardEntry entry) {
        // เพิ่มข้อมูลผู้เล่นใหม่เข้าไปในรายการ
        leaderboard.add(entry);

        // เรียงลำดับอันดับ
        // 1. เรียงคะแนนจากมากไปน้อย
        // 2. ถ้าคะแนนเท่ากัน ให้คนที่ใช้เวลาน้อยกว่าอยู่อันดับสูงกว่า
        leaderboard.sort(Comparator
                .comparingInt(LeaderboardEntry::getScore).reversed()
                .thenComparingDouble(LeaderboardEntry::getTime));

        // ถ้ามีข้อมูลเกิน 10 คน
        // ให้ลบคนสุดท้ายออก เพื่อเก็บเฉพาะ Top 10
        if (leaderboard.size() > 10) {
            leaderboard.remove(leaderboard.size() - 1);
        }
    }

    // เมธอดสำหรับดึงข้อมูล Leaderboard ทั้งหมดออกไปใช้งาน
    public static List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }
}