package model.Robots.RobotCaptains;

import model.Fire;
import model.Robots.FireFightersRobots.Robot;
import model.SimulationInfo;
import model.events.EventBuilder;
import model.events.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Represents a dumb robot captain
 */
public class DumbRobotCaptain extends RobotCaptain
{
    public DumbRobotCaptain(SimulationInfo sInfo, EventHandler eH)
    {
        super(sInfo, eH);
    }

    /**
     * Assign a not extinguished fire to all available robots without thinking
     */
    @Override
    public void planInterventions()
    {
        ArrayList<Fire> remainingFires = this.sInfo.getRemainingFires();
        Collections.shuffle(remainingFires);
        if (remainingFires.isEmpty())
        {
            return;
        }
        ArrayList<Robot> freeRobots = this.sInfo.getInactiveRobots();

        Iterator<Fire> remainingFiresIterator = remainingFires.iterator();

        for (Robot r : freeRobots)
        {
            if (!remainingFiresIterator.hasNext())
            {
                remainingFiresIterator = remainingFires.iterator();
            }
            this.eHandler.addEvents(EventBuilder.goToFire(r, remainingFiresIterator.next(), this.sInfo.getSimulationMap(), this.eHandler.getCurrentDate()));
        }
    }
}
