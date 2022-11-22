package fr.iuttlse3.simplehdr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Stockage {

    /**
     * Liste stockant l'image sous-exposée, l'image normale, l'image sur-exposée et l'image HDR générée
     */
    private static ArrayList<BufferedImage> images;

    /**
     * Initialise le stockage des images à "null"
     */
    public static void initialiser() {
        Stockage.images = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            Stockage.images.add(i, null);
        }
    }

    /**
     * Ajouter une image
     * @param bi Image à ajouter
     */
    public static void ajouter(BufferedImage bi) {
        if (Stockage.images.size() == 4) {
            Stockage.images.set(3, bi);
        } else {
            Stockage.images.add(bi);
        }
    }

    /**
     * Verifie si le stockage d'images est vide ou non
     *
     * @return boolean
     */
    public static boolean vide() {
        return Stockage.images.isEmpty();
    }

    /**
     * Verifie qu'il existe trois images valides
     * @return boolean
     */
    public static boolean valide() {
        if (Stockage.getSousExposee() != null && Stockage.getNormaleExposee() != null &&
                Stockage.getSurExposee() != null) {
            if (Stockage.getSousExposee().getWidth() == Stockage.getNormaleExposee().getWidth() &&
                    Stockage.getSurExposee().getWidth() == Stockage.getNormaleExposee().getWidth()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne l'image sous-exposée
     * @return BufferedImage
     */
    public static BufferedImage getSousExposee() {
        return Stockage.images.get(0);
    }

    /**
     * Modifier l'image sous-exposée
     *
     * @param bi Nouvelle image
     */
    public static void setSousExposee(BufferedImage bi) {
        Stockage.images.set(0, bi);
    }

    /**
     * Retourne l'image noramelement exposée
     * @return BufferedImage
     */
    public static BufferedImage getNormaleExposee() {
        return Stockage.images.get(1);
    }

    /**
     * Modifie l'image normale
     * @param bi Nouvelle image
     */
    public static void setNormaleExposee(BufferedImage bi) {
        Stockage.images.set(1, bi);
    }

    /**
     * Retourne l'image sur-exposée
     * @return BufferedImage
     */
    public static BufferedImage getSurExposee() {
        return Stockage.images.get(2);
    }

    /**
     * Modifie l'image sur-exposée
     * @param bi Nouvelle image
     */
    public static void setSurExposee(BufferedImage bi) {
        Stockage.images.set(2, bi);
    }

    /**
     * Retourne l'image HDR
     * @return BufferedImage
     */
    public static BufferedImage getHDR() {
        return Stockage.images.get(3);
    }

}
