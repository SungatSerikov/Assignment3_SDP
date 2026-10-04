public abstract class Remote {
    private final String id;
    private final int volumePreset;
    private Device implementation;

    protected Remote(String id, Device implementation, int volumePreset) {
        this.id = id;
        this.implementation = implementation;
        this.volumePreset = volumePreset;
    }

    public String execute() {
        return this.implementation.applySettings(true, this.volumePreset);
    }

    public void setImplementation(Device implementation) {
        this.implementation = implementation;
    }

    public String getId() {
        return this.id;
    }

    public int getVolumePreset() {
        return this.volumePreset;
    }
}