package graphics;

import exceptions.InvalidMovementException;
import gui.GUISimulator;
import gui.Simulable;
import io.DataReader;
import model.Fire;
import model.Map.Map;
import model.Robots.FireFightersRobots.Robot;
import model.Robots.RobotCaptains.InactiveRobotCaptain;
import model.Robots.RobotCaptains.RobotCaptain;
import model.SimulationInfo;
import model.events.EventBuilder;
import model.events.EventHandler;
import model.events.RobotArrivedOnFireEventListener;
import model.events.RobotNeedRefillEventListener;

import javax.management.InvalidAttributeValueException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represent the simulation
 */
public class Simulator implements Simulable, RobotNeedRefillEventListener, RobotArrivedOnFireEventListener
{
    /**
     * represent the duration in Ms of one increment of the simulation date
     */
    public static int stepSizeInMs = 1500;
    private GUISimulator gui;
    private int height;
    private int width;
    private Color backgroundColor = Color.BLACK;
    private EventHandler eHandler;
    private String simulationFile;
    private RobotCaptain captain;

    private SimulationInfo sInfo;

    /**
     * Build a new simulator with an inactive robot captain
     * @param simulationFile path to the simulation data
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws FileNotFoundException
     * @throws InvalidAttributeValueException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Simulator(String simulationFile) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        this.simulationFile = simulationFile;
        this.initiateSimulator();
        this.initiateSimulatorDisplay();
        this.captain = new InactiveRobotCaptain(this.sInfo, this.eHandler);
    }

    /**
     * Build a simulator with an instance of given captain robot type
     * @param simulationFile path to the simulation data
     * @param robotCaptainClass class implementing RobotCaptain to use as robot captain
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws FileNotFoundException
     * @throws InvalidAttributeValueException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public  Simulator(String simulationFile, Class robotCaptainClass) throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        this(simulationFile);
        this.captain = (RobotCaptain) robotCaptainClass.getDeclaredConstructor(SimulationInfo.class, EventHandler.class).newInstance(this.sInfo, this.eHandler);
    }

    /**
     * Initiate the simulator display environment
     */
    private void initiateSimulatorDisplay()
    {
        Map m = this.sInfo.getSimulationMap();
        this.height = m.getHeight() * CustomDrawable.printSize;
        this.width = m.getWidth() * CustomDrawable.printSize;

        this.gui = new GUISimulator(height, width, this.backgroundColor);

        this.gui.setSimulable(this);
        this.draw();
    }

    /**
     * Initiate the simulator data using given data file
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws FileNotFoundException
     * @throws InvalidAttributeValueException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void initiateSimulator() throws NoSuchMethodException, InstantiationException, FileNotFoundException, InvalidAttributeValueException, IllegalAccessException, InvocationTargetException
    {
        this.sInfo = DataReader.readData(this.simulationFile);
        this.eHandler = new EventHandler(this.sInfo.getSimulationMap());

        for (Robot r : this.sInfo.getRobots())
        {
            r.addNeedRefillEventListener(this);
            r.addArrivedOnFireEventListener(this);
        }

    }

    /**
     *
     * @return simulation information
     */
    public SimulationInfo getsInfo()
    {
        return sInfo;
    }

    /**
     * Execute next step of the simulation
     */
    @Override
    public void next()
    {
        boolean changes = false;

        this.captain.planInterventions();
        try
        {
            changes = this.eHandler.executeNext();
        } catch (InvalidMovementException e)
        {
            e.printStackTrace();
        }

        if (changes)
        {
            this.gui.reset();
            this.draw();
        }
    }

    /**
     * reset simulation
     */
    @Override
    public void restart()
    {
        try
        {
            this.initiateSimulator();
            this.captain.seteHandler(this.eHandler);
            this.captain.setsInfo(this.sInfo);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (InvalidAttributeValueException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        this.gui.reset();
        this.draw();
    }

    /**
     * Draw the map on the screen
     */
    private void drawMap()
    {
        Map m = this.sInfo.getSimulationMap();
        for (int i = 0; i < m.getHeight(); i++)
        {
            for (int j = 0; j < m.getWidth(); j++)
            {
                m.getCell(i, j).draw(this.gui);
            }
        }
    }

    /**
     * Draw the robots on the screen
     */
    private void drawRobots()
    {
        ArrayList<model.Robots.FireFightersRobots.Robot> robots = this.sInfo.getRobots();
        for (Robot r : robots)
        {
            r.draw(this.gui);
        }
    }

    /**
     * Draw the fires on the screen
     */
    private void drawFires()
    {
        ArrayList<Fire> fires = this.sInfo.getFires();

        for (Fire f : fires)
        {
            f.draw(this.gui);
        }
    }

    /**
     * Add all given elements to the event pipe line
     * @param events events to add
     */
    public void addEvents(ArrayList<model.events.Event> events)
    {
        this.eHandler.addEvents(events);
    }

    /**
     * Draw all information on the screen
     */
    public void draw()
    {
        this.drawMap();
        this.drawFires();
        this.drawRobots();
    }

    /**
     * return true when the simulation is over. Simulation is over when all fires are extinguished.
     * @return true if the simulation is over, false otherwise
     */
    public boolean isSimulationDone()
    {
        ArrayList<Fire> remainingFires = (ArrayList<Fire>) this.sInfo.getFires().stream().filter(f -> !f.isExtinct()).collect(Collectors.toList());
        return remainingFires.isEmpty();
    }

    /**
     * Handle the robot need refill event
     * @param r robot that needs refill
     */
    @Override
    public void robotNeedRefill(Robot r)
    {
        this.eHandler.addEvents(EventBuilder.buildRefillEvents(r, this.sInfo.getSimulationMap(), this.eHandler.getCurrentDate()));
    }

    /**
     * Handle the robot is arrived on fire event
     * @param r robot that raised the event
     * @param f the fire r is on
     */
    @Override
    public void robotIsArrivedOnFire(Robot r, Fire f)
    {
        this.eHandler.addEvents(EventBuilder.buildIntervention(r, f, this.eHandler.getCurrentDate()));
    }
}
