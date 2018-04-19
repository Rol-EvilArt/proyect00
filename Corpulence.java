
/**
 * Enumeracion - Tipos de corpulencia.
 *
 * @author d4s1ns
 * @version 2018/04/19
 */
public enum Corpulence {
    HEAVY ("Fuerte"),
    MEDIUM ("Media"),
    THIN ("Débil");
    
    // Tipo de corpulencia (sp).
    private String translate;
    
    /**
     * Constructor - Construye los elementos de la enumeracion especificando el tipo de
     * corpulencia traducido.
     * @param translate El tipo de corpulencia.
     */
    private Corpulence(String translate) {
        this.translate = translate;
    }    
    
    /**
     * Devuelve el tipo de corpulencia.
     * @return Devuelve el tipo de corpulencia.
     */
    public String getName() {
        return translate;
    }
    
}
