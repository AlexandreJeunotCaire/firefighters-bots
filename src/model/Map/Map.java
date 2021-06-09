package model.Map;

import model.Direction;

import java.util.ArrayList;

/**
 * Represent a simulation map
 */
public class Map
{
    ArrayList<ArrayList<Cell>> cells;
    private int cellSize;
    private int height;
    private int width;

    /**
     * Build  a new map from all cells
     * @param height the map height
     * @param width the map width
     * @param cellSize the cells size
     * @param c all cells
     */
    public Map(int height, int width, int cellSize, ArrayList<ArrayList<Cell>> c)
    {
        this.height = height;
        this.width = width;
        this.cellSize = cellSize;

        this.cells = c;
    }

    /**
     *
     * @return the map height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     *
     * @return the map width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Return the ask cell if it exist
     * @param row the cell row
     * @param column the cell column
     * @return the asked cell if it exist
     */
    public Cell getCell(int row, int column)
    {
        return this.cells.get(row).get(column);
    }

    /**
     * Check if a cell has a neighbour in the given direction
     * @param src the source cell
     * @param dir the related direction
     * @return true if the neighbour exist, false otherwise
     */
    public boolean isNeighbor(Cell src, Direction dir)
    {
        try
        {
            this.cells.get(src.getRow() + dir.getiOffset()).get(src.getColumn() + dir.getjOffset());
            return true;
        } catch (IndexOutOfBoundsException e)
        {
            return false;
        }
    }

    /**
     *
     * @param src the source cell
     * @param dir the related direction
     * @return the neighbour cell
     */
    public Cell getNeighbor(Cell src, Direction dir)
    {
        return this.cells.get(src.getRow() + dir.getiOffset()).get(src.getColumn() + dir.getjOffset());
    }

    /**
     *
     * @return all cells
     */
    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }

    /**
     *
     * @return the string representation of the map
     */
    @Override
    public String toString()
    {
        String str = "{height : " + this.height + ", width : " + this.width + " , " + "cells : [";

        for (int i = 0; i < this.cells.size(); i++)
        {
            str += "[";
            for (int j = 0; j < this.cells.get(0).size(); j++)
            {
                str += this.cells.get(i).get(j) + ",";
            }
            str += "]";
        }
        str += "]";
        return str;
    }
}