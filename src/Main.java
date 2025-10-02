
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {

        // Para testar outros arquivos, basta trocar o nome no final da linha acima, ou
        // comente a linha e descomente uma das que estão abaixo:
        String caminhoDoArquivo = "C:\\Users\\rodri\\OneDrive\\Área de Trabalho\\Projetos\\Unifor\\T2-Circuitos-Eulerianos\\data\\c4.txt";
        // String caminhoDoArquivo = "C:\\Users\\erfon\\OneDrive\\Desktop\\T2-Circuitos-Eulerianos\\data\\caminho_triangulo.txt";
        // String caminhoDoArquivo = "C:\\Users\\erfon\\OneDrive\\Desktop\\T2-Circuitos-Eulerianos\\data\\desconexo.txt";
        // String caminhoDoArquivo = "C:\\Users\\erfon\\OneDrive\\Desktop\\T2-Circuitos-Eulerianos\\data\\konigsberg.txt";
        // String caminhoDoArquivo = "C:\\Users\\erfon\\OneDrive\\Desktop\\T2-Circuitos-Eulerianos\\data\\c4_isolado9.txt";

        StdOut.println("Lendo o grafo diretamente do arquivo: " + caminhoDoArquivo);

        In in = new In(caminhoDoArquivo);
        Graph G = new Graph(in);

        StdOut.println("Grafo carregado (" + G.V() + " vértices, " + G.E() + " arestas).");
        StdOut.println("----------------------------------------");

        // Teste do Algoritmo de Hierholzer
        // StdOut.println("Executando o algoritmo de Hierholzer...");
        // Hierholzer hierholzer = new Hierholzer(G);
        // if (hierholzer.hasEulerianCircuit()) {
        //     StdOut.println("Circuito Euleriano encontrado:");
        //     StringBuilder path = new StringBuilder();
        //     for (int v : hierholzer.getTour()) {
        //         path.append(v).append(" ");
        //     }
        //     if (path.length() > 0) {
        //         path.setLength(path.length() - 1);
        //     }
        //     StdOut.println(path.toString());
        // }
        // Teste do Algoritmo de Fleury
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
        }

        StdOut.println("----------------------------------------");
    }
}
