package Parking.metier;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import Parking.Controleur;

public class Parking
{
    private Controleur ctrl;
    private int nbPlaces;
    private int nbColonnes;
    private int nbLignes;
    private Semaphore semaphore;
    private boolean[] placesOccupees;
    private int[] vehiculeSurPlace;
    private boolean[] placeEstRemorque;
    private final ReentrantLock mutex = new ReentrantLock(true);

    public Parking(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.nbColonnes = 6;
        this.nbLignes = 3;
        this.nbPlaces = nbColonnes * nbLignes;
        this.semaphore = new Semaphore(nbPlaces, true);
        this.placesOccupees = new boolean[nbPlaces];
        this.vehiculeSurPlace = new int[nbPlaces];
        this.placeEstRemorque = new boolean[nbPlaces];
    }

    /**
     * Entree d'un vehicule dans le parking.
     * Si avecRemorque, on acquiert 2 permits et on cherche 2 places contigues
     * verticalement (meme colonne, lignes consecutives).
     * Le mutex (ReentrantLock) protege les tableaux partages sans synchronized.
     * Pour les remorques : si aucune paire contigue n'est disponible apres
     * l'acquisition, on relache et on reessaie (backoff leger).
     */
    public void entrer(int idVehicule, boolean avecRemorque) throws InterruptedException
    {
        if (avecRemorque)
        {
            while (true)
            {
                semaphore.acquire(2);
                int[] places;
                mutex.lock();
                try
                {
                    places = trouverDeuxPlacesContigues();
                    if (places != null)
                    {
                        placesOccupees[places[0]] = true;
                        placesOccupees[places[1]] = true;
                        vehiculeSurPlace[places[0]] = idVehicule;
                        vehiculeSurPlace[places[1]] = idVehicule;
                        placeEstRemorque[places[0]] = true;
                        placeEstRemorque[places[1]] = true;
                    }
                }
                finally
                {
                    mutex.unlock();
                }
                if (places != null)
                {
                    System.out.println("Vehicule " + idVehicule + " (remorque) entre -> places "
                        + (places[0] + 1) + " et " + (places[1] + 1)
                        + " | Permits: " + semaphore.availablePermits()
                        + " | En attente: " + semaphore.getQueueLength());
                    ctrl.incrementerVehiculeEntre();
                    ctrl.majAffichage();
                    return;
                }
                else
                {
                    semaphore.release(2);
                    Thread.sleep(100);
                }
            }
        }
        else
        {
            semaphore.acquire();
            int place;
            mutex.lock();
            try
            {
                place = trouverPlaceLibre();
                if (place >= 0)
                {
                    placesOccupees[place] = true;
                    vehiculeSurPlace[place] = idVehicule;
                    placeEstRemorque[place] = false;
                }
            }
            finally
            {
                mutex.unlock();
            }
            if (place >= 0)
            {
                System.out.println("Vehicule " + idVehicule + " entre -> place " + (place + 1)
                    + " | Permits: " + semaphore.availablePermits()
                    + " | En attente: " + semaphore.getQueueLength());
                ctrl.incrementerVehiculeEntre();
                ctrl.majAffichage();
            }
        }
    }

    public void sortir(int idVehicule, int duree, boolean avecRemorque) throws InterruptedException
    {
        int placesLiberees;
        mutex.lock();
        try
        {
            placesLiberees = 0;
            for (int i = 0; i < nbPlaces; i++)
            {
                if (vehiculeSurPlace[i] == idVehicule)
                {
                    placesOccupees[i] = false;
                    vehiculeSurPlace[i] = 0;
                    placeEstRemorque[i] = false;
                    placesLiberees++;
                }
            }
        }
        finally
        {
            mutex.unlock();
        }
        System.out.println("Vehicule " + idVehicule + (avecRemorque ? " (remorque)" : "")
            + " sort (" + placesLiberees + " place(s), duree: " + duree + "ms)"
            + " | Permits: " + (semaphore.availablePermits() + placesLiberees)
            + " | En attente: " + semaphore.getQueueLength());
        ctrl.majAffichage();
        semaphore.release(placesLiberees);
    }

    /**
     * Cherche 2 places libres contigues verticalement (meme colonne, lignes consecutives).
     * Doit etre appele sous le mutex.
     */
    private int[] trouverDeuxPlacesContigues()
    {
        for (int col = 0; col < nbColonnes; col++)
        {
            for (int lig = 0; lig < nbLignes - 1; lig++)
            {
                int place1 = lig * nbColonnes + col;
                int place2 = (lig + 1) * nbColonnes + col;
                if (!placesOccupees[place1] && !placesOccupees[place2])
                {
                    return new int[]{place1, place2};
                }
            }
        }
        return null;
    }

    /** Doit etre appele sous le mutex. */
    private int trouverPlaceLibre()
    {
        for (int i = 0; i < nbPlaces; i++)
        {
            if (!placesOccupees[i]) return i;
        }
        return -1;
    }

    public int getNbPlaces()             { return nbPlaces; }
    public int getNbColonnes()           { return nbColonnes; }
    public int getNbLignes()             { return nbLignes; }
    public int getAvailablePermits()     { return semaphore.availablePermits(); }
    public int getQueueLength()          { return semaphore.getQueueLength(); }

    public int getNbPlacesOccupees()
    {
        mutex.lock();
        try
        {
            int count = 0;
            for (boolean b : placesOccupees) if (b) count++;
            return count;
        }
        finally
        {
            mutex.unlock();
        }
    }

    public boolean[] getPlacesOccupees()
    {
        mutex.lock();
        try { return placesOccupees.clone(); }
        finally { mutex.unlock(); }
    }

    public int[] getVehiculeSurPlace()
    {
        mutex.lock();
        try { return vehiculeSurPlace.clone(); }
        finally { mutex.unlock(); }
    }

    public boolean[] getPlaceEstRemorque()
    {
        mutex.lock();
        try { return placeEstRemorque.clone(); }
        finally { mutex.unlock(); }
    }
}
