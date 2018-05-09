package me.HeyAwesomePeople.ConnectFour;

import me.HeyAwesomePeople.ConnectFour.game.GameManager;
import me.HeyAwesomePeople.ConnectFour.gui.GamePage;

public class Main {

    private Main() {
        new GameManager(new GamePage());
        //TODO must use arraylist and use iterator to go through it
        //TODO interface class, abstract class
        //TODO polymorphism
    }

    public static void main(String args[]) {
        new Main();
    }

}
