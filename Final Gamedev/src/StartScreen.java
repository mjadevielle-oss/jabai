import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartScreen extends JPanel {
    private JFrame frame;

    public StartScreen(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Survive the unknown land", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 45));
        title.setForeground(Color.BLACK);
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        centerPanel.add(startButton);  // Centered by GridBagLayout
        add(centerPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
    }
}