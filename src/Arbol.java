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

    public LinkedList<Transicion> removeDuplicatesTrans(LinkedList<Transicion> list){
        return (LinkedList<Transicion>) new LinkedList<Transicion>(new LinkedHashSet<Transicion>(list));
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
        String A="N";
        String P="";
        String U="";
        if(isAnulable()){
            A="A";
        }
        for(Node p: primeros()){
            P += p.leafVal+" ";
        }
        for(Node p: ultimos()){
            U += p.leafVal+" ";
        }
        g = "\"" + index + "\" [label=\"" + value.getVal() + " \nA: "+A+"\nP: "+P+"\nU: "+U;
        if(isLeaf()){
            g+= "\ni: "+leafVal;
        }
        g+=" \"];\n";

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
    private LinkedList<Estado> tablaTrans;
    private LinkedList<Node> terminales;
    private String nombre;
    private int nEstado;
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
        tablaTrans = new LinkedList<Estado>();
        terminales = new LinkedList<Node>();
        nEstado=0;
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
        ponerTerminales();
    }

    private void ponerTerminales(){
        LinkedList<Node> auxs = root.getLeaves(root, new LinkedList<Node>());
        System.out.println("terminales");
        for(Node n: auxs){
            boolean existe = false;
            for(Node a : terminales){
                if(n.getValue().getVal().equals(a.getValue().getVal())){
                    existe = true;
                }
            }
            if(!existe){
                System.out.println(n.getValue().getVal());
                terminales.add(n);
            }
        }
        System.out.println("----------------");
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

    public void calcTransiciones(Estado est) throws IOException {
        if(tablaTrans.size()==0){
            tablaTrans.add(est);
            nEstado++;
        }
        for(Node n: terminales){
            LinkedList<Transicion> transicions = new LinkedList<Transicion>();
            LinkedList<Node> nodos = new LinkedList<Node>();
            System.out.println("Primeros ");
            for(Node x: est.getListaNodos()){
                System.out.println(x.getLeafVal());
                if(x.getValue().getVal().equals(n.getValue().getVal())){
                    nodos.addAll(x.getSiguientes());
                    nodos = root.removeDuplicates(nodos);
                }
            }
                Estado ex = existeEstado(nodos);
                if(ex==null){
                    Estado estado = new Estado("S"+nEstado);
                    nEstado++;
                    estado.setListaNodos(nodos);
                    for(Node nodo: nodos){
                        if(nodo.getValue().getVal().equals("¢")){
                            System.out.println("ACEPTACION");
                            estado.setAceptacion(true);
                            break;
                        }
                    }
//                    transicions.add(new Transicion(n, estado));
                    est.getTransiciones().add(new Transicion(n, estado));
                    est.setTransiciones(root.removeDuplicatesTrans(est.getTransiciones()));
                    tablaTrans.add(estado);
                    calcTransiciones(estado);
                }
                else{
                    //transicions.add(new Transicion(n, ex));
                    if(nodos.size()>0){
                        est.getTransiciones().add(new Transicion(n, ex));
                        est.setTransiciones(root.removeDuplicatesTrans(est.getTransiciones()));
                    }
                }

            System.out.println("------------------");
        }
        graphTrans();
        graphAfd();
    }

    public void graphTrans() throws IOException {
        StringBuilder g = new StringBuilder();
        g.append("digraph {\n" +
                "splines=\"line\";\n" +
                "rankdir = TB;\n" +
                "node [shape=plain, height=0.5, width=1.5, fontsize=25];\n" +
                "graph[dpi=90];\n\n"+
                "N [label=<\n" +
                "<table border=\"0\" cellborder=\"1\" cellpadding=\"12\">\n");
        g.append("  <tr><td>Estado</td><td colspan=\"").append(terminales.size()).append("\">Transiciones</td></tr>");

        for(Estado es : tablaTrans){
            g.append("  <tr><td>").append(es.getNombre());
            g.append(" {");
            for(Node x: es.getListaNodos()){
                g.append(x.getLeafVal());
                g.append(" ");
            }
            g.append("}");
            g.append("</td>");
            g.append("<td>");
            for(Transicion t: es.getTransiciones()){
                g.append(t.getTerminal().getValue().getVal());
                g.append(" -» ");
                g.append(t.getEstado().getNombre());
                g.append("  ");
            }
            g.append("</td></tr>\n");
        }
        g.append("</table>>];\n}");

        BufferedWriter writer = new BufferedWriter(new FileWriter("trans_"+nombre+".dot"));
        writer.write(String.valueOf(g));

        writer.close();

        String command = "dot -Tpng trans_"+nombre+".dot -o trans_"+nombre+".png";
        System.out.println(command);
        Process p = Runtime.getRuntime().exec(command);
    }

    public void graphAfd() throws IOException {
        StringBuilder g = new StringBuilder();
        g.append("digraph {\n" +
                //"splines=\"line\";\n" +
                "overlap = false;\n" +
                "splines = true;\n"+
                "rankdir = LR;\n" +
                "node [shape=circle, height=0.5, width=1.5, fontsize=20];\n" +
                "edge [fontsize=20];\n"+
                "graph[dpi=90];\n\n");

        for(Estado e: tablaTrans){
            g.append("\"").append(e.getNombre()).append("\" [label=\"").append(e.getNombre()).append("\"");
            if(e.isAceptacion()){
                g.append(", peripheries=2");
            }
            g.append("];\n");
        }
        for(Estado e: tablaTrans){
            for(Transicion t: e.getTransiciones()){
                g.append("\"").append(e.getNombre()).append("\" -> \"").append(t.getEstado().getNombre()).append("\"");
                g.append("[label=\"").append(t.getTerminal().getValue().getVal()).append("\"];");
            }
        }
        g.append("}");

        BufferedWriter writer = new BufferedWriter(new FileWriter("afd_"+nombre+".dot"));
        writer.write(String.valueOf(g));

        writer.close();

        String command = "dot -Tpng afd_"+nombre+".dot -o afd_"+nombre+".png";
        System.out.println(command);
        Process p = Runtime.getRuntime().exec(command);
    }

    private Estado existeEstado(LinkedList<Node> nodos){
        int x;
        for(Estado e: tablaTrans){
//            if(e.getListaNodos().equals(nodos)){
//                System.out.println("eXiste");
//                return e;
//            }
            x=0;
            for(Node n: e.getListaNodos()){
                for(Node c: nodos){
                    System.out.println(c.getLeafVal()+"  "+n.getLeafVal());
                    if(c.getLeafVal()==n.getLeafVal()){
                        x++;
                    }
                }
            }
            if(x==nodos.size()){
                System.out.println("Existe");
                return e;
            }
        }
        return null;
    }

    public void graph() throws IOException {
        numerar();
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



