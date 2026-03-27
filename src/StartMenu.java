import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StartMenu extends JFrame {

    private JComboBox<String> questionCountBox;

    private final Color BG = new Color(20, 24, 30);
    private final Color PANEL = new Color(34, 40, 49);
    private final Color BORDER = new Color(80, 92, 108);
    private final Color TEXT = new Color(210, 210, 210);
    private final Color SUBTEXT = new Color(170, 175, 180);
    private final Color TITLE = new Color(120, 155, 210);
    private final Color GOLD = new Color(210, 170, 95);
    private final Color PLAY = new Color(88, 140, 170);
    private final Color EXIT = new Color(150, 95, 95);
    private final Color PURPLE = new Color(150, 125, 180);

    public StartMenu() {
        setTitle("Math Quiz Game - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel topPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        topPanel.setBackground(BG);

        JLabel title = new JLabel("MATH QUIZ GAME", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 44));
        title.setForeground(TITLE);

        JLabel subtitle = new JLabel("OOP Project", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 22));
        subtitle.setForeground(TEXT);

        JLabel mathDecor = new JLabel("+   -   ×   ÷", SwingConstants.CENTER);
        mathDecor.setFont(new Font("Arial", Font.BOLD, 28));
        mathDecor.setForeground(GOLD);

        JLabel chooseLabel = new JLabel("Choose Number of Questions", SwingConstants.CENTER);
        chooseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        chooseLabel.setForeground(SUBTEXT);

        topPanel.add(title);
        topPanel.add(subtitle);
        topPanel.add(mathDecor);
        topPanel.add(chooseLabel);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(BG);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(PANEL);
        centerPanel.setPreferredSize(new Dimension(460, 330));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        centerPanel.setLayout(new GridLayout(4, 1, 20, 20));

        questionCountBox = new JComboBox<>(new String[]{"5", "10", "15", "20"});
        questionCountBox.setFont(new Font("Arial", Font.BOLD, 24));
        questionCountBox.setBackground(new Color(50, 56, 65));
        questionCountBox.setForeground(new Color(40, 40, 40));

        JButton playButton = new JButton("PLAY");
        JButton leaderboardButton = new JButton("LEADERBOARD");
        JButton exitButton = new JButton("EXIT");

        styleButton(playButton, PLAY, TEXT);
        styleButton(leaderboardButton, PURPLE, TEXT);
        styleButton(exitButton, EXIT, TEXT);

        centerPanel.add(questionCountBox);
        centerPanel.add(playButton);
        centerPanel.add(leaderboardButton);
        centerPanel.add(exitButton);

        centerWrapper.add(centerPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.setBackground(BG);

        JLabel footer = new JLabel("Monruedi Hoisang 68160042", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 18));
        footer.setForeground(SUBTEXT);

        JLabel footer2 = new JLabel("Math Quiz Game OOP Project", SwingConstants.CENTER);
        footer2.setFont(new Font("Arial", Font.PLAIN, 15));
        footer2.setForeground(new Color(140, 145, 150));

        bottomPanel.add(footer);
        bottomPanel.add(footer2);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        playButton.addActionListener(e -> {
            int totalQuestions = Integer.parseInt(questionCountBox.getSelectedItem().toString());
            new GameFrame(totalQuestions);
            dispose();
        });

        leaderboardButton.addActionListener(e -> showLeaderboardDialog());

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to exit the game?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void showLeaderboardDialog() {
        List<LeaderboardEntry> entries = LeaderboardManager.getLeaderboard();

        StringBuilder sb = new StringBuilder();
        sb.append("=== TOP 10 LEADERBOARD ===\n\n");

        if (entries.isEmpty()) {
            sb.append("No scores yet.\n");
        } else {
            for (int i = 0; i < entries.size(); i++) {
                LeaderboardEntry e = entries.get(i);
                sb.append((i + 1)).append(". ")
                        .append(e.getPlayerName())
                        .append(" | Score: ").append(e.getScore())
                        .append(" | ").append(e.getDifficulty())
                        .append(" | Q: ").append(e.getTotalQuestions())
                        .append(" | Combo: ").append(e.getMaxCombo())
                        .append(" | Time: ").append(String.format("%.2f s", e.getTime()))
                        .append("\n");
            }
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

    private void styleButton(JButton button, Color bgColor, Color textColor) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}