package model.pathFinding;

import model.Map.Cell;
import model.Map.Map;
import model.Robots.FireFightersRobots.Robot;

import java.util.ArrayList;

/**
 * Represent an abstract path finder
 */
public abstract class PathFinder
{
    protected Map m;

    /**
     * Build a path finder for the given map
     * @param m the related map
     */
    public PathFinder(Map m)
    {
        this.m = m;
    }

    /**
     * Build the shortest path for the robot to the given cell
     * @param r the related robot
     * @param cDest the destination cell
     * @return the shortest path if it exist
     */
    public abstract ArrayList<MovementInfo> findPathFor(Robot r, Cell cDest);

    /**
     * Build a path to the nearest refill point for the given robot
     * @param r the related robot
     * @return the shortest path to the nearest refill point
     */
    public abstract ArrayList<MovementInfo> findShortestPathToWater(Robot r);
}
