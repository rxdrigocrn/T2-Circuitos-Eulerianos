
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
        String[] arquivos = {
            "C:\\Users\\rodri\\OneDrive\\Área de Trabalho\\Projetos\\Unifor\\T2-Circuitos-Eulerianos\\data\\c4.txt",
            "C:\\Users\\rodri\\OneDrive\\Área de Trabalho\\Projetos\\Unifor\\T2-Circuitos-Eulerianos\\data\\caminho_triangulo.txt",
            "C:\\Users\\rodri\\OneDrive\\Área de Trabalho\\Projetos\\Unifor\\T2-Circuitos-Eulerianos\\data\\desconexo.txt",
            "C:\\Users\\rodri\\OneDrive\\Área de Trabalho\\Projetos\\Unifor\\T2-Circuitos-Eulerianos\\data\\konigsberg.txt",
            "C:\\Users\\rodri\\OneDrive\\Área de Trabalho\\Projetos\\Unifor\\T2-Circuitos-Eulerianos\\data\\c4_isolado9.txt"
        };

        for (String caminhoDoArquivo : arquivos) {
            StdOut.println("========================================");
            StdOut.println("Lendo o grafo do arquivo: " + caminhoDoArquivo);

            In in = null;
            try {
                in = new In(caminhoDoArquivo);
            } catch (Exception e) {
                StdOut.println("Erro ao abrir arquivo: " + e.getMessage());
                continue;
            }

            Graph G = new Graph(in);
            StdOut.println("Grafo carregado (" + G.V() + " vértices, " + G.E() + " arestas).");
            StdOut.println("----------------------------------------");

            // --- Hierholzer ---
            StdOut.println("Executando o algoritmo de Hierholzer...");
            Hierholzer hierholzer = new Hierholzer(G);
            if (hierholzer.hasEulerianCircuit()) {
                StdOut.println("Circuito Euleriano encontrado:");
                StringBuilder path = new StringBuilder();
                for (int v : hierholzer.getTour()) {
                    path.append(v).append(" ");
                }
                if (path.length() > 0) {
                    path.setLength(path.length() - 1);
                }
                StdOut.println(path.toString());
            } else {
                StdOut.println("Grafo não possui circuito Euleriano.");
            }

            StdOut.println("----------------------------------------");

            // --- Fleury ---
            StdOut.println("Executando o algoritmo de Fleury...");
            Fleury fleury = new Fleury(G);
            if (fleury.hasEulerianCircuit()) {
                StdOut.println("Circuito Euleriano encontrado:");
                StringBuilder path = new StringBuilder();
                for (int v : fleury.getTour()) {
                    path.append(v).append(" ");
                }
                if (path.length() > 0) {
                    path.setLength(path.length() - 1);
                }
                StdOut.println(path.toString());
            } else {
                StdOut.println("Grafo não possui circuito Euleriano.");
            }

            StdOut.println("========================================\n\n");
        }
    }
}
