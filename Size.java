import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Representa el tamano de una criatura del mundo del juego.
 * 
 * Pertenece al proyecto 'the-witcher'.
 * 
 * El tamano de una criatura esta compuesto por la altura(cm), el peso(kg), el valor
 * del tamano y su corpulencia. 
 * 
 * La corpulencia de las criaturas puede ser de varios tipos:
 * la corpulencia por defecto es Media. Las criaturas por debajo de la media tienen 
 * corpulencia Debil y, las que estan por encima, Fuerte.
 * 
 * Una criatura puede tener un corpulencia fuera de la media de su tipo. Debido a lo
 * inusual, este hecho se calcula de forma aleatoria y, solo se puede comprobar una vez.
 * 
 * Las criaturas con corpulencias extraordinarias sufren modificaciones a sus
 * caracteristicas.
 *
 * @author d4s1ns
 * @version 2018/04/19
 */
public class Size {
    // Fichero con la tabla de alturas humanas.
    private static final String HUMAN_HEIGHTS= "data/human/size/height.data";
    // Fichero con la tabla de alturas humanas.
    private static final String HUMAN_WEIGHTS= "data/human/size/weight.data";
    
    // Corpulencia de la criatura.
    private Corpulence corpulence;
    // VERDADERO - Se ha modificado el tamano inicial. FALSO - No se ha modificado.
    private boolean grown;
    // Altura de la criatura (cm).
    private int height;
    // Datos asociados a la altura.
    private String[] heightData;
    // Modificador de destreza.
    private int modDEX;
    // Modificador de constitucion.
    private int modCON;
    // Tamano de la criatura.
    private int size;
    // Altura de la criatura (kg).
    private int weight;
    // Datos asociados al peso.
    private String[] weightData;
    
    /**
     * Constructor - Construye objetos tamano a partir del valor del tamano y una cadena
     * de texto con uno de los posibles valores para la corpulencia 
     * (HEAVY, MEDIUM, THIN).
     * @param size Valor del tamano.
     * @param corpulence Cadena de texto con uno de los valores para la corpulencia 
     *        (HEAVY, MEDIUM, THIN).
     */
    public Size(int size, String corpulence) {
        this.size = size;
        grown = (size <= 3);
        this.corpulence = Corpulence.valueOf(corpulence);
        String sSize = String.valueOf(size);
        heightData = searchOnFile(HUMAN_HEIGHTS, sSize);
        weightData = searchOnFile(HUMAN_WEIGHTS, sSize);
        setHeight();
        setWeight();
        modCON = 0;
        modDEX = 0;
    }
    
    /**
     * Determina el indice inicial para el minimo valor de peso asociado al tipo de
     * corpulencia actual.
     * @return Devuelve el indice inicial para el minimo valor de peso asociado al
     * tipo de corpulencia actual.
     */
    private int chooseWeightIndex() {
        int refund = 0;
        switch (corpulence) {
            case HEAVY: 
                refund = 5;
                break;
            case MEDIUM:
                refund = 3;
                break;
            case THIN:
                refund = 1;
                break;
        }
        return refund;
    }
    
    /**
     * Trata de dotar de una corpulencia excepcional a una criatura de corpulencia 
     * fuerte.
     * Si existe o no una modificacion se establece de forma aleatoria. Los cambios
     * pueden tener repercursion sobre los atributos de la criatura.
     */
    private void growHeavy() {
        grown = true;
        int roll = new Roll(1,10).getTotal();
        if (roll >= 6 && roll <= 8) {
            weight = minWeight() - new Roll(1, 20).getTotal();
            modDEX = 1;
        }
        else if (roll == 9 || roll == 10) {
            weight = maxWeight() - new Roll(2, 20).getTotal();
            modCON = 1;
            modDEX = -2;
        }
    }
    
    /**
     * Trata de dotar de una corpulencia excepcional a una criatura de corpulencia 
     * media.
     * Si existe o no una modificacion se establece de forma aleatoria. Los cambios
     * pueden tener repercursion sobre los atributos de la criatura.
     */
    private void growMedium() {
        int roll = new Roll(1,10).getTotal();
        if (roll >= 5 && roll <= 7 ) {
            weight = maxWeight() + new Roll(1, 20).getTotal();
            modCON = -1;
            modDEX = 1;
        }
        else if (roll >= 8 && roll <= 10) {
            weight = minWeight() - new Roll(1, 20).getTotal();
            modCON = 1;
            modDEX = -1;
        }
    }
    
    /**
     * Trata de dotar de una corpulencia excepcional a una criatura de corpulencia 
     * debil.
     * Si existe o no una modificacion se establece de forma aleatoria. Los cambios
     * pueden tener repercursion sobre los atributos de la criatura.
     */
    private void growThin() {
        int roll = new Roll(1,6).getTotal();
        if (roll == 4 || roll == 5 ) {
            weight = maxWeight() + new Roll(1, 10).getTotal();
            modCON = 1;
        }
        else if (roll == 6) {
            weight = maxWeight() + new Roll(1, 20).getTotal();
            modCON = 2;
            modDEX = -1;
        }
    }
    
    /**
     * Devuelve el peso maximo para el tipo de corpulencia actual.
     * @return Devuelve el peso maximo para el tipo de corpulencia actual.
     */
    private int maxWeight() {
        return Integer.parseInt(weightData[chooseWeightIndex() + 1]);
    }
    
    /**
     * Devuelve el peso minimo para el tipo de corpulencia actual.
     * @return Devuelve el peso minimo para el tipo de corpulencia actual.
     */
    private int minWeight() {
        return Integer.parseInt(weightData[chooseWeightIndex()]);
    }
    
    /**
     * Devuelve un numero aleatorio entre los dos pasados por parametros
     * (ambos inclusive).
     * @param n1 El primer numero (debe ser el mayor).
     * @param n2 El segundo numero ( debe ser igual o menor que el primero).
     * @return Devuelve un numero aleatorio entre los dos pasados por parametros
     *         (ambos inclusive).
     */
    private int randomBetween(int n1, int n2) { 
        return new Random().nextInt(n1 - n2) + n2;
    }
    
    /**
     * Busca el valor indicado en la primera columna del fichero indicado y devuelve una
     * coleccion de cadenas de texto con todos los valores de la linea.
     * 
     * Cada valor debe estar separado por tabulaciones de los anteriores.
     * 
     * @param file Ruta absoluta o relativa al fichero (incluyendo la extension).
     * @param searched Valor buscado en la primera columna del fichero.
     * @return Devuelve una coleccion de cadenas de texto con todos los valores de la 
     *         linea donde se ha encontrado el valor buscado. Si el valor no ha sido
     *         encontrado, devuelve null.
     */
    private String[] searchOnFile(String file, String searched) {
        String[] refund= null;
         try {
            File archivo = new File(file);
            Scanner sc = new Scanner(archivo);
            boolean searching = true;
            while (sc.hasNextLine() && searching) {
                String[] line = sc.nextLine().split("\t");
                if (searched.equals(line[0])) {
                    refund = line;
                    searching = false;
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return refund;
    }
    
    /**
     * Establece una altura aleatoria para la criatura en funcion al tamano.
     * Requiere de un fichero externo.
     */
    private void setHeight() {
        int min = Integer.parseInt(heightData[1]);
        int max = Integer.parseInt(heightData[2]);
        height = randomBetween(max, min);
    }
    
    /**
     * Establece un peso aleatorio en funcion al tamano y la corpulencia.
     * Requiere de un fichero externo.
     */
    private void setWeight() {
        int index = chooseWeightIndex();
        int min = Integer.parseInt(weightData[index]);
        int max = Integer.parseInt(weightData[index + 1]);
        weight = randomBetween(max, min);
    }
    
    /**
     * Devuelve VERDADERO si se ha tratado de modificar el peso del personaje fuera
     * de los limites por tamano o, FALSO en caso contrario.
     * @return Devuelve VERDADERO si se ha tratado de modificar el peso del personaje 
     *         fuera de los limites por tamano o, FALSO en caso contrario.
     */
    public boolean isGrown() {
        return grown;
    }
    
    /**
     * Devuelve la altura de la criatura (cm).
     * @return Devuelve la altura de la criatura (cm).
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Devuelve el peso de la criatura (kg).
     * @return Devuelve el peso de la criatura (kg).
     */
    public int getWeight() {
        return weight;
    }
    
    /**
     * Devuelve el tamano de la criatura.
     * @return Devuelve el tamano de la criatura.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Intenta dotar de un tamano excepcional a la criatura.
     * Solo se puede intentar una vez, el resultado varia dependiendo del tipo de 
     * corpulencia.
     */
    public void grow() {
        if (!grown) {
            grown = true;
            switch (corpulence) {
                case HEAVY:
                    growHeavy();
                    break;
                case MEDIUM:
                    growMedium();
                    break;
                case THIN:
                    growThin();
                    break;
            }
        }
    }
    
    /**
     * Modifica la corpulencia de la criatura.
     * @param corpulence Nuevo valor de la corpulencia.
     */
    public void setCorpulence(String corpulence) {
        this.corpulence = Corpulence.valueOf(corpulence);
        setWeight();
    }
    
    /**
     * Establece la altura indicada por parametro para la criatura.
     * Requiere un fichero externo. Si la altura indicada no esta dentro de los valores
     * permitidos no hace nada.
     * @param height Nueva altura para la criatura.
     */
    public void setHeight(int height) {
        int min = Integer.parseInt(heightData[1]);
        int max = Integer.parseInt(heightData[2]);
        if (height >= min && height <= max) {
            this.height = height;
        }
    }
    
    /**
     * Establece el peso indicado por parametro para la criatura.
     * Requiere un fichero externo. Si el peso indicado no esta dentro de los valores
     * permitidos no hace nada.
     * @param weight Nuevo peso para la criatura.
     */
    public void setWeight(int weight) {
        int index = chooseWeightIndex();
        int min = Integer.parseInt(weightData[index]);
        int max = Integer.parseInt(weightData[index + 1]);
        if (weight >= min && weight <= max) {
            this.weight = weight;
        }
    }
    
    /**
     * Devuelve toda la informacion sobre el tamano de la criatura como una cadena de 
     * texto.
     * @return Devuelve toda la informacion sobre el tamano de la criatura como una cadena de 
     * texto.
     */
    public String toString() {
        String refund = "Tamaño : " + size + "\t";
        refund += "Corpulencia: " + corpulence.getName() + "\n";
        refund += "Altura: " + height + "cm\tPeso: " + weight + "kg";
        return refund;
    }
    
}