import java.io.IOException;
import java.util.LinkedList;

public class Analizador {
    //Variable que representa la lista de tokens
    public LinkedList<Token> Salida;
    private LinkedList<Error> errores;
    //Variable que representa el estado actual
    private int estado;
    //Variable que representa el lexema que actualmente se esta acumulando
    private String auxlex;
    private Token.Tipo tipo;
    public int numErr;
    private String[] reservadas = {"CONJ"};
    private int fila;
    private int columna;



    public LinkedList<Token> escanear(String entrada)
    {
        entrada = entrada + "¢";
        Salida = new LinkedList<>();
        errores = new LinkedList<>();
        fila = 1;
        columna = 0;
        estado = 0;
        this.numErr = 0;
        auxlex = "";
        char c;
        char k;
        for (int i = 0; i <= entrada.length() - 1; i++)
        {
            c = entrada.charAt(i);
            k = c;
            if(i+1<entrada.length()) {
                k = entrada.charAt(i + 1);
            }
            columna++;
            switch (estado)
            {
                case 0:
                    if (Character.isLetter(c))
                    {
                        estado = 1;
                        auxlex += c;
                    }
                    else if (Character.isDigit(c))
                    {
                        estado = 4;
                        auxlex += c;
                    }
                    else if (c=='{')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.LLAVE_IZQ);
                        }
                    }
                    else if (c=='}')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.LLAVE_DER);
                        }
                    }
                    else if (c=='/')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            estado = 11;
                        }
                    }
                    else if (c=='*')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.ASTERISCO);
                        }
                    }
                    else if (c=='%')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            estado = 16;
                        }
                    }
                    else if (c=='<')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            estado = 13;
                        }
                    }
                    else if (c==':')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.DOS_PUNTOS);
                        }
                    }
                    else if (c==';')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.PUNTO_COMA);
                        }
                    }
                    else if (c=='.')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.PUNTO);
                        }
                    }
                    else if (c==',')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.COMA);
                        }
                    }
                    else if (c=='"')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            estado = 17;
                        }
                    }
                    else if (c=='+')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.MAS);
                        }
                    }
                    else if (c=='?')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.INTERROGACION);
                        }
                    }
                    else if (c=='|')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.OR);
                        }
                    }
                    else if (c=='-')
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            estado = 18;
                        }
                    }
                    else if (Character.isWhitespace(c))
                    {
                        if (c=='\n')
                        {
                            columna = 0;
                            fila++;
                        }
                        estado = 0;
                    }
                    else if ((int)c>=32&&(int)c<=125)
                    {
                        auxlex += c;
                        if(k=='~'){
                            estado = 7;
                        }else if(k==','){
                            estado = 9;
                        }else{
                            agregarToken(Token.Tipo.RANGO);
                        }
                    }
                    else
                    {
                        if (c=='¢' && i == entrada.length() - 1)
                        {
                            //Hemos concluido el análisis léxico.
                            System.out.println("Hemos concluido el analiss con exito");
                        }
                        else
                        {
                            auxlex += c;
                            System.out.println("Error lexico con: " + auxlex);
                            Form1.f1.consoleP("Error lexico con: " + auxlex);
                            this.numErr+=1;
                            estado = 0;
                            auxlex = "";
                        }
                    }
                    break;
                case 1:
                    if (Character.isLetter(c)|| Character.isDigit(c)|| c=='_')
                    {
                        estado = 2;
                        auxlex += c;
                    }
                    else if (c=='~')
                    {
                        estado = 3;
                        auxlex += c;
                    }
                    else
                    {
                            columna--;
                            agregarToken(Token.Tipo.ID);
                            i -= 1;
                    }
                    break;
                case 2:
                    if (Character.isLetter(c)|| Character.isDigit(c)|| c=='_')
                    {
                        estado = 2;
                        auxlex += c;
                    }
                    else if (Character.isWhitespace(c))
                    {
                        columna--;
                        if (esReservada(auxlex))
                        {
                            agregarToken(tipo);
                        }
                        else
                        {
                            agregarToken(Token.Tipo.ID);
                        }
                        if (c=='\n')
                        {
                            columna = 0;
                            fila++;
                        }
                        columna++;
                    }
                    else
                    {
                        if (esReservada(auxlex))
                        {
                            columna--;
                            agregarToken(tipo);
                            i -= 1;
                        }
                        else
                        {
                            columna--;
                            agregarToken(Token.Tipo.ID);
                            i -= 1;
                        }
                    }
                    break;
                case 3:
                    if (Character.isLetter(c))
                    {
                        auxlex += c;
                        agregarToken(Token.Tipo.RANGO);
                    }
                    else
                    {
                        System.out.println("error");
                        Form1.f1.consoleP("Error lexico con: " + auxlex);
                        //ERROR
                    }
                    break;
                case 4:
                    if (Character.isDigit(c))
                    {
                        estado = 4;
                        auxlex += c;
                    }
                    else if (c=='~')
                    {
                        estado = 5;
                        auxlex += c;
                    }
                    else if (Character.isWhitespace(c))
                    {
                        columna--;
                        agregarToken(Token.Tipo.NUMERO);
                        if (c=='\n')
                        {
                            columna = 0;
                            fila++;
                        }
                        columna++;
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.NUMERO);
                        i -= 1;
                    }
                    break;
                case 5:
                    if (Character.isDigit(c))
                    {
                        estado = 6;
                        auxlex += c;
                    }else{
                        System.out.println("error");
                        Form1.f1.consoleP("Error lexico con: " + auxlex);
                        //ERROR
                    }
                    break;
                case 6:
                    if (Character.isDigit(c))
                    {
                        estado = 6;
                        auxlex += c;
                    }
                    else if (Character.isWhitespace(c))
                    {
                        columna--;
                        agregarToken(Token.Tipo.RANGO);
                        if (c=='\n')
                        {
                            columna = 0;
                            fila++;
                        }
                        columna++;
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.RANGO);
                        i -= 1;
                    }
                    break;
                case 7:
                    if (c=='~')
                    {
                        estado = 8;
                        auxlex += c;
                    }
                    else
                    {
                        auxlex += c;
                        System.out.println("Error lexico con: " + auxlex);
                        Form1.f1.consoleP("Error lexico con: " + auxlex);
                        this.numErr += 1;
                        estado = 0;
                        auxlex = "";
                    }
                    break;
                case 8:
                    if ((int)c>=32&&(int)c<=125)
                    {
                        auxlex += c;
                        agregarToken(Token.Tipo.RANGO);
                    }
                    else
                    {
                        auxlex += c;
                        System.out.println("Error lexico con: " + auxlex);
                        Form1.f1.consoleP("Error lexico con: " + auxlex);
                        this.numErr += 1;
                        estado = 0;
                        auxlex = "";
                    }
                    break;
                case 9:
                    if (c==',')
                    {
                        agregarToken(Token.Tipo.ASCII);
                        estado = 10;
                        auxlex += c;
                        agregarToken(Token.Tipo.COMA);
                    }
                    else if (Character.isWhitespace(c))
                    {
                        columna--;
                        agregarToken(Token.Tipo.ASCII);
                        if (c=='\n')
                        {
                            columna = 0;
                            fila++;
                        }
                        columna++;
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.ASCII);
                        i -= 1;
                    }
                    break;
                case 10:
                    if ((int)c>=32&&(int)c<=125)
                    {
                        auxlex += c;
                        estado = 9;
                    }
                    else
                    {
                        auxlex += c;
                        System.out.println("Error lexico con: " + auxlex);
                        Form1.f1.consoleP("Error lexico con: " + auxlex);
                        this.numErr += 1;
                        estado = 0;
                        auxlex = "";
                    }
                    break;
                case 11:
                    if (c=='/')
                    {
                        estado = 12;
                        auxlex += c;
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.ASCII);
                        i -= 1;
                    }
                    break;
                case 12:
                    if (c=='\n')
                    {
                        agregarToken(Token.Tipo.COM_SIM);
                    }
                    else
                    {
                        estado = 12;
                        auxlex += c;
                    }
                    break;
                case 13:
                    if (c=='!')
                    {
                        estado = 14;
                        auxlex += c;
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.ASCII);
                        i -= 1;
                    }
                    break;
                case 14:
                    if (c=='!')
                    {
                        estado = 15;
                    }
                    else
                    {
                        estado = 14;
                    }
                    auxlex += c;
                    break;
                case 15:
                    if (c=='>')
                    {
                        auxlex += c;
                        agregarToken(Token.Tipo.COM_MULTI);
                    }
                    else
                    {
                        estado = 14;
                        auxlex += c;
                    }
                    break;
                case 16:
                    if (c=='%')
                    {
                        auxlex += c;
                        agregarToken(Token.Tipo.PORC_PORC);
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.ASCII);
                        i -= 1;
                    }
                    break;
                case 17:
                    if (c=='"')
                    {
                        auxlex += c;
                        agregarToken(Token.Tipo.CADENA);
                    }
                    else
                    {
                        estado = 17;
                        auxlex += c;
                    }
                    break;
                case 18:
                    if (c=='>')
                    {
                        auxlex += c;
                        agregarToken(Token.Tipo.FLECHA);
                    }
                    else
                    {
                        columna--;
                        agregarToken(Token.Tipo.ASCII);
                        i -= 1;
                    }
                    break;
            }

        }
        return Salida;
    }

    public void agregarToken(Token.Tipo tipo)
    {
        Salida.add(new Token(tipo, auxlex, fila, columna));
        auxlex = "";
        estado = 0;
    }

    private Boolean esReservada(String palabra)
    {
        boolean res = false;
        for (String reservada : reservadas)
        {
            if (palabra.equals(reservada))
            {
                if (reservada.equals("CONJ"))
                {
                    tipo = Token.Tipo.CONJ;
                }
                res = true;
            }
        }
        return res;
    }

    public void imprimirListaToken(LinkedList<Token> lista)
    {
        for (Token item : lista)
        {
            System.out.println(item.getTipo() + "           " + item.getVal());
        }
        System.out.println("Numero de errores: "+numErr);
        Form1.f1.consoleP("Numero de errores: "+numErr);
    }

    public void validarExpresiones(LinkedList<Token> entrada){
        for (int i=0; i<entrada.size();i++) {
            if(entrada.get(i).getTipo() == Token.Tipo.ID){
                if((entrada.get(i+1).getTipo() == Token.Tipo.DOS_PUNTOS)){
                    Form1.f1.consoleP(entrada.get(i).getVal()+": EXPRESION VALIDA");
                }
            }
        }
    }

    public LinkedList<Expresion> generarExpresiones(LinkedList<Token> entrada) throws IOException {
        LinkedList<Expresion> expresiones = new LinkedList<>();
        boolean porc = false;
        for (int i=0; i<entrada.size();i++) {
            if(entrada.get(i).getTipo() == Token.Tipo.ID){
                if((entrada.get(i+1).getTipo() == Token.Tipo.FLECHA)&&(entrada.get(i-2).getTipo() != Token.Tipo.CONJ)){
                    Expresion ex = new Expresion(entrada.get(i).getVal());
                    int j = i+2;
                    Token tok = entrada.get(j);
                    ex.addToken(new Token(Token.Tipo.PUNTO, ".",0,0));
                    while(tok.getTipo()!= Token.Tipo.PUNTO_COMA) {
                        if ((tok.getTipo() != Token.Tipo.LLAVE_IZQ) && (tok.getTipo() != Token.Tipo.LLAVE_DER)) {
                            ex.addToken(tok);
                        }
                        j++;
                        tok = entrada.get(j);
                    }
                    ex.addToken(new Token(Token.Tipo.CADENA, "¢",0,0));
                    ex.ponerArbol();
                    expresiones.add(ex);
                }
            }
        }
        return expresiones;
    }

}
