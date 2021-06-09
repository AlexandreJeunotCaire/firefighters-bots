package model.pathFinding;

import model.Direction;
import model.Map.Cell;

/**
 * Represent a movement information
 */
public class MovementInfo
{
    private Cell cSrc;
    private Direction d;
    private int date;

    /**
     * Build a new movement information according parameters
     * @param cSrc the source cell
     * @param d the related direction
     * @param date the time taken to go to that cell
     */
    public MovementInfo(Cell cSrc, Direction d, int date)
    {
        this.cSrc = cSrc;
        this.d = d;
        this.date = date;
    }

    public Cell getcSrc()
    {
        return cSrc;
    }

    public Direction getD()
    {
        return d;
    }

    public int getDate()
    {
        return date;
    }
}
