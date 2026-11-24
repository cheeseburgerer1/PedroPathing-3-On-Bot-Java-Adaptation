# PedroPathing 3 for FTC OnBot Java

This repository is at the planning stage. The intended deliverable is an editable Java source tree that builds on a REV Control Hub and can be used from a team OpMode. The package folders currently contain only Git placeholders.

## Baseline

Use [PedroPathing commit efdf80e](https://github.com/cheeseburgerer1/PedroPathing/tree/efdf80e8d17acd14a57750b689f3d0d10c965f96) as the baseline. Its version is 3.0.2. It contains 51 Java files in `core` and 24 in `revhub`. Both modules target Java 8. The REV Hub module compiles against FTC RobotCore and Hardware 12.0.0.

The [OnBot reference](https://github.com/cheeseburgerer1/PedroPathing-for-OnBotJava) also identifies its JAR as 3.0.2. Of the 75 matching Java paths, 69 have identical file contents. Six differ across the two repositories: `ForesightConfig`, `ManualDrive`, `Curve`, `BezierCurve`, `PiecewiseInterpolator` and `Utils`. Start these six from the source commit, including its out-of-range interpolation fix. Consult the reference when a specific OnBot issue appears.

## Folder mapping

| Source or purpose | Repository destination |
| --- | --- |
| Packages under `core/src/main/java/com/pedropathing/` | `src/com/pedropathing/`, with their existing package paths |
| Files under `revhub/src/main/java/com/pedropathing/revhub/` | `src/com/pedropathing/revhub/`, including drivetrains and localizers |
| Robot configuration, tuning and autonomous checks | `src/org/firstinspires/ftc/teamcode/pedro/` |

For deployment, the **contents** of repository `src/` map to `FIRST/java/src/` on the Hub. For example, `Follower.java` will live at `FIRST/java/src/com/pedropathing/follower/Follower.java`. Only Java sources belong in the upload. Keep the plan and Git placeholders on the development machine.

## Implementation and acceptance plan

1. **Establish the test environment.** Record the Robot Controller app/FTC SDK version, Control Hub OS, active robot configuration and installed external libraries. Use a backed-up test source tree and check that the existing OnBot build passes. Use a single copy of PedroPathing on the test Hub to avoid duplicate classes from an earlier JAR. FTC SDK 12.0.0 is the upstream compile baseline.
2. **Port core as a unit.** Bring over the 51 core files with their package names and public APIs intact. Include the upstream BSD-3-Clause license and preserve the copyright headers when copying source. Run OnBot's Build Everything. Check Java 8 lambdas, method references, streams and available Android library methods against the actual compiler. Make a compatibility edit only for a reproducible issue. Exercise line and Bézier endpoints, heading interpolation at and just outside segment bounds, and follower state transitions.
3. **Port the REV Hub adapters.** Add all 24 REV Hub files and build again. Resolve FTC classes against the Hub SDK. Check driver APIs for Pinpoint, OctoQuad, SparkFun OTOS, REV IMU and motors, including classes for hardware the team does not own because the compiler still processes them. The upstream imports require no separate Ivy or web-tuning library. Use Driver Station telemetry for initial checks. Evaluate integrations from the reference only if a later feature needs them.
4. **Wire in the team's robot.** Implement the team configuration factory for the actual drivetrain and localizer, using the source constructor order `Follower(Localizer, Drivetrain, Algorithm)`. The reference's default `Constants.create` returns null. Fill in real hardware names, directions, units, geometry and measured tuning values. Verify initialization and pose telemetry while moving the robot by hand before commanding a drive. Keep robot-specific values in the team package.
5. **Validate the deployment.** Starting from a fresh test setup, load the sources and run Build Everything. Confirm the test OpMode is selectable on the Driver Station. Exercise a short straight path and a curved path at limited power. Check endpoint and heading error against team-agreed tolerances, verify completion and stop behavior, then restart the Robot Controller and repeat. Record SDK/OS, configuration, build result and the hardware combinations actually exercised. Treat other adapter combinations as compile-tested until run on matching hardware.

The first milestone is a working source deployment. A precompiled JAR or web tuner can be evaluated separately after the same source passes the Control Hub checks.

## OnBot Java references

- [FIRST guide to creating and building an OnBot Java OpMode](https://ftc-docs.firstinspires.org/en/latest/programming_resources/tutorial_specific/onbot_java/creating_op_modes/Creating-and-Running-an-Op-Mode-%28OnBot-Java%29.html)
- [FIRST guide to external libraries](https://ftc-docs.firstinspires.org/en/latest/programming_resources/shared/external_libraries_blocks/external-libraries-blocks.html)
