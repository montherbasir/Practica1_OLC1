import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static LinkedList<Expresion> principio() {
        Analizador a = new Analizador();
        String cadena ="{\n" +
                "\n" +
                "\n" +
                "//Definicion de mis conjuntos \n" +
                "CONJ: vocales -> a,e,i,o,u;\n" +
                "CONJ: otrasLetras -> m,r,c,l,g,s;\n" +
                "CONJ: abecedario -> a~z;\n" +
                "CONJ: conjnum -> 2~6;\n" +
                "CONJ: digito -> 0~9;\n" +
                "<!\n" +
                "\tDefinicion de mis expresiones regulares\n" +
                "!>\n" +
                "//Expresion regular \n" +
                "Expresion1 -> . . \"a\" * | \"a\" \"b\" b;\n" +
                "Expresion2 ->  . +{abecedario}  . {conjnum} ? . \":\" +{abecedario};\n" +
                "Expresion3 -> . . . . . . * | {abecedario} \"_\" + {conjnum} \">\" + {conjnum} \"es \" | \"TRUE\" \"FALSE\" \".\" ;  //Expresion de COmparacion   \n" +
                "operaciones -> . +{digito} + . |\"+\" |\"-\" |\"*\" \"/\" +{digito};\n" +
                "\n" +
                "%%\n" +
                "%%\n" +
                "\n" +
                "<!\n" +
                "\tDefinicion de lexemas\n" +
                "!>\n" +
                "//error en la validacion del lexema\n" +
                "Expresion1 : \"los murcielagos llevan_en_su_nombre_todas_las_vocales.\"; \n" +
                "//validacion exitosa del lexema\n" +
                "Expresion1 : \"El murcielago es_una_animal_mamifero\";\n" +
                "//validacion exitosa del lexema\n" +
                "Expresion2 : \"numero 5: cinco\";\n" +
                "Expresion2 : \"numero 2\";\n" +
                "\n" +
                "Expresion3 : \"la_expresion  5>4 es TRUE.\"; //validacion exitosa del lexema\n" +
                "Expresion3 : \"95>4 es FALSE.\"; //error en la validacion del lexema\n" +
                "\n" +
                "operaciones : \"5+13*7+9/10-1\"; <! validACION EXItosa del LEXEma !>\n" +
                "operaciones : \"5++3*11\"; <! error en LA valid4cion del LEXEma !>\n" +
                "<!\n" +
                "\tFin del archivo\n" +
                "!>\n" +
                "}";

        try {
            LinkedList<Token> list = a.escanear(cadena);
            a.imprimirListaToken(list);
            return a.generarExpresiones(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
