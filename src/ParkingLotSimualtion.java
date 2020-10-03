import Exceptions.AgeLimitException;
import Exceptions.DuplicateRegNoException;
import Exceptions.ParkingFullException;
import Exceptions.SlotVacantException;
import classes.Driver;
import classes.ParkingLot;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * class to simulate the parking Lot
 */
public class ParkingLotSimualtion {
    private ParkingLot parkinglot;
    // parse string instruction
    static String[] parse(String s){
        if(s==null)
            return null;
        return s.split(" ");
    }

    //map query to function call and handle exception to retreive results
    private String query(String[]query){
        if(query.length==0||query==null)
                return null;
        String output=null;
        try {

            switch (query[0]) {
                case "Create_parking_lot": {
                    this.parkinglot = new ParkingLot(Integer.parseInt(query[1]));
                    output = "created Parking of " + query[1] + "Slots.";
                }
                    break;
                case "Park": {
                    int slotNumber = this.parkinglot.getParkingSlot(new Driver(Integer.parseInt(query[3]), query[1]));
                    output= String.format("Car with vehicle registration number \"%s\" has been parked at slot number %d", query[1], slotNumber);
                }
                    break;
                case "Leave": {
                    String regNo = this.parkinglot.vacateParkingSlot(Integer.parseInt(query[1]));
                    output= String.format("Slot number %s vacated, the car with vehicle registration number \"%s\" left the space", query[1], regNo);
                }
                break;
                case "Slot_numbers_for_driver_of_age": {
                    int[] slotNumbers = this.parkinglot.slotNoByAge(Integer.parseInt(query[1]));
                    output = Arrays.toString(slotNumbers);
                }
                break;
                case "Vehicle_registration_number_for_driver_of_age": {
                    String[] vRegNos = this.parkinglot.vehicleRegNoByAge(Integer.parseInt(query[1]));
                    output = Arrays.toString(vRegNos);
                }
                break;
                case "Slot_number_for_car_with_number": {
                    int slotNo = this.parkinglot.slotNumberByRegNo(query[1]);
                    output= Integer.toString(slotNo);
                }
                break;

            }
        } catch (AgeLimitException e) {
            output = "Age Invalid";
        } catch (ParkingFullException e) {
            output="No available Parking Slots";
        } catch (SlotVacantException e) {
            output= "Slot already Vacant";
        } catch (DuplicateRegNoException e) {
            output="Duplicate Vehicle Reg Number";
        }
        return output;
     }
    public static void main(String[] args) {
        String parkingFile=new Scanner(System.in).nextLine();
       // String parkingFile="Input.txt";
        ParkingLotSimualtion parkingLotSimualtion=new ParkingLotSimualtion();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(parkingFile));
            String instruction=null;
            while((instruction=reader.readLine())!=null){
                System.out.println(parkingLotSimualtion.query(parse(instruction)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
