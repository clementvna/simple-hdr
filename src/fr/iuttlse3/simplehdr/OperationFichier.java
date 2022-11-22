package fr.iuttlse3.simplehdr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public abstract class OperationFichier {

    /**
     * Ajoute une image
     * @param f Fichier contenant l'image a ajouter
     */
    public static void ajouter(File f) {
        try {
            BufferedImage image = ImageIO.read(f);
            Stockage.ajouter(image);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Modifie le fichier de l'image sous-exposée
     * @param f Nouveau fichier
     */
    public static void setSousExposee(File f) {
        try {
            BufferedImage image = ImageIO.read(f);
            Stockage.setSousExposee(image);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Modifie le fichier de l'image normalement exposée
     * @param f Nouveau fichier
     */
    public static void setNormaleExposee(File f) {
        try {
            BufferedImage image = ImageIO.read(f);
            Stockage.setNormaleExposee(image);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Modifie le fichier de l'image sur-exposée
     * @param f Nouveau fichier
     */
    public static void setSurExposee(File f) {
        try {
            BufferedImage image = ImageIO.read(f);
            Stockage.setSurExposee(image);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Enregistre l'image HDR generee dans un nouveau fichier
     * @param fi Fichier de destination
     * @param fo Format du fichier de destination
     */
    public static void enregistrer(File fi, String fo) {
        try {
            ImageIO.write(Stockage.getHDR(), fo, fi);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

}
