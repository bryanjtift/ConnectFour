package me.HeyAwesomePeople.ConnectFour.gui;

import me.HeyAwesomePeople.ConnectFour.User;

import javax.swing.*;
import java.awt.*;

public class GamePage {

    private JPanel gamePanel = new JPanel();

    // Top of Page
    private JLabel titleText = new JLabel("Connect Four");
    private JButton resetGame = new JButton("Reset");
    private JLabel turn = new JLabel();

    // Actual game
    private JPanel game = new JPanel();

    // Message at bottom
    private JLabel bottomMsg = new JLabel();

    public GamePage() {
        // Defines frame and sets frame title
        JFrame frame = new JFrame("Connect Four");
        // Sets frame size on the screen
        frame.setSize(800, 600);
        // Sets what happens when user closes screen
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createPanel();

        // Sets where the frame pops up (null is center of screen)
        frame.setLocationRelativeTo(null);

        // Show the frame
        frame.setVisible(true);
        // Adds gamePanel to the frame
        frame.add(gamePanel);
    }

    /**
     * Creates the panel layout for our game frame.
     * This uses GridBagLayout and BorderLayout to make a nice and neat frame for our game.
     */
    private void createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        {
            JPanel border = new JPanel();
            border.setLayout(new BorderLayout());

            JPanel top = new JPanel();
            {
                titleText.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
                top.add(resetGame);
                top.add(titleText);
                top.add(turn);
            }
            JPanel middle = new JPanel();
            {
                game.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                middle.add(game);
            }
            JPanel bottom = new JPanel();
            {
                bottom.add(bottomMsg);
            }

            border.add(top, BorderLayout.PAGE_START);
            border.add(middle, BorderLayout.CENTER);
            border.add(bottom, BorderLayout.PAGE_END);

            panel.add(border);
        }

        gamePanel.add(panel);

    }

    /**
     * Return the game panel
     *
     * @return  JPanel of the game
     */
    public JPanel getGamePanel() {
        return this.game;
    }

    /**
     * Allows you to set the message in the top right of the screen.
     * Designed to be used to show who's turn it is in the game.
     *
     * @param user  User object who's turn it is
     */
    public void setTurnMessage(User user) {
        turn.setText(user.getName() + "'s Turn");
        turn.setForeground(user.getColor());
    }

    /**
     * Allows you to set the message at the bottom of the frame.
     * Designed to be used to show who won the game.
     *
     * @param s String to set the message to
     */
    public void setBottomMsg(String s) {
        bottomMsg.setText(s);
    }

    /**
     * Returns the JButton object for the reset button
     *
     * @return  Reset button on the game panel
     */
    public JButton getResetButton() {
        return this.resetGame;
    }

}
