import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeaderboardManager {
    private static final List<LeaderboardEntry> leaderboard = new ArrayList<>();

    public static void addEntry(LeaderboardEntry entry) {
        leaderboard.add(entry);

        leaderboard.sort(Comparator
                .comparingInt(LeaderboardEntry::getScore).reversed()
                .thenComparingDouble(LeaderboardEntry::getTime));

        if (leaderboard.size() > 10) {
            leaderboard.remove(leaderboard.size() - 1);
        }
    }

    public static List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }
}