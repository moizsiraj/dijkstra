import java.util.*;

public class Acyclicity {
    private static int acyclic(Node[] adjList) {
        Stack<Node> nodeStack = new Stack<>();
        for (int i = 0; i < adjList.length; i++) {
            Stack<Node> recStack = new Stack<>();
            if (adjList[i] != null) {
                Node currentNode = adjList[i];
                recStack.add(currentNode);
                currentNode.visited = true;
                for (int j = 0; j < currentNode.edges.size(); j++) {
                    nodeStack.add(currentNode.edges.get(j));
                }
                while (currentNode.edges.size() != 0 && !nodeStack.empty()) {
                    currentNode = nodeStack.pop();
                    if (!currentNode.visited && !recStack.contains(currentNode)) {
                        recStack.add(currentNode);
                        currentNode.visited = true;
                        for (int j = 0; j < currentNode.edges.size(); j++) {
                            nodeStack.add(currentNode.edges.get(j));
                        }
                    } else if (currentNode.visited && recStack.contains(currentNode)) {
                        return 1;
                    }
                }
            }
            recStack.empty();
        }
        return 0;
    }

    public static void main(String[] args) {
        int currentIndex = 0;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Node[] adjList = new Node[n];
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            if (currentIndex == 0) {
                Node xNode = new Node(x);
                Node yNode = new Node(y);
                adjList[currentIndex] = xNode;
                currentIndex++;
                adjList[currentIndex] = yNode;
                currentIndex++;
                adjList[currentIndex - 2].edges.add(yNode);
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
                    adjList[currentIndex - 2].edges.add(yNode);
                } else if (xIndex != -1 && yIndex == -1) {
                    Node yNode = new Node(y);
                    adjList[currentIndex] = yNode;
                    currentIndex++;
                    adjList[xIndex].edges.add(yNode);
                } else if (xIndex == -1 && yIndex != -1) {
                    Node xNode = new Node(x);
                    Node yNode = adjList[yIndex];
                    adjList[currentIndex] = xNode;
                    adjList[currentIndex].edges.add(yNode);
                    currentIndex++;
                } else {
                    Node yNode = adjList[yIndex];
                    adjList[xIndex].edges.add(yNode);
                }
            }
        }
        System.out.println(acyclic(adjList));
    }
}

class Node {
    int vertex;
    LinkedList<Node> edges;
    boolean visited;

    public Node(int vertex) {
        this.vertex = vertex;
        edges = new LinkedList<>();
    }

    @Override
    public String toString() {
        return String.valueOf(vertex);
    }
}

