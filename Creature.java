import java.util.ArrayList;
import java.util.Random;

/**
 * Representa una criatura del mundo del juego.
 * 
 * Pertenece a la aplicacion 'the-witcher'.
 * 
 * 
 *
 * @author d4s1ns
 * @version 2018/04/19
 */
public class Creature {
    // Valor fijo a sumar a la tirada de generacion de atributos.
    private final static int GATT_BASE = 6;
    // Numero de dados a tirar para generar un atributo.
    private final static int GATT_NDICES = 2;
    // Numero de caras de los dados a tirar para generar un atributo.
    private final static int GATT_NFACES = 6;
    
    // Nombre del personaje.
    private String name;
    // Raza del personaje.
    private String race;
    // ATRIBUTOS PRINCIPALES DEL PERSONAJE.
    private int strength;           // Fuerza.
    private int constitution;       // Constitucion.
    private int dextery;            // Destreza.
    private int intelligence;       // Inteligencia.
    private int power;              // Poder.
    private int charisma;           // Carisma.
    // Tamano de la criatura.
    private Size size;
/*    
111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999
*/
    /**
     * Constructor -
     */
    public Creature(String name) {
        this.name = name;
        setAttributes();
        int size = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        this.size = new Size(size, "MEDIUM");
    }
    
    /**
     * Simula el lanzamiento de 'x' dados de 'y' caras y devuelve la suma de 
     * sus resultados.
     * @param numOfDice Numero de dados a lanzar.
     * @param numOfFaces Numero de caras de cada lado.
     * @return Devuelve la suma de los resultados obtenidos al lanzar 'x' dados de 'y' 
     *         caras.
     */
    public int roll(int numOfDice, int numOfFaces) {
        Random dicer = new Random();
        ArrayList<Integer> dices = new ArrayList<>();
        int thrownResult = 0;
        // Lanzamos todos los dados que se nos indican.
        for (int i = 0; i < numOfDice; i++) {
            dices.add(dicer.nextInt(numOfFaces) + 1);
        }
        // Sumanos sus resultados.
        for (int result : dices) {
            thrownResult += result;
        }
        return thrownResult;
    }
    
    /**
     * Genera la puntuacion de los atributos de la criatura.
     */
    public void setAttributes() {
        strength = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        dextery = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        constitution = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        intelligence = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        power = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        charisma = roll(GATT_NDICES, GATT_NFACES) + GATT_BASE;
        
    }
    
    /**
     * Devuelve toda la informacion sobre el personaje como una cadena de texto.
     * @return Devuelve toda la informacion sobre el personaje como una cadena de texto.
     */
    @Override
    public String toString() {
        String refund = "Nombre: " + name + "\n";
        refund += size;
        refund += "Atributos:\n";
        refund += "\tFuerza: " + strength + "\n";
        refund += "\tDestreza: " + dextery + "\n";
        refund += "\tConstitucion: " + constitution + "\n";
        refund += "\tPoder: " + power + "\n";
        refund += "\tInteligencia: " + intelligence + "\n";
        refund += "\tCarisma: " + charisma + "\n";
        return refund;
    }
    
    /**
     * Muestra por la terminal de texto toda la informacion de la criatura.
     */
    public void show() {
        System.out.println(this);
    }
    
    /**
     * 
     */
    public void setCorpulence(String corpulence) {
        size.setCorpulence(corpulence);
    }
}
