package Parking.ihm;

import javax.swing.*;
import java.awt.*;

import Parking.Controleur;

public class PanelParking extends JPanel 
{
    private Controleur ctrl;
    
    // Taille fixe du panel parking
    private static final int LARGEUR = 450;
    private static final int HAUTEUR = 400;
    
    public PanelParking(Controleur ctrl) 
    {
        this.ctrl = ctrl;
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activer l'anti-aliasing pour un meilleur rendu
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Déléguer le dessin à la classe utilitaire
        DessinerParking.dessinerParking(g2d, ctrl.getNbPlaces(), getWidth(), getHeight());
    }
}
