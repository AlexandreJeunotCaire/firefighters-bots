package model.events;

import exceptions.InvalidMovementException;
import model.Map.Map;
import model.Robots.FireFightersRobots.Robot;

/**
 * Represent a refill action
 */
public class FillEvent extends Event
{
    private Robot r;
    private Map m;

    /**
     * Build a new fill event based on a robot
     * @param date the date the event should be executed on
     * @param r the related robot
     * @param m the related map
     */
    public FillEvent(long date, Robot r, Map m)
    {
        super(date);
        this.r = r;
        this.m = m;
    }

    /**
     * Execute the event
     */
    @Override
    public void execute()
    {
        this.r.fillTank();
        //once robot tank full he can get picked up for a new intervention by the robot captain
        r.setInactivity(true);
    }
}
