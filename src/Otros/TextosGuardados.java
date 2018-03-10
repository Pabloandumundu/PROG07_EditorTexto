package Otros;

import java.io.File;

/**
 * Clase para instanciar cada vez que se crea o se abre un pestaña, con dos variables que guardan el path y el 
 * path completo del archivo correspondiente si se diera el caso; cuando se abre una nueva pestaña no
 * se guarda ningún path solo se crea un objeto (para acompasar el index de esta clase con el index de las 
 * pestañas), si se abre un archivo se creará un objeto que incluirá los dos paths correspondientes al archivo 
 * añadidos, por último cuando se guarda un archivo se mira en el index (mismo index de pestañas que esta clase)
 * se selecciona la instancia de TextosGuardados correspondiente y se le introducen los paths correspondientes al
 * archivo guardado.
 * 
 * Se accederá a las dos variables mediante getters y setters.
 * 
 * @author Pablo Andueza
 * @version 1.0
 * 
 */
public class TextosGuardados {
    
    /**
     * Los atributos de la clase son:
     * pathcompleto: El path completo del archivo (chooser.getSelectedFile().getAbsolutePath()) tipo String.
     * path: El path de archivo (chooser.getCurrentDirectory()) tipo File.
     */
    private String pathcompleto = "";
    private File path;
    
    /**
     * @return <code>String</code> El path completo.
     */
    public String getPathcompleto() {
        return pathcompleto;
    }

    /**
     * @param pathcompleto: El path completo.
     */
    public void setPathcompleto(String pathcompleto) {
        this.pathcompleto = pathcompleto;
    }
    
   /**
     * @return <code>File</code> El path (el directorio).
     */
    public File getPath() {
        return path;
    }

    /**
     * @param path: El path (el directorio).
     */
    public void setPath(File path) {
        this.path = path;
    }   
    
}
