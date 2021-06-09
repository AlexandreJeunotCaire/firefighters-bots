package model.events;

import model.Fire;
import model.Robots.FireFightersRobots.Robot;

/**
 * Represent a robot intervention
 */
public class InterventionEvent extends Event
{
    private Robot r;
    private Fire f;

    /**
     * Build an intervention for the given robot on the specified fire
     * @param date the date the vent should be executed on
     * @param r the related robot
     * @param f the related fire
     */
    public InterventionEvent(long date, Robot r, Fire f)
    {
        super(date);
        this.r = r;
        this.f = f;
    }

    /**
     * Do the intervention
     */
    @Override
    public void execute()
    {
        // only if the fire haven't been extinguished while the robot was traveling to it or before he could do his intervention, do the intervention
        if (!f.isExtinct())
        {
            this.f.extinguishBy(r.pourWater(f.getIntensity()));
        }
        //If the robot tank isn't empty he can be picked up by the robot captain for a new intervention
        if (r.getWaterVolume() > 0)
        {
            r.setInactivity(true);
        } else
        {
            r.throwNeedRefillEvent();
        }
    }
}
