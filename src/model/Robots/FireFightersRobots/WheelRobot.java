package model.Robots.FireFightersRobots;

import graphics.ImagePanel;
import gui.GUISimulator;
import model.Map.Cell;
import model.Map.Map;
import model.Map.TerrainNature;

import java.awt.*;

/**
 * represents a wheel robot
 */
public class WheelRobot extends Robot
{
    private static double defaultSpeed = 60;
    private static Color robotColor = Color.decode("#9D00FF");
    private static int tankVolume = 5000;
    private static int refillTimeMs = 600000;
    private static int interventionTime = 5000;
    private static int unitaryInterventionVolume = 100;
    private static String texturePath = "ressources/wheelsRobot.png";

    public WheelRobot(String s, Map m)
    {
        super(s, m);
        if (this.speed == -1)
        {
            this.speed = WheelRobot.defaultSpeed;
        }

        this.fillTank();
    }

    @Override
    public boolean canGoOnCell(Cell cDest)
    {
        return (cDest.getNature() == TerrainNature.TERRAIN_LIBRE || cDest.getNature() == TerrainNature.HABITAT);
    }

    @Override
    public double getCellTravelTime(Cell cSrc)
    {
        return Robot.travelTimeSec(this.speed, cSrc.getSize());
    }

    @Override
    public int interventionTimeMs(int interventionVolume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionToUse(interventionVolume, WheelRobot.unitaryInterventionVolume);
        return unitaryInterventionToPour * WheelRobot.interventionTime;
    }

    @Override
    public int getRefillTimeMs()
    {
        return WheelRobot.refillTimeMs;
    }

    @Override
    public int pourWater(int volume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionToUse(volume, WheelRobot.unitaryInterventionVolume);
        int volumeToUse = unitaryInterventionToPour * WheelRobot.unitaryInterventionVolume;
        this.waterVolume -= volumeToUse;
        return volumeToUse;
    }

    @Override
    public void fillTank()
    {
        this.waterVolume = WheelRobot.tankVolume;
    }

    @Override
    public void draw(GUISimulator gui)
    {
        int offset = this.printSize / 2;
        int x = this.position.getColumn() * this.printSize + offset;
        int y = this.position.getRow() * this.printSize + offset;
        ImagePanel img = new ImagePanel(x, y, WheelRobot.texturePath);
        gui.addGraphicalElement(img);
    }
}
