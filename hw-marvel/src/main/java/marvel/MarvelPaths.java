package marvel;

import java.io.InputStream;
import java.util.*;
import graph.*;


public class MarvelPaths {

    public static final boolean RUN = true;

    // this class does not represent and ADT, so
    // it does not have an abstraction function and Representation Invariant

    /**
     * creates a Graph from the given filename, which should be in the specified format
     *
     * @param filename is the name of the tsv file to build a graph from
     * @spec.requires filename is not null and can be found by this code
     * @spec.effects creates a new instance of Graph
     * @return a graph
     */
    public static Graph<String, String> buildGraph(String filename) throws IllegalArgumentException {
        if (filename == null) {
            throw new IllegalArgumentException("filename is equal to null");
        }
        Set<String> charactersOfMarvel = new HashSet<>();
        Map<String, Set<String>> booksOfMarvel = new HashMap<>();
        Iterator<MarvelParserModel> tsvUserIterator = MarvelParser.parseData(filename);
        while(tsvUserIterator.hasNext()) {
            MarvelParserModel marvelCharacterRecord = tsvUserIterator.next();
            String hero = marvelCharacterRecord.getHero();
            String book = marvelCharacterRecord.getBook();
            charactersOfMarvel.add(hero);
            if(!booksOfMarvel.containsKey(book)) {
                booksOfMarvel.put(book, new HashSet<>());
            }
            booksOfMarvel.get(book).add(hero);
        }
        /*
        for(String book: booksOfMarvel.keySet()){
            //System.out.println(book);
            for(String character: booksOfMarvel.get(book)){
                System.out.println("    " + character);
            }
        }*/

        Graph<String, String> graph = new Graph<>();

        /*
        for(String book : booksOfMarvel.keySet()){
            for(String hero: booksOfMarvel.get(book)){
                if(!graph.containsNode(new Node(hero))){
                    graph.addNode(new Node(hero));
                }
                for(String edgeHero: booksOfMarvel.get(book)){
                    if(hero.equals(edgeHero)){
                        graph.addEdge(book, new Node(hero) , new Node(edgeHero));
                    }
                }
            }
        }
        System.out.println("size: " + graph.size());
        System.out.println("Edges size: " + graph.numOfEdges());
        */
        for (String c : charactersOfMarvel) {
            graph.addNode(new Node<String>(c));
        }
        //System.out.println("Nodes size: " + graph.size());


        for(String book : booksOfMarvel.keySet()){
            for(String hero: booksOfMarvel.get(book)){
                /*if(!graph.containsNode(new Node(hero))){
                    graph.addNode(new Node(hero));
                }*/
                for(String edgeHero: booksOfMarvel.get(book)){
                    if(!hero.equals(edgeHero)){
                        graph.addEdge(book, new Node<String>(hero) , new Node<String>(edgeHero));
                    }
                }
            }
        }

        //System.out.println("Nodes size: " + graph.size());

        //System.out.println("Edges size: " + graph.numOfEdges());
        return graph;
    }

    /**
     * searches for the shortest path from the node origin to node destination (if any)from graph g
     *
     * @param graph is the collection of nodes and edges where path is being found
     * @param origin is where the path starts
     * @param destination is where the path ends
     * @spec.requires graph, origin, and destination must not be null
     * @return the shortest path, otherwise null
     */
    public static List<Edge<String, String>> bfsSearch(Graph<String, String> graph, Node<String> origin, Node<String> destination)
            throws IllegalArgumentException {
        if (graph == null || origin == null || destination == null) {
            throw new IllegalArgumentException("the origin or destination nodes cannot be null");
        }
        /*if ((!graph.containsNode(origin)) || (!graph.containsNode(destination))) {
            throw new IllegalArgumentException("the origin and destination node is not in the graph");
        }*/

        LinkedList<Node<String>> nodesToVisit = new LinkedList<>();
        Map<Node<String>, List<Edge<String, String>>> nodesToPath = new HashMap<>();

        nodesToPath.put(origin, new ArrayList<Edge<String, String>>());
        nodesToVisit.add(origin);


        while(nodesToVisit.size() != 0){
            Node<String> node = /*((LinkedList<String>)*/ nodesToVisit.removeFirst();
            if (node.equals(destination)) {
                return new ArrayList<Edge<String, String>>(nodesToPath.get(destination));
            }
            Set<Edge<String,String>> edgeSet = graph.setOfEdges(node);
            Set<Edge<String, String>> sortEdges = new TreeSet<Edge<String, String>>(new Comparator<Edge<String, String>>() {
                        public int compare(Edge<String, String> o1, Edge<String, String> o2) {
                            if(!(o1.getChild().getLabel().equals(o2.getChild().getLabel()))) {
                                return o1.getChild().getLabel().compareTo(o2.getChild().getLabel());
                            }
                            if (!(o1.getLabel().equals(o2.getLabel()))) {
                                return o1.getLabel().compareTo(o2.getLabel());
                            }
                            return 0;
                        }
                    });
            sortEdges.addAll(edgeSet);

            //List<Edge<String, String>> edgeList = new ArrayList<>();
            //edgeList.addAll(graph.setOfEdges(node));

            for (Edge<String, String> edge : sortEdges) {
                Node<String> endNode = edge.getChild();
                if (!nodesToPath.containsKey(endNode)) {
                    List<Edge<String, String>> path = new ArrayList<>(nodesToPath.get(node));
                    path.add(edge);
                    nodesToPath.put(endNode, /*(ArrayList<Edge>)*/ path);
                    nodesToVisit.add(endNode);
                }
            }

        }
        return null;
    }

    /**
     * allows client to find shortest path
     *
     * @param args are the arguments for the main method
     * @spec.requires a valid tsv file so the graph can be built
     * @spec.effects prints out shortest path to console, otherwise prints 'no path found'
     */
    public static void main(String[] args)  {
        Graph<String, String> g = buildGraph("marvel.tsv");
        System.out.println("This program is to finds the shortest path between two nodes");
        Scanner console = new Scanner(System.in);
        System.out.println("Starting node: ");
        String origin = console.nextLine();
        System.out.println("Destination node: ");
        String destination = console.nextLine();
        System.out.println();

        if ((!g.containsNode(new Node<String>(origin)) && (!g.containsNode(new Node<String>(destination))))) {
            if ((!g.containsNode(new Node<String>(origin)))) {
                System.out.println("unknown character " + origin);
            } if (!(g.containsNode(new Node<String>(destination)))) {
                System.out.println("unknown character " + destination);
            }
        } else {
            String tempOrigin = origin;
            String result = "path from " + origin + " to " + destination + ":";
            List<Edge<String, String>> path = MarvelPaths.bfsSearch(g, new Node<String> (origin),
                    new Node<String> (destination));

            if(origin.equals(destination)){
                result += "";
            }else if (path.isEmpty()) {
                result += "\n" + "no path found";
            } else {
                for (Edge<String, String> edge : path) {
                    result += "\n" + tempOrigin + " to " + edge.getChild().getLabel() + " via " + edge.getLabel();
                    tempOrigin = edge.getChild().getLabel();
                }
            }
            System.out.println(result);
        }
    }
}

