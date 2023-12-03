package cypher;

/**
 * Xifra i desxifra missatges utilitzant l'algorisme de desplaçament
 *
 * @author ProvenSoft
 */
public class XifratCesar {

    /**
     * xifra el missatge amb l'algorisme de desplaçament
     *
     * @param missatge el missatge a xifrar
     * @param desp el desplaçament a aplicar
     * @return el missatge xifrat
     */
    public static String xifrarCesar(String missatge, int desp) {
        String result = "";
        final String lletres = "abcdefghijklmnopqrstuvwxyz1234567890 ,;:";
        missatge = missatge.toLowerCase();
        for (int i = 0; i < missatge.length(); i++) {
            char c1 = missatge.charAt(i);
            int index1 = lletres.indexOf(c1);
            int index2 = (index1 + desp)%lletres.length();
            char c2 = lletres.charAt(index2);
            result += c2;
        }
        return result;
    }

    /**
     * desxifra el missatge amb l'algorisme de desplaçament
     *
     * @param missatge el missatge a desxifrar
     * @param desp el desplaçament a aplicar
     * @return el missatge desxifrat
     */
    public static String desxifrarCesar(String missatge, int desp) {
        String result = "";
        final String lletres = "abcdefghijklmnopqrstuvwxyz1234567890 ,;:";
        missatge = missatge.toLowerCase();
        for (int i = 0; i < missatge.length(); i++) {
            char c1 = missatge.charAt(i);
            int index1 = lletres.indexOf(c1);
            int index2 = (index1 - desp)%lletres.length();
            if (index2 < 0) {
                index2 += lletres.length();
            }
            char c2 = lletres.charAt(index2);
            result += c2;
        }
        return result;
    }
}
