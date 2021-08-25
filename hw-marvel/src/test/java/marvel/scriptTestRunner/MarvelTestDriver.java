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


package marvel.scriptTestRunner;

import graph.Edge;
import graph.Graph;
import graph.Node;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    private final Map<String, Graph<String, String>> graphs =
            new HashMap<>();
    private final BufferedReader input;
    private final PrintWriter output;


    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            MarvelTestDriver td;

            if (args.length == 0) {
                td = new MarvelTestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
                System.out.println("Running in interactive mode.");
                System.out.println("Type a line in the script testing language to see the output.");
            } else {
                String fileName = args[0];
                File tests = new File(fileName);

                System.out.println("Reading from the provided file.");
                System.out.println("Writing the output from running those tests to standard out.");
                if (tests.exists() || tests.canRead()) {
                    td = new MarvelTestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("  Run the gradle 'build' task");
        System.err.println("  Open a terminal at hw-graph/build/classes/java/test");
        System.err.println("  To read from a file: java graph.scriptTestRunner.GraphTestDriver <name of input script>");
        System.err.println("  To read from standard in (interactive): java graph.scriptTestRunner.GraphTestDriver");
    }

    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }
    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("FindPath")) {
                findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<String, String>());
        output.println("created graph "+graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, String> g = graphs.get(graphName);
        Node<String> arg = new Node<>(nodeName);
        if(!g.containsNode(arg))
            g.addNode(arg);
        output.println("added node "+ nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        Graph<String, String> g = graphs.get(graphName);
        g.addEdge(edgeLabel, new Node<String>(parentName), new Node<String>(childName));
        output.println("added edge " + edgeLabel + " from " + parentName +
                " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, String> graph = graphs.get(graphName);
        String result = graphName + " contains:";
        Set<Node<String>> sortNodes = new TreeSet<>(new Comparator<Node<String>>() {
            public int compare(Node<String> o1, Node<String> o2) {
                if (!o1.getLabel().equals(o2.getLabel())) {
                    return o1.getLabel().compareTo(o2.getLabel());
                }
                return 0;
            }
        });
        sortNodes.addAll(graph.setOfNodes());
        for (Node n: sortNodes) {
            result += " " + n.getLabel();
        }
        output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, String> graph = graphs.get(graphName);
        String result = "the children of " + parentName + " in " + graphName + " are:";
        Set<Edge<String, String>> sortEdges = new TreeSet<>(new Comparator<Edge<String, String>>() {
            public int compare(Edge<String, String> o1, Edge<String, String> o2) {
                if(!(o1.getChild().equals(o2.getChild()))) {
                    return o1.getChild().getLabel().compareTo(o2.getChild().getLabel());
                }
                if (!(o1.getLabel().equals(o2.getLabel()))) {
                    return o1.getLabel().compareTo(o2.getLabel());
                }
                return 0;
            }
        });
        sortEdges.addAll(graph.setOfEdges(new Node<String>(parentName)));
        for (Edge<String, String> exists: sortEdges) {
            result += " " + exists.getChild().getLabel() + "(" + exists.getLabel() +")";
        }
        output.println(result);
    }

    private void loadGraph(List<String> arguments) throws CommandException {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);
        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename)  {
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String node1Name = arguments.get(1).replace('_', ' ');
        String node2Name = arguments.get(2).replace('_', ' ');
        findPath(graphName, node1Name, node2Name);
    }

    private void findPath(String graphName, String origin, String destination) {
        Graph<String, String> graph = graphs.get(graphName);
        //origin = origin.replaceAll("_", " ");
        //destination = destination.replaceAll("_", " ");
        Node<String> start = new Node<>(origin);
        Node<String> end  = new Node<>(destination);
        if (!graph.containsNode(start) && !graph.containsNode(end)) {
            output.println("unknown character " + origin);
            output.println("unknown character " + destination);
        } else if (!graph.containsNode(start)) {
            output.println("unknown character " + origin);
        } else if (!graph.containsNode(end)) {
            output.println("unknown character " + destination);
        } else {
            Node<String> tempOrigin = start;
            String result = "path from " + origin + " to " + destination + ":";
            List<Edge<String, String>> path = MarvelPaths.bfsSearch(graph, start, end);

            // check if there is a path found
            if (path == null) {
                result += "\n" + "no path found";
            } else {
                for (Edge<String, String> edge : path) {
                    result += "\n" + tempOrigin.getLabel() + " to " + edge.getChild().getLabel() + " via " + edge.getLabel();
                    //System.out.println("\n" + tempOrigin.getLabel() + " to " + edge.getChild() + " via " + edge.getLabel());
                    tempOrigin = edge.getChild();
                }
            }
            output.println(result);
        }
    }




    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
