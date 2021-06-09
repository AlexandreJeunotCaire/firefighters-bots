package model.events;

import exceptions.InvalidMovementException;

/**
 * Abstract event
 */
public abstract class Event
{
    private long date;

    /**
     *
     * @param date the date the event should be executed on
     */
    public Event(long date)
    {
        this.date = date;
    }

    public long getDate()
    {
        return date;
    }

    /**
     * Execute the event
     * @throws InvalidMovementException
     */
    public abstract void execute() throws InvalidMovementException;
}
