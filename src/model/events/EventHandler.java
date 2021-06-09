package model.events;

import exceptions.InvalidMovementException;
import model.Map.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represent an event pipeline manager
 */
public class EventHandler
{
    private int currentDate = 0;
    private ArrayList<Event> events;
    private boolean eventsDone = false;

    private Map m;

    /**
     * Build an event pipeline manager based on the given simulation map
     * @param m a simulation map
     */
    public EventHandler(Map m)
    {
        this.m = m;
        this.events = new ArrayList<Event>();
    }

    /**
     * Add all given events to the execution pipeline
     * @param events
     */
    public void addEvents(ArrayList<Event> events)
    {
        for (Event e : events)
        {
            this.events.add(e);
        }
    }

    /**
     * increment the current date of the simulation
     */
    public void incrementDate()
    {
        this.currentDate += 1;
        if (this.events.size() == 0)
        {
            this.eventsDone = true;
            return;
        }

        Event eWithMaxDate = Collections.max(this.events, Comparator.comparing(e -> e.getDate()));
        if (eWithMaxDate.getDate() < this.currentDate)
        {
            this.eventsDone = true;
        }
    }

    /**
     * Execute the next date of the event pipeline
     * @return true if events have been executed, false otherwise
     * @throws InvalidMovementException
     */
    public boolean executeNext() throws InvalidMovementException
    {
        this.incrementDate();

        ArrayList<Event> eventsToExecute = (ArrayList<Event>) this.events.stream().filter(e -> e.getDate() == this.currentDate).collect(Collectors.toList());

        if (eventsToExecute.isEmpty())
        {
            return false;
        }
        for (Event e : eventsToExecute)
        {
            e.execute();
        }

        return true;
    }

    /**
     *
     * @return the current date of the simulation
     */
    public int getCurrentDate()
    {
        return currentDate;
    }
}
