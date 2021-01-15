package stars.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents the Access period of each school in NTU.
 */
public class AccessPeriod implements Serializable {
    /**
     * Unique SerializationID for AccessPeriod.
     */
    private static final long serialVersionUID = 6529685098260017699L;
    /**
     * Start dateTime of Access Period of school.
     */
    private LocalDateTime accessStart;
    /**
     * End dateTime of Access Period of school.
     */
    private LocalDateTime accessEnd;
    /**
     * The school of this Access Period e.g NBS/SCSE/CEE
     */
    private String school;

    /**
     * Creates a AccessPeriod.
     * @param sch School of this Access Period e.g. NBS/SCSE/CEE
     * @param as Start dateTime of Access Period of school
     * @param ae End dateTime of Access Period of school
     */
    public AccessPeriod(String sch, LocalDateTime as, LocalDateTime ae){
        school = sch;
        accessStart = as;
        accessEnd = ae;
    };

    /**
     * Gets the School of this Access Period e.g. NBS/SCSE/CEE
     * @return School name e.g. NBS/SCSE/CEE
     */
    public String getSchool(){
        return school;
    }

    /**
     * Change the School of this Access Period e.g. NBS/SCSE/CEE
     * @param sch School name e.g. NBS/SCSE/CEE
     */
    public void setSchool(String sch){
        school = sch;
    }

    /**
     *  Gets the Start of Access Period of school
     * @return Start dateTime
     */
    public LocalDateTime getAccessStart(){
        return accessStart;
    }

    /**
     * Change the Start of Access Period of school
     * @param as new Start dateTime
     */
    public void setAccessStart(LocalDateTime as){
        accessStart = as;
    }

    /**
     * Gets the End  of Access Period of school
     * @return End dateTime
     */
    public LocalDateTime getAccessEnd(){
        return accessEnd;
    }

    /**
     * Change the End of Access Period of school
     * @param ae new End dateTime
     */
    public void setAccessEnd(LocalDateTime ae){
        accessEnd = ae;
    }

    /**
     *  Checks if current system dateTime is within the school's Access period
     * @return true or false
     */
    public boolean validateAccessPeriod(){
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isBefore(accessStart) || currentTime.isAfter(accessEnd)){
            return false;
        } else {
            return true;
        }
    }


}
