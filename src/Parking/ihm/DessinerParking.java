package Parking.ihm;

import java.awt.*;

public class DessinerParking 
{
    public static final int NB_COLONNES = 6;
    public static final int NB_LIGNES   = 3;
    
    public static final int MARGE = 20;
    public static final int ESPACE_NUMERO = 20; // Espace pour le numéro au-dessus
    
    /**
     * Dessine le parking complet
     */
    public static void dessinerParking(Graphics2D g2d, int nbPlaces, int largeurPanel, int hauteurPanel) 
    {
        int largeurDisponible = largeurPanel - 2 * MARGE;
        int hauteurDisponible = hauteurPanel - 2 * MARGE;
        
        int largeurPlace = largeurDisponible / NB_COLONNES;
        int hauteurPlace = (hauteurDisponible - NB_LIGNES * ESPACE_NUMERO) / NB_LIGNES;
        
        int offsetX = (largeurPanel - NB_COLONNES * largeurPlace) / 2;
        int offsetY = (hauteurPanel - NB_LIGNES * (hauteurPlace + ESPACE_NUMERO)) / 2;
        
        int numPlace = 0;
        
        for (int ligne = 0; ligne < NB_LIGNES; ligne++) 
        {
            for (int col = 0; col < NB_COLONNES; col++) 
            {
                if (numPlace >= nbPlaces) break;
                
                int x = offsetX + col * largeurPlace;
                int y = offsetY + ligne * (hauteurPlace + ESPACE_NUMERO);
                
                dessinerPlace(g2d, x, y, largeurPlace, hauteurPlace, numPlace + 1);
                numPlace++;
            }
        }
    }
    
    /**
     * Dessine une place de parking
     */
    public static void dessinerPlace(Graphics2D g2d, int x, int y, int largeur, int hauteur, int numero) 
    {
        // Numéro de la place (au-dessus du carré)
        g2d.setColor(Color.WHITE);
        int fontSize = Math.max(12, Math.min(largeur / 4, ESPACE_NUMERO - 2));
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        String texte = String.valueOf(numero);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (largeur - fm.stringWidth(texte)) / 2;
        int textY = y + fm.getAscent();
        g2d.drawString(texte, textX, textY);
        
        // Fond de la place (décalé vers le bas pour laisser place au numéro)
        int yPlace = y + ESPACE_NUMERO;
        g2d.setColor(new Color(80, 180, 80)); // Vert
        g2d.fillRect(x + 2, yPlace + 2, largeur - 4, hauteur - 4);
        
        // Bordure
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x + 2, yPlace + 2, largeur - 4, hauteur - 4);
    }
    
}
