package classes;

/**
 * Holds the info related to each slot in a parking Lot
 * Has functionalities required to handle occupying slot,vacating slot updating slot info
 * retrieve driver and car info if occupied
 */
public class Slot {
    private Driver driver;
    private int slotNumber;

    public Slot( int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    private void setDriver(Driver driver) {
        if(this.driver!=null)
                return ;
        this.driver = driver;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    /**
     * removes driver info
     * @return true if slot is unoccupied
     */
    public boolean vacateSlot(){
        driver=null;
        return driver==null;
    }

    /**
     * Adds the driver details to simulate occupancy
     *
     * @param d
     * @return success or failure
     */
    public boolean occupySlot(Driver d){
        if(driver!=null)
            return false;
        this.setDriver(d);
        return true;
    }

    /**
     * check slot occupancy state
     * @return true or false
     */
    public boolean isVacant(){
        return this.driver==null;
    }
}
