package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {
    public DcMotor left;
    public Servo lift;
    public CRServo push;

    private double endTime;
    private boolean on, pushing;
    private static final double MAX_POWER = 1.0;
    private static final double INCREASE = 0.05;
    private static final double MAX_TIME = 788;

    public ElapsedTime runtime;

    public Shooter(DcMotor _left, CRServo _push, Servo _lift) {
        left = _left;
        push = _push;
        lift = _lift;

        on = false;
        pushing = false;
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPosition(0.69);

        left.setDirection(DcMotorSimple.Direction.FORWARD);
        push.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on)
                left.setPower(MAX_POWER);
            else
                left.setPower(0.0);
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

        if(!pushing && change) {
            push.setPower(1.0);
            pushing = true;
            endTime = runtime.milliseconds() + MAX_TIME;
        }

        if(pushing && runtime.milliseconds() > endTime) {
            pushing = false;
            push.setPower(0.0);
        }
    }

    public void pushRing2() {
        push.setPower(1.0);
        endTime = runtime.milliseconds() + MAX_TIME;

        if(runtime.milliseconds() > endTime)
          push.setPower(0.0);
    }

    public void Up (boolean change){
        if(change)
        lift.setPosition(0.20);
    }

    public void Down (boolean change){
        if(change)
       lift.setPosition(0.69);
    }
    


    public void startShoot(double p) {
        left.setPower(p * MAX_POWER);
    }


}
