import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Collision {
    private List<Rectangle> walls;
    private boolean debugMode = true;  // Set to true to show collision boxes by default

    public Collision() {
        walls = new ArrayList<>();
// horizontal more width than height. ______ pakera
         //Vertical more height thank width | patalakad
        // x = left or right move, y = up or down move,
        walls.add(new Rectangle(205, 140, 5, 2300)); // 1
        walls.add(new Rectangle(210, 115, 920, 5)); // 2
        walls.add(new Rectangle(264, 178  , 400, 5)); // 3
        walls.add(new Rectangle(264, 190, 5, 245)); // 4
        walls.add(new Rectangle(264, 500, 5, 790)); // 5
        walls.add(new Rectangle(264, 1350, 5, 195)); // 6
        walls.add(new Rectangle(264, 1600, 5, 390)); // 7
        walls.add(new Rectangle(264, 2050, 5, 90)); // 8
        walls.add(new Rectangle(1180, 20, 5, 1250)); //9
        walls.add(new Rectangle(1130, 20, 5, 100)); //10
        walls.add(new Rectangle(1130, 10, 160, 5)); // 11
        walls.add(new Rectangle(1180, 1390, 5, 1250)); //12
        walls.add(new Rectangle(1180, 1280, 80, 5)); // 13
        walls.add(new Rectangle(1180, 1350, 80, 5)); //14
        walls.add(new Rectangle(1130, 2490, 200, 5)); // 15
        walls.add(new Rectangle(1130, 2350, 5, 100)); // 16
        walls.add(new Rectangle(205, 2350, 900, 5)); // 17
        walls.add(new Rectangle(1250, 10, 5, 1250)); // 18
        walls.add(new Rectangle(1250, 1390, 5, 1250)); //19
        walls .add(new Rectangle(1310, 2370, 5, 100)); //20
        walls.add(new Rectangle(1310, 10, 5, 100));//21
        walls.add(new Rectangle(1310, 110, 1000, 5)); //22
        walls.add(new Rectangle(2290, 150, 5, 20)); //23
        walls.add(new Rectangle(2280, 180, 5, 2300)); //24

        System.out.println("Loaded " + walls.size() + " collision walls");
    }

    private Rectangle makeRectangle(int x1, int y1, int x2, int y2) {
        int left = Math.min(x1, x2);
        int top = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        if (width == 0) width = 1;
        if (height == 0) height = 1;

        return new Rectangle(left, top, width, height);
    }

    public boolean checkCollision(int charX, int charY, int charWidth, int charHeight) {
        Rectangle charRect = new Rectangle(charX, charY, charWidth, charHeight);

        for (Rectangle wall : walls) {
            if (charRect.intersects(wall)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Draw collision boxes for debugging
     * Call this from GamePanel's paintComponent method
     */
    public void drawCollisionBoxes(Graphics g, int offsetX, int offsetY) {
        if (!debugMode) return;

        Graphics2D g2d = (Graphics2D) g;

        // Draw all collision walls with semi-transparent red
        for (int i = 0; i < walls.size(); i++) {
            Rectangle wall = walls.get(i);
            int screenX = wall.x + offsetX;
            int screenY = wall.y + offsetY;

            // Fill with semi-transparent red
            g2d.setColor(new Color(255, 0, 0, 100));  // Red with 100/255 opacity
            g2d.fillRect(screenX, screenY, wall.width, wall.height);

            // Draw solid red border
            g2d.setColor(new Color(255, 0, 0, 200));  // More opaque red
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(screenX, screenY, wall.width, wall.height);

            // Draw wall number for easy identification
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("#" + (i + 1), screenX + 5, screenY + 15);
        }
    }

    /**
     * Draw character's collision box for debugging
     * Shows green when safe, yellow when colliding
     */
    public void drawCharacterBox(Graphics g, int charX, int charY, int charWidth, int charHeight,
                                 int offsetX, int offsetY) {
        if (!debugMode) return;

        Graphics2D g2d = (Graphics2D) g;

        // Adjust collision box size (make it smaller than character sprite)
        int boxPadding = 1;  // Reduce this number to make box bigger, increase to make smaller
        int adjustedX = charX + boxPadding;
        int adjustedY = charY + boxPadding;
        int adjustedWidth = charWidth - (boxPadding * 2);
        int adjustedHeight = charHeight - (boxPadding * 2);

        // Calculate screen position
        int screenX = adjustedX + offsetX;
        int screenY = adjustedY + offsetY;

        // Check if currently colliding (using adjusted collision box)
        boolean isColliding = checkCollision(adjustedX, adjustedY, adjustedWidth, adjustedHeight);

        // Draw character collision box (green if no collision, yellow if colliding)
        if (isColliding) {
            g2d.setColor(new Color(255, 255, 0, 120));  // Yellow when colliding
        } else {
            g2d.setColor(new Color(0, 255, 0, 80));  // Green when safe
        }
        g2d.fillRect(screenX, screenY, adjustedWidth, adjustedHeight);

        // Draw border
        g2d.setColor(isColliding ? new Color(255, 255, 0) : new Color(0, 255, 0));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(screenX, screenY, adjustedWidth, adjustedHeight);

        // Draw collision status text (smaller size)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));  // Changed to size 8 and PLAIN style
        String status = isColliding ? "COLLISION!" : "Safe";

        // Position options - choose one by uncommenting:

        // Option 1: Bottom of character box (current)
        // g2d.drawString(status, screenX + 5, screenY + adjustedHeight - 5);

        // Option 2: Top of character box
        g2d.drawString(status, screenX + 5, screenY + 12);

        // Option 3: Center of character box
        // g2d.drawString(status, screenX + adjustedWidth/2 - 10, screenY + adjustedHeight/2);

        // Option 4: Above character box
        // g2d.drawString(status, screenX + 5, screenY - 5);

        // Option 5: Below character box
        // g2d.drawString(status, screenX + 5, screenY + adjustedHeight + 15);
    }

    /**
     * Toggle debug mode on/off
     */
    public void toggleDebugMode() {
        debugMode = !debugMode;
        System.out.println("Collision Debug Mode: " + (debugMode ? "ON" : "OFF"));
    }

    /**
     * Set debug mode
     */
    public void setDebugMode(boolean enabled) {
        debugMode = enabled;
    }

    /**
     * Check if debug mode is enabled
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Add a new collision wall
     */
    public void addWall(int x1, int y1, int x2, int y2) {
        walls.add(makeRectangle(x1, y1, x2, y2));
        System.out.println("Added wall #" + walls.size() + ": (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
    }

    /**
     * Remove the last added wall
     */
    public void removeLastWall() {
        if (!walls.isEmpty()) {
            walls.remove(walls.size() - 1);
            System.out.println("Removed last wall. Total walls: " + walls.size());
        }
    }

    /**
     * Print all wall coordinates (useful for copying to code)
     */
    public void printWalls() {
        System.out.println("\n=== Current Collision Walls ===");
        for (int i = 0; i < walls.size(); i++) {
            Rectangle wall = walls.get(i);
            System.out.println("walls.add(makeRectangle(" + wall.x + ", " + wall.y + ", " +
                    (wall.x + wall.width) + ", " + (wall.y + wall.height) + "));");
        }
        System.out.println("Total boxes: " + walls.size());
        System.out.println("==============================\n");
    }
}