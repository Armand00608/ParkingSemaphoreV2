package Parking.ihm;

import java.awt.*;

public class DessinerAttente
{
    private static final int TAILLE = 60;

    public static void dessiner(Graphics g, int largeur, int hauteur, int numVehicule)
    {
        int x = (largeur  - TAILLE) / 2;
        int y = (hauteur - TAILLE) / 2;

        g.setColor(Color.ORANGE);
        g.fillRect(x, y, TAILLE, TAILLE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, TAILLE, TAILLE);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		if (numVehicule == -1) return;
		String text = "V" + numVehicule;
		FontMetrics fm = g.getFontMetrics();
		int textX = x + (TAILLE - fm.stringWidth(text)) / 2;
		int textY = y + ((TAILLE - fm.getHeight()) / 2) + fm.getAscent();
		g.drawString(text, textX, textY);
    }
}