package model.events;

import model.Fire;
import model.Robots.FireFightersRobots.Robot;

/**
 * represent a arrived on fire event listener behaviour
 */
public interface RobotArrivedOnFireEventListener
{
    void robotIsArrivedOnFire(Robot r, Fire f);
}
