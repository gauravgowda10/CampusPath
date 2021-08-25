package pathfinder;

import graph.Edge;
import graph.Graph;
import graph.Node;
import marvel.MarvelParser;
import marvel.MarvelParserModel;
import marvel.MarvelPaths;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import java.util.*;

public class DijkstrasAlgorithm {

    // this class does not represent and ADT, so
    // it does not have an abstraction function and Representation Invariant

    /**
     * searches for the shortest path from the node origin to node destiation (if any)from graph g
     *
     * @param <T> represents the type of each node's label
     * @param graph is the collection of nodes and edges where path is being found
     * @param origin is where the path starts
     * @param destination is where the path ends
     * @spec.requires graph, origin, and destination must not be null
     * @return the shortest weighted path between origin and destination, otherwise null
     */
    public static <T> Path<T>  dijkstraSearch(Graph<T, Double> graph, T origin, T destination) throws IllegalArgumentException{

        // Construct Priority Queue that stores the paths in order of least cost path to greatest cost path
        PriorityQueue<Path<T>> activePQueue = new PriorityQueue<>(new Comparator<Path<T>>() {
            @Override
            public int compare(Path<T> o1, Path<T> o2) {
                return Double.compare(o1.getCost(), o2.getCost());
            }

        });

        // Set of we know the minimum-cost paths
        Set<T> finished = new HashSet<>();

        // Add a path from start to itself to active
        Path<T> selfPath = new Path<>(origin);
        activePQueue.add(selfPath);

        while(!activePQueue.isEmpty()){
            // get current lowest-cost path
            Path<T> minPath = activePQueue.remove();

            // sets current destination
            T minDest = minPath.getEnd();

            // case for if the current destination is equal to final destination
            if (minDest.equals(destination))
                return minPath;

            // checks to see if current Destination is in current set of minimum-cost paths
            if(finished.contains(minDest))
                continue;

            for(Edge<T, Double> edge: graph.setOfEdges(new Node<T>(minDest))){
                // If we don't know the minimum-cost path from start to child,
                // examine the path we've just found
                if (!finished.contains(edge.getChild())) {
                    // add new path
                    Path<T> newPath = minPath.extend(edge.getChild().getLabel(), edge.getLabel());
                    activePQueue.add(newPath);
                }
            }
            // update set of minimum-cost paths
            finished.add(minDest);
        }
        // if not path is found from origin to destination
        return null;
    }
}
