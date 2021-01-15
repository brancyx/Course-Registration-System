package stars.model;// import javax.lang.stars.users.model.util.ElementScanner14;
import java.io.Serializable;
import java.time.*;
import java.util.*;

/**
 * Represents a Lesson which an Index has.
 * 
 * @author
 *
 */
public class Lesson implements Serializable {
    /**
     * Unique SerializationID for Lesson
     */
    private static final long serialVersionUID = 6529685098267757696L;
    /**
     * The index number which this Lesson belongs to.
     */
    private String courseIndex;
    /**
     * The class type of this Lesson. It can be lecture, tutorial or laboratory.
     */
    private String classType;
    /**
     * The day of this Lesson is held.
     */
    private String day;
    /**
     * The start time of this Lesson.
     */
    private LocalTime startTime;
    /**
     * The end time of this Lesson.
     */
    private LocalTime endTime;
    /**
     * The venue of this Lesson is held.
     */
    private String venue;
    /**
     * The week of this Lesson is held. A Lesson can be set to held on every
	 * week, odd/even week or from Week2-13.
     */
    private String week;
    /**
     * The group code given to this Lesson.
     */
    private String group;

    /**
     * Creates a Lesson.
     */
    public Lesson(){};

    /**
     * Creates a Lesson
     * @param idx Course Index number.
     * @param ct Class Type.
     * @param d Day.
     * @param st Start time.
     * @param et End time.
     * @param ve Venue.
     * @param w Week.
     * @param g Group.
     */
    public Lesson(String idx, String ct, String d, LocalTime st, LocalTime et, String ve, String w, String g){
        courseIndex = idx;
        classType = ct;
        day = d;
        startTime = st;
        endTime = et;
        venue = ve;
        week = w;
        group = g;
    }
    
    /**
     * Gets the index number of this Lesson belongs to.
     * @return this Lesson's index number.
     */
    public String getCourseIndex(){
        return courseIndex;
    }

    /**
     * Changes the index number of this Lesson.
     * @param idx this Lesson's new index number.
     */
    public void setCourseIndex(String idx){
        courseIndex = idx;
    }

    /**
     * Gets the class type of this Lesson.
     * @return this Lesson's class type.
     */
    public String getClassType(){
        return classType;
    }

    /**
     * Changes the class type of this Lesson.
     * @param ct this Lesson's new class type.
     */
    public void setClassType(String ct){
        classType = ct;
    }

    /**
     * Gets the day which this lesson is held.
     * @return this Lesson's day.
     */
    public String getDay(){
        return day;
    }

    /**
     * Changes the day of this Lesson is held.
     * @param d this Lesson's new day.
     */
    public void setDay(String d){
        day = d;
    }

    /**
     * Gets the start time of this Lesson.
     * @return this Lesson's start time.
     */
    public LocalTime getStartTime(){
        return startTime;
    }

    /**
     * Changes the start time of this Lesson.
     * @param st this Lesson's start time.
     */
    public void setStartTime(LocalTime st){
        startTime = st;
    }

    /**
     * Gets the end time of this Lesson.
     * @return this Lesson's end time.
     */
    public LocalTime getEndTime(){
        return endTime;
    }

    /**
     * Changes the end time of this Lesson.
     * @param et this Lesson's end time.
     */
    public void setEndTime(LocalTime et){
        endTime = et;
    }

    /**
     * Gets the week this Lesson is held.
     * @return this Lesson's week.
     */
    public String getWeek(){
        return week;
    }

    /**
     * Changes the week which this Lesson is held.
     * @param w this Lesson's new week.
     */
    public void setWeek(String w){
        week = w;
    }

    /**
     * Gets the venue of this Lesson is held.
     * @return this Lesson's venue.
     */
    public String getVenue(){
        return venue;
    }

    /**
     * Changes the venue which this Lesson is held.
     * @param v this Lesson's new venue.
     */
    public void setVenue(String v){
        venue = v;
    }

    /**
     * Gets the group code which is give to this Lesson.
     * @return this Lesson's group code.
     */
    public String getGroup(){
        return group;
    }

    /**
     * Changes the group code of this Lesson.
     * @param g this Lesson's new group code.
     */
    public void setGroup(String g){
        group = g;
    }

    /**
     * Checks against Lesson object array to determine if any time clash. If
	 * there is a time clash return true if not return false.
     * @param lesson the Lesson array that want to compare with.
     * @return true or false.
     */
    public boolean IsTimeClashWLessson(List <Lesson> lesson){
        for (Lesson l: lesson){
            if (this.getWeek().equals("EVEN") && l.getWeek().equals("ODD"))
                continue;
            else if (this.getWeek().equals("ODD") && l.getWeek().equals("EVEN"))
                    continue;
            else{
                if (this.getDay().equals(l.getDay())){
                    if (this.getStartTime().compareTo(l.getStartTime()) == 0 || l.getStartTime().isAfter(this.getStartTime()) && l.getStartTime().isBefore(this.getEndTime()) || l.getStartTime().isBefore(this.getStartTime()) && l.getEndTime().isAfter(this.getStartTime())){
                        return true;
                    } 
                }

            }
                
        }
        return false;
    }


}
