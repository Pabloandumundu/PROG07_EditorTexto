package Interfaz;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.text.DefaultEditorKit; // Usado para añadir funcionalidad de copiar,cortar y pegar.

/**
 * Clase con atributos estáticos (que son objetos) que se corresponden con elementos del menú superior
 * (JMenubar, JMenus y JMenuitems).
 * Todo el menú se construye sobre una instancia de está clase (un objeto cualquiera) si no, no funciona.
 * Y como elementos internos de ese menú se utilizan los objetos definidos aquí, si no, aparecen desajustados. 
 * 
 * @author Pablo Andueza
 * @version 1.0
 */
public class MenuSuperior {
    
    /**
     * La barra de menú (objeto de la clase JMenuBar).
     */
    static JMenuBar barra = new JMenuBar();
    
    /**
     * El menú Archivo (JMenu) y sus Items (JMenuItems). 
     * Se podía definir el Mneotic de los JMenuItems (pero no del JMenu) después del titulo tras una coma
     * pero por homogenizar las declaraciones se definen los Mneotic (junto con los accelerator) en la clase
     * principal.
     */
    static JMenu menuArchivo = new JMenu("Archivo");    
    static JMenuItem opcionNuevo = new JMenuItem("Nuevo");
    static JMenuItem opcionAbrir = new JMenuItem("Abrir");
    static JMenuItem opcionCerrar = new JMenuItem("Cerrar");
    static JMenuItem opcionGuardar = new JMenuItem("Guardar");
    static JMenuItem opcionGuardarComo = new JMenuItem("Guardar Como");
    static JMenuItem opcionSalir = new JMenuItem("Salir");
    
    /**
     * El menú Editar (JMenu) y sus Items (JMenuItems).
     * Para la funcionalidad de copiar, cortar y pegar a los menuitems simplemente se les asocia las
     * funcionalidades ya definidas en DefaultEditorKit.
     */
    static JMenu menuEditar = new JMenu("Editar");    
    static JMenuItem opcionCortar = new JMenuItem(new DefaultEditorKit.CutAction());   
    static JMenuItem opcionCopiar = new JMenuItem(new DefaultEditorKit.CopyAction());
    static JMenuItem opcionPegar = new JMenuItem(new DefaultEditorKit.PasteAction());
    
}
