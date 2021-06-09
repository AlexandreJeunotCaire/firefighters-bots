package model;

/**
 * Represents the four possible direction of movements
 */
public enum Direction
{
    NORTH("N", -1, 0), SOUTH("S", 1, 0), EAST("E", 0, 1), WEST("W", 0, -1);
    private String c;
    private int jOffset;
    private int iOffset;

    Direction(String c, int iOffset, int jOffset)
    {
        this.c = c;
        this.iOffset = iOffset;
        this.jOffset = jOffset;
    }

    public String getCode()
    {
        return this.c;
    }

    public String toString()
    {
        return this.c;
    }

    public int getiOffset()
    {
        return iOffset;
    }

    public int getjOffset()
    {
        return jOffset;
    }
}