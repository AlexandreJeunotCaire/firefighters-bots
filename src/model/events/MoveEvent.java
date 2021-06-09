package model.events;

import exceptions.InvalidMovementException;
import model.Direction;
import model.Map.Map;
import model.Robots.FireFightersRobots.Robot;

/**
 * Represent a robot movement
 */
public class MoveEvent extends Event
{
    private Robot robotToMove;
    private Direction dir;
    private Map m;

    /**
     * Build a new event for the given robot in the specified direction
     * @param date the date the event should be executed on
     * @param r the related robot
     * @param dir the direction the robot should got to
     * @param m the related map
     */
    public MoveEvent(long date, Robot r, Direction dir, Map m)
    {
        super(date);
        this.robotToMove = r;
        this.m = m;
        this.dir = dir;
    }

    /**
     *
     * @return returns the movement direction
     */
    public Direction getDir()
    {
        return dir;
    }

    /**
     *
     * @return the related robot
     */
    public Robot getRobotToMove()
    {
        return robotToMove;
    }

    /**
     * Move the robot to the given direction if possible
     * @throws InvalidMovementException
     */
    @Override
    public void execute() throws InvalidMovementException
    {
        if (this.m.isNeighbor(this.robotToMove.getPosition(), this.dir))
        {
            this.robotToMove.setPosition(this.m.getNeighbor(this.robotToMove.getPosition(), dir));
        } else
        {
            throw new InvalidMovementException("Robot out of map borders");
        }
    }
}
