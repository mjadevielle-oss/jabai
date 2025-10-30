import javax.swing.JFrame ;

public class Main {

    public static void main (String [] args)
    {
        System.out.println("Game starting......");
        JFrame frame = new JFrame("Survive the unknown land");

        StartScreen startScreen = new StartScreen(frame);

        frame.add(startScreen);

        frame.setSize(640,480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}