package me.HeyAwesomePeople.ConnectFour;

import java.awt.*;

public enum User implements UserType {

    RED("Red", Color.RED), BLUE("Blue", Color.BLUE), UNDEFINED("No User", Color.GRAY);

    final private Color color;
    private String name;

    User(String name, Color color) {
        this.color = color;
        this.name = name;
    }

    /**
     * Returns the name of the User object, usually a color.
     * @return      the name of the object
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the color of the User object as a AWT object
     * @return      the java.awt.Color of the object
     */
    public Color getColor() { return this.color; }

}
