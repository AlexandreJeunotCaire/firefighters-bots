package model.Map;

import graphics.CustomDrawable;
import graphics.ImagePanel;
import gui.GUISimulator;

import java.awt.*;

/**
 * Represent a map cell
 */
public class Cell implements CustomDrawable
{
    public static int cellPrintSizeRatio = 100;
    private int size;
    private int row;
    private int column;
    private Color color;

    private TerrainNature nature;

    /**
     * Build a new cell
     * @param row the cell row
     * @param column the cell column
     * @param size the cell width and height
     * @param nature the cell terrain nature
     * @see TerrainNature
     */
    public Cell(int row, int column, int size, TerrainNature nature)
    {
        this.row = row;
        this.column = column;
        this.nature = nature;
        this.size = size;
        this.color = nature.getColor();
    }

    /**
     *
     * @return the cell column
     */
    public int getColumn()
    {
        return column;
    }

    /**
     *
     * @return the cell row
     */
    public int getRow()
    {
        return row;
    }

    /**
     *
     * @return the cell width and height
     */
    public int getSize()
    {
        return size;
    }

    /**
     *
     * @return the cell terrain nature
     * @see TerrainNature
     */
    public TerrainNature getNature()
    {
        return nature;
    }

    /**
     * Draw the cell on the given screen
     * @param gui gui to draw on
     */

    @Override
    public void draw(GUISimulator gui)
    {
        int offset = this.printSize / 2;
        int x = this.column * this.printSize + offset;
        int y = this.row * this.printSize + offset;

        ImagePanel img = new ImagePanel(x, y, this.getNature().getTexturePath());
        gui.addGraphicalElement(img);
    }

    /**
     * Put the cell into string format
     * @return the string representation of the cell
     */
    @Override
    public String toString()
    {
        return "{[" + this.row + ", " + this.column + "], " + this.nature.getCode() + "}";
    }
}