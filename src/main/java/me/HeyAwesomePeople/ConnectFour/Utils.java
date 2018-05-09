package me.HeyAwesomePeople.ConnectFour;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    /**
     * Method that automatically scales all incoming images to 64x64 to fit the playboard.
     *
     * @param srcImg    Image that you are going to rescale
     * @return          ImageIcon that has been resized
     */
    public static ImageIcon getScaledImage(Image srcImg){
        BufferedImage resizedImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, 64, 64, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }
}
