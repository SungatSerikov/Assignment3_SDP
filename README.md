# Assignment 3 — Bridge Pattern

- **Student:** Sunggat Serikov
- **Group:** SE-2528
- **Topic:** D — Remote controls
- **Repository:** [Assignment3_SDP](https://github.com/SungatSerikov/Assignment3_SDP)
- **Base commit:** `c311bca018d6bf5190e9cf11bae25d2b320ea429`

## Project description

This project demonstrates the Bridge pattern using remote controls and devices.

The two independent dimensions are remote types and device types. `BasicRemote` turns a device on with volume 30. `QuietRemote` turns a device on with volume 5.

Each remote stores an ID, a volume preset, and a reference to the `Device` interface. It delegates device operations through this interface. The client selects the concrete device.

## Bridge roles

| Role | Class | Source path |
|---|---|---|
| Abstraction | Remote | src/Remote.java |
| Refined abstraction A1 | BasicRemote | src/BasicRemote.java |
| Refined abstraction A2 | QuietRemote | src/QuietRemote.java |
| Implementor interface | Device | src/Device.java |
| Concrete implementor I1 | TvDevice | src/TvDevice.java |
| Concrete implementor I2 | RadioDevice | src/RadioDevice.java |
| Concrete implementor I3 | ProjectorDevice | src/ProjectorDevice.java |
| Client | Main | src/Main.java |
| Shared implementation helper | AbstractDevice | src/AbstractDevice.java |

`AbstractDevice` contains the common device state and output logic.

## Build and run

Requires JDK 17. No external dependencies are needed.

Run these commands from the project root:

```text
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

The demo runs all seven checks without interactive input.

## Expected results

| Check | Combination or action | Expected result |
|---|---|---|
| T1 | BasicRemote + TvDevice | `TV power=ON volume=30` |
| T2 | BasicRemote + RadioDevice | `RADIO power=ON volume=30` |
| T3 | QuietRemote + TvDevice | `TV power=ON volume=5` |
| T4 | QuietRemote + RadioDevice | `RADIO power=ON volume=5` |
| T5 | Same BasicRemote switches from TV to radio | Before: `TV power=ON volume=30`; after: `RADIO power=ON volume=30` |
| T6 | BasicRemote + ProjectorDevice | `PROJECTOR power=ON volume=30` |
| T7 | QuietRemote + ProjectorDevice | `PROJECTOR power=ON volume=5` |

T5 must also report:

```text
sameObject=true | idUnchanged=true | presetUnchanged=true
```

The remote ID remains `SWITCH-1`, and its volume preset remains `30`.

Each check compares actual and expected results to calculate PASS or FAIL. A failed check also prints the expected result. A successful run ends with:

```text
SUMMARY: 7/7 PASS
```

The captured console output is saved in `demo-output.txt`.

## Projector extension

The base commit contains TV, radio, and checks T1–T5.

The extension adds `ProjectorDevice` and checks T6–T7. Within `src/`, only `Main.java` and the new `ProjectorDevice.java` changed. Existing remote classes, `Device`, `AbstractDevice`, `TvDevice`, and `RadioDevice` remained unchanged.

`sources.txt` was updated to include the new source file. The source changes are recorded in `extension.diff`.
