public class BasicRemote extends Remote {
    public BasicRemote(String id, Device implementation) {
        super(id, implementation, 30);
    }
}