package model.Robots.RobotCaptains;

import model.SimulationInfo;
import model.events.EventHandler;

/**
 * Represent an abstract robot captain
 */
public abstract class RobotCaptain
{
    protected SimulationInfo sInfo;
    protected EventHandler eHandler;

    /**
     * Build a robot captain for given simulation information and a given event manager
     * @param sInfo the simulation information
     * @param eH the event manager of the simulation
     */
    public RobotCaptain(SimulationInfo sInfo, EventHandler eH)
    {
        this.sInfo = sInfo;
        this.eHandler = eH;
    }

    public void setsInfo(SimulationInfo sInfo)
    {
        this.sInfo = sInfo;
    }

    public void seteHandler(EventHandler eHandler)
    {
        this.eHandler = eHandler;
    }

    /**
     * Plan robots interventions
     */
    public abstract void planInterventions();
}
