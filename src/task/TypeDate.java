package task;

public enum TypeDate {

    ONETIME("однократная"),
    DAILY("ежедневная"),
    WEEKLY("еженедельная"),
    MONTHLY("ежемесячная"),
    YEARLY("ежегодная");

    private final String titleType;

    TypeDate(String titleType) {
        this.titleType = titleType;
    }

    public String getTitleType() {
        return titleType;
    }

    @Override
    public String toString() {
        return titleType;
    }
}
