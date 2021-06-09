package tests.oldTests;

import graphics.Simulator;
import model.Map.Map;
import model.SimulationInfo;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class TestSimulator
{
    private Map fireWarMap;

    public static void main(String[] args) throws FileNotFoundException, InvalidAttributeValueException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Simulator s = new Simulator(args[0]);
        SimulationInfo sI = s.getsInfo();
    }
}
