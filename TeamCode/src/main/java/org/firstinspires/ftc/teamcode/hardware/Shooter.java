package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    private DcMotor left, right;
    private Servo push;

    private boolean on;
    private double where;
    private static final double MAX_POWER = 1.0;

    public Shooter(DcMotor _left, DcMotor _right, Servo _push) {
        left = _left;
        right = _right;
        push = _push;

        on = false;
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        where = 0.0;
        push.setPosition(where);
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
        if(change) {
            if(where == 0.0)
                where = 1.0;
            else
                where = 0.0;
            push.setPosition(where);
        }
    }

}
