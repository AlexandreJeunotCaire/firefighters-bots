package io;

import model.Fire;
import model.Map.Map;
import model.Map.MapBuilder;
import model.Map.TerrainNature;
import model.Robots.FireFightersRobots.Robot;
import model.Robots.RobotTypes;
import model.SimulationInfo;

import javax.management.InvalidAttributeValueException;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class reading data from simulation file.
 * The simulation file should have a specific serialization ! See readme.md !
 */
public class DataReader
{
    public static String filepath;

    /**
     * New DataReader that will read from given  file
     * @param f path to the file to read simulation data from
     */
    private DataReader(String f)
    {
        filepath = f;
    }

    /**
     * read data from given file
     * @param filepath file to read simulation data from
     * @return The simulation data if they where matching expected serialization
     * @throws FileNotFoundException
     * @throws InvalidAttributeValueException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static SimulationInfo readData(String filepath) throws FileNotFoundException, InvalidAttributeValueException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        DataReader dr = new DataReader(filepath);
        Map m = readMap();
        return new SimulationInfo(m, readFires(m), readRobots(m));
    }

    /**
     * Read map related data
     * @return The map if file serialization was matching expected one
     * @throws FileNotFoundException
     * @throws InvalidAttributeValueException
     */
    private static Map readMap() throws FileNotFoundException, InvalidAttributeValueException
    {
        Scanner s = new Scanner(new File(filepath));

        Pattern commentPattern = Pattern.compile("#.*");
        DataReader.skipPattern(s, commentPattern);

        int height = s.nextInt();
        int width = s.nextInt();
        int cellSize = s.nextInt();
        MapBuilder mb = new MapBuilder(height, width, cellSize);


        s.useDelimiter("\n");
        Pattern newRowPattern = Pattern.compile("# l[0-9]+");


        while (DataReader.goToNextPattern(s, newRowPattern))
        {
            DataReader.skipPattern(s, newRowPattern);
            mb.newRow();

            while (!s.hasNext(commentPattern))
            {
                mb.addCell(TerrainNature.valueOf(s.nextLine()));
            }
        }

        return mb.buildMap();
    }

    /**
     * read fires related information
     * @param m the map fires are attached to
     * @return The fires list
     * @throws FileNotFoundException
     */
    public static ArrayList<Fire> readFires(Map m) throws FileNotFoundException
    {
        Scanner s = new Scanner(new File(filepath));

        ArrayList<Fire> fires = new ArrayList<Fire>();

        s.useDelimiter("\n");
        goToNextPattern(s, Pattern.compile("# Incendies"));
        skipPattern(s, Pattern.compile("#.*"));

        Scanner subS = new Scanner(s.nextLine());
        int nbFires = subS.nextInt();
        subS.close();
        for (int i = 0; i < nbFires; i++)
        {
            subS = new Scanner(s.nextLine());
            fires.add(new Fire(m.getCell(subS.nextInt(), subS.nextInt()), subS.nextInt()));
            subS.close();
        }

        return fires;
    }

    /**
     * Read robots related information
     * @param m the map they are attached to
     * @return A robot list
     * @throws FileNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static ArrayList<Robot> readRobots(Map m) throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
    {
        Scanner s = new Scanner(new File(filepath));

        ArrayList<Robot> robots = new ArrayList<Robot>();

        s.useDelimiter("\n");
        goToNextPattern(s, Pattern.compile("# Robots"));
        skipPattern(s, Pattern.compile("#.*"));

        Scanner subS = new Scanner(s.nextLine());
        int nbRobots = subS.nextInt();
        subS.close();

        for (int i = 0; i < nbRobots; i++)
        {
            String line = s.nextLine();

            Class robotClass = RobotTypes.valueOf(line.split(" ")[2]).getC();

            Object r = robotClass.getDeclaredConstructor(String.class, Map.class).newInstance(line, m);
            robots.add((Robot) r);
        }

        return robots;
    }

    /**
     * Skip the given patterns while there are some
     * @param s The scanner to skip on
     * @param pattern the pattern to skip
     */
    private static void skipPattern(Scanner s, Pattern pattern)
    {
        while (s.hasNext(pattern))
        {
            s.nextLine();
        }
    }

    /**
     * Go to next occurrence of the given pattern
     * @param s related scanner
     * @param pattern the pattern to go on next occurrence
     * @return true if pattern found, false otherwise
     */
    private static boolean goToNextPattern(Scanner s, Pattern pattern)
    {
        while (s.hasNext() && !s.hasNext(pattern))
        {
            s.nextLine();
        }
        return s.hasNext();
    }
}
