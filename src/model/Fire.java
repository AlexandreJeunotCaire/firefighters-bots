package model;

import graphics.CustomDrawable;
import graphics.ImagePanel;
import gui.GUISimulator;
import model.Map.Cell;

import java.awt.*;

/**
 * represent a fire
 */
public class Fire implements CustomDrawable
{
    private static Color fireColor = Color.RED;
    private static String texturePath = "ressources/fire.png";
    private Cell position;
    private int intensity;
    private boolean extinguished = false;

    /**
     * Build a new fire at the given position
     * @param position the position of the fire
     * @param intensity its intensity
     */
    public Fire(Cell position, int intensity)
    {
        this.position = position;
        this.intensity = intensity;
    }

    public Cell getPosition()
    {
        return position;
    }

    public int getIntensity()
    {
        return intensity;
    }

    /**
     *
     * @return the fire string representation
     */
    @Override
    public String toString()
    {
        return "{ pos : " + this.position + ", intensity : " + this.intensity + " }";
    }

    /**
     * Remove the given intensity to the fire
     * @param pourWater the water poured onto the fire
     */
    public void extinguishBy(int pourWater)
    {
        this.intensity = this.intensity - pourWater;
        if (this.intensity <= 0)
        {
            this.extinguished = true;
        }
    }

    /**
     *
     * @return true if fire intensity is 0, false otherwise
     */
    public boolean isExtinct()
    {
        return this.extinguished;
    }

    /**
     * Draw the fire
     * @param gui gui to draw on
     */
    @Override
    public void draw(GUISimulator gui)
    {
        if (this.extinguished)
        {
            return;
        }
        int offset = this.printSize / 2;
        int x = this.position.getColumn() * this.printSize + offset;
        int y = this.position.getRow() * this.printSize + offset;

        ImagePanel img = new ImagePanel(x, y, Fire.texturePath);
        gui.addGraphicalElement(img);
    }

}
