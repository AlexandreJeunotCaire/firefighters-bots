package model;

import model.Map.Map;
import model.Robots.FireFightersRobots.Robot;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents all the information of a simulation
 */
public class SimulationInfo
{
    private Map simulationMap;
    private ArrayList<Fire> fires;
    private ArrayList<Robot> robots;


    /**
     * Build a new batch of simulation information from simulation data
     * @param simulationMap the simulation map
     * @param fires the simulation fires
     * @param robots the simulation robots
     */
    public SimulationInfo(Map simulationMap, ArrayList<Fire> fires, ArrayList<Robot> robots)
    {
        this.simulationMap = simulationMap;
        this.fires = fires;
        this.robots = robots;
    }

    public Map getSimulationMap()
    {
        return simulationMap;
    }

    public ArrayList<Robot> getRobots()
    {
        return robots;
    }

    public ArrayList<Fire> getFires()
    {
        return fires;
    }

    /**
     *
     * @return the simulation information string representation
     */
    @Override
    public String toString()
    {
        String str = "{\n";
        str += "map : " + this.simulationMap + ",\n";
        str += "fires : " + this.fires + ",\n";
        str += "robots : " + this.robots;
        return str + "\n}";
    }

    /**
     *
     * @return not extinguished fires
     */
    public ArrayList<Fire> getRemainingFires()
    {
        return (ArrayList<Fire>) this.fires.stream().filter(f -> !f.isExtinct()).collect(Collectors.toList());
    }

    /**
     *
     * @return all inactive robots
     */
    public ArrayList<Robot> getInactiveRobots()
    {
        return (ArrayList<Robot>) this.robots.stream().filter(r -> r.isInactive()).collect(Collectors.toList());
    }
}
