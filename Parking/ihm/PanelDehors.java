package Parking.ihm;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Parking.Controleur;
import Parking.metier.Vehicule;

/**
 * Panneau affichant les vehicules qui attendent dehors (bloques au semaphore).
 * Chaque vehicule lance sa thread immediatement, mais peut etre en file d'attente
 * sur le semaphore. Ce panneau affiche tous les vehicules crees avec leur statut.
 */
public class PanelDehors extends JPanel
{
    private Controleur ctrl;
    private JTextArea textArea;

    public PanelDehors(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setBackground(new Color(30, 30, 50));
        this.setLayout(new BorderLayout());

        JLabel titre = new JLabel("  Vehicules en attente", JLabel.LEFT);
        titre.setForeground(Color.ORANGE);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        this.add(titre, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setBackground(new Color(30, 30, 50));
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(30, 30, 50));
        this.add(scroll, BorderLayout.CENTER);
    }

    public void maj()
    {
        int enAttente = ctrl.getQueueLength();
        List<Vehicule> liste = ctrl.getVehicules();
        StringBuilder sb = new StringBuilder();
        sb.append("Threads bloques au semaphore : ").append(enAttente).append("\n\n");
        sb.append(String.format("%-8s %-12s %-8s%n", "ID", "Type", "Etat"));
        sb.append("--------------------------------\n");
        for (Vehicule v : liste)
        {
            String typeLabel = v.isAvecRemorque() ? "Remorque" : "Simple  ";
            String etat = v.getState() == Thread.State.WAITING
                       || v.getState() == Thread.State.TIMED_WAITING
                       || v.getState() == Thread.State.BLOCKED ? "ATTENTE" : v.getState().toString();
            sb.append(String.format("%-8s %-12s %-8s%n", "V" + v.getIdVehicule(), typeLabel, etat));
        }
        textArea.setText(sb.toString());
        repaint();
    }
}
