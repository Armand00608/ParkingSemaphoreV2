package Parking.ihm;

import java.awt.*;

public class DessinerDehors
{
    private static final int NB_COLONNES = 10;
    private static final int NB_LIGNES   = 2;
    private static final int TAILLE      = 40;
    private static final int MARGE       = 2;

    public static void dessiner(Graphics g, int nbVehicules, int nbVehiculeEntre)
    {
        int nb = Math.min(nbVehicules, NB_COLONNES * NB_LIGNES);

        for (int i = 0; i < nb; i++)
        {
            int col = i % NB_COLONNES;
            int lig = i / NB_COLONNES;

            int x = col * (TAILLE + MARGE);
            int y = lig * (TAILLE + MARGE);

            g.setColor(Color.WHITE);
            g.fillRect(x, y, TAILLE, TAILLE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, TAILLE, TAILLE);

            g.setFont(new Font("Arial", Font.BOLD, 14));
            String num = "V" + String.valueOf(i + nbVehiculeEntre + 1);
            FontMetrics fm = g.getFontMetrics();
            int tx = x + (TAILLE - fm.stringWidth(num)) / 2;
            int ty = y + (TAILLE + fm.getAscent()) / 2 - 2;
            g.drawString(num, tx, ty);
        }
    }

    public static Dimension getTaillePreferee(int nbVehicules)
    {
        int nb  = Math.min(nbVehicules, NB_COLONNES * NB_LIGNES);
        int col = Math.min(nb, NB_COLONNES);
        int lig = (nb > NB_COLONNES) ? 2 : (nb > 0 ? 1 : 0);

        int w = col * (TAILLE + MARGE);
        int h = lig * (TAILLE + MARGE);
        return new Dimension(w, h);
    }
}