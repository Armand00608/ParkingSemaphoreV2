package Parking.ihm;

import javax.swing.*;
import java.awt.*;
import Parking.Controleur;

/**
 * Panneau d'information affichant les statistiques du parking :
 * - Nombre de permits disponibles
 * - Nombre de threads en attente
 * - Places occupees / total
 * - Vehicules entres
 */
public class PanelInfo extends JPanel
{
    private Controleur ctrl;

    public PanelInfo(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setBackground(new Color(40, 40, 40));
        this.setPreferredSize(new Dimension(0, 70));
    }

    public void maj()
    {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int permits   = ctrl.getAvailablePermits();
        int enAttente = ctrl.getQueueLength();
        int occupees  = ctrl.getNbPlacesOccupees();
        int total     = ctrl.getNbPlaces();
        int entres    = ctrl.getNbVehiculeEntre();

        g2.setFont(new Font("Monospaced", Font.BOLD, 14));

        int x = 20;
        int y = 24;
        int esp = 26;

        g2.setColor(Color.CYAN);
        g2.drawString("Permits disponibles : " + permits, x, y);

        g2.setColor(enAttente > 0 ? Color.ORANGE : Color.GREEN);
        g2.drawString("Threads en attente  : " + enAttente, x, y + esp);

        int x2 = 320;
        g2.setColor(Color.WHITE);
        g2.drawString("Places occupees : " + occupees + " / " + total, x2, y);

        g2.setColor(Color.YELLOW);
        g2.drawString("Vehicules entres : " + entres, x2, y + esp);

        // Legende
        int x3 = 620;
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));

        g2.setColor(Color.GREEN);
        g2.fillRect(x3, y - 12, 13, 13);
        g2.setColor(Color.WHITE);
        g2.drawString("Libre", x3 + 18, y);

        g2.setColor(Color.RED);
        g2.fillRect(x3, y + esp - 12, 13, 13);
        g2.setColor(Color.WHITE);
        g2.drawString("Voiture", x3 + 18, y + esp);

        int x4 = 720;
        g2.setColor(new Color(200, 150, 50));
        g2.fillRect(x4, y - 12, 13, 13);
        g2.setColor(Color.WHITE);
        g2.drawString("Remorque", x4 + 18, y);
    }
}
