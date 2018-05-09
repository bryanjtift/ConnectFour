package me.HeyAwesomePeople.ConnectFour.game;

import me.HeyAwesomePeople.ConnectFour.User;

import javax.swing.*;

class GamePiece extends JLabel {

    private User user;

    GamePiece() {
        // Default user type for game piece
        this.user = User.UNDEFINED;
    }

    /**
     * Allows you to set the user for the game piece
     *
     * @param user  User object, typically the one who just placed the piece
     */
    void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the owner of the piece as a User object
     *
     * @return  User object
     */
    User getUser() {
        return this.user;
    }

}
