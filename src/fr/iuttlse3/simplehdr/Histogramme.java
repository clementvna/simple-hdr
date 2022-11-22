package fr.iuttlse3.simplehdr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Histogramme {

    /**
     * Taille initiale de l'histogramme
     */
    public static final int TAILLE = 257;
    /**
     * Tableau contenant les valeurs de l'histogramme
     */
    private static ArrayList<Double> tableau;

    /**
     * Instancie l'histogramme et initialiser les valeurs à 0
     */
    public static void initialiser() {
        Histogramme.tableau= new ArrayList<>(TAILLE);
        for (int i = 0; i < Histogramme.TAILLE; i++) {
            Histogramme.tableau.add(0.0d);
        }
    }

    /**
     * Retourne la valeur à l'index i
     * @param i Index de la valeur
     * @return double
     */
    public static double get(int i) {
        return Histogramme.tableau.get(i);
    }

    /**
     * Genere l'histogramme de la repartition RGB d'une image
     * @param bi Image dont on crée l'histogramme
     */
    public static void creer(BufferedImage bi) {
        int indice;
        double valeur;
        int largeur = bi.getWidth();
        int hauteur = bi.getHeight();
        Color pixel;

        for (int i = 0; i < Histogramme.TAILLE; i++) {
            Histogramme.tableau.set(i, 0.0d);
        }

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                pixel = new Color(bi.getRGB(x, y));
                indice = OperationImage.echelonner(pixel);
                valeur = Histogramme.tableau.get(indice) + 1.0d;
                Histogramme.tableau.set(indice, valeur);
            }
        }

    }

}
