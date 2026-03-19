package Parking.metier;

import java.util.concurrent.Semaphore;
import Parking.Controleur;

public class Parking
{
    public static final int NB_COLONNES = 6;

    private Controleur ctrl;
    private int nbPlaces;
    private Semaphore semaphore;
    private Semaphore mutex;          // mutex pour protéger l'accès aux tableaux (remplace synchronized)
    private boolean[] placesOccupees;
    private int[] vehiculeSurPlace;   // id du véhicule sur chaque place
    private boolean[] placeRemorque;  // true sur la 1ère case d'un couple remorque

    public Parking(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.nbPlaces = 18;
        this.semaphore = new Semaphore(nbPlaces, true);
        this.mutex = new Semaphore(1, true);
        this.placesOccupees = new boolean[nbPlaces];
        this.vehiculeSurPlace = new int[nbPlaces];
        this.placeRemorque = new boolean[nbPlaces];
    }

    public void entrer(int idVehicule, boolean hasRemorque) throws InterruptedException
    {
        if (hasRemorque)
        {
            // Besoin de 2 places adjacentes sur la même ligne
            while (true)
            {
                semaphore.acquire(2);
                mutex.acquire();
                int place = trouverDeuxPlacesAdjacentes();
                if (place >= 0)
                {
                    placesOccupees[place] = true;
                    placesOccupees[place + 1] = true;
                    vehiculeSurPlace[place] = idVehicule;
                    vehiculeSurPlace[place + 1] = idVehicule;
                    placeRemorque[place] = true;
                    System.out.println("Véhicule " + idVehicule + " (remorque) entre -> places " + (place + 1) + "-" + (place + 2));
                    ctrl.incrementerVehiculeEntre();
                    mutex.release();
                    ctrl.majAffichage();
                    return;
                }
                mutex.release();
                // Pas de 2 places adjacentes libres, relâcher et réessayer
                semaphore.release(2);
                Thread.sleep(100);
            }
        }
        else
        {
            semaphore.acquire();
            mutex.acquire();
            int place = trouverPlaceLibre();
            placesOccupees[place] = true;
            vehiculeSurPlace[place] = idVehicule;
            System.out.println("Véhicule " + idVehicule + " entre -> place " + (place + 1));
            ctrl.incrementerVehiculeEntre();
            mutex.release();
            ctrl.majAffichage();
        }
    }

    public void sortir(int idVehicule, int duree, boolean hasRemorque) throws InterruptedException
    {
        mutex.acquire();
        for (int i = 0; i < nbPlaces; i++)
        {
            if (vehiculeSurPlace[i] == idVehicule)
            {
                placesOccupees[i] = false;
                vehiculeSurPlace[i] = 0;

                if (hasRemorque && placeRemorque[i])
                {
                    // C'est la 1ère place du couple
                    placeRemorque[i] = false;
                    if (i + 1 < nbPlaces)
                    {
                        placesOccupees[i + 1] = false;
                        vehiculeSurPlace[i + 1] = 0;
                    }
                    System.out.println("Véhicule " + idVehicule + " (remorque) sort de places " + (i + 1) + "-" + (i + 2) + " (durée: " + duree + "ms)");
                }
                else if (hasRemorque)
                {
                    System.out.println("Véhicule " + idVehicule + " (remorque) sort (durée: " + duree + "ms)");
                }
                else
                {
                    System.out.println("Véhicule " + idVehicule + " sort de place " + (i + 1) + " (durée: " + duree + "ms)");
                }
                break;
            }
        }
        mutex.release();
        ctrl.majAffichage();
        semaphore.release(hasRemorque ? 2 : 1);
    }

    private int trouverPlaceLibre()
    {
        for (int i = 0; i < nbPlaces; i++)
        {
            if (!placesOccupees[i]) return i;
        }
        return -1;
    }

    /**
     * Cherche 2 places adjacentes libres sur la même ligne de la grille.
     * Retourne l'indice de la première place, ou -1 si aucune paire trouvée.
     */
    private int trouverDeuxPlacesAdjacentes()
    {
        for (int i = 0; i < nbPlaces - 1; i++)
        {
            // Vérifier que les 2 places sont sur la même ligne
            if (i % NB_COLONNES < NB_COLONNES - 1)
            {
                if (!placesOccupees[i] && !placesOccupees[i + 1])
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getNbPlaces()         { return nbPlaces; }
    public int getNbPlacesOccupees() 
    { 
        int count = 0;
        for (boolean b : placesOccupees) if (b) count++;
        return count;
    }
    public boolean[] getPlacesOccupees()  { return placesOccupees; }
    public int[]     getVehiculeSurPlace(){ return vehiculeSurPlace; }
    public boolean[] getPlaceRemorque()   { return placeRemorque; }
}
