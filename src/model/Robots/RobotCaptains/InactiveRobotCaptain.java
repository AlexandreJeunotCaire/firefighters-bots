package model.Robots.RobotCaptains;

import model.SimulationInfo;
import model.events.EventHandler;

/**
 * Represents a lazy robot captain doing absolutely nothing
 */
public class InactiveRobotCaptain extends RobotCaptain
{
    public InactiveRobotCaptain(SimulationInfo sInfo, EventHandler eH)
    {
        super(sInfo, eH);
    }

    /**
     * Does nothing
     */
    @Override
    public void planInterventions()
    {

    }
}
