import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// คลาส GameFrame สืบทอดจาก JFrame
// ใช้เป็นหน้าจอหลักสำหรับเล่นเกมคณิตศาสตร์
public class GameFrame extends JFrame {

    // ช่องกรอกชื่อผู้เล่น และคำตอบ
    private JTextField nameField, answerField;

    // กล่องเลือกความยาก
    private JComboBox<String> difficultyBox;

    // ปุ่มควบคุมต่าง ๆ
    private JButton startButton, submitButton, restartButton, menuButton;

    // Label สำหรับแสดงข้อมูลบน HUD
    private JLabel playerValue, difficultyValue, scoreValue, timerValue, questionCountValue, comboValue;

    // Label สำหรับแสดงโจทย์และสถานะ
    private JLabel questionLabel, statusLabel;

    // ออบเจกต์เกมหลัก
    private QuizGame game;

    // Timer สำหรับจับเวลา
    private Timer timer;

    // จำนวนข้อทั้งหมดของเกม
    private int totalQuestions;

    // ชุดสีสำหรับตกแต่ง UI

    private final Color BG = new Color(20, 24, 30);         // สีพื้นหลังหลัก
    private final Color PANEL = new Color(34, 40, 49);      // สีพื้นหลัง panel
    private final Color PANEL2 = new Color(45, 53, 63);     // สี panel รอง
    private final Color BORDER = new Color(80, 92, 108);    // สีขอบ

    private final Color TEXT = new Color(215, 215, 215);    // สีข้อความหลัก
    private final Color SUBTEXT = new Color(170, 175, 180); // สีข้อความรอง
    private final Color TITLE = new Color(120, 155, 210);   // สีหัวข้อ
    private final Color GOLD = new Color(210, 170, 95);     // สีทอง
    private final Color GREEN = new Color(105, 180, 120);   // สีเขียว
    private final Color RED = new Color(195, 105, 105);     // สีแดง
    private final Color PURPLE = new Color(150, 125, 180);  // สีม่วง

    // Constructor ของหน้าจอเกม
    public GameFrame(int totalQuestions) {
        this.totalQuestions = totalQuestions;

        // ตั้งค่าหน้าต่าง
        setTitle("Math Quiz Game - Play");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // เปิดแบบเต็มจอ

        // สร้าง panel หลัก
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // เรียกเมธอดสร้างส่วนต่าง ๆ ของหน้าจอ
        createHeader(mainPanel);
        createGameArea(mainPanel);
        createBottomControls(mainPanel);

        // เพิ่ม panel หลักลงใน frame
        add(mainPanel);
        setVisible(true);
    }

    // ส่วนหัวของหน้าจอ

    private void createHeader(JPanel mainPanel) {
        JPanel header = new JPanel(new BorderLayout(20, 20));
        header.setBackground(BG);

        // Panel สำหรับตั้งค่าผู้เล่นและความยาก
        JPanel setupPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        setupPanel.setBackground(PANEL);
        setupPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Label สำหรับชื่อและระดับความยาก
        JLabel nameLabel = createLabel("Player Name:");
        JLabel diffLabel = createLabel("Difficulty:");

        // สร้างช่องกรอกชื่อและกล่องเลือกความยาก
        nameField = createTextField();
        difficultyBox = createComboBox();

        // เพิ่มลง setupPanel
        setupPanel.add(nameLabel);
        setupPanel.add(nameField);
        setupPanel.add(diffLabel);
        setupPanel.add(difficultyBox);

        // Panel สำหรับชื่อเกม
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

        // จัดวางตำแหน่ง
        header.add(setupPanel, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
    }

    // ส่วนกลางของเกม
    private void createGameArea(JPanel mainPanel) {
        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(BG);

        // HUD สำหรับแสดงข้อมูลเกม
        JPanel hudPanel = new JPanel(new GridLayout(1, 6, 15, 15));
        hudPanel.setBackground(BG);

        // สร้าง label สำหรับแสดงข้อมูลต่าง ๆ
        playerValue = createHUDBox("-");
        difficultyValue = createHUDBox("-");
        scoreValue = createHUDBox("0");
        comboValue = createHUDBox("0");
        timerValue = createHUDBox("0.0 s");
        questionCountValue = createHUDBox("-");

        // เพิ่มข้อมูลลง HUD
        hudPanel.add(wrapHUD("PLAYER", playerValue));
        hudPanel.add(wrapHUD("LEVEL", difficultyValue));
        hudPanel.add(wrapHUD("SCORE", scoreValue));
        hudPanel.add(wrapHUD("COMBO", comboValue));
        hudPanel.add(wrapHUD("TIME", timerValue));
        hudPanel.add(wrapHUD("QUESTION", questionCountValue));

        // กล่องหลักสำหรับโจทย์และคำตอบ
        JPanel gameBox = new JPanel(new GridLayout(4, 1, 18, 18));
        gameBox.setBackground(PANEL);
        gameBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // ข้อความแนะนำ
        JLabel instruction = new JLabel("Type your answer and press ENTER or SUBMIT", SwingConstants.CENTER);
        instruction.setFont(new Font("Arial", Font.PLAIN, 18));
        instruction.setForeground(SUBTEXT);

        // Label แสดงโจทย์
        questionLabel = new JLabel("Press Start to Begin", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 42));
        questionLabel.setOpaque(true);
        questionLabel.setBackground(PANEL2);
        questionLabel.setForeground(GOLD);
        questionLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TITLE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // ช่องกรอกคำตอบ
        answerField = createTextField();
        answerField.setHorizontalAlignment(JTextField.CENTER);
        answerField.setFont(new Font("Arial", Font.BOLD, 34));
        answerField.setEnabled(false); // ปิดไว้ก่อนเริ่มเกม
        answerField.addActionListener(this::submitAnswerAction);

        // Label แสดงสถานะ
        statusLabel = new JLabel("Status: Waiting to start...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(SUBTEXT);

        // เพิ่มทุกอย่างลง gameBox
        gameBox.add(instruction);
        gameBox.add(questionLabel);
        gameBox.add(answerField);
        gameBox.add(statusLabel);

        center.add(hudPanel, BorderLayout.NORTH);
        center.add(gameBox, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);
    }

    // ปุ่มด้านล่าง

    private void createBottomControls(JPanel mainPanel) {
        JPanel bottom = new JPanel(new GridLayout(1, 4, 15, 15));
        bottom.setBackground(BG);

        // สร้างปุ่ม
        startButton = new JButton("START");
        submitButton = new JButton("SUBMIT");
        restartButton = new JButton("RESTART");
        menuButton = new JButton("MENU");

        // ตกแต่งปุ่ม
        styleGameButton(startButton, TITLE);
        styleGameButton(submitButton, GREEN);
        styleGameButton(restartButton, GOLD);
        styleGameButton(menuButton, PURPLE);

        // ปิดปุ่มบางปุ่มไว้ก่อน
        submitButton.setEnabled(false);
        restartButton.setEnabled(false);

        // เพิ่มปุ่มลง panel
        bottom.add(startButton);
        bottom.add(submitButton);
        bottom.add(restartButton);
        bottom.add(menuButton);

        // กำหนด Action ให้ปุ่ม
        startButton.addActionListener(this::startGameAction);
        submitButton.addActionListener(this::submitAnswerAction);
        restartButton.addActionListener(this::restartGameAction);
        menuButton.addActionListener(this::backToMenuAction);

        mainPanel.add(bottom, BorderLayout.SOUTH);
    }

    // เริ่มเกม

    private void startGameAction(ActionEvent e) {
        String playerName = nameField.getText().trim();

        // ถ้าไม่กรอกชื่อ ให้แจ้งเตือน
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // รับค่าความยากจาก combo box
        Difficulty difficulty = Difficulty.valueOf(
                difficultyBox.getSelectedItem().toString()
        );

        // สร้างผู้เล่นและเริ่มเกม
        Player player = new Player(playerName);
        game = new QuizGame(player, difficulty, totalQuestions);
        game.startGame();

        // อัปเดต HUD
        playerValue.setText(player.getName());
        difficultyValue.setText(difficulty.toString());
        scoreValue.setText("0");
        comboValue.setText("0");
        timerValue.setText("0.0 s");

        // แสดงโจทย์ข้อแรก
        updateQuestionDisplay();
        showStatus("Game Started!", TITLE);

        // เปิดการใช้งานช่องตอบและปุ่ม
        answerField.setText("");
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        restartButton.setEnabled(true);
        startButton.setEnabled(false);
        nameField.setEnabled(false);
        difficultyBox.setEnabled(false);

        answerField.requestFocusInWindow();
        startTimer(); // เริ่มจับเวลา
    }

    // ส่งคำตอบ

    private void submitAnswerAction(ActionEvent e) {
        if (game == null || game.isGameOver()) return;

        try {
            // รับคำตอบจากผู้เล่น
            int answer = Integer.parseInt(answerField.getText().trim());

            // เก็บคำตอบที่ถูกต้องไว้
            int correctAnswer = game.getCurrentQuestion().getCorrectAnswer();

            // ตรวจคำตอบ
            boolean correct = game.submitAnswer(answer);

            if (correct) {
                // ถ้าตอบถูก
                flashQuestionBox(new Color(50, 90, 60), GOLD);
                showStatus("Correct! Combo x" + game.getPlayer().getCombo(), GREEN);
            } else {
                // ถ้าตอบผิด
                flashQuestionBox(new Color(90, 50, 50), new Color(230, 180, 180));
                showStatus("Wrong! Correct = " + correctAnswer, RED);
            }

            // อัปเดตคะแนนและคอมโบ
            scoreValue.setText(String.valueOf(game.getPlayer().getScore()));
            comboValue.setText(String.valueOf(game.getPlayer().getCombo()));
            answerField.setText("");
            answerField.requestFocusInWindow();

            // ถ้ายังไม่จบเกม ไปข้อถัดไป
            if (!game.isGameOver()) {
                updateQuestionDisplay();
            } else {
                endGame();
            }

        } catch (NumberFormatException ex) {
            // ถ้ากรอกไม่ใช่ตัวเลข
            JOptionPane.showMessageDialog(this,
                    "Please enter numbers only!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // รีสตาร์ตเกม

    private void restartGameAction(ActionEvent e) {
        boolean confirm = showStyledConfirmDialog(
                "RESTART GAME",
                "Do you want to restart the game?"
        );

        if (confirm) {
            resetGameUI();
        }
    }

    // กลับไปเมนูหลัก

    private void backToMenuAction(ActionEvent e) {
        boolean confirm = showStyledConfirmDialog(
                "BACK TO MENU",
                "Do you want to go back to Main Menu?"
        );

        if (confirm) {
            if (timer != null) timer.stop();
            new StartMenu();
            dispose(); // ปิดหน้าปัจจุบัน
        }
    }

    // อัปเดตโจทย์ที่แสดง
    private void updateQuestionDisplay() {
        questionCountValue.setText(game.getCurrentQuestionNumber() + " / " + game.getTotalQuestions());
        questionLabel.setText(game.getCurrentQuestion().generateQuestionText());
    }

    // เริ่มจับเวลา
    private void startTimer() {
        if (timer != null) timer.stop();

        timer = new Timer(100, e -> {
            if (game != null && !game.isGameOver()) {
                timerValue.setText(String.format("%.1f s", game.getElapsedSeconds()));
            }
        });
        timer.start();
    }

    // จบเกม

    private void endGame() {
        if (timer != null) timer.stop();

        // ปิดการตอบคำถาม
        answerField.setEnabled(false);
        submitButton.setEnabled(false);
        startButton.setEnabled(true);
        nameField.setEnabled(true);
        difficultyBox.setEnabled(true);

        Player p = game.getPlayer();

        // บันทึกผลลง Leaderboard
        LeaderboardManager.addEntry(new LeaderboardEntry(
                p.getName(),
                game.getDifficulty().toString(),
                game.getTotalQuestions(),
                p.getScore(),
                game.getElapsedSeconds(),
                p.getMaxCombo()
        ));

        // แสดงผลลัพธ์ตอนจบ
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

        // เปลี่ยนข้อความและสี
        questionLabel.setText("GAME FINISHED!");
        questionLabel.setBackground(new Color(65, 48, 48));
        questionLabel.setForeground(new Color(220, 180, 180));
        showStatus("Finished", PURPLE);
    }

    // รีเซ็ตหน้าจอเกม

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

    // Dialog ยืนยันแบบตกแต่ง

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

        styleGameButton(yesButton, GREEN);
        styleGameButton(noButton, RED);

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

    // Dialog แสดงผลลัพธ์สุดท้าย

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

        // เพิ่มข้อมูลผลลัพธ์
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

    // เมธอดช่วยสร้าง UI

    // สร้าง Label มาตรฐาน
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(TEXT);
        return label;
    }

    // สร้าง TextField มาตรฐาน
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

    // สร้าง ComboBox สำหรับเลือกระดับความยาก
    private JComboBox<String> createComboBox() {
        JComboBox<String> box = new JComboBox<>(new String[]{"EASY", "MEDIUM", "HARD"});
        box.setFont(new Font("Arial", Font.PLAIN, 18));
        box.setBackground(new Color(50, 56, 65));
        box.setForeground(new Color(45, 45, 45));
        return box;
    }

    // ตกแต่งปุ่ม
    private void styleGameButton(JButton button, Color bg) {
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setBackground(bg);
        button.setForeground(new Color(35, 35, 35));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // สร้างกล่องค่า HUD
    private JLabel createHUDBox(String value) {
        JLabel label = new JLabel(value, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(GOLD);
        return label;
    }

    // ห่อ HUD เป็น panel พร้อมหัวข้อ
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

    // เพิ่มข้อมูลแต่ละแถวในหน้าผลลัพธ์
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

    // ทำให้กล่องโจทย์กระพริบตอนตอบถูก/ผิด
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

    // แสดงสถานะธรรมดา
    private void showStatus(String message) {
        statusLabel.setText("Status: " + message);
    }

    // แสดงสถานะพร้อมเปลี่ยนสี
    private void showStatus(String message, Color color) {
        statusLabel.setText("Status: " + message);
        statusLabel.setForeground(color);
    }
}