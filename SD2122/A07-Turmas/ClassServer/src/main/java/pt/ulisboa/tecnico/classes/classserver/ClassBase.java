package pt.ulisboa.tecnico.classes.classserver;


import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ClassBase {
    private static final Logger LOGGER = Logger.getLogger(ClassBase.class.getName());

    private int capacity = 0;

    private boolean openEnrollments = false;

    private boolean active = true;

    private boolean debug = false;

    private boolean gossip = true;

    private Integer[] timeVector = {0, 0};

    private int vectorEntry;

    private Timer timer = new Timer();

    private TimerTask gossipTask;

    private boolean forceGossip;

    private String qualifier;

    ConcurrentHashMap<String, Student> enrolled = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Student> discarded = new ConcurrentHashMap<>();

    //Setters and Getters
    //-----------------------------------------------------------------------------------------//

    /**
     * Capacity getter
     *
     * @return capacity
     */
    public int getCapacity(){ return this.capacity;}

    /**
     * Capacity setter
     *
     * @param capacity capacity to set
     */
    public void setCapacity(int capacity){ this.capacity = capacity;}

    /**
     * OpenEnrollments getter
     *
     * @return true if enrollments are open
     */
    public boolean getOpenEnrollments(){ return this.openEnrollments;}

    /**
     * OpenEnrollments setter
     *
     * @param openEnrollments openEnrollments to set
     */
    public void setOpenEnrollments(boolean openEnrollments){ this.openEnrollments = openEnrollments; }

    /**
     * Enrolled getter
     *
     * @return ConcurrentHashMap with all enrolled students
     */
    public ConcurrentHashMap<String, Student> getEnrolled() { return enrolled; }

    /**
     * Discarded getter
     *
     * @return ConcurrentHashMap with all discarded students
     */
    public ConcurrentHashMap<String, Student> getDiscarded() { return discarded; }

    public void setEnrolled(ConcurrentHashMap<String, Student> enrolled) {
        this.enrolled = enrolled;
    }

    public void setDiscarded(ConcurrentHashMap<String, Student> discarded) {
        this.discarded = discarded;
    }

    /**
     * @return true if the server's status is active
     */
    public boolean isActive() { return active; }

    /**
     * Active setter
     *
     * @param active boolean value to set server's status
     */
    public void setActive(boolean active) { this.active = active; }

    /**
     * Gossip getter
     *
     * @return true if the server's gossip is on
     */
    public boolean isGossip() { return gossip; }

    /**
     * Gossip setter
     *
     * @param gossip boolean value to set server's status
     */
    public void setGossip(boolean gossip) { this.gossip = gossip; }

    public boolean isForceGossip() {
        return forceGossip;
    }

    public void setForceGossip(boolean forceGossip) {
        this.forceGossip = forceGossip;
    }

    /**
     * @return true if debug flag is active
     */
    public boolean isDebug() { return debug; }

    /**
     * Debug setter
     *
     * @param debug boolean value to set debug on server's terminal
     */
    public void setDebug(boolean debug) { this.debug = debug; }

    /**
     * TimeVector getter
     *
     * @return timeVector
     */
    public Integer[] getTimeVector() {
        return timeVector;
    }

    /**
     * TimeVector setter
     *
     * @param timeVector updated
     */
    public void setTimeVector(Integer[] timeVector) {
        this.timeVector = timeVector;
    }

    /**
     * TimeVectorEntry setter
     *
     * @param vectorEntry updated
     */
    public void setVectorEntry(int vectorEntry) {
        this.vectorEntry = vectorEntry;
    }

    /**
     * TimeVectorEntry getter
     *
     * @return vectorEntry
     */
    public int getVectorEntry() {
        return vectorEntry;
    }

    /**
     * Qualifier setter
     *
     * @param qualifier updated
     */
    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * Qualifier getter
     *
     * @return qualifier
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * GossipTask setter
     *
     * @param gossipTask updated
     */
    public void setGossipTask(TimerTask gossipTask) {
        this.gossipTask = gossipTask;
    }

    /**
     * GossipTask getter
     *
     * @return gossipTask
     */
    public TimerTask getGossipTask() {
        return gossipTask;
    }

    /**
     * Log function used in verbose
     *
     * @param message message to print in server's terminal
     * @param print boolean value to print if true
     */
    public void log(String message, boolean print){
        if (print)
            System.out.println(message);
    }

    //CheckCode functions
    //-----------------------------------------------------------------------------------------//

    /**
     * Code given when trying to enroll
     *
     * @param student student to check enrolled code
     * @return response code for the command
     */
    public synchronized ResponseCode checkEnrolledCode(Student student){
        if (!isActive()){
            return ResponseCode.INACTIVE_SERVER;
        }
        else if (!getOpenEnrollments()){
            return ResponseCode.ENROLLMENTS_ALREADY_CLOSED;
        }
        else if (getEnrolled().containsKey(student.getStudentId())){
            return ResponseCode.STUDENT_ALREADY_ENROLLED;
        }
        else if(getEnrolled().size() >= getCapacity()){
            return ResponseCode.FULL_CLASS;
        }
        else {
            return ResponseCode.OK;
        }
    }

    /**
     * Code given when trying to list
     *
     * @return response code for the command
     */
    public ResponseCode checkListClassCode(){
        if (!isActive()){
            return ResponseCode.INACTIVE_SERVER;
        }
        else {
            return ResponseCode.OK;
        }
    }

    /**
     * Code given when trying to openEnrollments
     *
     * @param capacity to check if class is already full
     * @return response code for the command
     */
    public synchronized ResponseCode checkOpenEnrollmentsCode(int capacity){
        if (!isActive()){
            return ResponseCode.INACTIVE_SERVER;
        }
        else if (getOpenEnrollments()) {
            return ResponseCode.ENROLLMENTS_ALREADY_OPENED;
        }
        else if (getEnrolled().size() > capacity){
            return ResponseCode.FULL_CLASS;
        }
        else {
            return ResponseCode.OK;
        }
    }

    /**
     * Code given when trying to closeEnrollments
     *
     * @return response code for the command
     */
    public synchronized ResponseCode checkCloseEnrollmentsCode(){
        if (!isActive()){
            return ResponseCode.INACTIVE_SERVER;
        }
        else if (!this.getOpenEnrollments()) {
            return ResponseCode.ENROLLMENTS_ALREADY_CLOSED;
        }
        else {
            return ResponseCode.OK;
        }
    }

    /**
     * Code given when trying to cancel student enrollment
     *
     * @param studentId to check which student to cancel
     * @return response code for the command
     */
    public synchronized ResponseCode checkCancelEnrollmentCode(String studentId){
        if (!isActive()){
            return ResponseCode.INACTIVE_SERVER;
        }
        else if (!getEnrolled().containsKey(studentId)) {
            return ResponseCode.NON_EXISTING_STUDENT;
        }
        else {
            return ResponseCode.OK;
        }
    }

    /**
     * Code given when trying to activate
     *
     * @return response code for the command
     */
    public ResponseCode checkActivateCode(){
        return ResponseCode.OK;
    }

    /**
     * Code given when trying to deactivate
     *
     * @return response code for the command
     */
    public ResponseCode checkDeactivateCode(){
        return ResponseCode.OK;
    }

    /**
     * Code given when trying to dump
     *
     * @return response code for the command
     */
    public ResponseCode checkDumpCode(){
        return ResponseCode.OK;
    }

    /**
     * Code given when trying to activateGossip
     *
     * @return response code for the command
     */
    public ResponseCode checkActivateGossipCode(){
        return ResponseCode.OK;
    }

    /**
     * Code given when trying to deactivateGossip
     *
     * @return response code for the command
     */
    public ResponseCode checkDeactivateGossipCode(){
        return ResponseCode.OK;
    }

    /**
     * Code given when trying to force a gossip
     *
     * @return response code for the command
     */
    public ResponseCode checkGossipCode(){
        return ResponseCode.OK;
    }
    //CommandFunctions
    //-----------------------------------------------------------------------------------------//

    /**
     * Enrolls the student in the class
     *
     * @param studentId the id of the student that wants to enroll
     * @param studentName the name of the student that wants to enroll
     */
    public synchronized void enroll(String studentId, String studentName){
        getTimeVector()[getVectorEntry()]++;
        LocalTime time = LocalTime.now();
        Student student = Student.newBuilder()
                .setStudentId(studentId)
                .setStudentName(studentName)
                .setLastModified(time.toString())
                .build();
        getEnrolled().put(studentId, student);
        getDiscarded().remove(studentId);
    }

    /**
     * List all the students (enrolled and discarded)
     *
     * @return the classState
     */
    public synchronized ClassState listClass(){
        return ClassState.newBuilder()
                .setCapacity(getCapacity())
                .setOpenEnrollments(getOpenEnrollments())
                .addAllEnrolled(getEnrolled().values())
                .addAllDiscarded(getDiscarded().values())
                .build();
    }

    /**
     * OpenEnrollments for the class and sets its maximum capacity
     *
     * @param capacity current maximum class capacity
     */
    public synchronized void openEnrollments(int capacity){
        getTimeVector()[getVectorEntry()]++;
        setOpenEnrollments(true);
        setCapacity(capacity);
    }

    /**
     * CloseEnrollments for the class
     */
    public synchronized void closeEnrollments() {
        getTimeVector()[getVectorEntry()]++;
        setOpenEnrollments(false);
    }

    /**
     * Cancel the enrollment of the student with the studentId given
     *
     * @param studentId the id of the student that will be discarded
     */
    public synchronized void cancelEnrollment(String studentId) {
        getTimeVector()[getVectorEntry()]++;
        LocalTime time = LocalTime.now();
        Student student = getEnrolled().get(studentId);
        getDiscarded().put(studentId, Student.newBuilder()
                .setStudentId(student.getStudentId())
                .setStudentName(student.getStudentName())
                .setLastModified(time.toString())
                .build());
        getEnrolled().remove(studentId);
    }

    /**
     * Sets server status to active
     */
    public synchronized void activate(){
        setActive(true);
    }

    /**
     * Sets server status to inactive
     */
    public synchronized void deactivate(){
        setActive(false);
    }

    /**
     * Sets server gossip active
     */
    public synchronized void activateGossip(){ setGossip(true); }

    /**
     * Sets server gossip inactive
     */
    public synchronized void deactivateGossip () { setGossip(false); }

    /**
     * Forces the server to gossip
     */
    public synchronized void gossip() {
        setForceGossip(true);
        getGossipTask().run();
        setForceGossip(false);
    }

    /**
     * Gets timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Dumps the classState
     *
     * @return the current classState
     */
    public ClassState dump(){
        return ClassState.newBuilder()
                .setCapacity(getCapacity())
                .setOpenEnrollments(getOpenEnrollments())
                .addAllEnrolled(getEnrolled().values())
                .addAllDiscarded(getDiscarded().values())
                .build();
    }

    /**
     * Merges the current classState with the other server's classState
     * It only merges if the clock vector of the current server is outdated
     *
     * @param classState the other server ClassState
     * @return response code for the command
     */
    public synchronized ResponseCode updateState(ClassState classState){

        if (!isActive()) return ResponseCode.INACTIVE_SERVER;

        if (getQualifier().equals("S")){
            setCapacity(classState.getCapacity());
            setOpenEnrollments(classState.getOpenEnrollments());
        }

        boolean outdated = false;

        for (int i = 0; i < classState.getTimeVectorList().size(); i++) {
            if (getTimeVector()[i] < classState.getTimeVectorList().get(i)){
                outdated = true;
                break;
            }
        }

        if(!outdated){
            return ResponseCode.OK;
        }

        for (int i = 0; i < classState.getTimeVectorList().size(); i++){
            getTimeVector()[i] = Math.max(classState.getTimeVectorList().get(i), getTimeVector()[i]);
        }

        ArrayList<Student> currentList = new ArrayList<>(enrolled.values());
        ArrayList<Student> toUpdateList = new ArrayList<>(classState.getEnrolledList());
        currentList.addAll(new ArrayList<>(discarded.values()));
        toUpdateList.addAll(classState.getDiscardedList());

        ConcurrentHashMap<String, Student> current = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Student> toUpdate = new ConcurrentHashMap<>();
        currentList.forEach(student -> current.put(student.getStudentId(), student));
        toUpdateList.forEach(student -> toUpdate.put(student.getStudentId(), student));

        ConcurrentHashMap<String, Student> toUpdateEnrolled = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Student> toUpdateDiscarded = new ConcurrentHashMap<>();
        classState.getEnrolledList().forEach(student -> toUpdateEnrolled.put(student.getStudentId(), student));
        classState.getDiscardedList().forEach(student -> toUpdateDiscarded.put(student.getStudentId(), student));

        ArrayList<Student> mergedEnrolled = new ArrayList<>();
        ArrayList<Student> mergedDiscarded = new ArrayList<>();

        ConcurrentHashMap<String, Student> newEnrolled = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Student> newDiscarded = new ConcurrentHashMap<>();


        current.forEach((id, student) -> {
            if (toUpdate.containsKey(id)){
                if (student.getLastModified().compareTo(toUpdate.get(id).getLastModified()) > 0) {
                    if (getEnrolled().containsKey(student.getStudentId())){
                        mergedEnrolled.add(student);
                    }
                    else if(getDiscarded().containsKey(student.getStudentId())){
                        mergedDiscarded.add(student);
                    }
                }
                else {
                    if (toUpdateEnrolled.containsKey(id)){
                        mergedEnrolled.add(student);
                    }
                    else if (toUpdateDiscarded.containsKey(id)){
                        mergedDiscarded.add(student);
                    }
                }
            }
            else {
                if (getEnrolled().containsKey(student.getStudentId())){
                    mergedEnrolled.add(student);
                }
                else if(getDiscarded().containsKey(student.getStudentId())){
                    mergedDiscarded.add(student);
                }
            }
        });

        toUpdate.forEach((id, student) -> {
            if (!current.containsKey(id)){
                if (toUpdateEnrolled.containsKey(id)){
                    mergedEnrolled.add(student);
                }
                else if (toUpdateDiscarded.containsKey(id)){
                    mergedDiscarded.add(student);
                }
            }
        });

        mergedDiscarded.forEach(student -> {
            newDiscarded.put(student.getStudentId(), student);
        });

        mergedEnrolled.sort(Comparator.comparing(Student::getLastModified));

        mergedEnrolled.forEach(student -> {
            if (newEnrolled.size() < getCapacity()){
                newEnrolled.put(student.getStudentId(), student);
            }
            else {
                newDiscarded.put(student.getStudentId(), student);
            }
        });

        setEnrolled(newEnrolled);
        setDiscarded(newDiscarded);

        return ResponseCode.OK;
    }
}
