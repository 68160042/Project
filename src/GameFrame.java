import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame {
    private JTextField nameField, answerField;
    private JComboBox<String> difficultyBox;
    private JButton startButton, submitButton, restartButton, menuButton;
    private JLabel playerValue, difficultyValue, scoreValue, timerValue, questionCountValue, comboValue;
    private JLabel questionLabel, statusLabel;

    private QuizGame game;
    private Timer timer;
    private int totalQuestions;

    private final Color BG = new Color(20, 24, 30);
    private final Color PANEL = new Color(34, 40, 49);
    private final Color PANEL2 = new Color(45, 53, 63);
    private final Color BORDER = new Color(80, 92, 108);

    private final Color TEXT = new Color(215, 215, 215);
    private final Color SUBTEXT = new Color(170, 175, 180);
    private final Color TITLE = new Color(120, 155, 210);
    private final Color GOLD = new Color(210, 170, 95);
    private final Color GREEN = new Color(105, 180, 120);
    private final Color RED = new Color(195, 105, 105);
    private final Color PURPLE = new Color(150, 125, 180);

    public GameFrame(int totalQuestions) {
        this.totalQuestions = totalQuestions;

        setTitle("Math Quiz Game - Play");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createHeader(mainPanel);
        createGameArea(mainPanel);
        createBottomControls(mainPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void createHeader(JPanel mainPanel) {
        JPanel header = new JPanel(new BorderLayout(20, 20));
        header.setBackground(BG);

        JPanel setupPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        setupPanel.setBackground(PANEL);
        setupPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel nameLabel = createLabel("Player Name:");
        JLabel diffLabel = createLabel("Difficulty:");

        nameField = createTextField();
        difficultyBox = createComboBox();

        setupPanel.add(nameLabel);
        setupPanel.add(nameField);
        setupPanel.add(diffLabel);
        setupPanel.add(difficultyBox);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(BG);

        JLabel titleLabel = new JLabel("MATH QUIZ GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(TITLE);

        JLabel subTitleLabel = new JLabel("Solve Fast • Score High • Build Combo", SwingConstants.CENTER);
        subTitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subTitleLabel.setForeground(SUBTEXT);

        titlePanel.add(titleLabel);
        titlePanel.add(subTitleLabel);

        header.add(setupPanel, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
    }

    private void createGameArea(JPanel mainPanel) {
        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(BG);

        JPanel hudPanel = new JPanel(new GridLayout(1, 6, 15, 15));
        hudPanel.setBackground(BG);

        playerValue = createHUDBox("-");
        difficultyValue = createHUDBox("-");
        scoreValue = createHUDBox("0");
        comboValue = createHUDBox("0");
        timerValue = createHUDBox("0.0 s");
        questionCountValue = createHUDBox("-");

        hudPanel.add(wrapHUD("PLAYER", playerValue));
        hudPanel.add(wrapHUD("LEVEL", difficultyValue));
        hudPanel.add(wrapHUD("SCORE", scoreValue));
        hudPanel.add(wrapHUD("COMBO", comboValue));
        hudPanel.add(wrapHUD("TIME", timerValue));
        hudPanel.add(wrapHUD("QUESTION", questionCountValue));

        JPanel gameBox = new JPanel(new GridLayout(4, 1, 18, 18));
        gameBox.setBackground(PANEL);
        gameBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JLabel instruction = new JLabel("Type your answer and press ENTER or SUBMIT", SwingConstants.CENTER);
        instruction.setFont(new Font("Arial", Font.PLAIN, 18));
        instruction.setForeground(SUBTEXT);

        questionLabel = new JLabel("Press Start to Begin", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 42));
        questionLabel.setOpaque(true);
        questionLabel.setBackground(PANEL2);
        questionLabel.setForeground(GOLD);
        questionLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TITLE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        answerField = createTextField();
        answerField.setHorizontalAlignment(JTextField.CENTER);
        answerField.setFont(new Font("Arial", Font.BOLD, 34));
        answerField.setEnabled(false);
        answerField.addActionListener(this::submitAnswerAction);

        statusLabel = new JLabel("Status: Waiting to start...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(SUBTEXT);

        gameBox.add(instruction);
        gameBox.add(questionLabel);
        gameBox.add(answerField);
        gameBox.add(statusLabel);

        center.add(hudPanel, BorderLayout.NORTH);
        center.add(gameBox, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);
    }

    private void createBottomControls(JPanel mainPanel) {
        JPanel bottom = new JPanel(new GridLayout(1, 4, 15, 15));
        bottom.setBackground(BG);

        startButton = new JButton("START");
        submitButton = new JButton("SUBMIT");
        restartButton = new JButton("RESTART");
        menuButton = new JButton("MENU");

        styleGameButton(startButton, TITLE);
        styleGameButton(submitButton, GREEN);
        styleGameButton(restartButton, GOLD);
        styleGameButton(menuButton, PURPLE);

        submitButton.setEnabled(false);
        restartButton.setEnabled(false);

        bottom.add(startButton);
        bottom.add(submitButton);
        bottom.add(restartButton);
        bottom.add(menuButton);

        startButton.addActionListener(this::startGameAction);
        submitButton.addActionListener(this::submitAnswerAction);
        restartButton.addActionListener(this::restartGameAction);
        menuButton.addActionListener(this::backToMenuAction);

        mainPanel.add(bottom, BorderLayout.SOUTH);
    }

    private void startGameAction(ActionEvent e) {
        String playerName = nameField.getText().trim();

        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Difficulty difficulty = Difficulty.valueOf(
                difficultyBox.getSelectedItem().toString()
        );

        Player player = new Player(playerName);
        game = new QuizGame(player, difficulty, totalQuestions);
        game.startGame();

        playerValue.setText(player.getName());
        difficultyValue.setText(difficulty.toString());
        scoreValue.setText("0");
        comboValue.setText("0");
        timerValue.setText("0.0 s");

        updateQuestionDisplay();
        showStatus("Game Started!", TITLE);

        answerField.setText("");
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        restartButton.setEnabled(true);
        startButton.setEnabled(false);
        nameField.setEnabled(false);
        difficultyBox.setEnabled(false);

        answerField.requestFocusInWindow();
        startTimer();
    }

    private void submitAnswerAction(ActionEvent e) {
        if (game == null || game.isGameOver()) return;

        try {
            int answer = Integer.parseInt(answerField.getText().trim());
            int correctAnswer = game.getCurrentQuestion().getCorrectAnswer();

            boolean correct = game.submitAnswer(answer);

            if (correct) {
                SoundPlayer.playCorrectSound();
                flashQuestionBox(new Color(50, 90, 60), GOLD);
                showStatus("Correct! Combo x" + game.getPlayer().getCombo(), GREEN);
            } else {
                SoundPlayer.playWrongSound();
                flashQuestionBox(new Color(90, 50, 50), new Color(230, 180, 180));
                showStatus("Wrong! Correct = " + correctAnswer, RED);
            }

            scoreValue.setText(String.valueOf(game.getPlayer().getScore()));
            comboValue.setText(String.valueOf(game.getPlayer().getCombo()));
            answerField.setText("");
            answerField.requestFocusInWindow();

            if (!game.isGameOver()) {
                updateQuestionDisplay();
            } else {
                endGame();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter numbers only!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void restartGameAction(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Do you want to restart the game?",
                "Restart Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            resetGameUI();
        }
    }

    private void backToMenuAction(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Do you want to go back to Main Menu?",
                "Back to Menu",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (timer != null) timer.stop();
            new StartMenu();
            dispose();
        }
    }

    private void updateQuestionDisplay() {
        questionCountValue.setText(game.getCurrentQuestionNumber() + " / " + game.getTotalQuestions());
        questionLabel.setText(game.getCurrentQuestion().generateQuestionText());
    }

    private void startTimer() {
        if (timer != null) timer.stop();

        timer = new Timer(100, e -> {
            if (game != null && !game.isGameOver()) {
                timerValue.setText(String.format("%.1f s", game.getElapsedSeconds()));
            }
        });
        timer.start();
    }

    private void endGame() {
        if (timer != null) timer.stop();

        answerField.setEnabled(false);
        submitButton.setEnabled(false);
        startButton.setEnabled(true);
        nameField.setEnabled(true);
        difficultyBox.setEnabled(true);

        Player p = game.getPlayer();

        LeaderboardManager.addEntry(new LeaderboardEntry(
                p.getName(),
                game.getDifficulty().toString(),
                game.getTotalQuestions(),
                p.getScore(),
                game.getElapsedSeconds(),
                p.getMaxCombo()
        ));

        showFinalResultDialog(
                p.getName(),
                game.getDifficulty().toString(),
                p.getScore(),
                game.getTotalQuestions(),
                p.getCorrectCount(),
                p.getWrongCount(),
                game.getElapsedSeconds(),
                game.getResultLevel(),
                p.getMaxCombo()
        );

        questionLabel.setText("GAME FINISHED!");
        questionLabel.setBackground(new Color(65, 48, 48));
        questionLabel.setForeground(new Color(220, 180, 180));
        showStatus("Finished", PURPLE);
    }

    private void resetGameUI() {
        if (timer != null) timer.stop();

        game = null;

        nameField.setText("");
        difficultyBox.setSelectedIndex(0);

        playerValue.setText("-");
        difficultyValue.setText("-");
        scoreValue.setText("0");
        comboValue.setText("0");
        timerValue.setText("0.0 s");
        questionCountValue.setText("-");

        questionLabel.setText("Press Start to Begin");
        questionLabel.setBackground(PANEL2);
        questionLabel.setForeground(GOLD);

        answerField.setText("");
        answerField.setEnabled(false);

        showStatus("Waiting to start...", SUBTEXT);

        startButton.setEnabled(true);
        submitButton.setEnabled(false);
        restartButton.setEnabled(false);

        nameField.setEnabled(true);
        difficultyBox.setEnabled(true);

        nameField.requestFocusInWindow();
    }

    private void showFinalResultDialog(String playerName, String difficulty,
                                       int score, int total, int correct, int wrong,
                                       double time, String resultLevel, int maxCombo) {

        JDialog dialog = new JDialog(this, "Final Result", true);
        dialog.setSize(580, 580);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(BG);
        dialog.setLayout(new BorderLayout());

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("GAME OVER", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(RED);

        JLabel sub = new JLabel("Final Score Summary", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 18));
        sub.setForeground(SUBTEXT);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(BG);
        header.add(title);
        header.add(sub);

        JPanel card = new JPanel(new GridLayout(8, 2, 10, 12));
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TITLE, 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        addResultRow(card, "Player Name", playerName);
        addResultRow(card, "Difficulty", difficulty);
        addResultRow(card, "Score", score + " / " + total);
        addResultRow(card, "Correct", String.valueOf(correct));
        addResultRow(card, "Wrong", String.valueOf(wrong));
        addResultRow(card, "Max Combo", String.valueOf(maxCombo));
        addResultRow(card, "Total Time", String.format("%.2f seconds", time));
        addResultRow(card, "Result", resultLevel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BG);

        JButton okButton = new JButton("OK");
        JButton againButton = new JButton("PLAY AGAIN");

        styleGameButton(okButton, TITLE);
        styleGameButton(againButton, GREEN);

        okButton.setPreferredSize(new Dimension(140, 45));
        againButton.setPreferredSize(new Dimension(160, 45));

        buttonPanel.add(okButton);
        buttonPanel.add(againButton);

        main.add(header, BorderLayout.NORTH);
        main.add(card, BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(main);

        okButton.addActionListener(e -> dialog.dispose());
        againButton.addActionListener(e -> {
            dialog.dispose();
            resetGameUI();
        });

        dialog.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(TEXT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 20));
        field.setBackground(new Color(50, 56, 65));
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> box = new JComboBox<>(new String[]{"EASY", "MEDIUM", "HARD"});
        box.setFont(new Font("Arial", Font.PLAIN, 18));
        box.setBackground(new Color(50, 56, 65));
        box.setForeground(new Color(45, 45, 45));
        return box;
    }

    private void styleGameButton(JButton button, Color bg) {
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setBackground(bg);
        button.setForeground(new Color(35, 35, 35));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JLabel createHUDBox(String value) {
        JLabel label = new JLabel(value, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(GOLD);
        return label;
    }

    private JPanel wrapHUD(String title, JLabel valueLabel) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel top = new JLabel(title, SwingConstants.CENTER);
        top.setFont(new Font("Arial", Font.BOLD, 16));
        top.setForeground(TEXT);

        panel.add(top);
        panel.add(valueLabel);
        return panel;
    }

    private void addResultRow(JPanel panel, String label, String value) {
        JLabel left = new JLabel(label + " :");
        left.setFont(new Font("Arial", Font.BOLD, 18));
        left.setForeground(TEXT);

        JLabel right = new JLabel(value);
        right.setFont(new Font("Arial", Font.PLAIN, 18));
        right.setForeground(GOLD);

        panel.add(left);
        panel.add(right);
    }

    private void flashQuestionBox(Color bg, Color fg) {
        questionLabel.setBackground(bg);
        questionLabel.setForeground(fg);

        Timer flashTimer = new Timer(400, e -> {
            questionLabel.setBackground(PANEL2);
            questionLabel.setForeground(GOLD);
        });
        flashTimer.setRepeats(false);
        flashTimer.start();
    }

    private void showStatus(String message) {
        statusLabel.setText("Status: " + message);
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText("Status: " + message);
        statusLabel.setForeground(color);
    }
}