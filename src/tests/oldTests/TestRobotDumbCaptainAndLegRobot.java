package tests.oldTests;

import graphics.Simulator;
import model.Robots.RobotCaptains.DumbRobotCaptain;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class TestRobotDumbCaptainAndLegRobot
{
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        Simulator s = new Simulator(args[0], DumbRobotCaptain.class);
    }
}
