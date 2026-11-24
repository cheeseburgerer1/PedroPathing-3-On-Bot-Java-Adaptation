package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** Builds a follower from the team's measured robot configuration. */
public final class RobotFollower {
    private RobotFollower() {}

    public static Follower createMecanumPinpoint(
            HardwareMap hardwareMap, MecanumConfig drive, PinpointConfig odometry, ForesightConfig tuning) {
        return new Follower(
                new PinpointLocalizer(hardwareMap, odometry),
                new Mecanum(hardwareMap, drive),
                new Foresight(tuning));
    }
}
