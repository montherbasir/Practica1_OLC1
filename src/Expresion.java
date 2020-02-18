import java.io.IOException;
import java.util.LinkedList;

public class Expresion {
    private String nombre;
    private LinkedList<Token> tokensList;
    private Arbol arbol;

    public Expresion(String nombre) {
        this.nombre = nombre;
        tokensList = new LinkedList<>();
    }

    public void addToken(Token t){
        tokensList.add(t);
    }

    public void ponerArbol() throws IOException {
        arbol = new Arbol(this.tokensList);
        arbol.add();
        arbol.graph();
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Token> getTokensList() {
        return tokensList;
    }

    public void setTokensList(LinkedList<Token> tokensList) {
        this.tokensList = tokensList;
    }
}
