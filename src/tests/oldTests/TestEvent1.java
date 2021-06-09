package tests.oldTests;

import graphics.Simulator;
import model.Direction;
import model.Fire;
import model.Robots.FireFightersRobots.Robot;
import model.SimulationInfo;
import model.events.Event;
import model.events.FillEvent;
import model.events.MoveEvent;
import model.events.PourEvent;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestEvent1
{
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        Simulator s = new Simulator(args[0]);
        SimulationInfo sI = s.getsInfo();

        Robot r = sI.getRobots().get(1);
        Fire f = sI.getFires().get(4);

        ArrayList<Event> events = new ArrayList<Event>();

        events.add(new MoveEvent(1, r, Direction.NORTH, sI.getSimulationMap()));

        events.add(new PourEvent(2, r, Math.min(r.getWaterVolume(), f.getIntensity()), f));

        events.add(new MoveEvent(3, r, Direction.WEST, sI.getSimulationMap()));
        events.add(new MoveEvent(4, r, Direction.WEST, sI.getSimulationMap()));

        events.add(new FillEvent(5, r, sI.getSimulationMap()));

        events.add(new MoveEvent(6, r, Direction.EAST, sI.getSimulationMap()));
        events.add(new MoveEvent(7, r, Direction.EAST, sI.getSimulationMap()));

        events.add(new PourEvent(8, r, Math.min(r.getWaterVolume(), f.getIntensity()), f));

        s.addEvents(events);
    }
}
