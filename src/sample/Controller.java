package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable{
    @FXML
    CodeArea codeArea;
    @FXML
    TextArea txtMensajes;
    @FXML
    ListView lsErrores;

    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public void initialize(URL location, ResourceBundle resources){
        colorPalabras_Reservadas();
        contadorLineas();
        inicializaErrores_Prueba();
    }

    File archivo;

    public void cargarArchivo() throws IOException{
        FileChooser fileChooser = new FileChooser();            //Nos deja seleccionar un archivo a cargar
        fileChooser.setTitle("Seleccione un archivo para cargar");
        archivo = fileChooser.showOpenDialog(new Stage());//Obtenemos ruta

        if(archivo != null) {
            FileInputStream stream = new FileInputStream(archivo);
            //Declaramos el encoding de entrada
            BufferedReader entrada = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));

            String linea = null;
            while ((linea = entrada.readLine()) != null) {//Lee archivo y lo pone en el textArea
                codeArea.appendText(linea + "\n");
            }
            entrada.close();

            //txtArea.appendText(rutaArchivo);
            txtMensajes.setText("");
            txtMensajes.setText("Archivo cargado con éxito");
        }
        else {
            txtMensajes.setText("");
            txtMensajes.setText("Error de carga en archivo");
        }

        /*
        codeArea.appendText("Hola\n");
        codeArea.appendText("Mundo\n");
        codeArea.appendText("Gitano priemra segunda tercera cuarta");
        */
    }

    String contenidoArchivo = new String();
    public void guardarArchivo () throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo");

        //Extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("RTF files (*.rtf)", "*.rtf");
        fileChooser.getExtensionFilters().addAll(extFilter,extFilter2);

        //Muestra dialogo
        archivo = fileChooser.showSaveDialog(new Stage());

        contenidoArchivo = (String) codeArea.getText();//Obtenemos contenido del textArea y la pasamosa string

        if(archivo != null) {
            SaveFile(contenidoArchivo,archivo);
            txtMensajes.setText("");
            txtMensajes.setText("Archivo guardado con éxito");
        }
        else {
            txtMensajes.setText("");
            txtMensajes.setText("Error en guardado de archivo");
        }

    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardarRapido () { // Guarda el archivo si previamente fue guardado como. No pregunta
        if(contenidoArchivo.equals("")) {
            txtMensajes.setText("");
            txtMensajes.setText("Primero ocupa guardar como");
        }
        else {
            String contenido = (String) codeArea.getText();
            SaveFile(contenido, archivo);
            txtMensajes.setText("");
            txtMensajes.setText("Archivo guardado rápido con éxito");
        }
    }

    public void contadorLineas(){
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
    }

    public void colorPalabras_Reservadas(){
        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public void inicializaErrores_Prueba(){
        ObservableList<Integer> items = FXCollections.observableArrayList(1,2,3,4);
        lsErrores.setItems(items);
    }

    public void manejoErrores(){
        int valor = lsErrores.getSelectionModel().getSelectedIndex();
        //Marcado de linea
        codeArea.moveTo(valor,0);//Primer digito comenzando de 0 indica linea, segundo indica columna
        codeArea.selectLine();//Resalta toda la linea
        //System.out.println(codeArea.getText(2));//Obtenemos el texto de la linea 3 con el getText. SOLO PRUEBA
    }

    public void abrirLexico() throws IOException{
        Stage primaryStage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Lexico.fxml"));
        root.getStylesheets().add(Controller.class.getResource("java-keywords.css").toExternalForm());//Importamos el css a usar
        primaryStage.setTitle("Compilador Manual - Traductores II");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }
}
