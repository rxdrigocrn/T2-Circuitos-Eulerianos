
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class Fleury {

    private Queue<Integer> tour;
    private boolean isEulerian;

    // Lista de adjacência mutável
    private List<Integer>[] adj;

    public Fleury(Graph G) {
        if (!isGraphConnected(G)) {
            StdOut.println("Fleury: Grafo não é Euleriano -> não é conexo (ignorando vértices isolados).");
            this.isEulerian = false;
            return;
        }

        if (!areAllDegreesEven(G)) {
            StdOut.println("Fleury: Grafo não é Euleriano -> algum vértice possui grau ímpar.");
            this.isEulerian = false;
            return;
        }

        this.isEulerian = true;
        this.tour = new Queue<>();

        // Criar cópia mutável
        adj = new ArrayList[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new ArrayList<>();
            for (int w : G.adj(v)) {
                adj[v].add(w);
            }
        }

        int start = findStartVertex(G);
        dfsFleury(start);
    }

    public boolean hasEulerianCircuit() {
        return isEulerian;
    }

    public Iterable<Integer> getTour() {
        return tour;
    }

    // ---------------- Fleury ----------------
    private void dfsFleury(int u) {
        while (!adj[u].isEmpty()) {
            int v = adj[u].get(0); // pega primeira aresta
            if (adj[u].size() == 1 || !isBridge(u, v)) {
                removeEdge(u, v);
                dfsFleury(v);
            } else {
                // se for ponte, pula pra outra aresta (se houver)
                adj[u].remove(Integer.valueOf(v));
                adj[v].remove(Integer.valueOf(u));
            }
        }
        tour.enqueue(u);
    }

    // ---------------- Auxiliares ----------------
    private boolean areAllDegreesEven(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isGraphConnected(Graph G) {
        int start = findStartVertex(G);
        if (start == -1) {
            return true;
        }

        boolean[] visited = new boolean[G.V()];
        dfsVisit(G, start, visited);

        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0 && !visited[v]) {
                return false;
            }
        }
        return true;
    }

    private void dfsVisit(Graph G, int u, boolean[] visited) {
        visited[u] = true;
        for (int v : G.adj(u)) {
            if (!visited[v]) {
                dfsVisit(G, v, visited);
            }
        }
    }

    private int findStartVertex(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                return v;
            }
        }
        return -1;
    }

    private void removeEdge(int u, int v) {
        adj[u].remove(Integer.valueOf(v));
        adj[v].remove(Integer.valueOf(u));
    }

    private boolean isBridge(int u, int v) {
        int before = countReachable(u);

        removeEdge(u, v);
        int after = countReachable(u);
        addEdge(u, v);

        return after < before;
    }

    private void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    private int countReachable(int start) {
        boolean[] visited = new boolean[adj.length];
        dfsMutable(start, visited);
        int count = 0;
        for (boolean b : visited) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    private void dfsMutable(int u, boolean[] visited) {
        visited[u] = true;
        for (int v : adj[u]) {
            if (!visited[v]) {
                dfsMutable(v, visited);
            }
        }
    }
}
