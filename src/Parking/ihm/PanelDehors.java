package Parking.ihm;
import javax.swing.*;
import java.awt.*;
import Parking.Controleur;

public class PanelDehors extends JPanel
{ 
	private Controleur ctrl; 
	private FrameParking frame;
	private JPanel panelAttente;
	private JPanel panelRestes;

	public PanelDehors(Controleur ctrl, FrameParking frame) 
	{ 
		this.setLayout(new BorderLayout());
		this.ctrl = ctrl; 
		this.frame = frame;
		this.setBackground(Color.GRAY);
		this.panelAttente = new JPanel(); 
		this.panelAttente.setBackground(Color.LIGHT_GRAY);
		this.panelAttente.setLayout(new BorderLayout());
		this.panelAttente.add(new JLabel("Zone d'attente", SwingConstants.CENTER), BorderLayout.NORTH);

		this.panelRestes = new JPanel(new GridBagLayout());
		this.panelRestes.setBackground(Color.GRAY);

		JPanel grille = new JPanel(new GridLayout(2, 10, 2, 2));
		grille.setBackground(Color.GRAY);
		for (int i = 1; i <= this.ctrl.getNbVehiculesDehors(); i++) 
		{ 
			if (i> 19)
				break;
			JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER); 
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
			label.setPreferredSize(new Dimension(40, 40));
			grille.add(label); 
		}

		this.panelRestes.add(grille);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelAttente, panelRestes);
		splitPane.setResizeWeight(0.25);
        splitPane.setDividerSize(2);
        splitPane.setEnabled(false); 

		this.add(splitPane, BorderLayout.CENTER);
	} 

}