package tests;

import graphics.Simulator;
import model.Robots.RobotCaptains.DumbRobotCaptain;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class TestRobotDumbCaptain
{
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        if (args.length != 1)
        {
            System.out.println("required a map to run on ! Read readme.md to know map serialization !");
            return;
        }
        Simulator s = new Simulator(args[0], DumbRobotCaptain.class);
    }
}
