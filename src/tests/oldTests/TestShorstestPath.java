package tests.oldTests;

import graphics.Simulator;
import model.Robots.FireFightersRobots.Robot;
import model.SimulationInfo;
import model.events.Event;
import model.events.EventBuilder;
import model.pathFinding.DijkstraPathFinder;
import model.pathFinding.MovementInfo;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestShorstestPath
{
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        Simulator s = new Simulator(args[0]);
        SimulationInfo sI = s.getsInfo();

        DijkstraPathFinder pf = new DijkstraPathFinder(sI.getSimulationMap());
        Robot r = sI.getRobots().get(0);
        Robot r2 = sI.getRobots().get(1);
        Robot r3 = sI.getRobots().get(2);

        ArrayList<MovementInfo> p = pf.findPathFor(r, sI.getSimulationMap().getCell(5, 5));
        ArrayList<MovementInfo> p2 = pf.findPathFor(r2, sI.getSimulationMap().getCell(6, 1));
        ArrayList<MovementInfo> p3 = pf.findPathFor(r3, sI.getSimulationMap().getCell(6, 0));

        ArrayList<Event> events = EventBuilder.pathToEvents(p, r, sI.getSimulationMap(), 0);
        events.addAll(EventBuilder.pathToEvents(p2, r2, sI.getSimulationMap(), 0));
        events.addAll(EventBuilder.pathToEvents(p3, r3, sI.getSimulationMap(), 0));

        s.addEvents(events);
    }
}
