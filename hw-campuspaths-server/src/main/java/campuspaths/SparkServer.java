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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();

        // Stores the current Campus Map
        CampusMap currentMap = new CampusMap();
        Gson gson = new Gson();

        Spark.get("/shortBuildingNames", (req, res) -> {
            Map<String, String> names = currentMap.buildingNames();
            res.type("text/plain");
            return gson.toJson(names);
        });


        // Adds the given item (?name=...) if not already present.
        Spark.get("/shortestBuildingPath", (req, res) -> {
            String start = req.queryParams("start");
            String end = req.queryParams("end");
            res.type("text/plain");
            if (start == null || !currentMap.shortNameExists(start)) {
                res.status(400);
                return "start building missing";
            }
            if(end == null || !currentMap.shortNameExists(end)){
                res.status(401);
                return "end building missing";
            }
            res.type("text/plain");
            Path p = currentMap.findShortestPath(start, end);
            return gson.toJson(p);
        });

        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
    }

}
