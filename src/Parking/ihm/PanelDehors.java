package Parking.ihm;

import javax.swing.*;
import java.awt.*;
import Parking.Controleur;

public class PanelDehors extends JPanel
{
    private Controleur ctrl;
    private FrameParking frame;
    private PanelAttente panelAttente;
    private PanelRestes  panelRestes;

    public PanelDehors(Controleur ctrl, FrameParking frame)
    {
        this.ctrl  = ctrl;
        this.frame = frame;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        this.panelAttente = new PanelAttente(ctrl);
        this.panelRestes  = new PanelRestes(ctrl);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelAttente, panelRestes);
        splitPane.setResizeWeight(0.25);
        splitPane.setDividerSize(2);
        splitPane.setEnabled(false);

        this.add(splitPane, BorderLayout.CENTER);
    }

    public void maj()
    {
        this.panelAttente.repaint();
        this.panelRestes.repaint();
    }

    // Panel pour la zone d'attente
    private static class PanelAttente extends JPanel
    {
        private Controleur ctrl;

        public PanelAttente(Controleur ctrl)
        {
            this.ctrl = ctrl;
            this.setBackground(Color.LIGHT_GRAY);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
			if (this.ctrl.getNbVehiculesDehors() ==0)
			{
				DessinerAttente.dessiner(g, getWidth(), getHeight(), -1);
				return;
			}
            super.paintComponent(g);
            DessinerAttente.dessiner(g, getWidth(), getHeight(), this.ctrl.getNbVehiculeEntre());
        }
    }

    // Panel pour les véhicules restants
    private static class PanelRestes extends JPanel
    {
        private Controleur ctrl;

        public PanelRestes(Controleur ctrl)
        {
            this.ctrl = ctrl;
            this.setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int nb = Math.min(ctrl.getNbVehiculesDehors(), 20);
            if (nb <= 0) return;

            Dimension taille = DessinerDehors.getTaillePreferee(nb);
            int x = (getWidth()  - taille.width)  / 2;
            int y = (getHeight() - taille.height) / 2;

            g.translate(x, y);
            DessinerDehors.dessiner(g, nb, this.ctrl.getNbVehiculeEntre());
            g.translate(-x, -y);
        }
    }
}