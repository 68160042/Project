import java.awt.*;

public class SoundPlayer {

    public static void playCorrectSound() {
        Toolkit.getDefaultToolkit().beep();
    }

    public static void playWrongSound() {
        Toolkit.getDefaultToolkit().beep();
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            System.out.println("Sound delay interrupted.");
        }
        Toolkit.getDefaultToolkit().beep();
    }
}