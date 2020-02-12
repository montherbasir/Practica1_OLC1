
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.IntelliJTheme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class LnF {
    private JTable table1;
    private JButton button1;
    private JRadioButton radioButton1;
    private JTree tree1;
    private JTextArea textArea1;
    private JTextField textField1;
    private JPanel root;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        JFrame frame = new JFrame("LnF");
        frame.setContentPane(new LnF().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
