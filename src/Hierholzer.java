import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Hierholzer {

    private Stack<Integer> tour; // Corresponde à lista T
    private boolean isEulerian;  // Flag para indicar se um circuito foi encontrado

    /**
     * Construtor que executa o algoritmo de Hierholzer para encontrar um circuito euleriano.
     * @param G O grafo de entrada.
     */
    public Hierholzer(Graph G) {
        // --- Verificações de pré-condição ---
        if (!isGraphConnected(G)) {
            StdOut.println("Hierholzer: Grafo não é Euleriano -> O grafo não é conexo (ignorando vértices isolados).");
            this.isEulerian = false;
            return;
        }

        if (!areAllDegreesEven(G)) {
            StdOut.println("Hierholzer: Grafo não é Euleriano -> Pelo menos um vértice possui grau ímpar.");
            this.isEulerian = false;
            return;
        }

        // Se passou nas verificações, o grafo é euleriano.
        this.isEulerian = true;
        this.tour = new Stack<>();

        // --- Implementação do Algoritmo de Hierholzer ---
        
        // Copia das listas de adjacência para simular a marcação/remoção de arestas.
        @SuppressWarnings("unchecked")
        Queue<Integer>[] adj = (Queue<Integer>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new Queue<>();
            for (int w : G.adj(v)) {
                adj[v].enqueue(w);
            }
        }

        int startVertex = findStartVertex(G);
        if (startVertex == -1) {
            return; // Grafo sem arestas, circuito é vazio.
        }

        Stack<Integer> S = new Stack<>();
        S.push(startVertex); // Passo 1

        while (!S.isEmpty()) { // Passo 2
            int u = S.peek();

            if (!adj[u].isEmpty()) { // Passo 2.b
                int w = adj[u].dequeue();
                removeEdge(adj, w, u);
                S.push(w);
            } else { // Passo 2.c
                tour.push(S.pop());
            }
        }
    }

    /**
     * Retorna true se o grafo é euleriano e um circuito foi encontrado.
     */
    public boolean hasEulerianCircuit() {
        return this.isEulerian;
    }

    /**
     * Retorna o circuito euleriano encontrado.
     */
    public Iterable<Integer> getTour() {
        return this.tour;
    }

    // --- Métodos Privados Auxiliares ---

    private boolean areAllDegreesEven(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) return false;
        }
        return true;
    }

    private boolean isGraphConnected(Graph G) {
        int startNode = findStartVertex(G);
        if (startNode == -1) return true;

        int nonIsolatedVertices = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) nonIsolatedVertices++;
        }

        Queue<Integer> q = new Queue<>();
        boolean[] visited = new boolean[G.V()];
        q.enqueue(startNode);
        visited[startNode] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int u = q.dequeue();
            for (int v : G.adj(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.enqueue(v);
                    count++;
                }
            }
        }
        return count == nonIsolatedVertices;
    }

    private int findStartVertex(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) return v;
        }
        return -1;
    }

    private void removeEdge(Queue<Integer>[] adj, int u, int v) {
        Queue<Integer> newAdjU = new Queue<>();
        boolean removed = false;
        for (int neighbor : adj[u]) {
            if (neighbor == v && !removed) {
                removed = true;
            } else {
                newAdjU.enqueue(neighbor);
            }
        }
        adj[u] = newAdjU;
    }
}