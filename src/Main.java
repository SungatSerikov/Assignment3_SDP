public class Main {
    private static int totalChecks = 0;
    private static int passedChecks = 0;

    public static void main(String[] args) {
        if (args.length != 1 || !"--demo".equals(args[0])) {
            System.out.println("Usage: java -cp out Main --demo");
            return;
        }

        checkExecution("T1", "BasicRemote + TvDevice",
                new BasicRemote("BASIC-1", new TvDevice()),
                "TV power=ON volume=30");

        checkExecution("T2", "BasicRemote + RadioDevice",
                new BasicRemote("BASIC-1", new RadioDevice()),
                "RADIO power=ON volume=30");

        checkExecution("T3", "QuietRemote + TvDevice",
                new QuietRemote("QUIET-1", new TvDevice()),
                "TV power=ON volume=5");

        checkExecution("T4", "QuietRemote + RadioDevice",
                new QuietRemote("QUIET-1", new RadioDevice()),
                "RADIO power=ON volume=5");

        checkRuntimeSwitch();

        checkExecution("T6", "BasicRemote + ProjectorDevice",
                new BasicRemote("BASIC-1", new ProjectorDevice()),
                "PROJECTOR power=ON volume=30");

        checkExecution("T7", "QuietRemote + ProjectorDevice",
                new QuietRemote("QUIET-1", new ProjectorDevice()),
                "PROJECTOR power=ON volume=5");

        System.out.println("SUMMARY: " + passedChecks
                + "/" + totalChecks + " PASS");
    }

    private static void checkRuntimeSwitch() {
        Remote remote = new BasicRemote("SWITCH-1", new TvDevice());
        final Remote original = remote;

        String idBefore = remote.getId();
        int presetBefore = remote.getVolumePreset();
        String before = remote.execute();

        remote.setImplementation(new RadioDevice());
        String after = remote.execute();

        String idAfter = remote.getId();
        int presetAfter = remote.getVolumePreset();

        boolean sameObject = original == remote;
        boolean idUnchanged = idBefore.equals(idAfter);
        boolean presetUnchanged = presetBefore == presetAfter;

        String expectedBefore = "TV power=ON volume=30";
        String expectedAfter = "RADIO power=ON volume=30";

        boolean passed = sameObject && idUnchanged && presetUnchanged
                && expectedBefore.equals(before)
                && expectedAfter.equals(after);

        String actual = "sameObject=" + sameObject
                + " | idUnchanged=" + idUnchanged
                + " | presetUnchanged=" + presetUnchanged
                + "\n  idBefore=" + idBefore + " | idAfter=" + idAfter
                + "\n  presetBefore=" + presetBefore + " | presetAfter=" + presetAfter
                + "\n  before=" + before + " | after=" + after;

        String expected = "sameObject=true | idUnchanged=true | presetUnchanged=true"
                + " | idAfter=" + idBefore + " | presetAfter=" + presetBefore
                + " | before=" + expectedBefore + " | after=" + expectedAfter;

        recordCheck("T5", "BasicRemote: TvDevice -> RadioDevice",
                passed, actual, expected);
    }

    private static void checkExecution(String testId, String participants,
                                       Remote remote, String expected) {
        String actual = remote.execute();
        boolean passed = expected.equals(actual);

        recordCheck(testId, participants, passed, actual, expected);
    }

    private static void recordCheck(String testId, String participants,
                                    boolean passed, String actual,
                                    String expected) {
        totalChecks++;

        if (passed) {
            passedChecks++;
        }

        System.out.println(testId + " " + (passed ? "PASS" : "FAIL")
                + " | " + participants + " | result=" + actual);

        if (!passed) {
            System.out.println("  expected=" + expected);
        }
    }
}