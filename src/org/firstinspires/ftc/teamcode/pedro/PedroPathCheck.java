package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.api.Paths;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Pedro 3 - Path Check", group = "Diagnostics")
public class PedroPathCheck extends OpMode {
    private String result;

    @Override
    public void init() {
        try {
            PathGeometryCheck.verify();
            result = "PASS - line, Bezier, heading and endpoint bounds";
        } catch (RuntimeException failure) {
            result = "FAIL - " + failure;
        }
        telemetry.addData("PedroPathing", result);
    }

    @Override
    public void loop() {
        telemetry.addData("PedroPathing", result);
    }
}

final class PathGeometryCheck {
    private PathGeometryCheck() {}

    static void verify() {
        Pose start = new Pose(0, 0);
        Pose end = new Pose(12, 0);
        Path line = Paths.line(start, end).linear(0, Math.PI / 2, 0.5);
        expect("line length", line.curve.length(), 12);
        expect("line midpoint", line.get(0.5).x(), 6);
        expect("interpolated heading", line.heading(0.25), Math.PI / 4);
        expect("heading below start", line.heading(-1e-8), 0);
        expect("heading above end", line.heading(1 + 1e-8), Math.PI / 2);

        Path bend = Paths.curve(start, new Pose(6, 6), end).constant(0);
        expect("Bezier midpoint x", bend.get(0.5).x(), 6);
        expect("Bezier midpoint y", bend.get(0.5).y(), 3);
        expect("Bezier endpoint", bend.endPose().distance(end), 0);
    }

    private static void expect(String name, double actual, double expected) {
        if (!(Math.abs(actual - expected) <= 1e-7)) {
            throw new IllegalStateException(name + ": expected " + expected + ", got " + actual);
        }
    }

    public static void main(String[] args) {
        verify();
    }
}
