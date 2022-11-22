package fr.iuttlse3.simplehdr;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Gamma {

    /**
     * Valeur Gamma par défaut
     */
    public final static double DEFAUT = 1.0d;

    /**
     * Valeur Gamma
     */
    private static double valeur = Gamma.DEFAUT;

    /**
     * Retourne la valeur du gamma adaptée à l'affichage
     * @return double
     */
    public static double get() {
        return Gamma.valeur;
    }

    /**
     * Définit la valeur du gamma
     * @param g Valeur du gamma
     */
    public static void set(double g) {
        Gamma.valeur = g / 10.0d;
    }

    /**
     * Retourne une image dont le gamma est corrigé
     * @param bi Image à corriger
     * @return BufferedImage
     */
    public static BufferedImage corriger(BufferedImage bi) {
        int[] gamma = new int[256];
        BufferedImage image = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        for (int i = 0; i < gamma.length; i++)
            gamma[i] = (int)(255 * Math.pow((double)i / 255, 1 / Gamma.valeur));
        for (int x = 0; x < bi.getWidth(); x++)
            for (int y = 0; y < bi.getHeight(); y++) {
                int p = 0;
                p += new Color(bi.getRGB(x, y)).getAlpha();
                p <<= 8;
                p += gamma[new Color(bi.getRGB(x, y)).getRed()];
                p <<= 8;
                p += gamma[new Color(bi.getRGB(x, y)).getGreen()];
                p <<= 8;
                p += gamma[new Color(bi.getRGB(x, y)).getBlue()];
                image.setRGB(x, y, p);
            }
        return image;
    }

}