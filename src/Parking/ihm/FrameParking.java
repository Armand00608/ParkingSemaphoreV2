package Parking.ihm;

import javax.swing.*;
import java.awt.*;

import Parking.Controleur;

public class FrameParking extends JFrame 
{
    private Controleur ctrl;
    private PanelParking panelParking;
    private JPanel panelDehors;
    
    public FrameParking(Controleur ctrl) 
    {
        this.ctrl = ctrl;
        
        this.setTitle("Parking - Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Plein écran
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Layout principal
        this.setLayout(new BorderLayout());
        
        // Panel de gauche (1/3 de l'écran) pour le parking
        this.panelParking = new PanelParking(ctrl);
        this.panelDehors = new PanelDehors(ctrl, this);
        
        // Créer un panel conteneur pour le parking (1/3 gauche) avec centrage
        JPanel panelGauche = new JPanel(new GridBagLayout());
        panelGauche.add(panelParking); // GridBagLayout centre automatiquement
        panelGauche.setBackground(Color.DARK_GRAY);
        
        // Panel de droite (2/3 de l'écran)

        // Utiliser un JSplitPane pour diviser l'écran
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, this.panelDehors);
        splitPane.setResizeWeight(0.33); // 1/3 pour la gauche
        splitPane.setDividerSize(5);
        splitPane.setEnabled(false); // Empêcher le redimensionnement manuel
        
        this.add(splitPane, BorderLayout.CENTER);
        
        // Positionner le divider après l'affichage
        this.addComponentListener(new java.awt.event.ComponentAdapter() 
        {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) 
            {
                splitPane.setDividerLocation(getWidth() / 3);
            }
        });
        
        this.setVisible(true);
    }
    
    /**
     * Met à jour l'affichage du parking
     */
    public void majAffichage() 
    {
        this.panelParking.repaint();
    }
}
