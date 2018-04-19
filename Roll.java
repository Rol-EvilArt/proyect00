import java.util.ArrayList;
import java.util.Random;

/**
 * Tirada de dados.
 * 
 * Pertence a la aplicacion 'the-witcher'.
 *
 * @author d4s1ns
 * @version 2018/04/19
 */
public class Roll {
    // Generador de numeros aleatorios.
    Random randomizer;
    // Resultados de cada dado.
    ArrayList<Integer> results;
    // Resultado de la tirada.
    int thrownResult;
    
    /**
     * Constructor - Construye e inicializa el lanzador de dados indicando, el numero de
     * dados a lanzar y el numero de caras de dichos dados.
     * @param numOfDice Numero de dados a lanzar.
     * @param numOfFaces Numero de caras de cada dado.
     */
    public Roll(int numOfDice, int numOfFaces) {
        randomizer = new Random();
        results = new ArrayList<>();
        thrownResult = 0;
        roll(numOfDice, numOfFaces);
    }
    
    /**
     * Simula el lanzamiento de 'x' dados de 'y' caras y devuelve la suma de 
     * sus resultados.
     * @param numOfDice Numero de dados a lanzar.
     * @param numOfFaces Numero de caras de cada lado.
     * @return Devuelve la suma de los resultados obtenidos al lanzar 'x' dados de 'y' 
     *         caras.
     */
    private void roll(int numOfDice, int numOfFaces) {
        int total = 0;
        // Lanzamos todos los dados que se nos indican.
        for (int i = 0; i < numOfDice; i++) {
            results.add(randomizer.nextInt(numOfFaces) + 1);
        }
        // Sumanos los resultados de cada dado.
        for (int result : results) {
            total += result;
        }
        thrownResult = total;
    }
    
    /**
     * Devuelve el resultado total de la tirada.
     * @return Devuelve el resultado total de la tirada.
     */
    public int getTotal() {
        return thrownResult;
    }
}
