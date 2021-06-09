package model.Robots.FireFightersRobots;

import exceptions.InvalidMovementException;
import graphics.ImagePanel;
import gui.GUISimulator;
import model.Map.Cell;
import model.Map.Map;
import model.Map.TerrainNature;

import java.awt.*;

/**
 * Represents a crawler robot
 */
public class CrawlerRobot extends Robot
{
    private static double maxSpeed = 80;
    private static double defaultSpeed = 60;
    private static int tankVolume = 2000;
    private static int refillTimeMs = 300000;
    private static int interventionTime = 8000;
    private static int unitaryInterventionVolume = 100;
    private static String texturePath = "ressources/crawlerRobot.png";

    private static Color robotColor = Color.decode("#654218");

    public CrawlerRobot(String s, Map m) throws InvalidMovementException
    {
        super(s, m);
        if (this.speed == -1)
        {
            this.speed = CrawlerRobot.defaultSpeed;
        } else if (speed > CrawlerRobot.maxSpeed)
        {
            throw new InvalidMovementException("Crawlers robot max speed is " + CrawlerRobot.maxSpeed + " km/h !");
        }

        this.fillTank();
    }


    @Override
    public boolean canGoOnCell(Cell cDest)
    {
        return !(cDest.getNature() == TerrainNature.EAU || cDest.getNature() == TerrainNature.ROCHE);
    }

    @Override
    public double getCellTravelTime(Cell cSrc)
    {
        if (cSrc.getNature() == TerrainNature.FORET)
        {
            return Robot.travelTimeSec(this.speed / 2, cSrc.getSize());
        } else
        {
            return Robot.travelTimeSec(this.speed, cSrc.getSize());
        }
    }

    @Override
    public int interventionTimeMs(int interventionVolume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionToUse(interventionVolume, CrawlerRobot.unitaryInterventionVolume);
        return unitaryInterventionToPour * CrawlerRobot.interventionTime;
    }

    @Override
    public int getRefillTimeMs()
    {
        return CrawlerRobot.refillTimeMs;
    }

    @Override
    public int pourWater(int volume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionToUse(volume, CrawlerRobot.unitaryInterventionVolume);
        int volumeToUse = unitaryInterventionToPour * CrawlerRobot.unitaryInterventionVolume;
        this.waterVolume -= volumeToUse;
        return volumeToUse;
    }

    @Override
    public void fillTank()
    {
        this.waterVolume = CrawlerRobot.tankVolume;
    }

    @Override
    public void draw(GUISimulator gui)
    {
        int offset = this.printSize / 2;
        int x = this.position.getColumn() * this.printSize + offset;
        int y = this.position.getRow() * this.printSize + offset;

        ImagePanel img = new ImagePanel(x, y, CrawlerRobot.texturePath);
        gui.addGraphicalElement(img);
    }
}
