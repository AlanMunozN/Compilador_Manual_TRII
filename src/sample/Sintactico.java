package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by Kaleb on 10/9/2016.
 */
public class Sintactico {

    Controller ctrl;
    int contadorNodos=1;
    int cordX=350, cordY=100;
    int hijosLibreria=0;
    int hijosVar=0;
    int hijosInicio=0;

    boolean encontradaLibreria=false,encontradaVar=false,encontradaInicio=false;

    public  static ArrayList<String> nombre_Nodo = new ArrayList<>(Arrays.asList());//Nombre del nodo
    public  static ArrayList<Integer> coordenada_X = new ArrayList<>(Arrays.asList());//Coordenada X
    public  static ArrayList<Integer> coodernada_Y = new ArrayList<>(Arrays.asList());//Coordenanda Y


    boolean programaEncontrado=false;
    public void detectarNodos_Arbol(){
        boolean verificaLibreria=false;
        boolean errorLibreria=false;
        boolean verificaVar=false;
        boolean verificaInicio=false;
        boolean errorVar=false;

        boolean libCompleta=false;
        boolean varCompleta=false;
        boolean inicioCompleta=false;

        boolean auxLibreria=false;
        boolean libreriaNOT=false;



        int hijos_Padre=0;
        int auxContador=0;

        for(int i=0;i<ctrl.lexico_Token.size();i++){
            auxContador=0;
            auxContador=i+1;

            if(ctrl.lexico_Token.get(i).equals("programa")){
                if(auxContador<ctrl.lexico_Token.size()){
                    if(ctrl.lexico_Token.get(i).matches("[a-zA-Z]+")){
                        programaEncontrado=true;

                        nombre_Nodo.add(ctrl.lexico_Token.get(i+1));//Agregamos la palabra reservada libreria
                        coordenada_X.add(100);
                        coodernada_Y.add(200);
                        i++;
                    }
                }
            }

            if(ctrl.lexico_Token.get(i).equals("libreria") && ctrl.lexico_Identificador.get(i).equals("Reservada") || verificaLibreria==true && libCompleta==false) {//Libreria

                encontradaLibreria=true;
                libreriaNOT=true;
                System.out.println("Entro libreria");

                if(auxLibreria==false) {
                    cordX = 170;
                    cordY = 200;
                    verificaLibreria = true;


                    nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos la palabra reservada libreria
                    coordenada_X.add(cordX);
                    coodernada_Y.add(cordY);
                    contadorNodos++;
                    hijos_Padre++;
                    hijosLibreria++;

                    if (hijos_Padre == 1) {
                        cordX = cordX + 90;
                    }

                /*
                if(ctrl.lexico_Token.get(i+1).matches("[<][A-Z][A-Za-z0-9]+[.][p][>]"))//Verificar siguiente parte
                    i++;
                else {
                    errorLibreria = true;
                    System.out.println("Error en libreria");
                }

                if(errorLibreria==true)
                    break;
                 */

                    i++;

                    nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos el nombre de la libreria <Hola.p>
                    coordenada_X.add(cordX);
                    coodernada_Y.add(cordY);
                    contadorNodos++;
                    hijosLibreria++;
                    cordX=cordX+80;
                    cordY=cordY+100;
                }

                if(auxLibreria==true) {
                    nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos lo que siga
                    coordenada_X.add(cordX);
                    coodernada_Y.add(cordY);
                    contadorNodos++;
                    hijosLibreria++;

                }
                auxLibreria=true;
                if (auxContador < ctrl.lexico_Token.size()) {
                    if (ctrl.lexico_Token.get(i + 1).equals("var")) {
                        verificaLibreria = false;
                        libCompleta = true;
                    }
                }

            }
            if(errorLibreria==true)//Terminar por error
                break;

            if(ctrl.lexico_Token.get(i).equals("var") && ctrl.lexico_Identificador.get(i).equals("Reservada") || verificaVar==true && varCompleta==false && libCompleta==true || libreriaNOT==false ) {//Var. Declaración

                System.out.println("Entró var");
                verificaVar=true;
                encontradaVar=true;

                if(hijosVar<1) {
                    i = i + 1;

                    cordX = 450;
                    cordY = 200;
                }
                if(ctrl.lexico_Token.get(i).equals("var"))
                    i++;

                nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos los elemento de var
                coordenada_X.add(cordX);
                coodernada_Y.add(cordY);
                contadorNodos++;
                cordY=cordY+100;
                hijosVar++;

                if(auxContador<ctrl.lexico_Token.size()) {
                    if (ctrl.lexico_Token.get(i + 1).equals("inicio")) {
                        verificaVar = false;
                        varCompleta = true;
                    }
                }
            }

            if(ctrl.lexico_Token.get(i).equals("inicio") && ctrl.lexico_Identificador.get(i).equals("Reservada") || verificaInicio==true && varCompleta==true && libCompleta==true) {//Var. Declaración

                verificaInicio=true;
                encontradaInicio=true;
                System.out.println("Entró inicio");

                if(hijosInicio<1) {
                    i++;

                    cordX = 550;
                    cordY = 200;
                }

                if(ctrl.lexico_Token.get(i).equals("inicio"))
                    i++;


                if(auxContador<ctrl.lexico_Token.size()) {
                    if (ctrl.lexico_Token.get(i + 1).equals(":=")) {
                        nombre_Nodo.add("<Asignacion>");//Agregamos los elemento de inicio
                        coordenada_X.add(cordX);
                        coodernada_Y.add(cordY);
                        contadorNodos++;
                        cordY = cordY + 100;
                        hijosInicio++;
                    }
                }


                nombre_Nodo.add(ctrl.lexico_Token.get(i));//Agregamos los elemento de inicio
                coordenada_X.add(cordX);
                coodernada_Y.add(cordY);
                contadorNodos++;
                cordY=cordY+100;
                hijosInicio++;
            }
        }
        System.out.println("Tamaño: "+nombre_Nodo.size());
        abrirArbol();
    }


    boolean programaVerificado=false, libreriaVerificado=false, varVerificado=false, inicioVerificado=false;

    public void deteccionNodos_Tokens(){//Usar banderas para que entre a un area si encuentra la palabra principal
        int listaLexico_Size=ctrl.lexico_Token.size();
        int auxSize_Lexico=0;
        for(int i=0;i<listaLexico_Size;i++){//Orden de la estructura del programa
            auxSize_Lexico=i+1;
            //Checar banderas para evitar que ingrese donde no debe. Posible solución variación de estructura según lo que puede encontrar
            if(ctrl.lexico_Token.get(i).equals("programa")) {
                System.out.println("Verifica prograa");
                if(auxSize_Lexico<listaLexico_Size){//Evitar que truene por dirección inválida por tamaño inferior
                    seccionPrograma(ctrl.lexico_Token.get(i),ctrl.lexico_Token.get(auxSize_Lexico));//Pasamos los valores
                }
            }
            else if(ctrl.lexico_Token.get(i).equals("libreria")) {
                System.out.println("Verifica libreria");
                if(auxSize_Lexico<listaLexico_Size){//Evitar que truene por dirección inválida por tamaño inferior
                    seccionLibreria(ctrl.lexico_Token.get(i),ctrl.lexico_Token.get(auxSize_Lexico));//Pasamos los valores
                }
            }
            else if(ctrl.lexico_Token.get(i).equals("var") || varVerificado==true) {
                varVerificado=true;
                if(ctrl.lexico_Token.get(i).equals("var"))
                    inicializaBanderas_Var();

                System.out.println("Verificar var");

                if (ctrl.lexico_Identificador.get(i).equals("Comentario Bloque") || ctrl.lexico_Identificador.get(i).equals("Comentario Linea")) {
                    if(auxSize_Lexico<listaLexico_Size) {
                        i++;
                    }
                }
                System.out.println("Verificando: "+ctrl.lexico_Token.get(i));

                if(auxSize_Lexico<listaLexico_Size){
                    if(ctrl.lexico_Token.get(auxSize_Lexico).equals(":=")){
                        asignacionInicio = true;
                    }
                    if(ctrl.lexico_Token.get(auxSize_Lexico).equals("(")) {
                        invocacionInicio = true;
                    }
                }

                if(!ctrl.lexico_Token.get(i).equals("var")) {//Ver en la lista de identificador si es comentario i++
                        Asignacion_Var(ctrl.lexico_Token.get(i));
                        //Si detecta funcion o procedimiento entra a esa sección para verificar

                        if(ctrl.lexico_Token.get(i).equals("inicio")) {//Ver como iría esta verificación
                            System.out.println("Se encontró inicio");
                            varVerificado = false;
                        }
                    }

            }
            else if(ctrl.lexico_Token.get(i).equals("inicio") || inicioVerificado==true) {//Arreglar la bandera
                inicioVerificado=true;
                System.out.println("Verifica inicio");
                //Contiene un bloque. El cual es génerico
            }
        }
    }


    public void seccionPrograma(String Token1, String Token2){
        if(Token1.equals("programa")) {
            if(Token2.matches("[A-Z][a-zA-Z0-9]+")) {
                nombre_Nodo.add(Token1);
                coordenada_X.add(100);
                coodernada_Y.add(200);

                nombre_Nodo.add(Token2);
                coordenada_X.add(100);
                coodernada_Y.add(300);
                programaVerificado=true;
            }
            else {
                System.out.println("Error en declaración de programa. Se esperaba nombre de programa");
            }
        }
    }

    public void seccionLibreria(String Token1, String Token2){
        if(Token1.equals("libreria")) {
            if(Token2.matches("[<][A-Z][a-zA-Z0-9]+[.][p][>]")) {//Si hay otra aumenta 100 en Y
                nombre_Nodo.add(Token1);
                coordenada_X.add(170);
                coodernada_Y.add(200);

                nombre_Nodo.add(Token2);
                coordenada_X.add(260);
                coodernada_Y.add(200);
                libreriaVerificado=true;
            }
            else {
                System.out.println("Error en declaración de libreria. Se esperaba nombre de libreria");
            }
        }
    }

    boolean ID_Encontrado=false, Asignacion_Encontrado=false, tipoDato_Encontrado=false, tipoArreglo_Encontrado=false, Separador_Encontrado=false;
    boolean abrirArreglo_Encontrado=false, inicioArreglo_Encontrado=false, puntosArreglo_Encontrado=false, limiteArreglo_Encontrado=false, cerrarArreglo_Encontrado=false, deArreglo_Encontrado=false;
    int auxVar=0;
    boolean errorSintactico_Encontrado=false;
    boolean arregloDetectado=false;
    boolean funcionDetectado=false; // Ver como se inicializa
    boolean procedimientoDetectado=false; // Ver como se inicializa
    boolean funcion_sinParametros=false;
    boolean funcion_parametroEncontrado=false;
    boolean funcion_unParametro=false;
    boolean funcion_dosParametros=false;

    boolean procedimiento_unParametro=false;
    boolean procedimiento_dosParametro=false;

    public void inicializaBanderas_Var(){
        ID_Encontrado=false; Asignacion_Encontrado=false; tipoDato_Encontrado=false; tipoArreglo_Encontrado=false; Separador_Encontrado=false;
        abrirArreglo_Encontrado=false; inicioArreglo_Encontrado=false; puntosArreglo_Encontrado=false; limiteArreglo_Encontrado=false; cerrarArreglo_Encontrado=false; deArreglo_Encontrado=false;

        auxVar=-1;

        errorSintactico_Encontrado=false; arregloDetectado=false;

        funcion_sinParametros=false; funcion_parametroEncontrado=false; funcion_unParametro=false; funcion_dosParametros=false;

        procedimiento_unParametro=false; procedimiento_dosParametro=false;

        System.out.println("Banderas inicializadas");
    }

    public void seccionVar(String Token){
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";
        String comentarioLinea = ("[{].[}]");
        String comentarioBloque = ("[{//].[//}]");

        System.out.println("Token Var: "+Token);//Areglar la sucesión por la bandera
        if(Token.matches(Identificador) || ID_Encontrado==true){//Declaración normal
            ID_Encontrado=true;
            if(Token.matches(":") || Asignacion_Encontrado==true){
                Asignacion_Encontrado=true;
                if(Token.matches(tipoDato) || tipoDato_Encontrado==true){
                    tipoDato_Encontrado=true;
                    if(Token.equals(";") || Separador_Encontrado==true){
                        Separador_Encontrado=true;
                        //Volvemos a inicializar las banderas
                        inicializaBanderas_Var();
                    }
                    else {
                            System.out.println("Se esperaba ;");
                    }
                }
                else if(Token.equals("arreglo") || tipoArreglo_Encontrado==true){//Declaraciópn de arreglo
                    tipoArreglo_Encontrado=true;
                    if(Token.equals("[") || abrirArreglo_Encontrado==true){
                        abrirArreglo_Encontrado=true;
                        if(Token.equals("1") || inicioArreglo_Encontrado==true){
                            inicioArreglo_Encontrado=true;
                            if(Token.equals("..") || puntosArreglo_Encontrado==true){
                                puntosArreglo_Encontrado=true;
                                if(Token.matches("[0-9]+") || limiteArreglo_Encontrado==true){
                                    limiteArreglo_Encontrado=true;
                                    if(Token.equals("]") || cerrarArreglo_Encontrado==true){
                                        cerrarArreglo_Encontrado=true;
                                        if(Token.equals("de") ||deArreglo_Encontrado==true){
                                            deArreglo_Encontrado=true;
                                            if(Token.matches(tipoDato) || tipoDato_Encontrado==true){
                                                tipoDato_Encontrado=true;
                                                if (Token.equals(";") || Separador_Encontrado==true){
                                                    Separador_Encontrado=true;
                                                    //Volvemos a inicializar las banderas
                                                    inicializaBanderas_Var();
                                                }
                                                else {
                                                        System.out.println("Se esperaba ;");
                                                }
                                            }
                                            else {
                                                    System.out.println("Se esperaba tipo de dato para el arreglo");
                                            }
                                        }
                                        else {
                                                System.out.println("Se esperaba declarion de tipo de arreglo de");
                                        }
                                    }
                                    else {
                                            System.out.println("Se esperaba cerrado de arreglo ]");
                                    }
                                }
                                else {
                                        System.out.println("Se esperaba límite de arreglo (Número)");
                                }
                            }
                            else {
                                    System.out.println("Se esperaba .. de arreglo");
                            }
                        }
                        else {
                                System.out.println("Se esperaba comienzo de arreglo 1");
                        }
                    }
                    else {

                            System.out.println("Se esperaba apertura de arreglo [");
                    }
                }
                else {

                        System.out.println("Se esperaba un tipo de dato");
                }
            }
            else {
                    System.out.println("Se esperaba :");
            }
        }
        else if(Token.matches(comentarioLinea)){
            System.out.println("");
            inicializaBanderas_Var();
        }
        else if(Token.matches(comentarioBloque)){
            System.out.println("");
            inicializaBanderas_Var();
        }
        auxVar=auxVar+1;
    }



    public void Asignacion_Var(String Token){
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";

        System.out.println("auxVar = "+auxVar);

        if(auxVar==0){
            if(Token.matches(Identificador)) {
                System.out.println("Identificador correcto");
            }
            else if(Token.equals("funcion")) {
                System.out.println("función correcto. Identificado");
                funcionDetectado=true;
            }
            else if(Token.equals("procedimiento")) {
                System.out.println("procedimiento correcto. Identificado");
                procedimientoDetectado=true;
            }
        }
        if(auxVar==1 && funcionDetectado==false && procedimientoDetectado==false){
            if(Token.equals(":")){
                System.out.println("Asignación correcto");
            }
            else
                System.out.println("Se esperaba :");
        }
        if(auxVar==2 && funcionDetectado==false && procedimientoDetectado==false){
            if(Token.matches(tipoDato)){
                System.out.println("Tipo de dato correcto");
            }
            else if(Token.equals("arreglo")){
                System.out.println("Tipo de dato arreglo correcto");
                arregloDetectado=true;
            }
            else
                System.out.println("Se esperaba tipo de dato");
        }
        if(auxVar==3 && arregloDetectado==false && funcionDetectado==false && procedimientoDetectado==false){//Será para declaración normal
            if(Token.equals(";")){
                System.out.println("Separador correcto");
                inicializaBanderas_Var();
            }
            else
                System.out.println("Se esperaba ;");
        }
        if(auxVar==3 && arregloDetectado==true){//Arreglo detectado
            if(Token.equals("[")){
                System.out.println("Apertura de arreglo correcto");
            }
            else
                System.out.println("Se esperaba [");
        }
        if(auxVar==4 && arregloDetectado==true){
            if(Token.equals("1")){
                System.out.println("Inicio de arreglo correcto");
            }
            else
                System.out.println("Se esperaba inicio de arreglo 1");
        }
        if(auxVar==5 && arregloDetectado==true){
            if(Token.equals("..")){
                System.out.println(".. correcto");
            }
            else
                System.out.println("Se esperaba .. de arreglo ");
        }
        if(auxVar==6 && arregloDetectado==true){
            if(Token.matches("[0-9]+")){
                System.out.println("Límite de arreglo correcto");
            }
            else
                System.out.println("Se esperaba límite de arreglo");
        }
        if(auxVar==7 && arregloDetectado==true){
            if(Token.equals("]")){
                System.out.println("Cierre de arreglo correcto");
            }
            else
                System.out.println("Se esperaba ]");
        }
        if(auxVar==8 && arregloDetectado==true){
            if(Token.equals("de")){
                System.out.println("de correcto");
            }
            else
                System.out.println("Se esperaba de");
        }
        if(auxVar==9 && arregloDetectado==true){
            if(Token.matches(tipoDato)){
                System.out.println("Tipo de arreglo correcto");
            }
            else
                System.out.println("Se esperaba tipo de dato de arreglo");
        }
        if(auxVar==10 && arregloDetectado==true){
            if(Token.equals(";")){
                System.out.println("Separador correcto");
                inicializaBanderas_Var();
            }
            else
                System.out.println("Se esperaba ;");
        }


        //Función
        //funcion <identificador> ([ <tipodato> <identificador> [, <tipodato> <identificador>] ]) como <tipodato>
        // funcion Funcion ( entero Numero , string Cadena ) como flotante
        if(funcionDetectado==true) {//Solo entra si se detectó una función el auxVar==0
            if (auxVar == 1) {
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto");
                }
                else
                    System.out.println("Se esperaba un identificador");
            }
            if(auxVar==2){
                if (Token.equals("(")) {
                    System.out.println("Apertura correcto");
                }
                else
                    System.out.println("Se esperaba un (");
            }
            if(auxVar==3){
                if (Token.equals(")")) {//Sin parametros
                    System.out.println("Cerrado correcto");
                    funcion_sinParametros=true;
                }
                else if (Token.matches(tipoDato)) {//Parametro detectado. Puede ser uno o doble
                    System.out.println("Tipo de dato correcto");
                    funcion_parametroEncontrado=true;
                }
                else
                    System.out.println("Se esperaba un tipo de dato o paréntesis de carrado");
            }


            if(funcion_sinParametros==true){//No se encontraon parametros
                if(auxVar==4){
                    if (Token.equals("como")) {
                        System.out.println("Palabra de asignacion de funcion \"como\" correcto");
                    }
                    else
                        System.out.println("Se esperaba palabra de asignacion de funcion \"como\"");
                }
                if(auxVar==5){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        inicializaBanderas_Var();
                    }
                    else
                        System.out.println("Se esperaba un tipo de dato");
                }
            }


            if(funcion_parametroEncontrado==true){//Se encontró parametro
                if(auxVar==4){
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                    }
                    else
                        System.out.println("Se esperaba un identificador");
                }
                if(auxVar==5){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        funcion_unParametro=true;//Se procede a verificar el fin
                        funcion_parametroEncontrado=false;
                    }
                    if (Token.equals(",")) {
                        System.out.println("Coma de segundo parametro correcto");
                        funcion_dosParametros=true;//Se procede a verificar el otro parametro
                        funcion_parametroEncontrado=false;
                    }
                    else
                        System.out.println("Se esperaba una coma o  paréntesis de cerrado");
                }
            }


            if(funcion_unParametro==true){//Se encontró un parametro
                if(auxVar==6){
                    if (Token.equals("como")) {
                        System.out.println("Palabra de asignacion de funcion \"como\" correcto");
                    }
                    else
                        System.out.println("Se esperaba palabra de asignacion de funcion \"como\"");
                }
                if(auxVar==7){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        inicializaBanderas_Var();
                    }
                    else
                        System.out.println("Se esperaba un tipo de dato");
                }
            }


            if(funcion_dosParametros==true){//Se encontró otro parámetro. Fueorn dobles
                if(auxVar==6){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                    }
                    else
                        System.out.println("Se esperaba un tipo de dato");
                }
                if(auxVar==7){
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                    }
                    else
                        System.out.println("Se esperaba un identificador");
                }
                if(auxVar==8){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                    }
                    else
                        System.out.println("Se esparaba parentesis de cerrado )");
                }
                if(auxVar==9){
                    if (Token.equals("como")) {
                        System.out.println("Palabra de asignacion de funcion \"como\" correcto");
                    }
                    else
                        System.out.println("Se esperaba palabra de asignacion de funcion \"como\"");
                }
                if(auxVar==10){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                        inicializaBanderas_Var();
                    }
                    else
                        System.out.println("Se esperaba un tipo de dato");
                }
            }

        }




        //procedimiento Algo (entero Rand, string Cadena)
        if(procedimientoDetectado==true){//Solo entra si se detectó un procedimiento el auxVar==0
            if (auxVar == 1) {
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto");
                }
                else
                    System.out.println("Se esperaba un identificador");
            }
            if(auxVar==2){
                if (Token.equals("(")) {
                    System.out.println("Apertura correcto");
                }
                else
                    System.out.println("Se esperaba un (");
            }
            if(auxVar==3){
                if (Token.equals(")")) {//Sin parametros
                    System.out.println("Cerrado correcto");
                    inicializaBanderas_Var();
                }
                else if (Token.matches(tipoDato)) {//Parametro detectado. Puede ser uno o doble
                    System.out.println("Tipo de dato correcto");
                    procedimiento_unParametro=true;
                }
                else
                    System.out.println("Se esperaba un tipo de dato o paréntesis de carrado");
            }

            if(procedimiento_unParametro==true){
                if (auxVar == 4) {
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                    }
                    else
                        System.out.println("Se esperaba un identificador");
                }
                if(auxVar==5){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        inicializaBanderas_Var();
                    }
                    if (Token.equals(",")) {
                        System.out.println("Coma de segundo parametro correcto");
                        procedimiento_dosParametro=true;//Se procede a verificar el otro parametro
                        procedimiento_unParametro=false;
                    }
                    else
                        System.out.println("Se esperaba una coma o  paréntesis de cerrado");
                }
            }


            if(procedimiento_dosParametro==true){
                if(auxVar==6){
                    if (Token.matches(tipoDato)) {
                        System.out.println("Tipo de dato correcto");
                    }
                    else
                        System.out.println("Se esperaba un tipo de dato");
                }
                if(auxVar==7){
                    if (Token.matches(Identificador)) {
                        System.out.println("Identificador correcto");
                    }
                    else
                        System.out.println("Se esperaba un identificador");
                }
                if(auxVar==8){
                    if (Token.equals(")")) {
                        System.out.println("Parentesis de cerrado correcto");
                        inicializaBanderas_Var();
                    }
                    else
                        System.out.println("Se esparaba parentesis de cerrado )");
                }
            }

        }

        auxVar=auxVar+1;
    }

    boolean asignacionInicio=false;
    boolean invocacionInicio=false;

    public void secciónInicio(String Token){
        /*
        <bloque> ::= <asignacion> | <si> | <invocar_funcion> |  <invocar_procedimiento> | <para> | <repite> | <hazlo_si> | <encasode> | leer
            | escribir | <comentarios>
         */
        String Identificador = "([A-Z]{1,1}[a-zA-Z0-9]{2,254})";
        String tipoDato = "(bool|entero|largo|byte|string|flotante)";

        if(asignacionInicio==true) {//La bandera cambiar por que en el for verifico el token siguiente y si es := se activa la bandera
            if (auxVar == 0) {//Asignación
                if (Token.matches(Identificador)) {//Asignar, llmar función o llamar procedimiento
                    System.out.println("Identificador correcto");
                } else
                    System.out.println("Se esperaba identificador");
            }
            if (auxVar == 1) {
                if (Token.equals(":=")) {
                    System.out.println("Asignación correcto");
                } else
                    System.out.println("Se esperaba asignación :=");
            }
            if (auxVar == 2) {
                if (Token.matches(tipoDato)) {
                    System.out.println("Tipo de dato correcto");
                } else
                    System.out.println("Se esperaba tipo de dato");
            }
            if(auxVar == 3){
                if(Token.equals(";")){
                    System.out.println("Separado correcto");
                    asignacionInicio = false;
                }
                else
                    System.out.println("Se esperada separador ;");
            }
        }

        if(invocacionInicio==true) {
            if (auxVar == 0) {//Invocar procedimiento o funcion
                if (Token.matches(Identificador)) {
                    System.out.println("Identificador correcto");
                } else
                    System.out.println("Se esperaba identificador");
            }
            if (auxVar == 1) {
                if (Token.equals("(")) {
                    System.out.println("Parentesis de apertura correcto");
                } else
                    System.out.println("Se esperaba (");
            }
            if (auxVar == 2) {
                if (Token.equals(")")) {
                    System.out.println("Parentesis de cerrado correcto");
                } else
                    System.out.println("Se esperaba )");
            }
            if (auxVar == 3) {
                if(Token.equals(";")){
                    System.out.println("Separado correcto");
                    invocacionInicio = false;
                }
                else
                    System.out.println("Se esperada separador ;");
            }
        }

        auxVar=auxVar+1;
    }


    public void abrirArbol(){
        arbolSintactico arb = new arbolSintactico();
        arb.setArbol();
    }
}
