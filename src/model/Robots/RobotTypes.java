package model.Robots;

import model.Robots.FireFightersRobots.CrawlerRobot;
import model.Robots.FireFightersRobots.DroneRobot;
import model.Robots.FireFightersRobots.LegRobot;
import model.Robots.FireFightersRobots.WheelRobot;

/**
 * The different types of robots
 */
public enum RobotTypes
{
    DRONE(DroneRobot.class), ROUES(WheelRobot.class), CHENILLES(CrawlerRobot.class), PATTES(LegRobot.class);

    private Class c;

    RobotTypes(Class c)
    {
        this.c = c;
    }

    public Class getC()
    {
        return c;
    }
}
