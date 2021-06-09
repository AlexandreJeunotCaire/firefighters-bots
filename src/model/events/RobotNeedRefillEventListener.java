package model.events;

import model.Robots.FireFightersRobots.Robot;

/**
 * represent a need refill event listener behaviour
 */
public interface RobotNeedRefillEventListener
{
    void robotNeedRefill(Robot r);
}
