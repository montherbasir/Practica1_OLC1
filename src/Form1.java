import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Form1 {
    private JPanel panel1;
    private JTree tree1;
    private JTextArea textArea1;
    private JButton cargarArchivoButton;
    private JButton analizarButton;
    private JTextArea textArea2;
    private JLabel icono;
    public static Form1 f1;
    public static JFrame frame;
    public Form1() {
        analizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    f1.llenarTree(Main.principio(textArea2.getText()));
                }catch (Exception ex){
                    new JOptionPane("Error");
                }

            }
        });
        cargarArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    JFileChooser open = new JFileChooser();
                    int option = open.showOpenDialog(frame);
                    File f1 = new File(open.getSelectedFile().getPath());
                    FileReader fr = new FileReader(f1);
                    BufferedReader br = new BufferedReader(fr);
                    String s;
                    while((s=br.readLine())!=null)
                    {
                        textArea2.append(s + "\n");
                    }
                    fr.close();
                }
                catch(Exception ae)
                {
                    System.out.println(ae);
                }
            }
        });
    }

    public static void main(String[] args) {
        frame  = new JFrame("Form1");
        f1 = new Form1();
        frame.setContentPane(f1.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void consoleP(String s){
        textArea1.append(s);
        textArea1.append("\n");
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
                        icono.setIcon(new ImageIcon(ruta+".png"));
                    }
                }
            });
        }
    }
}
