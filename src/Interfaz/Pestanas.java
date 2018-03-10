package Interfaz;

import javax.swing.JTabbedPane;

/**
 * Clase con un atributo estático (pestanas) que es un objeto de la clase contenedora de Jpanels JTabbePane
 * (las pestañas). Hay que utilizar el objeto pestanas definido en esta clase si no (si de definiera pestanas
 * en la clase principal por ejemplo), las pestañas aparecen “desajustadas” (se ven sus títulos en negrita
 * y descentrados).
 * 
 * @author Pablo Andueza
 * @version 1.0
 */
public class Pestanas {
    
    /**
     * El objeto pestañas que es una instancia de JTabbedPane.
     */
    static JTabbedPane pestanas = new JTabbedPane();    
 
}