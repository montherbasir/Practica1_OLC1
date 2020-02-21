import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.LinkedList;

public class Form1 {
    private JPanel panel1;
    private JTree tree1;
    private JTextArea textArea1;
    private JButton cargarArchivoButton;
    private JButton analizarButton;
    private JButton abrirFotoButton;
    private JButton button4;
    private JTextArea textArea2;
    private JLabel icono;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Form1");
        Form1 f1 = new Form1();
        frame.setContentPane(f1.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        f1.llenarTree(Main.principio());
    }

    public void llenarTree(LinkedList<Expresion> expresiones){
        if(expresiones!=null) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Analisis");
            DefaultTreeModel model = new DefaultTreeModel(root);
            DefaultMutableTreeNode[] nodosRaiz = new DefaultMutableTreeNode[expresiones.size()];
            int i = 0;
            for (Expresion ex : expresiones) {
                nodosRaiz[i] = new DefaultMutableTreeNode(ex.getNombre());
                root.add(nodosRaiz[i]);
                DefaultMutableTreeNode n = new DefaultMutableTreeNode("tree_"+ex.getNombre());
                DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("sig_"+ex.getNombre());
                DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("trans_"+ex.getNombre());
                DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("afd_"+ex.getNombre());
                nodosRaiz[i].add(n);
                nodosRaiz[i].add(n1);
                nodosRaiz[i].add(n2);
                nodosRaiz[i].add(n3);
            }
            tree1.setModel(model);
            tree1.setRootVisible(false);
            tree1.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode x = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                    if (x.isLeaf()) {
                        String ruta = x.toString();
                        textArea1.append(ruta);
                        icono.setIcon(new ImageIcon(ruta+".png"));
                    }
                }
            });
        }
    }
}
