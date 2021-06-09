package tests.oldTests;

import graphics.Simulator;
import model.Direction;
import model.SimulationInfo;
import model.events.Event;
import model.events.MoveEvent;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestEvent0
{
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        Simulator s = new Simulator(args[0]);
        SimulationInfo sI = s.getsInfo();
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 1; i < 5; i++)
        {
            events.add(new MoveEvent(i, sI.getRobots().get(0), Direction.NORTH, sI.getSimulationMap()));
        }
        s.addEvents(events);
    }
}
