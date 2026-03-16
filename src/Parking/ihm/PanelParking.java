package Parking.ihm;

import javax.swing.*;
import java.awt.*;
import Parking.Controleur;

public class PanelParking extends JPanel
{
    private Controleur ctrl;

    public PanelParking(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(450, 400));
    }

    public void maj()
    {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        DessinerParking.dessiner(g, ctrl.getNbPlaces(), ctrl.getPlacesOccupees(), ctrl.getVehiculeSurPlace(), ctrl.getPlaceRemorque());
    }

}
