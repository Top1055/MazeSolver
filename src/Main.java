import javax.swing.*;

public class Main {

    public JFrame frame;
    public Panel panel;

    public Main() {
        frame = new JFrame();
        panel = new Panel();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}