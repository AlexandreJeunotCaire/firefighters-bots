package model.events;

import exceptions.InvalidMovementException;
import model.Fire;
import model.Robots.FireFightersRobots.Robot;

/**
 * Represent a robot is arrived on fire event
 */
public class ArrivedOnFireEvent extends Event
{

    private Fire f;
    private Robot r;

    /**
     *
     * @param date The date the event should be executed on
     * @param f The related fire
     * @param r The related robot
     */
    public ArrivedOnFireEvent(long date, Fire f, Robot r)
    {
        super(date);
        this.f = f;
        this.r = r;
    }

    /**
     * Run the event
     */
    @Override
    public void execute()
    {
        //Just raised the robot arrived on fire event that would be catch by every listeners of it
        this.r.throwArrivedOnFireEvent(this.f);
    }
}
