import java.util.*;

public class Dijkstra {
    private static int distance(Node[] adjList, int s, int t) {
        if (s == t) {
            return -1;
        }
        Node source = Node.findNode(adjList, s);
        Node dest = Node.findNode(adjList, t);
        Queue<Node> queue = new PriorityQueue<>();
        if (source == null || dest == null) {
            return -1;
        }

        if (source != null) {
            source.distance = 0;
            queue.addAll(Arrays.asList(adjList));
            while (!queue.isEmpty()) {
                Node currentNode = queue.poll();
                for (int i = 0; i < currentNode.edges.size(); i++) {
                    Edge temp = currentNode.edges.get(i);
                    if (temp.node.distance > (currentNode.distance + temp.cost)) {
                        temp.node.distance = currentNode.distance + temp.cost;
                        temp.node.prev = currentNode;
                        queue.remove(temp.node);
                        queue.add(temp.node);
                    }
                }
            }
        }
        Node prev = dest.prev;
        while (prev != null) {
            if (prev == source) {
                return Node.findNode(adjList, t).distance;
            } else {
                prev = prev.prev;
            }
        }
        return -1;
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
        int u = scanner.nextInt();
        int v = scanner.nextInt();
        System.out.println(distance(adjList, u, v));
    }
}

class Node implements Comparable<Node> {
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

    public static Node findNode(Node[] adjList, int value) {
        for (int i = 0; i < adjList.length; i++) {
            if (adjList[i] != null) {
                if (adjList[i].vertex == value) {
                    return adjList[i];
                }
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return String.valueOf(vertex);
    }

    @Override
    public int compareTo(Node o) {
        if (this.distance < o.distance) {
            return -1;
        } else if (this.distance > o.distance) {
            return 1;
        } else {
            return 0;
        }
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

