import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Node {
    private Node left, right;
    private int index;
    private Token value;
    private int leafVal;
    private LinkedList<Node> siguientes;

    public LinkedList<Node> getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(LinkedList<Node> siguientes) {
        this.siguientes = siguientes;
    }

    public LinkedList<Node> removeDuplicates(LinkedList<Node> list){
        return (LinkedList<Node>) new LinkedList<Node>(new LinkedHashSet<Node>(list));
    }

    public boolean isAnulable() {
        switch (value.getTipo()){
            case ID:
            case CADENA:
                return false;
            case ASTERISCO:
            case INTERROGACION:
                return true;
            case MAS:
                return left.isAnulable();
            case OR:
                return left.isAnulable() || right.isAnulable();
            case PUNTO:
                return left.isAnulable() && right.isAnulable();
        }
        return false;
    }

    public LinkedList<Node> primeros(){
        LinkedList<Node> list = new LinkedList<>();
        switch (value.getTipo()){
            case ID:
            case CADENA:
                list.add(this);
                break;
            case ASTERISCO:
            case INTERROGACION:
            case MAS:
                list = left.primeros();
                break;
            case OR:
                list.addAll(left.primeros());
                list.addAll(right.primeros());
                break;
            case PUNTO:
                if(left.isAnulable()){
                    list.addAll(left.primeros());
                    list.addAll(right.primeros());
                }else{
                    list.addAll(left.primeros());
                }
                break;
        }
        return removeDuplicates(list);
    }

    public LinkedList<Node> ultimos(){
        LinkedList<Node> list = new LinkedList<>();
        switch (value.getTipo()){
            case ID:
            case CADENA:
                list.add(this);
                break;
            case ASTERISCO:
            case INTERROGACION:
            case MAS:
                list = left.ultimos();
                break;
            case OR:
                list.addAll(left.ultimos());
                list.addAll(right.ultimos());
                break;
            case PUNTO:
                if(right.isAnulable()){
                    list.addAll(left.ultimos());
                    list.addAll(right.ultimos());
                }else{
                    list.addAll(right.ultimos());
                }
                break;
        }
        return removeDuplicates(list);
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
        siguientes = new LinkedList<Node>();
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
    private LinkedList<Node> tablaSig;
    private String nombre;
    public LinkedList<Node> getTablaSig() {
        return tablaSig;
    }

    public void setTablaSig(LinkedList<Node> tablaSig) {
        this.tablaSig = tablaSig;
    }

    public Arbol(LinkedList<Token> tokens_, String nombre_) {
        tokens = tokens_;
        num=0;
        tablaSig = new LinkedList<Node>();
        nombre = nombre_;
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
        for(Node n: list){
            n.getSiguientes().sort(Comparator.comparing(Node::getLeafVal));
        }
        System.out.println(s);
    }

    public void llenarTablaSig(){
        calcSig(root);
        tablaSig.addAll(root.getLeaves(root, new LinkedList<Node>()));
        for(Node n: tablaSig){
            System.out.println(n.getIndex()+" "+n.getValue().getVal()+" -> ");
            if(n.getSiguientes()!=null) {
                for (Node n1 : n.getSiguientes()) {
                    System.out.println("      " + n1.getIndex() + " " + n1.getValue().getVal());
                }
            }
        }
    }

    public void grafTablaSig() throws IOException {
        StringBuilder g = new StringBuilder();
        g.append("digraph {\n" +
                        "splines=\"line\";\n" +
                        "rankdir = TB;\n" +
                        "node [shape=plain, height=0.5, width=1.5, fontsize=25];\n" +
                        "graph[dpi=90];\n\n"+
                "N [label=<\n" +
                "<table border=\"0\" cellborder=\"1\" cellpadding=\"12\">\n");
        g.append("  <tr><td colspan=\"2\">Hoja</td><td>Siguientes</td></tr>");
        for(Node n : tablaSig){
            n.getValue().setVal(n.getValue().getVal().replace("<","«"));
            n.getValue().setVal(n.getValue().getVal().replace(">","»"));
            g.append("  <tr><td>").append(n.getValue().getVal()).append("</td><td>").append(n.getLeafVal()).append("</td>");
            g.append("<td>");
            if(n.getSiguientes().size()>0) {
                for (Node sig : n.getSiguientes()) {
                    g.append(sig.getLeafVal()).append(" ");
                }
            }else{
                g.append("--");
            }
            g.append("</td></tr>\n");
        }
                g.append("</table>>];\n}");

        BufferedWriter writer = new BufferedWriter(new FileWriter("sig_"+nombre+".dot"));
        writer.write(String.valueOf(g));

        writer.close();

        String command = "dot -Tpng sig_"+nombre+".dot -o sig_"+nombre+".png";
        System.out.println(command);
        Process p = Runtime.getRuntime().exec(command);
    }

    public void calcSig(Node n){
        if(n==null)return;
        switch (n.getValue().getTipo()){
            case ASTERISCO:
            case MAS:
                for(Node nodo : n.getLeft().ultimos()){
                    nodo.getSiguientes().addAll(n.getLeft().primeros());
                    nodo.removeDuplicates(nodo.getSiguientes());
                }
                break;
            case PUNTO:
                for(Node nodo : n.getLeft().ultimos()){
                    System.out.println("ultimos left ");
                    for(Node x: n.getLeft().ultimos()) {
                        System.out.println("    " + x.getValue().getVal());
                    }
                    System.out.println("primeros right ");
                    for(Node x: n.getRight().primeros()) {
                        System.out.println("    " + x.getValue().getVal());
                    }
                    nodo.getSiguientes().addAll(n.getRight().primeros());
                    nodo.removeDuplicates(nodo.getSiguientes());
                }
                break;
        }
        calcSig(n.getLeft());
        calcSig(n.getRight());
    }

    public void graph() throws IOException {
        String graph = "strict digraph {\n" +
                "splines=\"line\";\n" +
                "rankdir = TB;\n" +
                "node [shape=circle, height=0.5, width=1.5, fontsize=25];\n" +
                "graph[dpi=90];\n\n";

        graph += root.getGraph();

        graph += "}";

        BufferedWriter writer = new BufferedWriter(new FileWriter("tree_"+nombre+".dot"));
        writer.write(graph);

        writer.close();

        String command = "dot -Tpng tree_"+nombre+".dot -o tree_"+nombre+".png";
        System.out.println(command);
        Process p = Runtime.getRuntime().exec(command);
        numerar();
        grafTablaSig();
        cont++;
//        BufferedImage img = ImageIO.read(new File("avl.png"));
//        Thread.sleep(700);
//        thumb.setIcon(new ImageIcon(Arreglo.scaleimage(1000, 400, img)));
//        thumb.repaint();
//        thumb.revalidate();
//        Thread.sleep(300);
    }

}



