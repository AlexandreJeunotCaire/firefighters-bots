package model.Robots.FireFightersRobots;

import graphics.CustomDrawable;
import gui.GUISimulator;
import gui.Oval;
import model.Fire;
import model.Map.Cell;
import model.Map.Map;
import model.events.RobotArrivedOnFireEventListener;
import model.events.RobotNeedRefillEventListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represent an abstract robot
 */
public abstract class Robot implements CustomDrawable
{
    protected Cell position;
    protected double speed;
    protected int waterVolume;
    protected int tankVolume;
    protected boolean inactivity = true;
    protected ArrayList<RobotNeedRefillEventListener> needRefillEventListeners;
    protected ArrayList<RobotArrivedOnFireEventListener> robotArrivedOnFireListeners;

    /**
     * Build a new robot from a descriptive string
     * @param s the robot description string
     * @param m the simulation map
     */
    public Robot(String s, Map m)
    {
        this.needRefillEventListeners = new ArrayList<RobotNeedRefillEventListener>();
        this.robotArrivedOnFireListeners = new ArrayList<RobotArrivedOnFireEventListener>();
        this.speed = -1;
        Scanner sc = new Scanner(s);
        this.position = m.getCell(sc.nextInt(), sc.nextInt());
        sc.next();
        if (sc.hasNextInt())
        {
            this.speed = sc.nextInt();
        }
        sc.close();
    }

    /**
     * Compute the travel time on a given cell for a given speed
     * @param speed the travel speed
     * @param distInM the distance in meter
     * @return the travel time in seconds
     */
    protected static double travelTimeSec(double speed, double distInM)
    {
        double distInKm = distInM / 1000;
        return (int) ((distInKm / speed) * 60 * 60);
    }

    /**
     *
     * @return true if the robot can be hired, false if it is busy
     */
    public boolean isInactive()
    {
        return inactivity;
    }

    /**
     *
     * @param free new inactivity value
     */
    public void setInactivity(boolean free)
    {
        inactivity = free;
    }

    public Cell getPosition()
    {
        return this.position;
    }

    public void setPosition(Cell position)
    {
        this.position = position;
    }

    /**
     * Pour the given volume if possible
     * @param volume the volume to be poured
     * @return the volume poured (equal to volume param if robot's remaining water volume was enough, robot's remaining water volume otherwise)
     */

    public abstract int pourWater(int volume);

    /**
     * Fill robot's tank
     */
    public abstract void fillTank();

    /**
     *
     * @return robot's string representation
     */
    public String toString()
    {
        return "{ type : " + this.getClass() + ", position : " + this.position + ", speed : " + this.speed + " }";
    }

    /**
     * draw the robot
     * @param gui gui to draw on
     */
    @Override
    public void draw(GUISimulator gui)
    {
        int printSize = this.position.getSize() / Cell.cellPrintSizeRatio;
        gui.addGraphicalElement(new Oval(this.position.getColumn() * printSize, this.position.getRow() * printSize, Color.BLACK, Color.black, printSize / 2));
    }

    public int getWaterVolume()
    {
        return waterVolume;
    }

    /**
     *
     * @param cDest destination cell
     * @return true if the robot can on on the cell, false otherwise
     */
    public abstract boolean canGoOnCell(Cell cDest);

    /**
     * Compute travel time of the robot on the given cell
     * @param cSrc the related cell
     * @return the time the robot would take to travel from this cell to one of its neighbour
     */
    public abstract double getCellTravelTime(Cell cSrc);

    /**
     * Compute the intervention time of a robot for a given volume
     * @param interventionVolume the intervention volume
     * @return the time taken by the robot to pour that volume
     */
    public abstract int interventionTimeMs(int interventionVolume);

    /**
     * Throw a need refill event to all listeners
     * @see RobotNeedRefillEventListener
     */
    public void throwNeedRefillEvent()
    {
        for (RobotNeedRefillEventListener l : this.needRefillEventListeners)
        {
            l.robotNeedRefill(this);
        }
    }

    /**
     * Add a new listener to the need refill event
     * @param l the listener to add
     */
    public void addNeedRefillEventListener(RobotNeedRefillEventListener l)
    {
        this.needRefillEventListeners.add(l);
    }

    /**
     * Throw a arrived on fire event to all listeners of that event
     * @param f the related fire
     * @see RobotArrivedOnFireEventListener
     */
    public void throwArrivedOnFireEvent(Fire f)
    {
        for (RobotArrivedOnFireEventListener l : this.robotArrivedOnFireListeners)
        {
            l.robotIsArrivedOnFire(this, f);
        }
    }

    /**
     * Add a new listener to the arrived on fire event
     * @param l the listener to add
     * @see RobotArrivedOnFireEventListener
     */
    public void addArrivedOnFireEventListener(RobotArrivedOnFireEventListener l)
    {
        this.robotArrivedOnFireListeners.add(l);
    }

    /**
     * Compute the robot refill time
     * @return the time needed by the robot to refill itself in milliseconds
     */
    public abstract int getRefillTimeMs();

    /**
     * Compute the number of unitary interventions required for the given volume
     * @param interventionVolume the volume of the intervention to perform
     * @param unitaryInterventionVolume the unitary intervention volume
     * @return The number of unitary interventions required for the given volume if the tank of the robot was full enough. The number of remaining unitary interventions otherwise.
     */
    protected int unitaryInterventionToUse(int interventionVolume, int unitaryInterventionVolume)
    {
        int unitaryInterventionsRemaining = this.waterVolume / unitaryInterventionVolume;
        int unitaryInterventionRequired = interventionVolume / unitaryInterventionVolume + (interventionVolume % unitaryInterventionVolume > 0 ? 1 : 0);

        return Math.min(unitaryInterventionRequired, unitaryInterventionsRemaining);
    }

}