// NPC.java (modified)
public class NPC {
    private String name;
    private String dialogue;
    private int health;
    private boolean isFriendly;
    private int x; // World X position
    private int y; // World Y position

    // Constructor
    public NPC(String name, String dialogue, int health, boolean isFriendly, int x, int y) {
        this.name = name;
        this.dialogue = dialogue;
        this.health = health;
        this.isFriendly = isFriendly;
        this.x = x;
        this.y = y;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }

    public int getHealth() {
        return health;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setters for position
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Methods
    public String speak() {
        return name + " says: " + dialogue;
    }

    public void takeDamage(int damage) {
        if (health > 0) {
            health -= damage;
            if (health <= 0) {
                System.out.println(name + " has been defeated!");
            } else {
                System.out.println(name + " takes " + damage + " damage. Health: " + health);
            }
        }
    }

    public String interact() {
        if (isFriendly) {
            return speak();
        } else {
            return name + " attacks!";
        }
    }

    public boolean isAlive() {
        return health > 0;
    }
}