package sample;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main extends JFrame {

    //INICIA TABLA DE SIMBOLOS
    //Listas para tabla de símbolos
    public ArrayList<String> Linea_Tabla = new ArrayList<>(Arrays.asList());
    public ArrayList<String> Token_Tabla= new ArrayList<>(Arrays.asList());
    public ArrayList<String> Rol_Tabla = new ArrayList<>(Arrays.asList());
    public ArrayList<String> Ambito_Tabla = new ArrayList<>(Arrays.asList());
    public ArrayList<String> valorInicial_Tabla = new ArrayList<>(Arrays.asList());
    public ArrayList<String> valorFinal_Tabla = new ArrayList<>(Arrays.asList());//

    public void Prueba(){
        for (int i = 0; i < 3; i ++) {
            Linea_Tabla.add("Esto es parte de la primera lista "+ Integer.toString(i));
            Token_Tabla.add("Esto es parte de la segunda lista " + Integer.toString(i));
            Rol_Tabla.add("Esto es parte de la tercera lista" + Integer.toString(i));
            Ambito_Tabla.add("Estoe es pargbgbgbgte de la cuarta lista " + Integer.toString(i));
            valorInicial_Tabla.add("Esto es parte de la quinta lista" + Integer.toString(i));
            valorFinal_Tabla.add("Esto es parte de la sexta lista " + Integer.toString(i));
        }
    }
    public Main() {
        super("Ejemplo 3");
        Prueba();
        //creamos el arreglo de objetos que contendra el
        //contenido de las columnas
        Object[] data = new Object[6];
        // creamos el modelo de Tabla
        DefaultTableModel dtm= new DefaultTableModel();
        // se crea la Tabla con el modelo DefaultTableModel
        final JTable table = new JTable(dtm);
        // insertamos las columnas
        //for(int column = 0; column < 5; column++){
        dtm.addColumn("Linea");
        dtm.addColumn("Token");
        dtm.addColumn("Rol");
        dtm.addColumn("Ambito");
        dtm.addColumn("Valor inicial");
        dtm.addColumn("Valor final");
        //}
        // insertamos el contenido de las columnas
        /*for(int row = 0; row < 6; row++) {
            for(int column = 0; column < 6; column++) {
                data[column] = Linea_Tabla.get(0);
            }
            dtm.addRow(data);
        }*/
        for(int row = 0; row < 3; row ++) {
            for (int column = 0; column < 6; column++) {
                if(column == 0)
                    data[column] = Linea_Tabla.get(row);
                else if(column == 1)
                    data[column] = Token_Tabla.get(row);
                     else if(column == 2)
                    data[column] = Rol_Tabla.get(row);
                else if(column == 3)
                    data[column] = Ambito_Tabla.get(row);
                else if(column == 4)
                    data[column] = valorInicial_Tabla.get(row);
                else if(column == 5)
                    data[column] = valorFinal_Tabla.get(row);
            }
            dtm.addRow(data);
        }
        //se define el tamaÒo
        table.setPreferredScrollableViewportSize(new Dimension(900, 700));
        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);
        //Agregamos el JScrollPane al contenedor
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        //manejamos la salida
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }
    public static void main(String[] args) {
        Main frame = new Main();
        frame.pack();
        frame.setVisible(true);
    }
}