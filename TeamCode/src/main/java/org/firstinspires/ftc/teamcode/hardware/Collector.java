package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Collector {
    private DcMotor motor;
    private boolean on;
    private static final double MAX_POWER = 1.0;

    public Collector(DcMotor _motor) {
        on = false;
        motor = _motor;
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on)  motor.setPower(MAX_POWER);
            else    motor.setPower(0.0);
        }
    }
}
