package model.events;

import model.Fire;
import model.Robots.FireFightersRobots.Robot;

/**
 * Represent a robot pouring water event. This class is a dumb version of InterventionEvent
 * @see InterventionEvent
 */
public class PourEvent extends Event
{
    private int volume;
    private Robot r;
    private Fire f;

    /**
     * Build a new pour event for the given robot using the specified pouring volume
     * @param date the date the event should be executed on
     * @param r the related robot
     * @param volume the volume of pouring to be done
     * @param f the related fire
     */
    public PourEvent(long date, Robot r, int volume, Fire f)
    {
        super(date);
        this.r = r;
        this.volume = volume;
        this.f = f;
    }

    /**
     * Pour execution
     */
    @Override
    public void execute()
    {
        this.f.extinguishBy(r.pourWater(volume));
    }
}
