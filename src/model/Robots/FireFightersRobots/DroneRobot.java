package model.Robots.FireFightersRobots;

import exceptions.InvalidMovementException;
import graphics.ImagePanel;
import gui.GUISimulator;
import model.Map.Cell;
import model.Map.Map;

import java.awt.*;

/**
 * represents a drone robot
 */
public class DroneRobot extends Robot
{
    static Color robotColor = Color.decode("#4A8C8C");
    private static double maxSpeed = 150;
    private static double defaultSpeed = 100;
    private static int tankVolume = 10000;
    private static int refillTimeMs = 1800000;
    private static int interventionTime = 30000;
    private static int unitaryInterventionVolume = 10000;
    private static String texturePath = "ressources/droneRobot.png";

    public DroneRobot(String s, Map m) throws InvalidMovementException
    {
        super(s, m);

        if (this.speed == -1)
        {
            this.speed = DroneRobot.defaultSpeed;
        } else if (speed > DroneRobot.maxSpeed)
        {
            throw new InvalidMovementException("Crawlers robot max speed is " + DroneRobot.maxSpeed + " km/h !");
        }

        this.fillTank();
    }

    @Override
    public int pourWater(int volume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionToUse(volume, DroneRobot.unitaryInterventionVolume);
        int volumeToUse = unitaryInterventionToPour * DroneRobot.unitaryInterventionVolume;
        this.waterVolume -= volumeToUse;
        return volumeToUse;
    }

    @Override
    public void fillTank()
    {
        this.waterVolume = tankVolume;
    }

    @Override
    public boolean canGoOnCell(Cell cDest)
    {
        return true;
    }

    @Override
    public double getCellTravelTime(Cell cSrc)
    {
        return Robot.travelTimeSec(this.speed, cSrc.getSize());
    }

    @Override
    public int interventionTimeMs(int interventionVolume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionToUse(interventionVolume, DroneRobot.unitaryInterventionVolume);
        return unitaryInterventionToPour * DroneRobot.interventionTime;
    }

    @Override
    public int getRefillTimeMs()
    {
        return DroneRobot.refillTimeMs;
    }

    @Override
    public void draw(GUISimulator gui)
    {
        int offset = this.printSize / 2;
        int x = this.position.getColumn() * this.printSize + offset;
        int y = this.position.getRow() * this.printSize + offset;
        ImagePanel img = new ImagePanel(x, y, DroneRobot.texturePath);
        gui.addGraphicalElement(img);
    }

}
