public class Token {
    public enum Tipo
    {
        COM_SIM,
        COM_MULTI,
        LLAVE_IZQ,
        LLAVE_DER,
        ID,
        FLECHA,
        PUNTO_COMA,
        COMA,
        PORC_PORC,
        DOS_PUNTOS,
        CADENA,
        PUNTO,
        OR,
        INTERROGACION,
        ASTERISCO,
        MAS,
        RANGO,
        CONJ,
        NUMERO,
        ASCII
    }

    private Tipo tipo;
    private String valor;
    private int fila;
    private int columna;

    public Token(Tipo tipoDelToken, String val, int fila, int columna)
    {
        this.tipo = tipoDelToken;
        this.valor = val;
        this.fila = fila;
        this.columna = columna;
    }

    public String getVal()
    {
        if(tipo==Tipo.CADENA){
            return valor.replace("\"","");
        }else{
            return valor;
        }
    }
    public int getFila()
    {
        return fila;
    }
    public int getColumna()
    {
        int n = valor.length();
        return columna-n;
    }
    public Tipo getTipo(){
        return tipo;
    }
    public String getNombreTipo()
    {
        switch (tipo)
        {
            case COM_MULTI:
                return "Comentario multiple";
            case COM_SIM:
                return "Comentario simple";
            case ID:
                return "Identificador";
            case FLECHA:
                return "Flecha";
            case PUNTO:
                return "Punto";
            case OR:
                return "Or";
            case INTERROGACION:
                return "Interrogacion";
            case DOS_PUNTOS:
                return "Dos puntos";
            case LLAVE_IZQ:
                return "Llave abre";
            case LLAVE_DER:
                return "Llave cierra";
            case PUNTO_COMA:
                return "Punto y coma";
            case ASTERISCO:
                return "Asterisoc";
            case CADENA:
                return "Cadena";
            case MAS:
                return "Mas";
            case RANGO:
                return "Rango";
            case PORC_PORC:
                return "Porcentaje doble";
            case CONJ:
                return "Palabra Conjunto";
            default:
                return "Desconocido";
        }
    }
}
