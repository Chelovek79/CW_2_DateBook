package task;

public enum PersonalType {

    PERSONAL("личная"),
    WORK("рабочая");

    private final String personalType;

    PersonalType(String personalType) {
        this.personalType = personalType;
    }

    public String getPersonalType() {
        return personalType;
    }

    @Override
    public String toString() {
        return personalType;
    }
}
