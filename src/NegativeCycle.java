import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class NegativeCycle {
    private static int negativeCycle(Node[] adjList) {
        adjList[0].distance = 0;

        //running bellman ford for |V - 1| iteration for all edges
        for (int x = 0; x < adjList.length - 1; x++) {
            for (int i = 0; i < adjList.length; i++) {
                Node currentNode = adjList[i];
                for (int j = 0; j < currentNode.edges.size(); j++) {
                    Edge currentEdge = currentNode.edges.get(j);
                    if (currentEdge.node.distance > (currentNode.distance + currentEdge.cost)) {
                        currentEdge.node.distance = currentNode.distance + currentEdge.cost;
                        currentEdge.node.prev = currentNode;
                    }
                }
            }
        }

        //Running bellman ford for all edges one more time to check for negative cycle
        for (int i = 0; i < adjList.length; i++) {
            Node currentNode = adjList[i];
            for (int j = 0; j < currentNode.edges.size(); j++) {
                Edge currentEdge = currentNode.edges.get(j);
                if (currentEdge.node.distance > (currentNode.distance + currentEdge.cost)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int currentIndex = 0;
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Node[] adjList = new Node[n];
        int x = -1, y = -1, c;
        for (int i = 0; i < m; i++) {
            x = scanner.nextInt();
            y = scanner.nextInt();
            c = scanner.nextInt();
            if (currentIndex == 0) {
                Node xNode = new Node(x);
                Node yNode = new Node(y);
                adjList[currentIndex] = xNode;
                currentIndex++;
                adjList[currentIndex] = yNode;
                currentIndex++;
                adjList[currentIndex - 2].edges.add(new Edge(yNode, c));
            } else {
                int xIndex = -1;
                int yIndex = -1;
                for (int j = 0; j < currentIndex; j++) {
                    if (adjList[j].vertex == x) {
                        xIndex = j;
                    } else if (adjList[j].vertex == y) {
                        yIndex = j;
                    }
                }
                if (xIndex == -1 && yIndex == -1) {
                    Node xNode = new Node(x);
                    Node yNode = new Node(y);
                    adjList[currentIndex] = xNode;
                    currentIndex++;
                    adjList[currentIndex] = yNode;
                    currentIndex++;
                    adjList[currentIndex - 2].edges.add(new Edge(yNode, c));
                } else if (xIndex != -1 && yIndex == -1) {
                    Node yNode = new Node(y);
                    adjList[currentIndex] = yNode;
                    currentIndex++;
                    adjList[xIndex].edges.add(new Edge(yNode, c));
                } else if (xIndex == -1 && yIndex != -1) {
                    Node xNode = new Node(x);
                    Node yNode = adjList[yIndex];
                    adjList[currentIndex] = xNode;
                    adjList[currentIndex].edges.add(new Edge(yNode, c));
                    currentIndex++;
                } else {
                    Node yNode = adjList[yIndex];
                    adjList[xIndex].edges.add(new Edge(yNode, c));
                }
            }
        }
        System.out.println(negativeCycle(adjList));
    }
}


class Node {
    int vertex;
    LinkedList<Edge> edges;
    int distance;
    Node prev;

    public Node(int vertex) {
        this.vertex = vertex;
        edges = new LinkedList<>();
        distance = 10000;
        prev = null;
    }

    @Override
    public String toString() {
        return String.valueOf(vertex);
    }

}

class Edge {
    Node node;
    int cost;

    public Edge(Node node, int cost) {
        this.node = node;
        this.cost = cost;
    }
}

