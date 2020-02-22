import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static LinkedList<Expresion> principio(String cadena) {
        Analizador a = new Analizador();

        try {
            LinkedList<Token> list = a.escanear(cadena);
            a.imprimirListaToken(list);
            a.validarExpresiones(list);
            return a.generarExpresiones(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
