# PedroPathing 3 for FTC OnBot Java

Editable source port of [PedroPathing 3.0.2 at efdf80e](https://github.com/cheeseburgerer1/PedroPathing/tree/efdf80e8d17acd14a57750b689f3d0d10c965f96). The 51 core classes and 24 REV Hub source files retain their upstream packages. The upstream FTC SDK baseline is **12.0.0**. The [BSD-3-Clause license](LICENSE) covers the upstream code.

## Install on a Control Hub

1. Back up your existing OnBot Java sources. Check the Robot Controller app version. Use FTC SDK 12.0 for the baseline build.
2. If the test installation already contains PedroPathing, back up that version and use only one copy of each `com.pedropathing` class. An older `PedroPathing.jar` in OnBot's External Libraries can conflict with this source port.
3. Connect the Hub by USB file transfer. Merge the **contents** of this repository's `src/` into internal shared storage under `FIRST/java/src/`. Keep other team packages in place. The resulting tree must contain `FIRST/java/src/com/pedropathing/follower/Follower.java` and `FIRST/java/src/org/firstinspires/ftc/teamcode/pedro/PedroPathCheck.java`.
4. Reopen OnBot Java and select **Build Everything**. On the Driver Station select **TeleOp → Pedro 3 - Path Check**, then press **INIT**. The expected telemetry is **PASS**. This OpMode exercises path geometry and heading interpolation and requires no configured motors or sensors.

## Use on a robot

- For mecanum with goBILDA Pinpoint, call `RobotFollower.createMecanumPinpoint(hardwareMap, drive, odometry, tuning)` during OpMode initialization. Supply a `MecanumConfig` with the four configured motor names and directions, a `PinpointConfig` with the pod type, directions and measured offsets, and a `ForesightConfig` with the robot's measured speeds, decelerations, brake coefficients and tuned controllers.
- Other supported drives and localizers use the upstream constructor `new Follower(localizer, drivetrain, algorithm)`. Hardware adapters live in `com.pedropathing.revhub`. Select the adapter that matches your hardware.
- Set the starting pose, construct a path with a heading (for example, via `Paths.line(start, end).constant(headingRadians)`), and call `follow(path)` once. Call `update()` on every active OpMode loop. On shutdown call `follower.stop()` and `follower.drivetrain.stop()` to send the stop command immediately.
- Check pose and heading by moving the robot by hand. With robot-specific tuning in place, test a short straight path and a curve, verify endpoint error and stop behavior, then repeat after restarting the Robot Controller. The geometry diagnostic does not measure physical tracking accuracy.
