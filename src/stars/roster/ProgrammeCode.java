package stars.roster;

/**
 * Enumeration that represents the student's course programmes that is available in NTU.
 */

public enum ProgrammeCode {
    Business("Business"),
    Accountancy("Accountancy"),
    BusinessComputing("Business & Computing"),
    AccountancyBusiness("Accountancy & Business"),
    ComputerScience("Computer Science"),
    ComputerEngineering("Computer Engineering"),
    SportsScienceMangement("Sports Science Management"),
    ArtDesignMedia("Art Design Media"),
    Biology("Biology"),
    Chemistry("Chemistry"),
    Pyschology("Pyschology"),
    CivilEngineering("Civil Engineering"),
    ElectricalEngineering("Electrical Engineering"),
    MechanicalEngineering("Mechanical Engineering"),
    AerospaceEngineering("Aerospace Engineering"),
    MathematiclSciences("Mathematical Sciences"),
    CommunicationStudies("Communication Studies");

    /**
     * Display name of Enum
     */
    private String displayName;

    ProgrammeCode(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns display name of Enum
     * @return Programme display name
     */
    public String displayName() {
        return displayName;
    }

    // Optionally and/or additionally, toString.
    @Override public String toString() {
        return displayName;
    }
}
