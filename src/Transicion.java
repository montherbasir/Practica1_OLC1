import java.util.LinkedList;

public class Transicion {
    private Node terminal;
    private Estado estado;

    public Transicion(Node terminal, Estado estado) {
        this.terminal = terminal;
        this.estado = estado;
    }

    public Node getTerminal() {
        return terminal;
    }

    public void setTerminal(Node terminal) {
        this.terminal = terminal;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
