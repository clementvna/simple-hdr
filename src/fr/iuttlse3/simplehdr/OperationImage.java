package fr.iuttlse3.simplehdr;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public abstract class OperationImage {

    /**
     * Valeur minimale de la sélection RGB
     */
    private static int min = 85;

    /**
     * Valeur maximale de la sélection RGB
     */
    private static int max = 170;

    /**
     * Définit le minimum et le maximum de la sélection RGB
     * @param min Valeur minimale
     * @param max Valeur maximale
     */
    public static void setRange(int min, int max) {
        OperationImage.min = min;
        OperationImage.max = max;
    }

    /**
     * Renvoie la valeur minimale de la sélection RGB
     * @return int
     */
    public static int getMin() {
        return OperationImage.min;
    }

    /**
     * Renvoie la valeur maximale de la sélection RGB
     * @return int
     */
    public static int getMax() {
        return OperationImage.max;
    }

    /**
     * Etalonne une couleur RGB de 0 à 256
     * @param c Couleur RGB à étalonner
     * @return int
     */
    public static int echelonner(Color c) {
        return c.getRed() / 65536 + c.getGreen() / 255 + c.getBlue();
    }

    /**
     * Affiche les miniatures des images
     * @param bi Image à formater
     * @param l Largeur de l'image
     * @param h Hauteur de l'image
     * @return BufferedImage
     */
    public static BufferedImage formater(BufferedImage bi, int l, int h) {
        int largeur = bi.getWidth();
        int hauteur = bi.getHeight();
        double ratio;
        if (largeur < hauteur) {
            ratio = (double)l / largeur;
        } else {
            ratio = (double)h / hauteur;
        }
        largeur = (int)(largeur * ratio);
        hauteur = (int)(hauteur * ratio);
        BufferedImage miniature = new BufferedImage(largeur, hauteur, bi.getType());
        new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio),
                AffineTransformOp.TYPE_BICUBIC).filter(bi, miniature);
        return miniature;
    }

    //TODO Améliorer la fonction afin qu'elle traite les reflets correctement
    /**
     * Genere une image HDR a partir des trois images a expositions differentes
     */
    public static void hdr() {
        int largeur = Stockage.getNormaleExposee().getWidth();
        int hauteur = Stockage.getNormaleExposee().getHeight();
        BufferedImage image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
        Color pixel;
        Histogramme.initialiser();
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                pixel = new Color(Stockage.getNormaleExposee().getRGB(x, y));
                if (pixel.getRed() <= min && pixel.getGreen() <= min && pixel.getBlue() <= min) {
                    image.setRGB(x, y, Stockage.getSurExposee().getRGB(x, y));
                } else if (pixel.getRed() > min && pixel.getRed() < max && pixel.getGreen() > min &&
                        pixel.getGreen() < max && pixel.getBlue() > min && pixel.getBlue() < max) {
                    image.setRGB(x, y, Stockage.getSousExposee().getRGB(x, y));
                } else {
                    image.setRGB(x, y, Stockage.getNormaleExposee().getRGB(x, y));
                }
            }
        }
        Stockage.ajouter(Gamma.corriger(image));
        Histogramme.creer(image);
    }

}
