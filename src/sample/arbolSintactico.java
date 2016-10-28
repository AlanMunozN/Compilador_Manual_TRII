package sample;

import javax.swing.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kaleb on 10/9/2016.
 */
public class arbolSintactico extends JFrame{

    Sintactico sin = new Sintactico();

    ArrayList<Object> nodos = new ArrayList<>(Arrays.asList());

    public arbolSintactico()
    {
        super("Arbol");
    }


    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    public void prueba(){


        graph.getModel().beginUpdate();
        try
        {
            //Nodos obligatorios
            Object v1 = graph.insertVertex(parent, null, "programa", 400, 0, 80, 30);
            Object v2 = graph.insertVertex(parent, null, "<Libreria>", 350, 100, 80, 30);
            Object v3 = graph.insertVertex(parent, null, "var", 450, 100, 80, 30);
            Object v4 = graph.insertVertex(parent, null, "inicio", 550, 100, 80, 30);

            //nodos.add(v1); nodos.add(v2); nodos.add(v3); nodos.add(v4);//Insertamos los nodos

            //Unimos nodos principales
            graph.insertEdge(parent, null, "", v1, v2);
            graph.insertEdge(parent, null, "", v1, v3);
            graph.insertEdge(parent, null, "", v1, v4);

            for(int i=0;i<sin.nombre_Nodo.size();i++){//Creación de nodos
                Object generico = graph.insertVertex(parent, null, sin.nombre_Nodo.get(i), sin.coordenada_X.get(i), sin.coodernada_Y.get(i), 80, 30);
                nodos.add(generico);
            }

            int auxLibreria=0,auxVar=0,auxInicio=0;
            boolean pri=false,sec=false,ter=false,progPuesto=false;

            for(int i=0;i<sin.nombre_Nodo.size();i++){//Unir general

                //Object aux =  nodos.get(i);

                if(sin.programaEncontrado==true && progPuesto==false){
                    graph.insertEdge(parent, null, "", v1, nodos.get(i));//liberia
                    progPuesto=true;
                }

                if(sin.encontradaLibreria==true && auxLibreria<sin.hijosLibreria || pri==true && sec==false && ter==false){
                    pri=true;
                    if(auxLibreria==0) {
                        System.out.println("Hijo 1");
                        Object Children = nodos.get(i);
                        graph.insertEdge(parent, null, "", v2, Children);//liberia
                        auxLibreria++;
                    }
                    else if(auxLibreria==1){
                        System.out.println("Hijo 2");
                        Object Children = nodos.get(i);
                        graph.insertEdge(parent, null, "", v2, Children);//nombre
                        auxLibreria++;

                    }
                }
                if(sin.encontradaVar==true && auxVar<sin.hijosVar && pri==false || sec==true && ter==false){
                    sec=true;
                    if(auxVar==0) {
                        graph.insertEdge(parent, null, "", v3, nodos.get(i));//Primer elemento de var
                        auxVar++;
                    }
                    else {
                        graph.insertEdge(parent, null, "", nodos.get(i-1), nodos.get(i));//Elementos
                        auxVar++;
                    }
                }
                if(sin.encontradaInicio==true && auxInicio<sin.hijosInicio && pri==false && sec==false || ter==true){
                    ter=true;
                    if(auxVar==0) {
                        graph.insertEdge(parent, null, "", v4, nodos.get(i));//Primer elemento de inicio
                        auxInicio++;
                    }
                    else {
                        graph.insertEdge(parent, null, "", nodos.get(i-1), nodos.get(i));//Elementos
                        auxInicio++;
                    }
                }
            }

            /* Añadimos del arreglo según la posición de un for. Esto para unir
            graph.insertEdge(parent, null, "Edge", nodos.get(0), v2);//Unir nodos
            */
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public void setArbol(){
        arbolSintactico arbo = new arbolSintactico();
        arbo.prueba();
        arbo.setSize(800,600);
        arbo.setVisible(true);
        arbo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Solo cierra la ventana del arbol
    }

    //Hacer un método y mandarlo a llamar
    /*
    public static void main(String[] args)
    {
        arbolSintactico frame = new arbolSintactico();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }
    */
}
