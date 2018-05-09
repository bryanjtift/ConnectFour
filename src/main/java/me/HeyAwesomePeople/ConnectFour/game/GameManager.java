package me.HeyAwesomePeople.ConnectFour.game;

import me.HeyAwesomePeople.ConnectFour.User;
import me.HeyAwesomePeople.ConnectFour.Utils;
import me.HeyAwesomePeople.ConnectFour.gui.GamePage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {

        /*
              Game Board
           C1 C2 C3 C4 C5 C6 C7

       R1  00 01 02 03 04 05 06
       R2  07 08 09 10 11 12 13
       R3  14 15 16 17 18 19 20
       R4  21 22 23 24 25 26 27
       R5  28 29 30 31 32 33 34
       R6  35 36 37 38 39 40 41

     */

    private GamePage game;

    // Variables that hold who's turn it is and who is the winner
    private User currentPlayer;
    private User winner;

    // Images loaded into the program at run-time
    private ImageIcon grayIcon;
    private ImageIcon redIcon;
    private ImageIcon blueIcon;

    // Manages board locations and images
    private GamePiece[][] boardImgs = new GamePiece[10][10];
    private User[][] board = new User[10][10];
    private ArrayList<GamePiece> gamePieces = new ArrayList<>();

    public GameManager(GamePage game) {
        this.game = game;

        // Sets a listener for the reset button to reset the board/game
        game.getResetButton().addActionListener(e -> resetBoard());

        // Loads all images in from the resources folder
        grayIcon = Utils.getScaledImage(new ImageIcon(getClass().getClassLoader().getResource("resources/gray_circle.png")).getImage());
        redIcon = Utils.getScaledImage(new ImageIcon(getClass().getClassLoader().getResource("resources/red_circle.png")).getImage());
        blueIcon = Utils.getScaledImage(new ImageIcon(getClass().getClassLoader().getResource("resources/blue_circle.png")).getImage());

        // Labels the board and sets up the images
        // Game is ready after this is setup
        setupGame();
    }

    /**
     * Sets up the game
     */
    private void setupGame() {
        setupPlacements();
        setupGameBoard();

        // Pick a random color to begin the game
        if (ThreadLocalRandom.current().nextInt(100) > 50) {
            setTurn(User.RED);
        } else {
            setTurn(User.BLUE);
        }

        // Ensures the game board has all undefined parts
        Iterator<GamePiece> iterator = gamePieces.iterator();
        while (iterator.hasNext()) {
            iterator.next().setUser(User.UNDEFINED);
        }
    }

    /**
     * Resets the game board. Re-chooses starting player, sets winner to null, removes win message, and resets board.
     */
    private void resetBoard() {
        // Clears board
        game.getGamePanel().removeAll();
        // Sets board back up with gray points
        setupGame();
        // Clears winner
        winner = null;
        // Clears win message
        game.setBottomMsg("");
    }

    /**
     * Sets up the points on the board with blank (gray) game pieces.
     * The game pieces will later be assigned to a user and change colors.
     *
     * It also makes the first row of game pieces clickable for users
     * to drop their pieces from.
     */
    private void setupPlacements() {
        // Loop through all points on the board
        for (int x = 0; x < 6; x++) { // rows
            for (int y = 0; y < 7; y++) { // columns
                GamePiece point = new GamePiece();

                // If the point is in the first row, make it clickable.
                if (x == 0) {
                    makePointClickable(point, x, y);
                }

                point.setIcon(grayIcon);
                board[x][y] = User.UNDEFINED;
                boardImgs[x][y] = point;
                gamePieces.add(point);
            }
        }

        setupGameBoard();
    }

    /**
     * Sets who's turn it is
     *
     * @param t     User object who's turn it is
     */
    private void setTurn(User t) {
        currentPlayer = t;
        game.setTurnMessage(currentPlayer);
    }

    /**
     * This method makes the game piece clickable.
     * It adds a click listener to the game pieces to track when
     * they are clicked and then triggers a piece to drop, if it's
     * allowed to.
     *
     * @param piece GamePiece that is being make clickable
     * @param row   Row that the GamePiece is on
     * @param column    Column that the GamePiece is on
     */
    private void makePointClickable(GamePiece piece, int row, int column) {
        piece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Stop the click if the game has already determined a winner
                if (winner != null) return;

                // Check to see if clickable point is undefined or not
                if (board[row][column] == User.UNDEFINED) {
                    // Drop the chip
                    dropPiece(piece, column);

                    // Check to see if there is a winner
                    if (areFourConnected()) {
                        win();
                        return;
                    }

                    // Set new current player
                    if (currentPlayer == User.RED) {
                        setTurn(User.BLUE);
                    } else if (currentPlayer == User.BLUE) {
                        setTurn(User.RED);
                    }
                }
            }
        });
    }

    /**
     * Method that sets the winner, game is now over!
     */
    private void win() {
        winner = currentPlayer;
        game.setBottomMsg("Congratulations! " + winner.getName() + " has won!");
    }

    /**
     * This method is very special. It scans the whole board
     * to see if any 4 pieces are in a row.
     *
     * @return  True if there is a win, false if there isn't.
     */
    private boolean areFourConnected() {
        int height = 7;
        int width = 8;
        // horizontalCheck
        for (int j = 0; j < height - 3; j++) {
            for (int i = 0; i < width; i++) {
                if (this.board[i][j] == currentPlayer && this.board[i][j + 1] == currentPlayer && this.board[i][j + 2] == currentPlayer && this.board[i][j + 3] == currentPlayer) {
                    return true;
                }
            }
        }
        // verticalCheck
        for (int i = 0; i < width - 3; i++) {
            for (int j = 0; j < height; j++) {
                if (this.board[i][j] == currentPlayer && this.board[i + 1][j] == currentPlayer && this.board[i + 2][j] == currentPlayer && this.board[i + 3][j] == currentPlayer) {
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i = 3; i < width; i++) {
            for (int j = 0; j < height - 3; j++) {
                if (this.board[i][j] == currentPlayer && this.board[i - 1][j + 1] == currentPlayer && this.board[i - 2][j + 2] == currentPlayer && this.board[i - 3][j + 3] == currentPlayer)
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int i = 3; i < width; i++) {
            for (int j = 3; j < height; j++) {
                if (this.board[i][j] == currentPlayer && this.board[i - 1][j - 1] == currentPlayer && this.board[i - 2][j - 2] == currentPlayer && this.board[i - 3][j - 3] == currentPlayer)
                    return true;
            }
        }

        return false;
    }

    /**
     * Method that sets the GamePiece down and changes it's color and User assignment.
     *
     * @param row   Row to set GamePiece
     * @param column    Column to set GamePiece
     * @param piece     GamePiece to modify color/name
     */
    private void setPlacement(int row, int column, GamePiece piece) {
        piece.setUser(currentPlayer);
        board[row][column] = piece.getUser();
        boardImgs[row][column].setIcon((currentPlayer == User.BLUE) ? blueIcon : redIcon);
    }

    /**
     * Method drops the GamePiece from a higher location.
     * This will calculate how far it can fall on the board
     * before being set by setPlacement()
     *
     * @param piece GamePiece being dropped
     * @param column    Column the GamePiece is being dropped from (assumed drop from top row)
     */
    private void dropPiece(GamePiece piece, int column) {
        Integer furthestLocation = 0;
        for (int x = 0; x < 6; x++) {
            if (board[x][column] == User.UNDEFINED) {
                furthestLocation = x;
            }
        }
        setPlacement(furthestLocation, column, piece);
    }

    /**
     * Sets up the layout of the GameBoard using Java Swing
     */
    private void setupGameBoard() {
        GroupLayout layout = new GroupLayout(game.getGamePanel());
        game.getGamePanel().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][0])
                        .addComponent(boardImgs[1][0])
                        .addComponent(boardImgs[2][0])
                        .addComponent(boardImgs[3][0])
                        .addComponent(boardImgs[4][0])
                        .addComponent(boardImgs[5][0]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][1])
                        .addComponent(boardImgs[1][1])
                        .addComponent(boardImgs[2][1])
                        .addComponent(boardImgs[3][1])
                        .addComponent(boardImgs[4][1])
                        .addComponent(boardImgs[5][1]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][2])
                        .addComponent(boardImgs[1][2])
                        .addComponent(boardImgs[2][2])
                        .addComponent(boardImgs[3][2])
                        .addComponent(boardImgs[4][2])
                        .addComponent(boardImgs[5][2]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][3])
                        .addComponent(boardImgs[1][3])
                        .addComponent(boardImgs[2][3])
                        .addComponent(boardImgs[3][3])
                        .addComponent(boardImgs[4][3])
                        .addComponent(boardImgs[5][3]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][4])
                        .addComponent(boardImgs[1][4])
                        .addComponent(boardImgs[2][4])
                        .addComponent(boardImgs[3][4])
                        .addComponent(boardImgs[4][4])
                        .addComponent(boardImgs[5][4]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][5])
                        .addComponent(boardImgs[1][5])
                        .addComponent(boardImgs[2][5])
                        .addComponent(boardImgs[3][5])
                        .addComponent(boardImgs[4][5])
                        .addComponent(boardImgs[5][5]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardImgs[0][6])
                        .addComponent(boardImgs[1][6])
                        .addComponent(boardImgs[2][6])
                        .addComponent(boardImgs[3][6])
                        .addComponent(boardImgs[4][6])
                        .addComponent(boardImgs[5][6]))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(boardImgs[0][0])
                        .addComponent(boardImgs[0][1])
                        .addComponent(boardImgs[0][2])
                        .addComponent(boardImgs[0][3])
                        .addComponent(boardImgs[0][4])
                        .addComponent(boardImgs[0][5])
                        .addComponent(boardImgs[0][6]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(boardImgs[1][0])
                        .addComponent(boardImgs[1][1])
                        .addComponent(boardImgs[1][2])
                        .addComponent(boardImgs[1][3])
                        .addComponent(boardImgs[1][4])
                        .addComponent(boardImgs[1][5])
                        .addComponent(boardImgs[1][6]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(boardImgs[2][0])
                        .addComponent(boardImgs[2][1])
                        .addComponent(boardImgs[2][2])
                        .addComponent(boardImgs[2][3])
                        .addComponent(boardImgs[2][4])
                        .addComponent(boardImgs[2][5])
                        .addComponent(boardImgs[2][6]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(boardImgs[3][0])
                        .addComponent(boardImgs[3][1])
                        .addComponent(boardImgs[3][2])
                        .addComponent(boardImgs[3][3])
                        .addComponent(boardImgs[3][4])
                        .addComponent(boardImgs[3][5])
                        .addComponent(boardImgs[3][6]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(boardImgs[4][0])
                        .addComponent(boardImgs[4][1])
                        .addComponent(boardImgs[4][2])
                        .addComponent(boardImgs[4][3])
                        .addComponent(boardImgs[4][4])
                        .addComponent(boardImgs[4][5])
                        .addComponent(boardImgs[4][6]))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(boardImgs[5][0])
                        .addComponent(boardImgs[5][1])
                        .addComponent(boardImgs[5][2])
                        .addComponent(boardImgs[5][3])
                        .addComponent(boardImgs[5][4])
                        .addComponent(boardImgs[5][5])
                        .addComponent(boardImgs[5][6]))
        );
    }

}
