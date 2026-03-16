package Parking.ihm;

import java.awt.*;

public class DessinerParking
{
    public static final int NB_COLONNES   = 6;
    public static final int LARGEUR_PLACE = 60;
    public static final int HAUTEUR_PLACE = 80;
    public static final int MARGE         = 10;
    public static final int ESPACE_NUMERO = 20;
    public static final int HAUTEUR_ENTETE = ESPACE_NUMERO; // espace pour l'entete des colonnes

    public static void dessiner(Graphics g, int nbPlaces, int nbColonnes,
                                boolean[] placesOccupees,
                                int[] vehiculeSurPlace, boolean[] placeEstRemorque)
    {
        // Entetes des colonnes (A, B, C, ... max 26 colonnes)
        g.setFont(new Font("Arial", Font.BOLD, 13));
        FontMetrics fmHeader = g.getFontMetrics();
        int nbColonnesAffichees = Math.min(nbColonnes, 26);
        for (int col = 0; col < nbColonnesAffichees; col++)
        {
            String lettre = String.valueOf((char)('A' + col));
            int xCol = MARGE + col * (LARGEUR_PLACE + MARGE);
            g.setColor(new Color(180, 200, 255));
            g.drawString(lettre,
                xCol + (LARGEUR_PLACE - fmHeader.stringWidth(lettre)) / 2,
                MARGE + fmHeader.getAscent() - 2);
        }

        for (int i = 0; i < nbPlaces; i++)
        {
            int col = i % nbColonnes;
            int lig = i / nbColonnes;

            int x = MARGE + col * (LARGEUR_PLACE + MARGE);
            int y = MARGE + HAUTEUR_ENTETE + lig * (HAUTEUR_PLACE + MARGE + ESPACE_NUMERO) + ESPACE_NUMERO;

            // Numero au-dessus
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            String num = String.valueOf(i + 1);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(num, x + (LARGEUR_PLACE - fm.stringWidth(num)) / 2, y - 5);

            if (placesOccupees[i])
            {
                if (placeEstRemorque[i])
                {
                    // Verifier si c'est la place du HAUT de la remorque
                    int placeAuDessus = i - nbColonnes;
                    boolean estPlaceHaut = (placeAuDessus < 0
                        || vehiculeSurPlace[placeAuDessus] != vehiculeSurPlace[i]);

                    if (estPlaceHaut)
                    {
                        int yBas = MARGE + HAUTEUR_ENTETE
                            + (lig + 1) * (HAUTEUR_PLACE + MARGE + ESPACE_NUMERO)
                            + ESPACE_NUMERO;
                        dessinerVoitureRemorque(g, x, y, yBas, vehiculeSurPlace[i]);
                    }
                    // La place du bas est couverte par le dessin de la place du haut
                }
                else
                {
                    dessinerVoiture(g, x, y, vehiculeSurPlace[i]);
                }
            }
            else
            {
                effacerPlace(g, x, y);
            }
        }
    }

    public static void dessinerVoiture(Graphics g, int x, int y, int idVehicule)
    {
        g.setColor(Color.RED);
        g.fillRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);

        int vw = LARGEUR_PLACE - 16;
        int vh = HAUTEUR_PLACE - 20;
        int vx = x + 8;
        int vy = y + 10;

        g.setColor(Color.BLUE);
        g.fillRoundRect(vx, vy, vw, vh, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(vx, vy, vw, vh, 10, 10);

        g.setColor(Color.DARK_GRAY);
        g.fillOval(vx + 2,       vy + 4,        10, 10);
        g.fillOval(vx + vw - 12, vy + 4,        10, 10);
        g.fillOval(vx + 2,       vy + vh - 14,  10, 10);
        g.fillOval(vx + vw - 12, vy + vh - 14,  10, 10);

        g.setColor(Color.CYAN);
        g.fillRect(vx + 6, vy + vh / 2 - 5, vw - 12, 10);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String id = "V" + idVehicule;
        FontMetrics fm = g.getFontMetrics();
        g.drawString(id, x + (LARGEUR_PLACE - fm.stringWidth(id)) / 2, y + HAUTEUR_PLACE - 5);
    }

    /**
     * Dessine un vehicule avec remorque occupant 2 places verticalement.
     * La voiture est sur la place du haut, la remorque sur celle du bas.
     */
    public static void dessinerVoitureRemorque(Graphics g, int x, int yHaut, int yBas, int idVehicule)
    {
        // Place du haut : la voiture
        g.setColor(new Color(200, 60, 60));
        g.fillRect(x, yHaut, LARGEUR_PLACE, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, yHaut, LARGEUR_PLACE, HAUTEUR_PLACE);

        int vw = LARGEUR_PLACE - 16;
        int vh = HAUTEUR_PLACE - 20;
        int vx = x + 8;
        int vy = yHaut + 10;

        g.setColor(new Color(0, 100, 200));
        g.fillRoundRect(vx, vy, vw, vh, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(vx, vy, vw, vh, 10, 10);

        g.setColor(Color.DARK_GRAY);
        g.fillOval(vx + 2,       vy + 4,        10, 10);
        g.fillOval(vx + vw - 12, vy + 4,        10, 10);
        g.fillOval(vx + 2,       vy + vh - 14,  10, 10);
        g.fillOval(vx + vw - 12, vy + vh - 14,  10, 10);

        g.setColor(Color.CYAN);
        g.fillRect(vx + 6, vy + vh / 2 - 5, vw - 12, 10);

        // Place du bas : la remorque
        g.setColor(new Color(200, 150, 50));
        g.fillRect(x, yBas, LARGEUR_PLACE, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, yBas, LARGEUR_PLACE, HAUTEUR_PLACE);

        int rw = LARGEUR_PLACE - 16;
        int rh = HAUTEUR_PLACE - 24;
        int rx = x + 8;
        int ry = yBas + 12;

        g.setColor(new Color(180, 130, 40));
        g.fillRoundRect(rx, ry, rw, rh, 8, 8);
        g.setColor(Color.BLACK);
        g.drawRoundRect(rx, ry, rw, rh, 8, 8);

        g.setColor(Color.DARK_GRAY);
        g.fillOval(rx + 2,       ry + rh - 14, 10, 10);
        g.fillOval(rx + rw - 12, ry + rh - 14, 10, 10);

        // Barre d'attelage
        g.setColor(Color.YELLOW);
        int barreX = x + LARGEUR_PLACE / 2;
        g.drawLine(barreX - 1, yHaut + HAUTEUR_PLACE, barreX - 1, yBas);
        g.drawLine(barreX,     yHaut + HAUTEUR_PLACE, barreX,     yBas);
        g.drawLine(barreX + 1, yHaut + HAUTEUR_PLACE, barreX + 1, yBas);

        // ID du vehicule
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String id = "V" + idVehicule + "+R";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(id, x + (LARGEUR_PLACE - fm.stringWidth(id)) / 2, yHaut + HAUTEUR_PLACE - 5);
    }

    public static void effacerPlace(Graphics g, int x, int y)
    {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, LARGEUR_PLACE, HAUTEUR_PLACE);
    }

    /** Retourne la largeur totale necessaire pour la grille. */
    public static int getLargeurGrille(int nbColonnes)
    {
        return MARGE + nbColonnes * (LARGEUR_PLACE + MARGE);
    }

    /** Retourne la hauteur totale necessaire pour la grille (inclut entetes des colonnes). */
    public static int getHauteurGrille(int nbLignes)
    {
        return MARGE + HAUTEUR_ENTETE + nbLignes * (HAUTEUR_PLACE + MARGE + ESPACE_NUMERO) + ESPACE_NUMERO;
    }
}
