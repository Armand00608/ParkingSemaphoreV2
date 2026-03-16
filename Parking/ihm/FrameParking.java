package Parking.ihm;

import javax.swing.*;
import java.awt.*;
import Parking.Controleur;

public class FrameParking extends JFrame
{
    private PanelParking panelParking;
    private PanelDehors  panelDehors;
    private PanelInfo    panelInfo;

    public FrameParking(Controleur ctrl)
    {
        this.setTitle("Parking - Simulation (avec remorques)");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        panelParking = new PanelParking(ctrl);
        panelDehors  = new PanelDehors(ctrl);
        panelInfo    = new PanelInfo(ctrl);

        // Panneau gauche : grille de parking centree
        JPanel panelGauche = new JPanel(new GridBagLayout());
        panelGauche.setBackground(Color.DARK_GRAY);
        panelGauche.add(panelParking);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, panelDehors);
        splitPane.setResizeWeight(0.35);
        splitPane.setDividerSize(5);
        splitPane.setEnabled(false);

        this.setLayout(new BorderLayout());
        this.add(splitPane,  BorderLayout.CENTER);
        this.add(panelInfo,  BorderLayout.SOUTH);

        // Repositionner le divider apres le premier redimensionnement
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

    /** Met a jour l'affichage de tous les panneaux. */
    public void maj()
    {
        panelParking.maj();
        panelDehors.maj();
        panelInfo.maj();
    }
}
