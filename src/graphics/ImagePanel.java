package graphics;

import gui.GraphicalElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Image display
 */
public class ImagePanel implements GraphicalElement
{

    private BufferedImage image;
    private int x;
    private int y;

    /**
     * used to build an image panel
     * @param x x coordinate
     * @param y y coordinate
     * @param fp file path to the image
     */
    public ImagePanel(int x, int y, String fp)
    {
        this.x = x;
        this.y = y;
        try
        {
            this.image = ImageIO.read(new File(fp));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void paint(Graphics2D graphics2D)
    {
        graphics2D.drawImage(this.image, this.x - this.image.getWidth() / 2, this.y - this.image.getHeight() / 2, null);
    }
}
