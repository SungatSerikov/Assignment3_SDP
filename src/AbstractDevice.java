public abstract class AbstractDevice implements Device {
    private final String type;
    private boolean poweredOn;
    private int volume;

    protected AbstractDevice(String type) {
        this.type = type;
    }

    @Override
    public String applySettings(boolean poweredOn, int volume) {
        this.poweredOn = poweredOn;
        this.volume = volume;

        return this.type + " power="
                + (this.poweredOn ? "ON" : "OFF")
                + " volume=" + this.volume;
    }
}