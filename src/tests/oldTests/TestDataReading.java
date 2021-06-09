package tests.oldTests;

import io.DataReader;
import model.SimulationInfo;

import javax.management.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class TestDataReading
{
    public static void main(String[] args) throws FileNotFoundException, InvalidAttributeValueException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        SimulationInfo s = DataReader.readData(args[0]);
        System.out.println(s);
    }
}
