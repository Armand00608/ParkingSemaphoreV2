package Parking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import Parking.metier.*;
import Parking.ihm.*;

public class Controleur
{
    private static final int NB_VEHICULES = 30;

    private Parking parking;
    private List<Vehicule> vehicules;
    private FrameParking frame;
    private final AtomicInteger nbVehiculeEntre = new AtomicInteger(0);

    public Controleur()
    {
        parking = new Parking(this);
        vehicules = new ArrayList<>();

        // Creer l'IHM sur l'EDT
        javax.swing.SwingUtilities.invokeLater(() -> {
            frame = new FrameParking(this);
        });

        // Attendre que la frame soit creee
        while (frame == null)
        {
            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
        }

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
