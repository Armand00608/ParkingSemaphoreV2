package Parking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.reflect.InvocationTargetException;

import Parking.metier.*;
import Parking.ihm.*;

public class Controleur
{
    private static final int NB_VEHICULES = 30;

    private Parking parking;
    private List<Vehicule> vehicules;
    private FrameParking frame;
    private final AtomicInteger nbVehiculeEntre = new AtomicInteger(0);
    private javax.swing.Timer timerMaj;

    public Controleur()
    {
        parking = new Parking(this);
        vehicules = new ArrayList<>();

        // Creer l'IHM sur l'EDT et attendre qu'elle soit prete
        try
        {
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                frame = new FrameParking(this);
            });
        }
        catch (InvocationTargetException | InterruptedException e)
        {
            System.err.println("Erreur lors de l'initialisation de l'interface graphique : " + e.getMessage());
            e.printStackTrace();
        }

        // Rafraichissement periodique toutes les 500ms pour les etats des threads
        timerMaj = new javax.swing.Timer(500, e -> frame.maj());
        timerMaj.setCoalesce(true);
        timerMaj.start();

        System.out.println("=== Simulation avec " + NB_VEHICULES + " vehicules ===\n");

        for (int i = 0; i < NB_VEHICULES; i++)
        {
            Vehicule vehicule = new Vehicule(parking, i + 1);
            vehicules.add(vehicule);
            vehicule.start();

            try
            {
                majAffichage();
                Thread.sleep((int)(Math.random() * 300) + 100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        majAffichage();
    }

    // Getters pour l'IHM
    public int getNbPlaces()
    {
        return parking.getNbPlaces();
    }

    public int getNbColonnes()
    {
        return parking.getNbColonnes();
    }

    public int getNbLignes()
    {
        return parking.getNbLignes();
    }

    public int getNbPlacesOccupees()
    {
        return parking.getNbPlacesOccupees();
    }

    public int getAvailablePermits()
    {
        return parking.getAvailablePermits();
    }

    public int getQueueLength()
    {
        return parking.getQueueLength();
    }

    public boolean[] getPlacesOccupees()
    {
        return parking.getPlacesOccupees();
    }

    public int[] getVehiculeSurPlace()
    {
        return parking.getVehiculeSurPlace();
    }

    public boolean[] getPlaceEstRemorque()
    {
        return parking.getPlaceEstRemorque();
    }

    public List<Vehicule> getVehicules()
    {
        return new ArrayList<>(vehicules);
    }

    public int getNbVehiculeEntre()
    {
        return nbVehiculeEntre.get();
    }

    public void incrementerVehiculeEntre()
    {
        nbVehiculeEntre.incrementAndGet();
    }

    public void majAffichage()
    {
        if (frame != null)
        {
            javax.swing.SwingUtilities.invokeLater(() -> frame.maj());
        }
    }

    public static void main(String[] args)
    {
        new Controleur();
    }
}
