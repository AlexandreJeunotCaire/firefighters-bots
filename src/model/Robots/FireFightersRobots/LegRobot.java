package model.Robots.FireFightersRobots;

import graphics.ImagePanel;
import gui.GUISimulator;
import model.Map.Cell;
import model.Map.Map;
import model.Map.TerrainNature;

/**
 * represents a leg robot
 */
public class LegRobot extends Robot
{
    private static double defaultSpeed = 30;
    private static double rockSpeed = 20;

    private static int interventionTime = 1000;
    private static int unitaryInterventionVolume = 10;
    private static String texturePath = "ressources/legRobot.png";

    public LegRobot(String s, Map m)
    {
        super(s, m);
        this.speed = 30;
        this.waterVolume = Integer.MAX_VALUE;
    }

    @Override
    public boolean canGoOnCell(Cell cDest)
    {
        return !(cDest.getNature() == TerrainNature.EAU);
    }

    @Override
    public double getCellTravelTime(Cell cSrc)
    {
        if (cSrc.getNature() == TerrainNature.ROCHE)
        {
            return Robot.travelTimeSec(LegRobot.rockSpeed, cSrc.getSize());
        } else
        {
            return Robot.travelTimeSec(this.speed, cSrc.getSize());
        }
    }

    private int unitaryInterventionsRequired(int interventionVolume)
    {
        return interventionVolume / LegRobot.unitaryInterventionVolume + (interventionVolume % LegRobot.unitaryInterventionVolume > 0 ? 1 : 0);
    }

    @Override
    public int interventionTimeMs(int interventionVolume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionsRequired(interventionVolume);
        return unitaryInterventionToPour * LegRobot.interventionTime;
    }

    @Override
    public int getRefillTimeMs()
    {
        return 0;
    }

    @Override
    public int pourWater(int volume)
    {
        int unitaryInterventionToPour = this.unitaryInterventionsRequired(volume);
        int volumeToUse = unitaryInterventionToPour * LegRobot.unitaryInterventionVolume;
        return volumeToUse;
    }

    @Override
    public void fillTank()
    {
    }

    @Override
    public void draw(GUISimulator gui)
    {
        int offset = this.printSize / 2;
        int x = this.position.getColumn() * this.printSize + offset;
        int y = this.position.getRow() * this.printSize + offset;
        ImagePanel img = new ImagePanel(x, y, LegRobot.texturePath);
        gui.addGraphicalElement(img);
    }

}
