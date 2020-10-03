package classes;

import Exceptions.DuplicateRegNoException;
import Exceptions.ParkingFullException;
import Exceptions.SlotVacantException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class holds the information and functionalities required to simulate a parking lot automated system
 * uses Slot class to fill slots
 * functionalities include occupy and vacate slot,query info on parked drivers or slots
 */
public class ParkingLot {
    HashMap<Integer,HashMap<String,Slot>>parkingData;// store parking info based on age
    private int max_slots;
    private int slotsUsed;
    private PriorityQueue<Slot>slotsAvailable;// use minheap to retrieve slot closer to entry
    private Slot[] slotOccupancy;// have all slots
    public ParkingLot(int slots){
        this.max_slots=slots;
        parkingData=new HashMap<>();
        this.slotsAvailable=new PriorityQueue<Slot>(new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getSlotNumber()-o2.getSlotNumber();//return lowest slot available
            }
        });
        this.setupParkingSlots(slots);

    }

    /**
     * create Slot array and Available minHeap slots
     * @param slots
     */
    private void setupParkingSlots(int slots){
        this.slotOccupancy=new Slot[slots];
        for (int i=0;i<slots;i++)
        {
            this.slotOccupancy[i]=new Slot(i+1);
            this.slotsAvailable.add(this.slotOccupancy[i]);
        }
    }
    public HashMap<Integer, HashMap<String, Slot>> getParkingData() {
        return parkingData;
    }

    public void setParkingData(HashMap<Integer, HashMap<String, Slot>> parkingData) {
        this.parkingData = parkingData;
    }

    public int getSlots() {
        return max_slots;
    }

    public void setSlots(int slots) {
        this.max_slots = slots;
    }

    /**
     * This function does the job of driver map to slot
     * throws parking full if no slots available
     * validates if duplicate entry of car regNo
     * @param d
     * @return
     * @throws ParkingFullException
     */
    public int getParkingSlot(Driver d) throws ParkingFullException, DuplicateRegNoException {
        if(slotsAvailable.isEmpty())// check for slot availabitliy
            throw new ParkingFullException();
        if(this.slotNumberByRegNo(d.getvRegNo())!=-1)// validate duplicate reg entry
            throw  new DuplicateRegNoException();
        Slot slot=slotsAvailable.poll();
        slot.occupySlot(d);
        this.addParkingSlot(slot);
        return slot.getSlotNumber();
    }

    /**
     * helper function to map age to carRegNo
     * @param slot
     */
    public  void addParkingSlot(Slot slot){
        HashMap<String,Slot>map=parkingData.getOrDefault(slot.getDriver().getAge(),new HashMap<>());
        map.put(slot.getDriver().getvRegNo(),slot);
        this.parkingData.put(slot.getDriver().getAge(),map);
    }

    /**
     * vacate a parking slot if occupied
     * @param slotNumber
     * @return
     * @throws SlotVacantException
     */
    public String vacateParkingSlot(int slotNumber) throws SlotVacantException {
        Slot slot=this.slotOccupancy[slotNumber-1];
        if(slot.isVacant())// check if slot is not occupied
            throw new SlotVacantException();
        this.removeFromParkingData(slot);
        String regNo=slot.getDriver().getvRegNo();
        slot.vacateSlot();
        this.slotsAvailable.add(slot);//add slot to minheap
        return regNo;
    }

    /**
     * helper function to remove vehicle reg to age map
     * @param slot
     * @return
     */
    public  boolean removeFromParkingData(Slot slot){
        if(slot ==null)
                return false;
        HashMap<String,Slot>map=parkingData.getOrDefault(slot.getDriver().getAge(),null);
        map.remove(slot.getDriver().getvRegNo());
        return true;
    }

    /**
     * query result to retrieve vechile reg number by age
     * @param age
     * @return
     */
    public String[] vehicleRegNoByAge(int age){
        HashMap<String,Slot>map=this.parkingData.getOrDefault(age,null);
        String[]vRegNos=null;
        if(map==null||map.size()==0)
            return null;
        return map.keySet().toArray(new String[0]);
    }

    /**
     * query result to retrieve slotNumber by regNo
     * @param regNo
     * @return
     */
    public int slotNumberByRegNo(String regNo){
        for(Integer age:parkingData.keySet()){
            HashMap<String ,Slot>map=this.parkingData.get(age);
            if(map.containsKey(regNo))
                return map.get(regNo).getSlotNumber();
        }
        return -1;
    }

    /**
     * query result to retrieve slots by age
     * @param age
     * @return
     */
    public int[] slotNoByAge(int age){
        HashMap<String,Slot>map=this.parkingData.getOrDefault(age,null);
        int[]slotNumbers=null;
        int i=0;
        if(map==null||map.size()==0)
            return null;
        slotNumbers=new int[map.size()];
        for(String keys:map.keySet())
            slotNumbers[i++]=map.get(keys).getSlotNumber();
        return slotNumbers;
    }
}

