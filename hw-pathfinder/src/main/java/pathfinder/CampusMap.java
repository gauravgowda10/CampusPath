/*
 * Copyright (C) 2020 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.Graph;
import graph.Node;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <b>CampusMap</b> represents an immutable class that reads the campus buildings
 *  and paths from data files, and provides a route-finding tool to find
 *  the shortest waling path between two buildings on the UW Campus
 *
 */
public class CampusMap implements ModelAPI {
    // Rep invariant:
    // campusBuildings != null
    // && campusPaths != null
    // && graph != null

    // Abstract function:
    //  CampusMap, p, represents the Map of the University of Washington
    //  campus that finds the shortest path from building to building
    //
    // 	this.campusBuildings = a list of all buildings on campus
    //	this.campusPaths = a list of all paths possible taking on campus
    //  this.graph = list of paths (edges) and points (nodes) on UW campus


    // a List of all the Campus Buildings
    private final List<CampusBuilding> campusBuildings;
    // a List of all the Campus Paths
    private final List<CampusPath> campusPaths;

    // Graph of all nodes and Edges on UW Campus
    private final Graph<Point, Double> graph;


    private static final boolean DEBUG = false;
    /**
     * Constructs a new CampusMap
     *
     * @spec.requires rep invariant to hold true
     * @spec.effects constructs new instance of CampusMap
     */
    public CampusMap() {
        campusBuildings = CampusPathsParser.parseCampusBuildings("campus_buildings.tsv");
        campusPaths = CampusPathsParser.parseCampusPaths("campus_paths.tsv");
        graph = constructGraph();
        checkRep();

    }

    /**
     * Checks to see if the given Short name is a building
     *
     * @spec.requires shortName != null
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     *
     */
    @Override
    public boolean shortNameExists(String shortName) {
        for(CampusBuilding building: campusBuildings){
            if (building.getShortName().equals(shortName))
                return true;
        }
        return false;
    }

    /**
     * Gets the long name of building for the given short name
     *
     * @spec.requires shortName != null
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        if(!shortNameExists(shortName))
            throw new IllegalArgumentException();
        String longName = "";
        for(CampusBuilding building: campusBuildings){
            if (building.getShortName().equals(shortName))
                longName = building.getLongName();
        }
        return longName;
    }

    /**
     * Gets a map of building's shorts name to their long names in Campus Map
     *
     * @spec.requires campusPaths != null and campusBuildings != null
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> names = new HashMap<>();
        for(CampusBuilding building: campusBuildings){
            names.put(building.getShortName(), building.getLongName());
        }
        return names;
    }

    /**
     * Gets the shortest path from one building to another.
     *
     * @spec.requires startShortName != null and endShortName != null
     * @param startShortName is the abbreviated name of the starting point
     * @param endShortName is the abbreviated name of the end point
     * @return returns a shortest path between start building and end building.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // check to see if building names exists
        if(!shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException();
        }

        // Create Origin and Destination points
        Point origin = null;
        Point dest = null;
        for(CampusBuilding building: campusBuildings){
            if(building.getShortName().equals(startShortName)){
                origin = new Point(building.getX(), building.getY());
            }
            if(building.getShortName().equals(endShortName)){
                dest = new Point(building.getX(), building.getY());
            }
        }

        // Finds Shortest Path from origin to destination using dijkstra algorithm
        Path<Point> shortestPath = DijkstrasAlgorithm.dijkstraSearch(graph, origin, dest);

        // returns shortest path between origin and destination
        return shortestPath;
    }

    /**
     * Constructs with graph with nodes and edges
     *
     * @return graph with nodes and edges
     */
    private Graph<Point, Double> constructGraph() {
        Graph<Point, Double> g = new Graph<>();
        for(CampusPath x: campusPaths ){
            // adds origin Node
            g.addNode(new Node<>(new Point(x.getX1(), x.getY1())));

            // adds destination node
            g.addNode(new Node<>(new Point(x.getX2(), x.getY2())));

            // adds edge between origin and destination
            g.addEdge(x.getDistance(), new Node<>(new Point(x.getX1(),
                    x.getY1())), new Node<>(new Point(x.getX2(), x.getY2())));
        }
        return g;
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep(){
        if(DEBUG) {
            assert campusBuildings != null: "campusBuildings cannot be null";
            assert campusPaths != null: "campusPaths cannot be null";
            assert graph != null: "graph cannot be null";
        }
    }

}
