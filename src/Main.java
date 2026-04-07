import javax.swing.*;

// คลาส Main เป็นจุดเริ่มต้นของโปรแกรม
public class Main {

    // เมธอด main คือเมธอดหลักที่โปรแกรมจะเริ่มทำงานจากตรงนี้
    public static void main(String[] args) {

        try {
            // ตั้งค่าหน้าตาโปรแกรมให้เหมือนกับระบบปฏิบัติการของเครื่องผู้ใช้
            // เช่น Windows, macOS เป็นต้น
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ถ้าเกิดข้อผิดพลาดในการตั้งค่า Look and Feel
            // ให้แสดงรายละเอียดข้อผิดพลาดออกมา
            e.printStackTrace();
        }

        // ใช้ SwingUtilities.invokeLater เพื่อให้ GUI ทำงานบน Event Dispatch Thread (EDT)
        // ซึ่งเป็นวิธีที่ถูกต้องในการเปิดหน้าต่าง Swing
        SwingUtilities.invokeLater(() -> new StartMenu());
    }
}