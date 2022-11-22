package fr.iuttlse3.test;

import fr.iuttlse3.simplehdr.Gamma;
import fr.iuttlse3.simplehdr.Histogramme;
import fr.iuttlse3.simplehdr.OperationImage;
import fr.iuttlse3.simplehdr.Stockage;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;


public class UnitTests {

    /**
     * Suite de tests pour la classe Gamma.java
     */
    @Test
    public void testDefaultGammaValue() {
        org.junit.Assert.assertTrue(Gamma.DEFAUT == 1.0d);
    }

    @Test
    public void testGetSetGammaValue() {
        Gamma.set(2.0d);
        org.junit.Assert.assertTrue(Gamma.get() == 0.2d);
    }

    /**
     * Suite de tests pour la classe Stockage.java
     */
    @Test
    public void testAddGetStockage() {
        BufferedImage sousExposee = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage normaleExposee = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage surExposee = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Stockage.ajouter(sousExposee);
        org.junit.Assert.assertEquals(Stockage.getSousExposee(), sousExposee);
        Stockage.ajouter(normaleExposee);
        org.junit.Assert.assertEquals(Stockage.getNormaleExposee(), normaleExposee);
        Stockage.ajouter(surExposee);
        org.junit.Assert.assertEquals(Stockage.getSurExposee(), surExposee);
    }

    @Test
    public void testValideStockage() {
        BufferedImage sousExposee = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage normaleExposee = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        BufferedImage surExposee = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Stockage.ajouter(sousExposee);
        Stockage.ajouter(normaleExposee);
        Stockage.ajouter(surExposee);
        org.junit.Assert.assertTrue(Stockage.valide());
    }

    /**
     * Suite de tests pour la classe OperationImage.java
     */
    @Test
    public void testEchelonnerImage() {
        org.junit.Assert.assertEquals(OperationImage.echelonner(Color.BLACK), 0);
        org.junit.Assert.assertEquals(OperationImage.echelonner(Color.WHITE), 256);
    }

    @Test
    public void testFormaterImage() {
        BufferedImage image = OperationImage.formater(
                new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB), 400, 300);
        org.junit.Assert.assertTrue(image.getWidth() == 400);
        org.junit.Assert.assertTrue(image.getHeight() == 300);
    }

    @Test
    public void testGetSetRangeValues() {
        OperationImage.setRange(10, 20);
        org.junit.Assert.assertTrue(OperationImage.getMin() == 10);
        org.junit.Assert.assertTrue(OperationImage.getMax() == 20);
    }

    /**
     * Suite de tests pour la classe Histogramme.java
     */
    @Test
    public void testInitialiserGetHistogramme() {
        Histogramme.initialiser();
        for (int i = 0; i < 257; i++) {
            org.junit.Assert.assertTrue(Histogramme.get(i) == 0.0d);
        }
    }

}
