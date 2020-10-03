package classes;

import Exceptions.AgeLimitException;

/**
 * Holds information related to a driver like age and regInfo
 * has validations on Invalid Age info on setup
 */
public class Driver{
    private int Age;
    private String vRegNo;
    public  static final int AGE_LOWER_LIMIT=0;
    public  static final int AGE_UPPER_LIMIT=150;
    public int getAge() {
        return Age;
    }

    public Driver(int age, String vRegNo) throws AgeLimitException {
        this.setAge(age);
        this.setvRegNo(vRegNo);
    }

    public void setAge(int age) throws AgeLimitException {
        if(age<=AGE_LOWER_LIMIT||age>=AGE_UPPER_LIMIT)
            throw new AgeLimitException("AGE INVALID");
        this.Age = age;
    }

    public void setvRegNo(String vRegNo) {

        this.vRegNo = vRegNo;
    }

    public String getvRegNo() {
        return vRegNo;
    }
}
