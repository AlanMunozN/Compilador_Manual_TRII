package sample;

import com.sun.javafx.geom.transform.CanTransformVec3d;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Hashtable;

/**
 * Created by Kaleb on 11/8/2016.
 */
public class Semantico {

    Controller ctrl;

    static String variable_yaDeclarada="Variable previamente declarada";
    static String variable_noDeclarada="La variable no fue declarada previamente";
    boolean Declarada=false;
    boolean noDeclarada=false;


    Hashtable<String,String> variablesGlobales = new Hashtable<>();//Identificador tipoDato
    Hashtable<String,String> Funcion = new Hashtable<>();
    Hashtable<String,String> Procedimiento = new Hashtable<>();
    Hashtable<String,String> Resultados = new Hashtable<>();

    boolean parteGlobal=false, parteFuncion=false, parteProcedimiento=false, parteResultados=false;

    public void recorreToken() throws NullPointerException{

        int lexicoSize =  ctrl.lexico_Token.size();
        int auxSize;//+1
        int auxSize2;//+2
        String tipoDato = "(bool|entero|largo|byte|string)";
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String obtenerValor=null;
        String datoLlave=null;

        System.out.println("Entró al método");
        System.out.println("Tamaño lexicoSize: "+lexicoSize);

        for (int i=0; i<lexicoSize; i++){
            auxSize=i+1;
            auxSize2=i+2;
            obtenerValor=null;
            datoLlave=null;
            //System.out.println("Valor de i: "+i);

            if(ctrl.lexico_Token.get(i).equals("inicio")) {
                parteGlobal = false;
                System.out.println("Detectó inicio");
            }

            if(ctrl.lexico_Token.get(i).equals("var") || parteGlobal==true) {
                System.out.println("Entró a verificar globales");
                parteGlobal = true;
                if(auxSize<lexicoSize && auxSize2<lexicoSize){
                    if(ctrl.lexico_Token.get(i).matches(Identificador) && ctrl.lexico_Token.get(auxSize2).matches(tipoDato)) {
                        System.out.println("Metió un dato");
                        datoLlave=ctrl.lexico_Token.get(i);
                        obtenerValor=variablesGlobales.get(datoLlave);
                        if(obtenerValor!=null) {
                            System.out.println("Variable previamente declarada");
                            Declarada=true;
                            //ctrl.txtMensajes.appendText("Variable previamente declarada");
                        }
                        else
                            variablesGlobales.put(ctrl.lexico_Token.get(i), ctrl.lexico_Token.get(auxSize2));
                    }
                }
            }

            //Esta usa el hastable de resultados
            if(ctrl.lexico_Token.get(i).equals("inicio") || parteResultados==true && parteFuncion==false && parteProcedimiento==false) {
                parteResultados = true;
                System.out.println("Entró a inicio");
                if(auxSize<lexicoSize && auxSize2<lexicoSize){
                    if(ctrl.lexico_Token.get(i).matches(Identificador) && ctrl.lexico_Token.get(auxSize2).matches("[0-9]+")) {
                        datoLlave=ctrl.lexico_Token.get(i);
                        obtenerValor=variablesGlobales.get(datoLlave);
                        if(obtenerValor!=null)
                            Resultados.put(ctrl.lexico_Token.get(i), ctrl.lexico_Token.get(auxSize2));
                        else {
                            System.out.println("La variable no fue declarada previamente"+ctrl.lexico_Token.get(i));
                            noDeclarada=true;
                            //ctrl.txtMensajes.appendText("La variable no fue declarada previamente"+"\n");
                        }
                    }
                }
            }

            if(Declarada==true)
                break;
            else if(noDeclarada==true)
                break;

        }
        abrirTabla();
    }

    public void abrirTabla(){
        tablaSimbolos tbS = new tablaSimbolos();
        tbS.pack();
        tbS.setVisible(true);
    }
}
