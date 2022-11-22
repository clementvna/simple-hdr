package fr.iuttlse3.simplehdr;

import com.jidesoft.swing.RangeSlider;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class Interface extends JFrame implements ActionListener, ChangeListener {

    private final JPanel panneauContenu;

    private final JButton boutonParcourir1 = new JButton("Parcourir");
    private final JButton boutonParcourir2 = new JButton("Parcourir");
    private final JButton boutonParcourir3 = new JButton("Parcourir");
    private final JButton boutonZoom = new JButton("Agrandir l'histogramme");
    private final JButton boutonGenerer = new JButton("Générer l'image en HDR");
    private final JButton boutonEnregistrer = new JButton("Enregistrer image");

    private final JDesktopPane bureau = new JDesktopPane();

    private final JLabel miniatureSousExposee = new JLabel();
    private final JLabel libelleImageSousExposee = new JLabel("Image sous-exposée");
    private final JLabel miniatureNormale = new JLabel();
    private final JLabel libelleImageNormale = new JLabel("Image à exposition normale");
    private final JLabel miniatureSurExposee = new JLabel();
    private final JLabel libelleImageSurExposee = new JLabel("Image sur-exposée");
    private final JLabel libelleImageHdr = new JLabel("Image en HDR");
    private final JLabel zoneAffichageHdr = new JLabel();
    private final JLabel libelleGamma = new JLabel("Correction Gamma : 1.0");
    private final JLabel libelleSelectionRgb = new JLabel("Sélection RGB : 85-170");
    private final JLabel libelleConfiguration = new JLabel("Paramètres");

    private final JMenu menuFichier = new JMenu();
    private final JMenu menuConfiguration = new JMenu();
    private final JMenu menuAPropros = new JMenu();

    private final JMenuBar barreMenu = new JMenuBar();

    private final JMenuItem itemOuvrirImageNormale = new JMenuItem("Ouvrir la photo avec exposition normale");
    private final JMenuItem itemOuvrirImageSousExposee = new JMenuItem("Ouvrir la photo sous-exposée");
    private final JMenuItem itemOuvrirImageSurExposee = new JMenuItem("Ouvrir la photo sur-exposée");
    private final JMenuItem itemEnregistrerHdr = new JMenuItem("Enregistrer la photo HDR");
    private final JMenuItem itemChangerCouleur = new JMenuItem("Couleur de l'interface");
    private final JMenuItem itemAPropos = new JMenuItem("À propos de SimpleHDR");
    private final JMenuItem itemAide = new JMenuItem("Aide");

    private final JSeparator separateur = new JSeparator();

    private final JSlider selecteurGamma = new JSlider();

    private final RangeSlider selecteurRgb = new RangeSlider(0, 255);

    private JFreeChart histogramme;

    private ChartPanel zoneHistogramme;

    public Interface() {
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                Interface.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
        setFont(new Font("Century Gothic", Font.PLAIN, 14));
        setTitle("SimpleHDR");
        setForeground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 882, 651);
        panneauContenu = new JPanel();
        panneauContenu.setBackground(Color.WHITE);
        panneauContenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panneauContenu);
        panneauContenu.setLayout(null);

        bureau.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        bureau.setBackground(Color.WHITE);
        bureau.setBounds(0, 0, 219, 601);
        panneauContenu.add(bureau);

        libelleGamma.setFont(new Font("Tahoma", Font.PLAIN, 14));
        libelleGamma.setHorizontalAlignment(SwingConstants.CENTER);
        libelleGamma.setBounds(10, 120, 200, 26);
        bureau.add(libelleGamma);

        selecteurGamma.setMaximum(30);
        selecteurGamma.setMinimum(0);
        selecteurGamma.setValue(10) ;
        selecteurGamma.setBounds(10, 150, 200, 26);
        Gamma.set(selecteurGamma.getValue());
        bureau.add(selecteurGamma);
        selecteurGamma.addChangeListener(this);

        boutonGenerer.addActionListener(this);
        boutonGenerer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        boutonGenerer.setBounds(35, 200, 145, 41);
        bureau.add(boutonGenerer);

        boutonEnregistrer.addActionListener(this);
        boutonEnregistrer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        boutonEnregistrer.setBounds(35, 500, 145, 41);
        bureau.add(boutonEnregistrer);
        boutonEnregistrer.setVisible(false);

        menuFichier.setFont(new Font("Tahoma", Font.PLAIN, 13));
        menuFichier.setBounds(0, 0, 92, 20);
        menuFichier.setHorizontalAlignment(SwingConstants.LEFT);

        itemOuvrirImageSousExposee.addActionListener(this);
        itemOuvrirImageSousExposee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuFichier.add(itemOuvrirImageSousExposee);

        itemOuvrirImageNormale.addActionListener(this);
        itemOuvrirImageNormale.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuFichier.add(itemOuvrirImageNormale);

        itemOuvrirImageSurExposee.addActionListener(this);
        itemOuvrirImageSurExposee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuFichier.add(itemOuvrirImageSurExposee);

        itemEnregistrerHdr.addActionListener(this);
        itemEnregistrerHdr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuFichier.add(itemEnregistrerHdr);

        menuConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 13));
        menuConfiguration.setBounds(0, 0, 92, 20);
        menuConfiguration.setHorizontalAlignment(SwingConstants.LEFT);

        itemChangerCouleur.addActionListener(this);
        itemChangerCouleur.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuConfiguration.add(itemChangerCouleur);

        menuAPropros.setFont(new Font("Tahoma", Font.PLAIN, 13));
        menuAPropros.setBounds(102, 0, 57, 20);

        itemAide.addActionListener(this);
        itemAide.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuAPropros.add(itemAide);

        itemAPropos.addActionListener(this);
        itemAPropos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        menuAPropros.add(itemAPropos);

        setJMenuBar(barreMenu);
        barreMenu.add(menuFichier);
        menuFichier.setText("Fichier");
        barreMenu.add(menuConfiguration);
        menuConfiguration.setText("Configuration");
        barreMenu.add(menuAPropros);
        menuAPropros.setText("?");

        separateur.setForeground(Color.LIGHT_GRAY);
        separateur.setBounds(231, 157, 645, 2);
        panneauContenu.add(separateur);

        miniatureSousExposee.setBounds(252, 11, 162, 121);
        panneauContenu.add(miniatureSousExposee);
        miniatureSousExposee.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        miniatureSousExposee.setBackground(Color.WHITE);
        miniatureSousExposee.setLayout(null);

        libelleImageSousExposee.setFont(new Font("Tahoma", Font.PLAIN, 11));
        libelleImageSousExposee.setHorizontalAlignment(SwingConstants.CENTER);
        libelleImageSousExposee.setBounds(10, 38, 142, 14);
        miniatureSousExposee.add(libelleImageSousExposee);

        boutonParcourir1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        boutonParcourir1.addActionListener(this);
        boutonParcourir1.setBounds(38, 74, 89, 23);
        miniatureSousExposee.add(boutonParcourir1);

        miniatureNormale.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        miniatureNormale.setBackground(Color.WHITE);
        miniatureNormale.setBounds(469, 11, 162, 121);
        panneauContenu.add(miniatureNormale);
        miniatureNormale.setLayout(null);

        libelleImageNormale.setFont(new Font("Tahoma", Font.PLAIN, 11));
        libelleImageNormale.setHorizontalAlignment(SwingConstants.CENTER);
        libelleImageNormale.setBounds(0, 38, 162, 14);
        miniatureNormale.add(libelleImageNormale);

        boutonParcourir2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        boutonParcourir2.addActionListener(this);
        boutonParcourir2.setBounds(38, 74, 89, 23);
        miniatureNormale.add(boutonParcourir2);

        miniatureSurExposee.setLayout(null);
        miniatureSurExposee.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        miniatureSurExposee.setBackground(Color.WHITE);
        miniatureSurExposee.setBounds(692, 11, 162, 121);
        panneauContenu.add(miniatureSurExposee);

        libelleImageSurExposee.setFont(new Font("Tahoma", Font.PLAIN, 11));
        libelleImageSurExposee.setHorizontalAlignment(SwingConstants.CENTER);
        libelleImageSurExposee.setBounds(0, 38, 162, 14);
        miniatureSurExposee.add(libelleImageSurExposee);

        boutonParcourir3.setFont(new Font("Tahoma", Font.PLAIN, 11));
        boutonParcourir3.addActionListener(this);
        boutonParcourir3.setBounds(38, 74, 89, 23);
        miniatureSurExposee.add(boutonParcourir3);

        zoneAffichageHdr.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        zoneAffichageHdr.setBounds(237, 180, 622, 396);
        panneauContenu.add(zoneAffichageHdr);
        zoneAffichageHdr.setBackground(Color.WHITE);
        zoneAffichageHdr.setLayout(null);

        libelleImageHdr.setHorizontalAlignment(SwingConstants.CENTER);
        libelleImageHdr.setBounds(80, 140, 470, 92);
        libelleImageHdr.setFont(new Font("Script MT Bold", Font.PLAIN, 30));
        zoneAffichageHdr.add(libelleImageHdr);

        libelleSelectionRgb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        libelleSelectionRgb.setHorizontalAlignment(SwingConstants.CENTER);
        libelleSelectionRgb.setBounds(10, 50, 200, 26);
        bureau.add(libelleSelectionRgb);

        selecteurRgb.setBackground(new Color(255, 255, 255));
        selecteurRgb.setLowValue(85);
        selecteurRgb.setHighValue(170);
        selecteurRgb.setMaximum(255);
        selecteurRgb.setBounds(10, 80, 200, 26);
        bureau.add(selecteurRgb);
        selecteurRgb.addChangeListener(this);

        libelleConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 20));
        libelleConfiguration.setHorizontalAlignment(SwingConstants.CENTER);
        libelleConfiguration.setBounds(10, 5, 200, 40);
        bureau.add(libelleConfiguration);

        boutonZoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
        boutonZoom.addActionListener(this);
        boutonZoom.setBounds(5, 430, 200, 25);
        bureau.add(boutonZoom);
        boutonZoom.setVisible(false);

        Stockage.initialiser();

        setVisible(true);
    }

    public void actionPerformed (ActionEvent click) {
        if (click.getSource().equals(boutonParcourir1) || click.getSource().equals(itemOuvrirImageSousExposee)) {
            JFileChooser fichierImageSousExposee = new JFileChooser();
            fichierImageSousExposee.setFileFilter(new FileNameExtensionFilter("Images", "bmp", "gif", "jpg", "jpeg",
                    "png"));
            if (fichierImageSousExposee.showOpenDialog(Interface.this) == JFileChooser.APPROVE_OPTION) {
                if (isPicture(new File(fichierImageSousExposee.getSelectedFile().getAbsolutePath()))) {
                    if (Stockage.vide()) {
                        OperationFichier.ajouter(new File(fichierImageSousExposee.getSelectedFile().getAbsolutePath()));
                    } else {
                        OperationFichier.setSousExposee(
                                new File(fichierImageSousExposee.getSelectedFile().getAbsolutePath()));
                    }
                    miniatureSousExposee.setIcon(new ImageIcon(OperationImage.formater(Stockage.getSousExposee(),
                            162, 121)));
                    boutonParcourir1.setVisible(false);
                    libelleImageSousExposee.setVisible(false);
                }
            }
        }

        if (click.getSource().equals(boutonParcourir2) || click.getSource().equals(itemOuvrirImageNormale)) {
            JFileChooser fichierImageNormale = new JFileChooser();
            fichierImageNormale.setFileFilter(new FileNameExtensionFilter("Images", "bmp", "gif", "jpg", "jpeg",
                    "png"));
            if (fichierImageNormale.showOpenDialog(Interface.this) == JFileChooser.APPROVE_OPTION) {
                if (isPicture(new File(fichierImageNormale.getSelectedFile().getAbsolutePath())) ) {
                    if (Stockage.vide()) {
                        OperationFichier.ajouter(new File(fichierImageNormale.getSelectedFile().getAbsolutePath()));
                    } else {
                        OperationFichier.setNormaleExposee(
                                new File(fichierImageNormale.getSelectedFile().getAbsolutePath()));
                    }
                    miniatureNormale.setIcon(new ImageIcon(OperationImage.formater(Stockage.getNormaleExposee(),
                            162, 121)));
                    boutonParcourir2.setVisible(false);
                    libelleImageNormale.setVisible(false);
                }
            }
        }

        if (click.getSource().equals(boutonParcourir3) || click.getSource().equals(itemOuvrirImageSurExposee)) {
            JFileChooser fichierImageSurExposee = new JFileChooser();
            fichierImageSurExposee.setFileFilter(new FileNameExtensionFilter("Images", "bmp", "gif", "jpg", "jpeg",
                    "png"));
            if (fichierImageSurExposee.showOpenDialog(Interface.this) == JFileChooser.APPROVE_OPTION) {
                if (isPicture(new File(fichierImageSurExposee.getSelectedFile().getAbsolutePath()))) {
                    if (Stockage.vide()) {
                        OperationFichier.ajouter(new File(fichierImageSurExposee.getSelectedFile().getAbsolutePath()));
                    } else {
                        OperationFichier.setSurExposee(
                                new File(fichierImageSurExposee.getSelectedFile().getAbsolutePath()));
                    }
                    miniatureSurExposee.setIcon(new ImageIcon(OperationImage.formater(Stockage.getSurExposee(),
                            162, 121)));
                    boutonParcourir3.setVisible(false);
                    libelleImageSurExposee.setVisible(false);
                }
            }
        }

        if (click.getSource().equals(boutonGenerer)) {
            if (Stockage.valide()) {
                OperationImage.hdr();
                BufferedImage icon = OperationImage.formater(Stockage.getHDR(), 622, 396);
                zoneAffichageHdr.setBounds(237, 180, icon.getWidth(), icon.getHeight());
                zoneAffichageHdr.setIcon(new ImageIcon(icon));
                libelleImageHdr.setVisible(false);
                boutonEnregistrer.setVisible(true);
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                for (int i = 0; i < Histogramme.TAILLE; i++) {
                    dataset.addValue(Histogramme.get(i), "Couleur (0 à 255)", "" + i);
                }

                histogramme = ChartFactory.createBarChart("Histogramme de l'image HDR", "", "", dataset,
                        PlotOrientation.VERTICAL, false, false, false);
                zoneHistogramme = new ChartPanel(histogramme);
                zoneHistogramme.setBounds(5, 270, 210, 140);
                bureau.add(zoneHistogramme);
                boutonZoom.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,"Trois images de même dimensions sont nécessaires pour générer une image HDR.","Génération"
                                + "HDR impossible",JOptionPane.WARNING_MESSAGE);
            }
        }

        if (click.getSource().equals(boutonEnregistrer) || click.getSource().equals(itemEnregistrerHdr)) {
            if (Stockage.getHDR() != null) {
                String[] formats = {"jpg", "bmp"};
                int rang = JOptionPane.showOptionDialog(this, "Choisissez le format de l'image : ","Enregistrement HDR",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, formats, formats[1]);
                if (rang != -1) {
                    JFileChooser fileEnregistrerImage = new JFileChooser();
                    if (fileEnregistrerImage.showSaveDialog(Interface.this) == JFileChooser.APPROVE_OPTION) {
                        File fichierEnregistrement = new File(
                                fileEnregistrerImage.getSelectedFile().getAbsolutePath()+"." + formats[rang]);
                        OperationFichier.enregistrer(fichierEnregistrement, formats[rang]);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,"Générez l'image HDR pour pouvoir l'enregistrer !", "Enregistrement"
                        + " impossible", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (click.getSource().equals(boutonZoom)) {
            JFrame h = new JFrame();
            h.setBounds(10, 10, 700, 500);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            h.add(zoneHistogramme);
            h.setVisible(true);
            h.setResizable(false);
        }

        if (click.getSource().equals(itemAPropos)) {
            JOptionPane.showMessageDialog(this,
                    "<html>" +
                            "<center>" +
                            "Propriétaire : Mr. Carrara " +
                            "<br/><br/>" +
                            "<hr/><br/> Réalisé par 4 étudiants :" +
                            "<br/><br/>" +
                            "- BURZAVA Raphael -" +
                            "<br/>" +
                            "- VILLANUEVA Clément -" +
                            "<br/>" +
                            "- PAINEAU Alan -" +
                            "<br/>" +
                            "-CALVIERE Paul -" +
                            "<br/><br/>" +
                            "<hr/><br/>" +
                            "Conçu dans le cadre d'un projet tuteuré de 2nd année d'IUT Informatique." +
                            "<br/>" +
                            "Université Paul Sabatier (Toulouse) 2014 - 2015" +
                            "</center>" +
                            "</html>"
                    ,"À propos", JOptionPane.DEFAULT_OPTION);
        }

        if (click.getSource().equals(itemAide)) {
            JOptionPane.showMessageDialog(this,
                    "<html>" +
                            "<center>" +
                            "<h1>Paramétrage de l'image en HDR</h1>" +
                            "</center>" +
                            "<br/><hr/>" +
                            "<h2>La correction Gamma :</h2>" +
                            "La correction Gamma vous permet d'appliquer un filtre qui corrigera la " +
                            "luminosité de l'image." +
                            "<br/>" +
                            "Le Gamma prend une valeur entre 0 et 3 (du plus sombre vers le plus lumineux)." +
                            "<br/>" +
                            "Pour n'effectuer aucune correction, garder la valeur par défaut qui est de 1.0." +
                            "<br/><br/><hr/>" +
                            "<h2>La sélection RGB :</h2>" +
                            "Ce paramètre prend un intervalle de valeurs entre 0 et 255 qui correspond aux" +
                            "différentes couleurs de l'image." +
                            "<br/>" +
                            "La valeur 0 correspond au noir et celle de 255 au blanc. Modifiez ce paramètre pour " +
                            "optimiser la qualité de l'image générée." +
                            "<br/>" +
                            "Sélectionnez les zones de couleur que vous souhaitez préserver." +
                            "<br/><br/>" +
                            "<hr><h2>Histogramme de valeurs :</h2>" +
                            "L'histogramme affiche le nombre de pixels (en ordonnées) en fonction de la couleur" +
                            "(en abscisses)." +
                            "<br/><br/><br/>" +
                            "</html>"
                    , "Aide - Simple HDR", JOptionPane.DEFAULT_OPTION);
        }

        if (click.getSource().equals(itemChangerCouleur)) {
            String[] fond = {"Blanc", "Gris", "Noir"};
            int clr = JOptionPane.showOptionDialog(this, "Choisissez la couleur de l'interface : ", "Configuration de" +
                    "l'Interface", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, fond, fond[0]);
            Color couleur = Color.WHITE;
            if (clr == 1) {
                couleur = Color.GRAY;
            } else if (clr == 2) {
                couleur = Color.BLACK;
                panneauContenu.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                miniatureSousExposee.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                miniatureNormale.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                miniatureSurExposee.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                zoneAffichageHdr.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                libelleImageSousExposee.setForeground(Color.WHITE);
                libelleImageNormale.setForeground(Color.WHITE);
                libelleImageSurExposee.setForeground(Color.WHITE);
                libelleImageHdr.setForeground(Color.WHITE);
            } else {
                panneauContenu.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                miniatureSousExposee.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                miniatureNormale.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                miniatureSurExposee.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                zoneAffichageHdr.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
                libelleImageSousExposee.setForeground(Color.BLACK);
                libelleImageNormale.setForeground(Color.BLACK);
                libelleImageSurExposee.setForeground(Color.BLACK);
                libelleImageHdr.setForeground(Color.BLACK);
            }
            panneauContenu.setBackground(couleur);
            miniatureSousExposee.setBackground(couleur);
            miniatureNormale.setBackground(couleur);
            miniatureSurExposee.setBackground(couleur);
            zoneAffichageHdr.setBackground(couleur);
            bureau.setForeground(couleur);
        }
    }

    public void stateChanged(ChangeEvent ev) {
        if (ev.getSource().equals(selecteurGamma)) {
            Gamma.set(selecteurGamma.getValue());
            libelleGamma.setText("Correction Gamma : " + Gamma.get());
        }

        if (ev.getSource().equals(selecteurRgb)) {
            OperationImage.setRange(selecteurRgb.getLowValue(), selecteurRgb.getHighValue());
            libelleSelectionRgb.setText("Sélection RGB : " + selecteurRgb.getLowValue() +
                    "-" + selecteurRgb.getHighValue());
        }
    }

    public boolean isPicture(File f) {
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        if (type.equals("image")) {
            return true;
        }

        JOptionPane.showMessageDialog(this, "Le fichier que vous avez choisi n'est pas une image.",
                "Format du fichier incorrect", JOptionPane.WARNING_MESSAGE);
        return false;
    }

}
