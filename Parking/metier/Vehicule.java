package Parking.metier;

public class Vehicule extends Thread
{
    private Parking parking;
    private int id;
    private int dureeStationnement;
    private boolean avecRemorque;

    public Vehicule(Parking parking, int id)
    {
        this.parking = parking;
        this.id = id;
        this.dureeStationnement = (int)(Math.random() * 10000) + 5000;
        this.avecRemorque = Math.random() < 0.3;
    }

    public int getIdVehicule()
    {
        return id;
    }

    public boolean isAvecRemorque()
    {
        return avecRemorque;
    }

    public void run()
    {
        try
        {
            parking.entrer(id, avecRemorque);
            Thread.sleep(dureeStationnement);
            parking.sortir(id, dureeStationnement, avecRemorque);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
