package model.events;

import graphics.Simulator;
import model.Fire;
import model.Map.Map;
import model.Robots.FireFightersRobots.Robot;
import model.pathFinding.DijkstraPathFinder;
import model.pathFinding.MovementInfo;

import java.util.ArrayList;

/**
 * Represent a builder of event sequences
 */
public class EventBuilder
{
    /**
     * Translate a path into an event sequence
     * @param path path to be translated
     * @param r related robot
     * @param m related simulation map
     * @param currentDate current date of the simulation
     * @return the path translated into an event sequence
     */
    public static ArrayList<Event> pathToEvents(ArrayList<MovementInfo> path, Robot r, Map m, int currentDate)
    {
        ArrayList<Event> events = new ArrayList<Event>();
        for (MovementInfo mI : path)
        {
            events.add(new MoveEvent(mI.getDate() / Simulator.stepSizeInMs + currentDate, r, mI.getD(), m));
        }

        return events;
    }

    /**
     * Translate a path leading to a fire into an appropriate event sequence
     * @param r related robot
     * @param f related fire
     * @param m related simulation map
     * @param currentDate current date of the simulation
     * @return The path to the fire translated to event sequence followed by a arrived on fire event
     * @see ArrivedOnFireEvent
     */
    public static ArrayList<Event> goToFire(Robot r, Fire f, Map m, int currentDate)
    {
        r.setInactivity(false);
        ArrayList<Event> pathEvents = new ArrayList<Event>();
        DijkstraPathFinder pf = new DijkstraPathFinder(m);
        ArrayList<MovementInfo> path = pf.findPathFor(r, f.getPosition());

        pathEvents.addAll(EventBuilder.pathToEvents(path, r, m, currentDate));

        long date = currentDate;
        if (!path.isEmpty())
        {
            date = pathEvents.get(pathEvents.size() - 1).getDate();
        }

        pathEvents.add(new ArrivedOnFireEvent(date, f, r));

        return pathEvents;
    }

    /**
     * Build a refill sequence of event
     * @param r related robot
     * @param m related map
     * @param currentDate the current date of the simulation
     * @return The path to nearest water translated to event sequence followed by a refill event
     * @see FillEvent
     */
    public static ArrayList<Event> buildRefillEvents(Robot r, Map m, int currentDate)
    {
        DijkstraPathFinder pf = new DijkstraPathFinder(m);
        ArrayList<MovementInfo> pathToWater = pf.findShortestPathToWater(r);
        ArrayList<Event> events = EventBuilder.pathToEvents(pathToWater, r, m, currentDate);

        int refillTimeMs = r.getRefillTimeMs() / Simulator.stepSizeInMs;
        long date = currentDate;
        if (!pathToWater.isEmpty())
        {
            date = events.get(events.size() - 1).getDate();
        }

        events.add(new FillEvent(date + refillTimeMs, r, m));

        return events;
    }

    /**
     * Build an intervention event sequence
     * @param r related robot
     * @param f related fire
     * @param currentDate the current date of the simulation
     * @return The intervention event sequence
     */
    public static ArrayList<Event> buildIntervention(Robot r, Fire f, int currentDate)
    {
        ArrayList<Event> intervention = new ArrayList<Event>();
        int interventionTime = r.interventionTimeMs(f.getIntensity()) / Simulator.stepSizeInMs + 1;

        intervention.add(new InterventionEvent(currentDate + interventionTime, r, f));

        return intervention;
    }
}
