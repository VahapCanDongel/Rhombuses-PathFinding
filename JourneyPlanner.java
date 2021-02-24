import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
public class JourneyPlanner {

    Rhombus[] rhombuses = new RhombusTable().getRhombuses();
    List<Vertex> validVertexes = new ArrayList<>();
    /*
        Setting up parallelograms.
     */
    public JourneyPlanner() {
        for (int x = 0; x < rhombuses.length; x++) {
            List<Vertex> obstacle = rhombuses[x].getVertexes();
            validVertexes.addAll(obstacle);
        }
    }

    //Puzzles are being implemented to find the routes.And they're being printed.
    public static void main(String[] args)
    {
        JourneyPlanner journeyPlanner = new JourneyPlanner();
        List<Vertex[]> solutions = new ArrayList<>();

        solutions.add(
                new Vertex[]{
                        new Vertex(19,10),
                        new Vertex(13,3)});
        solutions.add(
                new Vertex[]{
                        new Vertex(19,8),
                        new Vertex(13,0)});
        solutions.add(
                new Vertex[]{
                        new Vertex(19,3),
                        new Vertex(12,5)});
        solutions.add(
                new Vertex[]{
                        new Vertex(16,21),
                        new Vertex(17,0)});
        solutions.add(
                new Vertex[]{
                        new Vertex(13,3),
                        new Vertex(19,10)});
        solutions.add(
                new Vertex[]{
                        new Vertex(13,0),
                        new Vertex(16,21)});
        solutions.add(
                new Vertex[]{
                        new Vertex(12,5),
                        new Vertex(19,3)});
        solutions.add(
                new Vertex[]{
                        new Vertex(10,9),
                        new Vertex(4,9)});
        solutions.add(
                new Vertex[]{
                        new Vertex(9,21),
                        new Vertex(14,13)});
        solutions.add(
                new Vertex[]{
                        new Vertex(8,22),
                        new Vertex(9,4)});
        solutions.add(
                new Vertex[]{
                        new Vertex(18,1),
                        new Vertex(5,23)});
        solutions.add(
                new Vertex[]{
                        new Vertex(17,0),
                        new Vertex(9,21)});

        runing(solutions);

    }
    /*
        Main loop through puzzles to solve.
     */
    public static void runing(List<Vertex[]> solutions){
        JourneyPlanner journeyPlanner = new JourneyPlanner();
        try
        {
            int sol = 1;
            System.out.print("Solutions has been generated successfully\n");
            for(Vertex[] puzzle: solutions) {
                FileWriter writer = new FileWriter(  sol + ".txt");
                Vertex start = puzzle[0];
                Vertex end = puzzle[1];
                List<Vertex> route = journeyPlanner.iterativeDeepening(start, end);
                System.out.println(route);
                /*
                Writing solution to file.
                */
                for (Vertex str : route)
                    writer.write(str + " ");
                writer.write(System.lineSeparator());
                writer.close();
                sol++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
        Get list of possible paths leading from vertex.
     */
    public List<Vertex> nextConfigs(Vertex vertex)
    {
        List<Vertex> collect = validVertexes.stream()
                .filter(targetVertex -> checkPathValidation(targetVertex, vertex))
                .collect(Collectors.toList());
        return collect;
    }
    //This is where the algorithm is happening to find the solution.
    public LinkedList<Vertex> iterativeDeepening(Vertex first, Vertex last)
    {
        for (int depth = 1; true; depth++) {
            LinkedList<Vertex> route = depthFirst(first, last, depth);
            if (route != null)
                return route;

        }
    }
    public LinkedList<Vertex> depthFirst(Vertex first, Vertex last, int depth) {
        if (depth == 0) return null;
        if (first.equals(last)) {
            LinkedList<Vertex> route = new LinkedList<>();
            route.add(first);
            return route;
        } else {
            List<Vertex> nexts = nextConfigs(first);
            for (Vertex next : nexts) {
                LinkedList<Vertex> route = depthFirst(next, last, depth - 1);
                if (route != null) {
                    if (!route.isEmpty()) {
                        route.addFirst(first);
                        return route;
                    }
                }
            }
            return null;
        }
    }
    //This is a implementation for A*search algorithm
/*
    public LinkedList<Vertex> aStar(Vertex vertex1, Vertex vertex2)
    {
        LinkedList<Vertex> route = new LinkedList<Vertex>();
        route.add(vertex1);
        PriorityQueue pairs = new PriorityQueue();
        pairs.add(new Pair(estimateDistance(vertex1, vertex1), route)); // A*
        while (true)
        {
// System.out.println(pairs); // debug traces
            if (pairs.size() == 0) return null; // no solutions exist
            Pair pair = (Pair) pairs.poll(); // retrieve and remove (log)
            route = pair.getRoute();
            Vertex last = route.getLast();
            if (last.equals(vertex2)) return route; // exit loop with solution
            List<Vertex> nextTowns = nextConfigs(last);
            for (Vertex next:nextTowns)
            {
                if (!route.contains(next)) {
                    LinkedList<Vertex> nextRoute = new LinkedList<Vertex>(route);
                    nextRoute.addLast(next);
                    //double distance = actualDistance(nextRoute); // A*
                    double distance = estimateDistance(next, vertex2); // A*
                    pairs.add(new Pair(distance, nextRoute)); // log too
                }
            }
        }
    }
    private double actualDistance(LinkedList<Vertex> destination) {
        double distance  = (destination.getLast().get_x() - destination.getFirst().get_x()) + (destination.getLast().get_y() - destination.getFirst().get_y());
        //Adding up the distances of our partial journey.
        return distance;
    }
    private double estimateDistance(Vertex end, Vertex goal) {
        double dist = Math.sqrt(Math.pow(end.get_x() - goal.get_x(), 2) + Math.pow(end.get_y() - goal.get_y(), 2));
        //Straight line distance from end of our partial journey to our goal state.
        return dist;
    }
 */
    //We check if we are in a rhombus
    /*
        Checking if point is on the inwards direction of a parallelogram side.
     */
    public boolean checkRhombusSide(Vertex p1, Vertex p2, Vertex startPoint, Vertex endPoint) {
        double function1 = (startPoint.get_y()-endPoint.get_y()); double function2 = (p1.get_x()-startPoint.get_x()); double function3 = (endPoint.get_x()-startPoint.get_x()); double function4 = (p1.get_y()-startPoint.get_y());
        double function5 = ( startPoint.get_y()-endPoint.get_y());double function6 = (p2.get_x()-startPoint.get_x());double function7= (endPoint.get_x()-startPoint.get_x());double function8 = (p2.get_y()-startPoint.get_y());
        return (function1*function2+function3*function4)*(function5*function6+function7*function8) > 0;
    }
    /*
        Checking if the point is inside of a parallelogram.
     */
    public boolean checkBox(Vertex point1, Vertex point2, Vertex point3, Vertex point4, Vertex check){
        boolean checking1 = checkRhombusSide(check, point3, point1, point2);
        boolean checking2 = checkRhombusSide(check, point4, point2, point3);
        boolean checking3 =  checkRhombusSide(check, point1, point3, point4);
        boolean checking4 = checkRhombusSide(check, point2, point4, point1);
        return checking1 &&
                checking2 &&
                checking3&&
                checking4;
    }
    /*
        Checking if route is illegal.
     */
    private boolean checkPathValidation(Vertex targetVertex, Vertex startVertex) {
        for (int i = 0; i < rhombuses.length; i++) {
            List<Vertex> rhombus = rhombuses[i].getVertexes();
            if(Vertex.linesIntersect(rhombus.get(0), rhombus.get(2), targetVertex, startVertex) || Vertex.linesIntersect(rhombus.get(1), rhombus.get(3), targetVertex, startVertex)||checkBox(rhombus.get(0), rhombus.get(1), rhombus.get(2), rhombus.get(3), targetVertex)){ return false;}
        }return true;
    }
    //Vertexes are being provided to find a solution
    class RhombusTable
    {
        private Rhombus[] rhombuses = new Rhombus[16];
        /*
        Defining parallelograms corners.
        */
        RhombusTable() {
            rhombuses[0] =
                    new Rhombus
                            (getRhombusCoordination(6, 19, 13, 19, 16, 21, 9, 21));
            rhombuses[1] =
                    new Rhombus
                            (getRhombusCoordination(14, 13, 15, 13, 15, 14, 14, 14));
            rhombuses[2] =
                    new Rhombus
                            (getRhombusCoordination(3, 22, 8, 22, 5, 23, 0, 23));
            rhombuses[3] =
                    new Rhombus
                            (getRhombusCoordination(3, 11, 7, 11, 4, 12, 0, 12));
            rhombuses[4] =
                    new Rhombus
                            (getRhombusCoordination(7, 0, 11, 0, 9, 4, 5, 4));
            rhombuses[5] =
                    new Rhombus
                            (getRhombusCoordination(13, 10, 19, 10, 15, 11, 9, 11));
            rhombuses[6] =
                    new Rhombus
                            (getRhombusCoordination(7, 18, 9, 18, 7, 23, 5, 23));
            rhombuses[7] =
                    new Rhombus
                            (getRhombusCoordination(9, 9, 10, 9, 10, 11, 9, 11));
            rhombuses[8] =
                    new Rhombus
                            (getRhombusCoordination(13, 0, 17, 0, 19, 8, 15, 8));
            rhombuses[9] =
                    new Rhombus
                            (getRhombusCoordination(8, 17, 16, 17, 22, 19, 14, 19));
            rhombuses[10] =
                    new Rhombus
                            (getRhombusCoordination(4, 9, 5, 9, 5, 12, 4, 12));
            rhombuses[11] =
                    new Rhombus
                            (getRhombusCoordination(17, 1, 18, 1, 17, 8, 16, 8));
            rhombuses[12] =
                    new Rhombus
                            (getRhombusCoordination(13, 3, 19, 3, 19, 10, 13, 10));
            rhombuses[13] =
                    new Rhombus
                            (getRhombusCoordination(12, 5, 16, 5, 14, 11, 10, 11));
            rhombuses[14] =
                    new Rhombus
                            (getRhombusCoordination(11, 5, 17, 5, 15, 13, 9, 13));
            rhombuses[15] =
                    new Rhombus
                            (getRhombusCoordination(6, 2, 7, 2, 7, 11, 6, 11));
        }

        private Vertex[] getRhombusCoordination(int x1, int x2, int y1, int y2, int z1, int z2, int v1, int v2)
        {
            final Vertex[] rhombus = new Vertex[4];
            rhombus[0] = new Vertex(x1, x2);
            rhombus[1] = new Vertex(y1, y2);
            rhombus[2] = new Vertex(z1, z2);
            rhombus[3] = new Vertex(v1, v2);
            return rhombus; }
        public Rhombus[] getRhombuses() {
            return rhombuses;
        }
    }
    class Rhombus
    {
        private List<Vertex> vertexes;
        Rhombus(Vertex[] vertexes)
        {
            this.vertexes = new ArrayList<>(4);
            for (Vertex vertex: vertexes)
            {
                this.vertexes.add(vertex);
            }
        }
        public List<Vertex> getVertexes() {
            return vertexes;
        }
    }
}
