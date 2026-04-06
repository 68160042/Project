import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StartMenu extends JFrame {

    private JComboBox<String> questionCountBox;

    private final Color BG = new Color(20, 24, 30);
    private final Color PANEL = new Color(34, 40, 49);
    private final Color PANEL2 = new Color(45, 53, 63);
    private final Color BORDER = new Color(80, 92, 108);
    private final Color TEXT = new Color(215, 215, 215);
    private final Color SUBTEXT = new Color(170, 175, 180);
    private final Color TITLE = new Color(120, 155, 210);
    private final Color GOLD = new Color(210, 170, 95);
    private final Color PLAY = new Color(88, 140, 170);
    private final Color EXIT = new Color(150, 95, 95);
    private final Color PURPLE = new Color(150, 125, 180);
    private final Color GREEN = new Color(105, 180, 120);
    private final Color RED = new Color(195, 105, 105);

    public StartMenu() {
        setTitle("Math Quiz Game - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        topPanel.setBackground(BG);

        JLabel title = new JLabel("MATH QUIZ GAME", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 44));
        title.setForeground(TITLE);

        JLabel mathDecor = new JLabel("+   -   ×   ÷", SwingConstants.CENTER);
        mathDecor.setFont(new Font("Arial", Font.BOLD, 28));
        mathDecor.setForeground(GOLD);

        topPanel.add(title);
        topPanel.add(mathDecor);

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

        styleButton(playButton, PLAY, new Color(40, 40, 40));
        styleButton(leaderboardButton, PURPLE, new Color(40, 40, 40));
        styleButton(exitButton, EXIT, new Color(40, 40, 40));

        centerPanel.add(questionCountBox);
        centerPanel.add(playButton);
        centerPanel.add(leaderboardButton);
        centerPanel.add(exitButton);

        centerWrapper.add(centerPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        add(mainPanel);

        playButton.addActionListener(e -> {
            int totalQuestions = Integer.parseInt(questionCountBox.getSelectedItem().toString());
            new GameFrame(totalQuestions);
            dispose();
        });

        leaderboardButton.addActionListener(e -> showLeaderboardDialog());

        exitButton.addActionListener(e -> {
            boolean confirm = showStyledConfirmDialog(
                    "EXIT GAME",
                    "Do you want to exit the game?"
            );

            if (confirm) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void showLeaderboardDialog() {
        List<LeaderboardEntry> entries = LeaderboardManager.getLeaderboard();

        JDialog dialog = new JDialog(this, "Leaderboard", true);
        dialog.setSize(900, 620);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.getContentPane().setBackground(BG);
        dialog.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(BG);

        JLabel titleLabel = new JLabel("TOP 10 LEADERBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(TITLE);

        JLabel subLabel = new JLabel("Best Players Ranking", SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subLabel.setForeground(SUBTEXT);

        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG);

        if (entries.isEmpty()) {
            JPanel emptyCard = new JPanel(new BorderLayout());
            emptyCard.setBackground(PANEL);
            emptyCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 2),
                    BorderFactory.createEmptyBorder(40, 20, 40, 20)
            ));

            JLabel emptyLabel = new JLabel("No scores yet.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 24));
            emptyLabel.setForeground(GOLD);

            emptyCard.add(emptyLabel, BorderLayout.CENTER);
            listPanel.add(emptyCard);
        } else {
            for (int i = 0; i < entries.size(); i++) {
                LeaderboardEntry entry = entries.get(i);
                JPanel row = createLeaderboardRow(i + 1, entry);
                listPanel.add(row);
                listPanel.add(Box.createVerticalStrut(12));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);
        scrollPane.setBackground(BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setFocusable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BG);

        JButton okButton = new JButton("OK");
        styleButton(okButton, TITLE, new Color(35, 35, 35));
        okButton.setPreferredSize(new Dimension(150, 48));
        okButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(okButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private JPanel createLeaderboardRow(int rank, LeaderboardEntry entry) {
        JPanel rowPanel = new JPanel(new BorderLayout(15, 15));
        rowPanel.setBackground(PANEL);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel rankLabel = new JLabel("#" + rank, SwingConstants.CENTER);
        rankLabel.setPreferredSize(new Dimension(80, 60));
        rankLabel.setFont(new Font("Arial", Font.BOLD, 28));
        rankLabel.setForeground(GOLD);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        infoPanel.setBackground(PANEL);

        JLabel nameLabel = new JLabel(entry.getPlayerName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(TEXT);

        JLabel detailLabel = new JLabel(
                "Score: " + entry.getScore() +
                        "   |   " + entry.getDifficulty() +
                        "   |   Q: " + entry.getTotalQuestions() +
                        "   |   Combo: " + entry.getMaxCombo() +
                        "   |   Time: " + String.format("%.2f s", entry.getTime())
        );
        detailLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        detailLabel.setForeground(SUBTEXT);

        infoPanel.add(nameLabel);
        infoPanel.add(detailLabel);

        rowPanel.add(rankLabel, BorderLayout.WEST);
        rowPanel.add(infoPanel, BorderLayout.CENTER);

        return rowPanel;
    }

    private boolean showStyledConfirmDialog(String titleText, String messageText) {
        final boolean[] result = {false};

        JDialog dialog = new JDialog(this, titleText, true);
        dialog.setSize(500, 240);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BG);

        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(TITLE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Confirmation Required", SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        subLabel.setForeground(SUBTEXT);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(subLabel);

        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.setBackground(PANEL);
        messagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel messageLabel = new JLabel(messageText, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(TEXT);

        messagePanel.add(messageLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BG);

        JButton yesButton = new JButton("YES");
        JButton noButton = new JButton("NO");

        styleButton(yesButton, GREEN, new Color(35, 35, 35));
        styleButton(noButton, RED, new Color(35, 35, 35));

        yesButton.setPreferredSize(new Dimension(140, 45));
        noButton.setPreferredSize(new Dimension(140, 45));

        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);

        return result[0];
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