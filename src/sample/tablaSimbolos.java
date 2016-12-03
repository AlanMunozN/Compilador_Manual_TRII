package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ckmu32 on 12/1/16.
 */
public class tablaSimbolos extends JFrame{

    Sintactico snt;

    public tablaSimbolos() {
        super("Tabla de símbolos");
        //Prueba();
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
        for(int row = 0; row < snt.Token_Tabla.size(); row ++) {
            for (int column = 0; column < 4; column++) {//Va 6
                if(column == 0)
                    data[column] = snt.Linea_Tabla.get(row);
                else if(column == 1)
                    data[column] = snt.Token_Tabla.get(row);
                else if(column == 2)
                    data[column] = snt.Rol_Tabla.get(row);
                else if(column == 3)
                    data[column] = snt.Ambito_Tabla.get(row);
                //else if(column == 4)
                  //  data[column] = snt.valorInicial_Tabla.get(row);
                //else if(column == 5)
                  //  data[column] = snt.valorFinal_Tabla.get(row);
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}
