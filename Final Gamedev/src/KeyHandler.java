import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel panel;

    public KeyHandler(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) panel.moveUp();
        if (code == KeyEvent.VK_S) panel.moveDown();
        if (code == KeyEvent.VK_A) panel.moveLeft();
        if (code == KeyEvent.VK_D) panel.moveRight();
        if (code == KeyEvent.VK_UP) panel.moveUp();
        if (code == KeyEvent.VK_DOWN) panel.moveDown();
        if (code == KeyEvent.VK_RIGHT) panel.moveRight();
        if (code == KeyEvent.VK_LEFT) panel.moveLeft();
        if (code == KeyEvent.VK_ESCAPE) System.exit(0);


        // Add sprinting logic: set sprinting to true when Shift is pressed
        if (code == KeyEvent.VK_SHIFT) panel.setSprinting(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Add sprinting logic: set sprinting to false when Shift is released
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            panel.setSprinting(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}