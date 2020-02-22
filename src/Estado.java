import java.util.LinkedList;

public class Estado {
    private String nombre;
    private LinkedList<Node> listaNodos;
    private LinkedList<Transicion> transiciones;

    public Estado(String nombre) {
        this.nombre = nombre;
        transiciones = new LinkedList<Transicion>();
    }

    public LinkedList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(LinkedList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Node> getListaNodos() {
        return listaNodos;
    }

    public void setListaNodos(LinkedList<Node> listaNodos) {
        this.listaNodos = listaNodos;
    }
}
