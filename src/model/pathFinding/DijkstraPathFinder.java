package model.pathFinding;

import model.Direction;
import model.Map.Cell;
import model.Map.Map;
import model.Map.TerrainNature;
import model.Robots.FireFightersRobots.Robot;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represent a dijkstra path finder
 */
public class DijkstraPathFinder extends PathFinder
{

    private java.util.Map<Cell, Integer> distances;
    private java.util.Map<Cell, MovementInfo> ancestors;
    private ArrayList<Cell> g;
    private ArrayList<Cell> q;

    /**
     * Build a new dijkstra path finder for the given map m
     * @param m the related map
     */
    public DijkstraPathFinder(Map m)
    {
        super(m);
        this.buildGraph();
    }

    /**
     * Initiate path finding data
     * @param cStart start cell
     */
    private void initiatePathFinder(Cell cStart)
    {
        this.distances = new HashMap<Cell, Integer>();
        this.ancestors = new HashMap<Cell, MovementInfo>();
        for (Cell c : this.g)
        {
            this.distances.put(c, Integer.MAX_VALUE);
        }

        this.distances.put(cStart, 0);
        this.q = new ArrayList<Cell>();
        this.q.addAll(this.g);
    }

    /**
     * Compute the path finding
     * @param r the related robot
     */
    private void computeDijkstra(Robot r)
    {
        while (!this.q.isEmpty())
        {
            Cell cSrc = Collections.min(this.q, Comparator.comparing(c -> this.distances.get(c)));
            this.q.remove(cSrc);

            for (Direction d : Direction.values())
            {
                if (this.m.isNeighbor(cSrc, d))
                {
                    Cell cDest = this.m.getNeighbor(cSrc, d);

                    if (r.canGoOnCell(cDest))
                    {
                        int travelTimeFromSrcToDestInMs = (int) (this.distances.get(cSrc) + r.getCellTravelTime(cSrc) * 1000);
                        if (this.distances.get(cDest) > travelTimeFromSrcToDestInMs)
                        {
                            this.distances.put(cDest, travelTimeFromSrcToDestInMs);
                            this.ancestors.put(cDest, new MovementInfo(cSrc, d, travelTimeFromSrcToDestInMs));
                        }
                    }
                }
            }
        }
    }

    /**
     * Compute shortest path using ancestors information from the destination cell
     * @param cStart the start cell
     * @param cDest the destination cell
     * @return the shortest path between cStart and cDest
     */
    private ArrayList<MovementInfo> computeShortestPath(Cell cStart, Cell cDest)
    {
        ArrayList<MovementInfo> shortestPath = new ArrayList<MovementInfo>();
        Cell cCurrent = cDest;

        while (cCurrent != cStart)
        {
            shortestPath.add(this.ancestors.get(cCurrent));
            cCurrent = this.ancestors.get(cCurrent).getcSrc();
        }

        Collections.reverse(shortestPath);

        return shortestPath;
    }

    /**
     * Build the graph representation of the map
     */
    protected void buildGraph()
    {
        this.g = (ArrayList<Cell>) this.m.getCells().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Find the shortest path for the given robot to the given destination
     * @param r the related robot
     * @param cDest the destination cell
     * @return the shortest path between robot position to the destination cell
     */
    @Override
    public ArrayList<MovementInfo> findPathFor(Robot r, Cell cDest)
    {
        this.initiatePathFinder(r.getPosition());
        this.computeDijkstra(r);

        return this.computeShortestPath(r.getPosition(), cDest);
    }

    /**
     * Check if a cell has a water cell next to it
     * @param c the related cell
     * @return true if c has a adjacent water source, false otherwise
     */
    private boolean hasWaterSourceNeighbour(Cell c)
    {
        for (Direction d : Direction.values())
        {
            if (this.m.isNeighbor(c, d) && this.m.getNeighbor(c, d).getNature() == TerrainNature.EAU)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Find the shortest path to the nearest compatible refill point for the given robot
     * @param r the related robot
     * @return the shortest path to the nearest compatible refill point
     */
    @Override
    public ArrayList<MovementInfo> findShortestPathToWater(Robot r)
    {
        this.initiatePathFinder(r.getPosition());
        this.computeDijkstra(r);
        ArrayList<Cell> refillSpots = (ArrayList<Cell>) this.g.stream().filter(c -> this.hasWaterSourceNeighbour(c) && c.getNature() != TerrainNature.EAU).collect(Collectors.toList());
        ArrayList<Cell> waterSources = (ArrayList<Cell>) this.g.stream().filter(c -> c.getNature() == TerrainNature.EAU).collect(Collectors.toList());

        if (r.canGoOnCell(waterSources.get(0)))
        {
            Cell nearestWaterSpot = Collections.min(waterSources, Comparator.comparing(c -> this.distances.get(c)));
            return this.computeShortestPath(r.getPosition(), nearestWaterSpot);
        }

        Cell nearestRefillSpot = Collections.min(refillSpots, Comparator.comparing(c -> this.distances.get(c)));
        return this.computeShortestPath(r.getPosition(), nearestRefillSpot);
    }
}
