package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {
    public DcMotor left, right;
    public Servo push;

    private double endTime;
    private boolean on, pushing;
    private static final double MAX_POWER = 1.0;
    private static final double INCREASE = 0.05;
    private static final double MAX_TIME = 250;

    public ElapsedTime runtime;

    public Shooter(DcMotor _left, DcMotor _right, Servo _push) {
        left = _left;
        right = _right;
        push = _push;

        on = false;
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        push.setPosition(0.0);
        pushing = false;
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on) {
                left.setPower(MAX_POWER);
                right.setPower(MAX_POWER);
            } else {
                left.setPower(0.0);
                right.setPower(0.0);
            }
        }
    }

    public void pushRing(boolean change) {
//        if(change) {
//            push.setPosition(INCREASE);
//            pushing = false;
//        } else if(pushing) {
//            push.setPosition(push.getPosition() + INCREASE);
//            if(push.getPosition() == 1.0) {
//                pushing = false;
//                push.setPosition(0.0);
//            }
//        }
//
//        if (change) {
//            if (push.getPosition() == 0.0)
//                push.setPosition(1.0);
//            else
//                push.setPosition(0.0);
//        }

        if(change) {
            push.setPosition(1.0);
            pushing = true;
            endTime = runtime.milliseconds() + MAX_TIME;
        }

        if(pushing && runtime.milliseconds() > endTime) {
            pushing = false;
            push.setPosition(0);
        }
    }
}
