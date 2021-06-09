package model.Map;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;

/**
 * Represent a map builder
 */
public class MapBuilder
{
    private ArrayList<ArrayList<Cell>> map;
    private ArrayList<Cell> currentRow;
    private int cellSize;
    private int expectedHeight;
    private int expectedWidth;

    /**
     * Create a new map builder
     * @param expectedHeight the map expected height at the end
     * @param expectedWidth the map expected width at the end
     * @param cellSize the cells size
     */
    public MapBuilder(int expectedHeight, int expectedWidth, int cellSize)
    {
        this.cellSize = cellSize;
        this.expectedHeight = expectedHeight;
        this.expectedWidth = expectedWidth;
        this.map = new ArrayList<ArrayList<Cell>>();
    }

    /**
     * add a new row to the map
     */
    public void newRow()
    {
        this.currentRow = new ArrayList<Cell>();
        this.map.add(this.currentRow);
    }

    /**
     * Add a new cell to the current row
     * @param n nature terrain of the new cell to add
     */
    public void addCell(TerrainNature n)
    {
        this.currentRow.add(new Cell(this.map.indexOf(this.currentRow), this.currentRow.size(), this.cellSize, n));
    }

    /**
     * Generate the map
     * @return the computed map
     * @throws InvalidAttributeValueException
     */
    public Map buildMap() throws InvalidAttributeValueException
    {
        if (this.expectedHeight != map.size())
        {
            throw new InvalidAttributeValueException(String.format("Excpected map height is %d but map height is %d", this.expectedHeight, this.map.size()));
        } else if (this.expectedWidth != map.get(0).size())
        {
            throw new InvalidAttributeValueException(String.format("Excpected map width is %d but map width is %d", this.expectedWidth, this.map.get(0).size()));
        }

        return new Map(this.expectedHeight, this.expectedWidth, this.cellSize, this.map);
    }
}
