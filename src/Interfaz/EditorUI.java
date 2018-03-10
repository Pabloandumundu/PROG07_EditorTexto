package Interfaz;

import java.util.ArrayList;

// Imports de Lectura/Escritura
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Imports de elementos del UI utilizados en esta clase. Los elementos del menu; JMenu, JMenubar y 
// JMenuitems, se usan en la clase MenuSuperior. El DefaultEditorKit de Swing (usado para cortar,
// copiar y pegar) tambien se usa en la clase MenuSuperior. El JTabbedPan se usa en la clase Pestanas.
import java.awt.event.*;
import java.awt.BorderLayout; // Para poder poner que el JTextArea ocupe todo el espacio del JPanel.
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke; // Usado a la hora de añadir Accelerators a las opciones de los menus.

import Otros.*; // Paquete propio

/**
 * Clase principal del programa (Editor de texto con un menú con opciones de Nuevo, Abrir, Cerrar, Guardar, 
 * Guardar Como, Salir y Copiar, Cortar y Pegar. Donde se se pueden editar varios textos en diferentes paneles
 * que se van creando y a los que se accede a través de pestañas), en el constructor se monta el menú y el 
 * espacio para las pestañas. Después se asocian las diferentes opciones del menú a las acciones correspondientes.
 * La clase contiene algunos atributos auxiliares incluidos dos arraylist donde se guardan: los TextArea 
 * correspondientes a cada pestaña (para poder acceder posteriormente a ellos a falta de DOM) y también
 * donde a sido guardado o desde donde se ha abierto el archivo para poder realizar guardado rápido (opción
 * Guardar) o mostrar por defecto la carpeta usada anteriormente para esa pestaña al usar opción Guardar Como.
 * 
 * @author Pablo Andueza
 * @version 1.0
 */
public class EditorUI extends JFrame {
    
    /**
     * Esta variable se utiliza para cuando se crea una nueva pestaña esta tenga en su nombre el número de pestañas actualmente
     * abiertas+1 + el número de pestañas cerradas para que vaya sumando y nunca tenga el mismo nombre que otra pestaña.
     * También se usa para seleccionar el index correcto en los ArrayList usados a la hora de introducir 
     * datos para que este index sea igual al index del JPanel actualmente focalizado.
     */
    static protected int contadorCierres=0;
    /**
     * Variable que suma cada vez que hay una pestaña nueva, se utilizará conjuntamente con contadorCierres
     */
    static protected int indiceJpanel = 0;    
    /**
     * Arraylist donde se guardan los JTexAreas que se van usando, como forma de tenerlos asociados
     * de una manera fácil para poder luego acceder a ellos desde cualquier lugar y método. Su index se
     * mantendrá parejo al index de las pestañas, así conocidendo el index de la pestaña se puede acceder
     * al JTextArea que contiene su panel.
     */
    static ArrayList<JTextArea> arrayTextFields = new ArrayList<>();    
    /**
     * Arrraylist de objetos tipo TextosGuardados; Para cada pestaña se guarda un objeto de este tipo 
     * donde se guarda el último directorio usado para dicha pestaña (ya sea al abrir o al guardar) y la ruta
     * completa del archivo, si es una pestaña nueva la ruta estará en blanco.
     */
    static ArrayList<TextosGuardados> arrayTextosGuardados = new ArrayList<>();
  

    /**
     * Constructor del EditorUI, donde se monta el menú y el JTabbedPanne para las pestañas.
     * Para ello primero se dan unos parámetros generales de la ventan, después se construye el
     * menú usando las variables de clase de MenuSuperior.java (que son en realidad objetos de las clases 
     * JMenuBar, Jmenu y JMenuItem), a los JMenu y los JMenuItem se les añade un Mnemonic (tecla de
     * acceso rápido alt+carácter), a los JMenuItem además se les añaden Acelerator (combinación de teclas
     * para acceso rápido que aparecen en el menú a la derecha de la opción).     * 
     * Por último se construye es espacio para las pestañas añadiendo el objeto pestañas de las clase Pestañas
     */    
    public EditorUI() {       
        
        //Parametros generales asociados a la ventana de la aplicación
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        setTitle("Editor de Texto con Paneles 1.01");
        
        /**
         * Objeto sobre el que se montará el menú superior.
         */
        MenuSuperior menuSuperior = new MenuSuperior();
        
        // Submenu Archivo
        
        menuSuperior.opcionNuevo.setMnemonic('N');
        menuSuperior.opcionNuevo.setAccelerator(KeyStroke.getKeyStroke("control N"));        
        menuSuperior.menuArchivo.add(menuSuperior.opcionNuevo);
        
        menuSuperior.opcionAbrir.setMnemonic('A');
        menuSuperior.opcionAbrir.setAccelerator(KeyStroke.getKeyStroke("control A"));
        menuSuperior.menuArchivo.add(menuSuperior.opcionAbrir);
        
        menuSuperior.opcionCerrar.setMnemonic('C');
        menuSuperior.opcionCerrar.setAccelerator(KeyStroke.getKeyStroke("control R"));
        menuSuperior.menuArchivo.add(menuSuperior.opcionCerrar);
        
        menuSuperior.opcionGuardar.setMnemonic('G');
        menuSuperior.opcionGuardar.setAccelerator(KeyStroke.getKeyStroke("control S"));
        menuSuperior.menuArchivo.add(menuSuperior.opcionGuardar);
        
        menuSuperior.opcionGuardarComo.setMnemonic('U');
        menuSuperior.opcionGuardarComo.setAccelerator(KeyStroke.getKeyStroke("control M"));
        menuSuperior.menuArchivo.add(menuSuperior.opcionGuardarComo);
        
        menuSuperior.opcionSalir.setMnemonic('S');  // no le añado Accelerator por seguridad
        menuSuperior.menuArchivo.add(menuSuperior.opcionSalir);
        
        // Submenu Editar
        
        menuSuperior.opcionCortar.setText("Cortar");
        menuSuperior.opcionCortar.setMnemonic('O');
        menuSuperior.opcionCortar.setAccelerator(KeyStroke.getKeyStroke("control X"));
        menuSuperior.menuEditar.add(menuSuperior.opcionCortar);
        
        menuSuperior.opcionCopiar.setText("Copiar");
        menuSuperior.opcionCopiar.setMnemonic('C');
        menuSuperior.opcionCopiar.setAccelerator(KeyStroke.getKeyStroke("control C"));
        menuSuperior.menuEditar.add(menuSuperior.opcionCopiar);
        
        menuSuperior.opcionPegar.setText("Pegar");
        menuSuperior.opcionPegar.setMnemonic('P');
        menuSuperior.opcionPegar.setAccelerator(KeyStroke.getKeyStroke("control V"));
        menuSuperior.menuEditar.add(menuSuperior.opcionPegar);        
        
        // Añadir los submenus a la barra (el menú)
        
        menuSuperior.menuArchivo.setMnemonic('A');
        menuSuperior.barra.add(menuSuperior.menuArchivo);
        menuSuperior.menuEditar.setMnemonic('E');
        menuSuperior.barra.add(menuSuperior.menuEditar);         
        
        // Establecer la barra
        
        setJMenuBar(menuSuperior.barra); 
               
        /**
         * Se monta el espacio para las pestañas.
         */
        getContentPane().add(Pestanas.pestanas);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método principal, se construye el entorno UI llamando al constructor de la clase.
     * Después se pone a la escucha en cada una de las opciones del menú y define las acciones para
     * cada una de ellas.
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        try{
            /**
             * Objeto ventana perteneciente a está clase (EditorUI) y utiliza su constructor.
             * En el se monta todo el UI.
             */
            EditorUI ventana = new EditorUI();
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido construir el UI");
        }
        
        // Al inicializar no se podra guardar ni cerrar pestaña pues no hay nada abierto.
        MenuSuperior menuSuperior = new MenuSuperior();
        menuSuperior.opcionGuardar.setEnabled(false);
        menuSuperior.opcionGuardarComo.setEnabled(false);
        menuSuperior.opcionCerrar.setEnabled(false);
        // Tampoco se puede copiar,cortar o pegar.
        menuSuperior.menuEditar.setEnabled(false);
        
        
        /**
         * Escucha y acción para la opción Nuevo del menú Archivo, se crea un Panel (pestaña) al que se le 
         * añade un JtextArea. El título que aparece en la pestaña será único y no se repetirá. Se asocia el
         * JTextArea a un nuevo elemento en arrayTextFields y se asocia la pestaña (con los datos en blanco) al
         * arrayTextosGuardados.
         */
        menuSuperior.opcionNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // Creamos el panel y lo añadimos a las pestañas.
                JPanel panel=new JPanel();
                panel.setLayout(new BorderLayout()); //para que el JTextArea ocupe todo el espacio del JPanel
                JTextArea textArea = new JTextArea();
                panel.add(textArea);               

                // Añadimos el panel con el nombre del archivo.
                Pestanas.pestanas.addTab("Nuevo Documento " + (Pestanas.pestanas.getTabCount()+1 + contadorCierres), panel);
                
                // Se activan las opciones de menú desabilitadas.               
                MenuSuperior menuSuperior = new MenuSuperior();
                menuSuperior.opcionGuardar.setEnabled(true);
                menuSuperior.opcionGuardarComo.setEnabled(true);
                menuSuperior.opcionCerrar.setEnabled(true);
                menuSuperior.menuEditar.setEnabled(true);               
                
                // Hace que aparezca seleccionada la última pestaña quese ha creado.
                Pestanas.pestanas.setSelectedIndex(indiceJpanel-contadorCierres);
                
                // Se añade el textArea al arraylist de TexAreas para poder acceder a este textArea desde
                // otros metodos en otros momentos.
                arrayTextFields.add(indiceJpanel-contadorCierres, textArea);
                
                // Se añade un objeto tipo TextosGuardados al array de TextoGuardados para que
                // tenga ya su posición en el indice del array (que es igual al indice de las pestañas).
                TextosGuardados textoguardado = new TextosGuardados();
                arrayTextosGuardados.add(indiceJpanel-contadorCierres, textoguardado);
                
                indiceJpanel++;
                
            }
            
        });
        
        /**
         * Escucha y acción para la opción Abrir del menú Archivo, se lanza el dialogo de abrir archivo, se lee
         * el contenido del archivo seleccionado y se mete en el JTextaera del panel (pestaña) que se crea.
         * Se asocia el JTextArea a un nuevo elemento en arrayTextFields y se asocia la pestaña (con la ruta del
         * archivo) al arrayTextosGuardados.
         */       
        menuSuperior.opcionAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                
                /**
                 * Objeto que contiene los elementos de los diálogos para abrir/guardar archivo.
                 */
                JFileChooser chooser = new JFileChooser();
                /**
                 * Se guarda la opción (no el archivo) seleccionada en el dialogo de abrir archivo; es decir
                 * Abrir o Cancelar.
                 */
                int result = chooser.showOpenDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION) { // Si se le da a Aceptar.
                    
                    // Creamos el panel y lo añadimos a las pestañas.
                    JPanel panel=new JPanel();
                    panel.setLayout(new BorderLayout()); //para que el JTextArea ocupe todo el espacio del JPanel
                    JTextArea textArea = new JTextArea();
                    panel.add(textArea);                

                    // Añadimos el panel con un nombre (el nombre del fichero abierto).
                    Pestanas.pestanas.addTab(chooser.getSelectedFile().getName(), panel);

                    // Se carga el contenido del archivo en el textArea.
                    try {
                        /**
                         * Variables temporales para los objetos de tipo FileReader y BufferedReader
                         */
                        FileReader reader = new FileReader(chooser.getSelectedFile().getAbsolutePath());
                        BufferedReader br = new BufferedReader(reader);
                        
                        textArea.read( br, null );
                        br.close();
                        textArea.requestFocus();
                    }
                    catch (IOException e2) {System.out.println("ERROR: Algún tipo de error ha sucedido al tratar de leer el archvio");}
                    catch (Exception e2) { System.out.println(e2);}

                    // Se activan las opciones de menú desabilitadas.               
                    MenuSuperior menuSuperior = new MenuSuperior();
                    menuSuperior.opcionGuardar.setEnabled(true);
                    menuSuperior.opcionGuardarComo.setEnabled(true);
                    menuSuperior.opcionCerrar.setEnabled(true);
                    menuSuperior.menuEditar.setEnabled(true);
                    
                    // Hace que aparezca seleccionada la última pestaña quese ha creado.
                    Pestanas.pestanas.setSelectedIndex(indiceJpanel-contadorCierres);
                    
                    // Se añade el textArea al arraylist de TexAreas para poder acceder a este textArea desde
                    // otros metodos en otros momentos.
                    arrayTextFields.add(indiceJpanel-contadorCierres, textArea);

                    /**
                     * Objeto tipo TextosGuardados para añadir al arrayTextosGuardados que asociará el 
                     * contenido al archivo usado y su directorio.
                     */
                    TextosGuardados textoguardado = new TextosGuardados();
                    
                    textoguardado.setPathcompleto(chooser.getSelectedFile().getAbsolutePath());
                    textoguardado.setPath(chooser.getCurrentDirectory());                
                    arrayTextosGuardados.add(indiceJpanel-contadorCierres, textoguardado);

                    indiceJpanel++;
                    
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Se ha seleccionado Cancel");
                }

            } 

        });
        
        /**
         * Escucha y acción para la opción Cerrar del menú Archivo, antes de cerrar la propia pestaña
         * se quitan de arrayTextFields y arrayTextosGuardados  los elementos con el mismo índice que la
         * pestaña, después se cerrará la pestaña y se comprueba si era la última pestaña en cuyo caso
         * se desactivarian algunas opciones del menú.
         */ 
        menuSuperior.opcionCerrar.addActionListener(new ActionListener() {                       
            @Override
            public void actionPerformed(ActionEvent e) {

                /**
                 * El índice de la pestaña a cerrar.
                 */
                int pestanaSeleccionada = Pestanas.pestanas.getSelectedIndex();
                
                // Se quita los elementos de los arrayList utilizados (los que esten en el mismo indice
                // que la pestaña).
                arrayTextFields.remove(pestanaSeleccionada);
                arrayTextosGuardados.remove(pestanaSeleccionada);
                // Se quita la pestaña en sí.
                Pestanas.pestanas.removeTabAt(pestanaSeleccionada);
                
                contadorCierres++;
                
                /**
                 * Si el número de pestañas vuelve a ser 0 se vuelven a desactivar algunas opciones del menú.
                 */
                if (Pestanas.pestanas.getTabCount() == 0){
                    MenuSuperior menuSuperior = new MenuSuperior();
                    menuSuperior.opcionGuardar.setEnabled(false);
                    menuSuperior.opcionGuardarComo.setEnabled(false);
                    menuSuperior.opcionCerrar.setEnabled(false);
                    menuSuperior.menuEditar.setEnabled(false);
                }
           
            }
            
        });      
        
        /**
         * Escucha y acción para la opción Guardar del menú Archivo, se comprueba si la pestaña ya tiene un archivo
         * asociado (por haber abierto desde uno o haber guardado en uno previamente) si es así se guardará 
         * utilizando la ruta asocidada (disponible desde arrayTextosGuardados) si no es así se lanzará el dialogo
         * de selección de archivo a guardar (igual que cuando se elije "Guardar Como").
         */
        menuSuperior.opcionGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                /**
                 * El TextArea seleccionado
                 */
                JTextArea textAreaSeleccionada = arrayTextFields.get(Pestanas.pestanas.getSelectedIndex());

                /**
                 * Se comprueba primero si el contenido de la pestaña seleccionada ha sido guardado previamente o 
                 * ha sido cargado desde un archivo (mirando si tiene una ruta asociada), si esto es así entonces se
                 * guardará directamente sin preguntar nada. Si no, se guardará preguntando ruta y nombre de archivo.
                 * 
                */                
                if (arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).getPathcompleto().equals("")){ 
                    // si no tiene path asociado
                    
                    /**
                     * Objeto que contiene los elementos de los diálogos para abrir/guardar archivo.
                     */
                    JFileChooser chooser = new JFileChooser();
                    /**
                     * Se guarda la opción (Abrir o Cancelar) seleccionada en el dialogo de guardar archivo. 
                     */
                    int result = chooser.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                    
                        try {
                            
                            /**
                             * Variables temporales para los objetos de tipo FileWriter y BufferedWritter.
                             */
                            FileWriter writer = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
                            BufferedWriter bw = new BufferedWriter( writer );
                            
                            textAreaSeleccionada.write( bw );
                            bw.close();
                            textAreaSeleccionada.requestFocus();

                            // Se pone como título del panel el nombre del archivo guardado.
                            Pestanas.pestanas.setTitleAt(Pestanas.pestanas.getSelectedIndex(), chooser.getSelectedFile().getName());

                            // Se asocian los paths del fichero guardado al textarea de la pestaña seleccionada.
                            arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).setPath(chooser.getCurrentDirectory());
                            arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).setPathcompleto(chooser.getSelectedFile().getAbsolutePath());
                        } 
                        catch (IOException e2) {System.out.println("ERROR: Algún tipo de error ha sucedido al tratar de guardar el archvio");}
                        catch (Exception e2) { System.out.println(e2);}
                    
                    } else if (result == JFileChooser.CANCEL_OPTION) {
                        System.out.println("Se ha seleccionado Cancel");
                    }                    
                    
                } else { // Si ya tenia un path asociado (se habia guardado o se habia cargado desde un archivo).
                    
                    try {
                        
                        /**
                         * Variables temporales para los objetos de tipo FileWriter y BufferedWritter.
                         */
                        FileWriter writer = new FileWriter(arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).getPathcompleto());
                        BufferedWriter bw = new BufferedWriter( writer );
                        
                        textAreaSeleccionada.write( bw );
                        bw.close();
                        textAreaSeleccionada.requestFocus();                       

                    }
                    catch (IOException e2) {System.out.println("ERROR: Algún tipo de error ha sucedido al tratar de guardar el archvio");}
                    catch (Exception e2) { System.out.println(e2);}                    
                    
                }          
               
            }            
            
        });
        
        /**
         * Escucha y acción para la opción Guardar Como del menú Archivo, lanzará el dialogo de selección de 
         * archivo (donde se sugiere inicialmente una ruta y nombre de archivo previamente usados si es que
         * los tenia), tras guardar se pondrá como título de la pestaña el nombre del archivo y se guardarán
         * el path completo y el directorio del archivo guardado en el arrayTextosGuardados.
         */        
        menuSuperior.opcionGuardarComo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){             

                /**
                 * El TextArea seleccionado
                 */
                JTextArea textAreaSeleccionada = arrayTextFields.get(Pestanas.pestanas.getSelectedIndex());
                /**
                 * El JFileChoose (dialogo para seleccionar archivo)
                 */
                JFileChooser chooser = new JFileChooser();                
                
                // Se sugiere inicialmente que el archivo a guardar tenga el mismo nombre que el de la pestaña
                // siempre que no sea "Nuevo Documento ..." en tal caso se deja en blanco.
                if (!(Pestanas.pestanas.getTitleAt(Pestanas.pestanas.getSelectedIndex()).startsWith("Nuevo Documento "))){
                    chooser.setSelectedFile(new File(Pestanas.pestanas.getTitleAt(Pestanas.pestanas.getSelectedIndex())));
                }
                // Se sugiere un directorio si es que esta pestaña ya ha usado uno (al cargar o al guardar)
                if (!arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).getPathcompleto().equals("")){
                    chooser.setCurrentDirectory(arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).getPath());
                }
                
                /**
                 * Se guarda la opción (Abrir o Cancelar) seleccionada en el dialogo de guardar archivo.
                 */
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                
                    try
                    {
                        /**
                         * Variables temporales para los objetos de tipo FileWriter y BufferedWritter.
                         */
                        FileWriter writer = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
                        BufferedWriter bw = new BufferedWriter( writer );
                        
                        textAreaSeleccionada.write( bw );
                        bw.close();
                        textAreaSeleccionada.requestFocus();

                        // Se pone como título del panel el nombre del archivo guardado
                        Pestanas.pestanas.setTitleAt(Pestanas.pestanas.getSelectedIndex(), chooser.getSelectedFile().getName());

                        // Se asocian los paths del fichero guardado al textarea de la pestaña seleccionada
                        arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).setPath(chooser.getCurrentDirectory());
                        arrayTextosGuardados.get(Pestanas.pestanas.getSelectedIndex()).setPathcompleto(chooser.getSelectedFile().getAbsolutePath());

                    }

                    catch(Exception e2) {}
                    
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Se ha seleccionado Cancel");
                }
                
            }
            
        });
        
        /**
         * Escucha y acción para la opción Salir del menú Archivo, que simplemente cierra la aplicación con
         * un System.exit(0).
         */
        menuSuperior.opcionSalir.addActionListener(new ActionListener() {                       
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
