package Parking.ihm;

import java.awt.*;

public class DessinerParking
{
    private static final int NB_COLONNES    = 6;
    private static final int LARGEUR_PLACE  = 60;
    private static final int HAUTEUR_PLACE  = 80;
    private static final int MARGE          = 10;
    private static final int ESPACE_NUMERO  = 20;

    public static void dessiner(Graphics g, int nbPlaces, boolean[] placesOccupees, int[] vehiculeSurPlace, boolean[] placeRemorque)
    {
        for (int i = 0; i < nbPlaces; i++)
        {
            int col = i % NB_COLONNES;
            int lig = i / NB_COLONNES;

            int x = MARGE + col * (LARGEUR_PLACE + MARGE);
            int y = MARGE + lig * (HAUTEUR_PLACE + MARGE + ESPACE_NUMERO) + ESPACE_NUMERO;

            // Numéro au-dessus
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            String num = String.valueOf(i + 1);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(num, x + (LARGEUR_PLACE - fm.stringWidth(num)) / 2, y - 5);

            if (placeRemorque[i])
            {
                // Dessiner voiture+remorque sur 2 cases
                dessinerVoitureAvecRemorque(g, x, y, vehiculeSurPlace[i]);

                // Dessiner aussi le numéro de la 2ème place
                i++;
                col = i % NB_COLONNES;
                int x2 = MARGE + col * (LARGEUR_PLACE + MARGE);
                g.setColor(Color.WHITE);
                num = String.valueOf(i + 1);
                fm = g.getFontMetrics();
                g.drawString(num, x2 + (LARGEUR_PLACE - fm.stringWidth(num)) / 2, y - 5);
            }
            else if (placesOccupees[i])
            {
                dessinerVoiture(g, x, y, vehiculeSurPlace[i]);
            }
            else
            {
                effacerVoiture(g, x, y);
            }
        }
    }

    public static void dessinerVoiture(Graphics g, int x, int y, int idVehicule)
    {
        g.setColor(Color.RED);
        g.fillRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);

        // Voiture
        int vw = LARGEUR_PLACE - 16;
        int vh = HAUTEUR_PLACE - 20;
        int vx = x + 8;
        int vy = y + 10;

        g.setColor(Color.BLUE);
        g.fillRoundRect(vx, vy, vw, vh, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(vx, vy, vw, vh, 10, 10);

        // Roues
        g.setColor(Color.DARK_GRAY);
        g.fillOval(vx + 2,       vy + 4,        10, 10);
        g.fillOval(vx + vw - 12, vy + 4,        10, 10);
        g.fillOval(vx + 2,       vy + vh - 14,  10, 10);
        g.fillOval(vx + vw - 12, vy + vh - 14,  10, 10);

        // Pare-brise
        g.setColor(Color.CYAN);
        g.fillRect(vx + 6, vy + vh / 2 - 5, vw - 12, 10);

        // ID du véhicule
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String id = "V" + idVehicule;
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (LARGEUR_PLACE - fm.stringWidth(id)) / 2;
        int ty = y + HAUTEUR_PLACE - 5;
        g.drawString(id, tx, ty);
    }

    public static void dessinerVoitureAvecRemorque(Graphics g, int x, int y, int idVehicule)
    {
        int doubleLargeur = LARGEUR_PLACE * 2 + MARGE;

        // Fond rouge pour les 2 places
        g.setColor(new Color(180, 50, 50));
        g.fillRect(x, y, doubleLargeur, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, doubleLargeur, HAUTEUR_PLACE);

        // === Voiture (partie gauche) ===
        int vw = LARGEUR_PLACE - 16;
        int vh = HAUTEUR_PLACE - 20;
        int vx = x + 8;
        int vy = y + 10;

        g.setColor(Color.BLUE);
        g.fillRoundRect(vx, vy, vw, vh, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(vx, vy, vw, vh, 10, 10);

        // Roues voiture
        g.setColor(Color.DARK_GRAY);
        g.fillOval(vx + 2,       vy + 4,        10, 10);
        g.fillOval(vx + vw - 12, vy + 4,        10, 10);
        g.fillOval(vx + 2,       vy + vh - 14,  10, 10);
        g.fillOval(vx + vw - 12, vy + vh - 14,  10, 10);

        // Pare-brise voiture
        g.setColor(Color.CYAN);
        g.fillRect(vx + 6, vy + vh / 2 - 5, vw - 12, 10);

        // === Attelage (barre entre voiture et remorque) ===
        int attelageX = vx + vw;
        int attelageY = y + HAUTEUR_PLACE / 2 - 3;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(attelageX, attelageY, MARGE + 16, 6);

        // === Remorque (partie droite) ===
        int rx = x + LARGEUR_PLACE + MARGE + 8;
        int rw = LARGEUR_PLACE - 16;
        int rh = vh;
        int ry = vy;

        g.setColor(new Color(255, 165, 0)); // Orange
        g.fillRoundRect(rx, ry, rw, rh, 8, 8);
        g.setColor(Color.BLACK);
        g.drawRoundRect(rx, ry, rw, rh, 8, 8);

        // Roues remorque
        g.setColor(Color.DARK_GRAY);
        g.fillOval(rx + 2,       ry + rh - 14, 10, 10);
        g.fillOval(rx + rw - 12, ry + rh - 14, 10, 10);

        // Texte "R" sur la remorque
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        String rText = "R";
        g.drawString(rText, rx + (rw - fm.stringWidth(rText)) / 2, ry + rh / 2 + fm.getAscent() / 2);

        // ID du véhicule (centré sur les 2 cases)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String id = "V" + idVehicule;
        fm = g.getFontMetrics();
        int tx = x + (doubleLargeur - fm.stringWidth(id)) / 2;
        int ty = y + HAUTEUR_PLACE - 5;
        g.drawString(id, tx, ty);
    }

    public static void effacerVoiture(Graphics g, int x, int y)
    {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);
    }
}
