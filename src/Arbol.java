import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

class Node {
    private Node left, right;
    private int index;
    private Token value;
    private int leafVal;
    private boolean anulable;

    public boolean isAnulable() {
        switch (value.getTipo()){
            case 
        }
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    public int getLeafVal() {
        return leafVal;
    }

    public void setLeafVal(int leafVal) {
        this.leafVal = leafVal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Token getValue() {
        return value;
    }

    public void setValue(Token value) {
        this.value = value;
    }

    public Node(Token val) {
        this.value = val;
        leafVal = 0;
    }

    public String getGraph() {
        String g;
        g = "\"" + index + "\" [label=\"" + value.getVal() + "\"];\n";

        if (left != null) {
            g += left.getGraph() + "\"" + index + "\" -> \"" + left.index + "\";\n";
        }
        if (right != null) {
            g += right.getGraph() + "\"" + index + "\" -> \"" + right.index + "\";\n";
        }
        return g;
    }

    public LinkedList<Node> getLeaves(Node n, LinkedList<Node> toks) {
        if (n == null) return null;
        if (n.isLeaf()) {
            toks.add(n);
        } else {
            getLeaves(n.left, toks);
            getLeaves(n.right, toks);
        }
        return toks;
    }

}

public class Arbol {
    Node root;
    LinkedList<Token> tokens;
    public static int cont=0;
    int num;
    public Arbol(LinkedList<Token> tokens_) {
        tokens = tokens_;
        num=0;
    }

    public Node add() {
        Node n = new Node(tokens.get(0));
        n.setIndex(num);
        num++;
        if (root == null) {
            root = n;
        }
        tokens.remove(0);
        switch (n.getValue().getTipo()) {
            case PUNTO:
            case OR:
                n.setLeft(add());
                n.setRight(add());
                break;
            case INTERROGACION:
            case ASTERISCO:
            case MAS:
                n.setLeft(add());
                break;
        }
        return n;
    }

    public void numerar(){
        LinkedList<Node> list = root.getLeaves(root, new LinkedList<Node>());
        StringBuilder s = new StringBuilder();
        int i=1;
        for(Node t: list){
            s.append(t.getValue().getVal()).append(" ");
            t.setLeafVal(i);
            i++;
        }
        System.out.println(s);
    }

    public void graph() throws IOException {
        String graph = "strict digraph {\n" +
                "splines=\"line\";\n" +
                "rankdir = TB;\n" +
                "node [shape=circle, height=0.5, width=1.5, fontsize=25];\n" +
                "graph[dpi=110];\n\n";

        graph += root.getGraph();

        graph += "}";

        BufferedWriter writer = new BufferedWriter(new FileWriter("tree"+cont+".dot"));
        writer.write(graph);

        writer.close();

        String command = "dot -Tpng tree"+cont+".dot -o tree"+cont+".png";
        System.out.println(command);
        Process p = Runtime.getRuntime().exec(command);
        cont++;
        numerar();
//        BufferedImage img = ImageIO.read(new File("avl.png"));
//        Thread.sleep(700);
//        thumb.setIcon(new ImageIcon(Arreglo.scaleimage(1000, 400, img)));
//        thumb.repaint();
//        thumb.revalidate();
//        Thread.sleep(300);
    }

}



