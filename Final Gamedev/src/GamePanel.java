import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // Character dimensions (based on drawing size)
    private static final int CHAR_WIDTH = 100;
    private static final int CHAR_HEIGHT = 100;

    // Glick dimensions (adjust these to change Glick size)
    private static final int GLICK_WIDTH = 50;  // Adjust this value to change Glick width
    private static final int GLICK_HEIGHT = 50; // Adjust this value to change Glick height

    // Collision box size adjustment (change these to resize the collision box)
    private static final int COLLISION_WIDTH = 40;   // Change this: smaller = tighter collision //right
    private static final int COLLISION_HEIGHT = 45;  // Change this: smaller = tighter collision //down
    private static final int COLLISION_OFFSET_X = 30; // Horizontal offset from character position
    private static final int COLLISION_OFFSET_Y = 20; // Vertical offset from character position

    public BufferedImage spritermap;

    private int whatframe = 0;
    private int screenX = WIDTH / 4;
    private int screenY = HEIGHT / 5;
    private int worldX = WIDTH / 4;
    private int worldY = HEIGHT / 5;
    private int normalSpeed = 10;
    private int sprintSpeed = 20;
    private boolean isSprinting = false;

    // Sprite arrays for each direction (6 frames each)
    private BufferedImage[] up = new BufferedImage[6];
    private BufferedImage[] down = new BufferedImage[6];
    private BufferedImage[] left = new BufferedImage[6];
    private BufferedImage[] right = new BufferedImage[6];
    private BufferedImage currentImage;
    private BufferedImage endImage;

    // Slime NPC properties (shared sprites)
    private BufferedImage[] slimeSprites = new BufferedImage[8];
    private List<Slime> slimes = new ArrayList<>();
    private Timer slimeTimer;

    private BufferedImage[] GlickSprites = new BufferedImage[10]; // Adjusted to match glickFiles length (10 sprites)
    private List<Glick> glick = new ArrayList<>();
    private Timer glickTimer;

    // Collision detection integration
    private Collision collision;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Color hexColor = Color.decode("#5f95f7");
        setBackground(hexColor);

        loadSprites();
        currentImage = down[0];

        // Initialize collision detection
        collision = new Collision();

        setFocusable(true);
        addKeyListener(new KeyHandler(this));

        // Initialize slimes
        slimes.add(new Slime(300, 280, 10));
        slimes.add(new Slime(300, 250, 10));
        slimes.add(new Slime(300, 390, 10));
        slimes.add(new Slime(300, 350, 10));
        slimes.add(new Slime(300, 370, 10));
        slimes.add(new Slime(300, 900, 10));
        slimes.add(new Slime(300, 790, 10));
        slimes.add(new Slime(300, 800, 10));
        slimes.add(new Slime(300, 750, 10));
        slimes.add(new Slime(300, 1000, 10));
        slimes.add(new Slime(300, 1100, 10));

        // Initialize glicks
        glick.add(new Glick(500, 2000, 10));
        glick.add(new Glick(500, 2090, 10));
        glick.add(new Glick(500, 2150, 10));
        glick.add(new Glick(500, 2250, 10));
        glick.add(new Glick(750, 2250, 10));
        glick.add(new Glick(600, 2250, 10));

        // Start slime movement timer
        slimeTimer = new Timer();
        slimeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Slime slime : slimes) {
                    slime.move();
                }
                repaint();
            }
        }, 0, 500);

        // Start glick movement timer
        glickTimer = new Timer();
        glickTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Glick g : glick) {
                    g.move(worldX, worldY); // Pass player position for attack logic
                }
                repaint();
            }
        }, 0, 500);
    }

    private void loadSprites() {
        String basePath = "res/PLAYER/";

        try {
            spritermap = ImageIO.read(new File("res/Map/emap3.png"));


            String[] upFiles = {
                    "upwalk.png", "upwalkright.png", "upwleft.png",
                    "upwalk.png", "upwalkright.png", "upwleft.png"
            };
            for (int i = 0; i < 6; i++) {
                up[i] = ImageIO.read(new File(basePath + upFiles[i]));
            }

            String[] downFiles = {
                    "dfront.png", "dfrontleft.png", "dfrontright.png",
                    "dfront.png", "dfrontleft.png", "dfrontright.png"
            };
            for (int i = 0; i < 6; i++) {
                down[i] = ImageIO.read(new File(basePath + downFiles[i]));
            }

            String[] leftFiles = {
                    "sider.png", "sider1.png", "sider2.png",
                    "sider.png", "sider1.png", "sider2.png"
            };
            for (int i = 0; i < 6; i++) {
                left[i] = ImageIO.read(new File(basePath + leftFiles[i]));
            }

            String[] rightFiles = {
                    "sidel.png", "sidel1.png", "sidel2.png",
                    "sidel.png", "sidel1.png", "sidel2.png"
            };
            for (int i = 0; i < 6; i++) {
                right[i] = ImageIO.read(new File(basePath + rightFiles[i]));
            }

            String[] slimeFiles = {
                    "slime.png", "slime2.png", "slime3.png", "slime4.png",
                    "slime5.png", "slime6.png", "slime7.png", "slime8.png"
            };
            String[] glickFiles = {
                    "attack.png" , "attackleft.png" , "dead.png" , "enemy.png","enemyright.png" ,"enemyleft.png","front.png",
                    "hilo.png","hiloleft.png","likod.png"
            };

            String basePathSlime = "res/Slime/";
            for (int i = 0; i < 8; i++) {
                slimeSprites[i] = ImageIO.read(new File(basePathSlime + slimeFiles[i]));
            }

            String basePathGlick = "res/Glick/";
            for (int i = 0; i < 10; i++) { // Load all 10 sprites
                GlickSprites[i] = ImageIO.read(new File(basePathGlick + glickFiles[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveUp() {
        int currentSpeed = isSprinting ? sprintSpeed : normalSpeed;
        int newWorldX = worldX;
        int newWorldY = worldY - currentSpeed;

        if (!collision.checkCollision(newWorldX + COLLISION_OFFSET_X, newWorldY + COLLISION_OFFSET_Y,
                COLLISION_WIDTH, COLLISION_HEIGHT)) {
            worldY = newWorldY;
            whatframe = (whatframe + 1) % 6;
            currentImage = up[whatframe];
            endImage = up[0];
            repaint();
        }
    }

    public void moveDown() {
        int currentSpeed = isSprinting ? sprintSpeed : normalSpeed;
        int newWorldX = worldX;
        int newWorldY = worldY + currentSpeed;

        if (!collision.checkCollision(newWorldX + COLLISION_OFFSET_X, newWorldY + COLLISION_OFFSET_Y,
                COLLISION_WIDTH, COLLISION_HEIGHT)) {
            worldY = newWorldY;
            whatframe = (whatframe + 1) % 6;
            currentImage = down[whatframe];
            endImage = down[0];
            repaint();
        }
    }

    public void moveRight() {
        int currentSpeed = isSprinting ? sprintSpeed : normalSpeed;
        int newWorldX = worldX + currentSpeed;
        int newWorldY = worldY;

        if (!collision.checkCollision(newWorldX + COLLISION_OFFSET_X, newWorldY + COLLISION_OFFSET_Y,
                COLLISION_WIDTH, COLLISION_HEIGHT)) {
            worldX = newWorldX;
            whatframe = (whatframe + 1) % 6;
            currentImage = right[whatframe];
            endImage = right[0];
            repaint();
        }
    }

    public void moveLeft() {
        int currentSpeed = isSprinting ? sprintSpeed : normalSpeed;
        int newWorldX = worldX - currentSpeed;
        int newWorldY = worldY;

        if (!collision.checkCollision(newWorldX + COLLISION_OFFSET_X, newWorldY + COLLISION_OFFSET_Y,
                COLLISION_WIDTH, COLLISION_HEIGHT)) {
            worldX = newWorldX;
            whatframe = (whatframe + 1) % 6;
            currentImage = left[whatframe];
            endImage = left[0];
            repaint();
        }
    }

    public void setSprinting(boolean sprinting) {
        this.isSprinting = sprinting;
    }

    // Toggle collision debug visualization
    public void toggleCollisionDebug() {
        collision.toggleDebugMode();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentImage != null && spritermap != null) {
            // Calculate map offset
            int mapDrawX = screenX - worldX;
            int mapDrawY = screenY - worldY;

            // Draw map
            g.drawImage(spritermap, mapDrawX, mapDrawY, null);

            // Draw collision boxes (red semi-transparent rectangles)
            collision.drawCollisionBoxes(g, mapDrawX, mapDrawY);

            // Draw character
            g.drawImage(currentImage, screenX, screenY, CHAR_WIDTH, CHAR_HEIGHT, null);

            // Draw character collision box (green or yellow)
            collision.drawCharacterBox(g, worldX + COLLISION_OFFSET_X, worldY + COLLISION_OFFSET_Y,
                    COLLISION_WIDTH, COLLISION_HEIGHT, mapDrawX, mapDrawY);

            // Draw all slimes
            for (Slime slime : slimes) {
                int slimeScreenX = screenX - worldX + slime.getWorldX();
                int slimeScreenY = screenY - worldY + slime.getWorldY();
                g.drawImage(slimeSprites[slime.getFrame()], slimeScreenX, slimeScreenY, CHAR_WIDTH, CHAR_HEIGHT, null);
            }

            // Draw all glicks
            for (Glick gl : glick) {
                int glickScreenX = screenX - worldX + gl.getWorldX();
                int glickScreenY = screenY - worldY + gl.getWorldY();
                g.drawImage(GlickSprites[gl.getFrame()], glickScreenX, glickScreenY, GLICK_WIDTH, GLICK_HEIGHT, null);
            }

            // Draw info overlay
            drawInfoOverlay(g);
        }
    }

    // Draw info overlay showing position and controls
    private void drawInfoOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(5, 5, 280, 100);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("COLLISION DEBUG", 15, 25);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Position: (" + worldX + ", " + worldY + ")", 15, 45);
        g.drawString("Speed: " + (isSprinting ? "SPRINT" : "Normal"), 15, 60);
        g.drawString("Press C - Toggle Collision View", 15, 75);
        g.drawString("Debug: " + (collision.isDebugMode() ? "ON" : "OFF"), 15, 90);
    }

    // Inner class for Slime NPC
    private static class Slime {
        private int worldX, worldY, frame, speed;
        private Random random;

        public Slime(int startX, int startY, int speed) {
            this.worldX = startX;
            this.worldY = startY;
            this.speed = speed;
            this.frame = 0;
            this.random = new Random();
        }

        public void move() {
            int direction = random.nextInt(4);
            switch (direction) {
                case 0: worldY -= speed; break;
                case 1: worldY += speed; break;
                case 2: worldX -= speed; break;
                case 3: worldX += speed; break;
            }
            frame = (frame + 1) % 8;
        }

        public int getWorldX() { return worldX; }
        public int getWorldY() { return worldY; }
        public int getFrame() { return frame; }
    }

    // Inner class for Glick NPC
    private static class Glick {
        private int worldX, worldY, frame, speed;
        private Random random;
        private boolean isAttacking = false;

        public Glick(int startX, int startY, int speed) {
            this.worldX = startX;
            this.worldY = startY;
            this.speed = speed;
            this.frame = 3; // Start with "enemy.png" as idle
            this.random = new Random();
        }

        public void move(int playerX, int playerY) {
            double distance = Math.sqrt(Math.pow(playerX - worldX, 2) + Math.pow(playerY - worldY, 2));
            int attackRange = 100; // Adjust range as needed

            if (distance <= attackRange) {
                // Attack mode: move towards player
                isAttacking = true;
                int dx = playerX - worldX;
                int dy = playerY - worldY;

                // Determine direction
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        worldX += speed; // Move right
                        frame = 1; // attackleft.png (swapped for correct direction)
                    } else {
                        worldX -= speed; // Move left
                        frame = 0; // attack.png (swapped for correct direction)
                    }
                } else {
                    if (dy > 0) {
                        worldY += speed; // Move down
                        frame = 6; // front.png (assuming down/front)
                    } else {
                        worldY -= speed; // Move up
                        frame = 9; // likod.png (corrected index)
                    }
                }
            } else {
                // Idle mode: random movement
                isAttacking = false;
                int direction = random.nextInt(4);
                switch (direction) {
                    case 0: worldY -= speed; frame = 9; break; // Up, likod.png (corrected index)
                    case 1: worldY += speed; frame = 6; break; // Down, front.png
                    case 2: worldX -= speed; frame = 5; break; // Left, enemyleft.png
                    case 3: worldX += speed; frame = 4; break; // Right, enemyright.png
                }
            }
        }

        public int getWorldX() { return worldX; }
        public int getWorldY() { return worldY; }
        public int getFrame() { return frame; }
    }
}
