package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.gamepad.Axis;
import org.firstinspires.ftc.teamcode.gamepad.Button;
import org.firstinspires.ftc.teamcode.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.hardware.Config;
import org.firstinspires.ftc.teamcode.hardware.Mugurel;

@TeleOp(name="Driver Controled" , group="Linear Opmode")
//@Disabled
public class DriverControled extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public Servo lift, servo1;
    public DcMotor rot;
    private Mugurel robot;

    private GamepadEx gaju, duta;

    @Override
    public void runOpMode()  {

        robot = new Mugurel(hardwareMap, telemetry, this, runtime);

        lift = hardwareMap.get(Servo.class, Config.lift);
        rot = hardwareMap.get(DcMotor.class, Config.rotBrat);
        servo1 = hardwareMap.get(Servo.class, Config.stransBrat);


        gaju = new GamepadEx(gamepad1);
        duta = new GamepadEx(gamepad2);

       // robot.setOpmode(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        robot.runner.setFace(0);
        robot.runner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift.setPosition(0.69);

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){
            gaju.update();
            duta.update();

            telemetry.addData("Status", "Run Time: " + runtime.toString());

            setFace(gaju.y, gaju.a, gaju.x, gaju.b);
            move(gaju.left_x, gaju.left_y, gaju.right_x, gaju.left_trigger.toButton(0.3), gaju.right_trigger.toButton(0.3), gaju.dpad_left, gaju.dpad_right);
            collect(duta.a, duta.b);
            shoot(duta.y, duta.x, duta.dpad_up, duta.dpad_down);

            rot.setPower(duta.left_y.raw);

           if(duta.dpad_left.pressed())
              servo1.setPosition(1.0);

           if (duta.dpad_right.pressed())
               servo1.setPosition(0.0);

         /*if(duta.dpad_up.pressed())
             lift.setPosition(0.20);

          if(duta.dpad_down.pressed())
             lift.setPosition(0.69);
*/

            telemetry.addData("Servo", lift.getPosition());
            telemetry.addData("Left Shoot", robot.shooter.left.getCurrentPosition());
            telemetry.update();
        }

    }

    private void setFace(Button front, Button back, Button left, Button right) {
        if (front != null && front.pressed()) robot.runner.setFace(0);
        else if (back != null && back.pressed()) robot.runner.setFace(Math.PI);
        else if (left != null && left.pressed()) robot.runner.setFace(Math.PI / 2.0);
        else if (right != null && right.pressed()) robot.runner.setFace(-Math.PI / 2.0);
    }

    private void move(Axis lx, Axis ly, Axis rx, Button smallPower, Button mediumPower, Button dl, Button dr) {
        double modifier = 1.0;
        if (smallPower != null && smallPower.raw) modifier = 0.23;
        if (mediumPower != null && mediumPower.raw)  modifier = 0.5;

        final double drive_y = robot.runner.scalePower(ly.raw);
        final double drive_x = robot.runner.scalePower(lx.raw);
        final double turn = -robot.runner.scalePower(rx.raw);

        if (dr != null && dr.raw) robot.runner.moveWithAngle(-1,0,0, modifier);
        else if (dl != null && dl.raw) robot.runner.moveWithAngle(1,0,0, modifier);
        else robot.runner.moveWithAngle(drive_x, drive_y, turn, modifier);
    }

    private void collect(Button startCollector, Button reverseCollector) {
        robot.collector.changeState(startCollector.pressed());
        robot.collector.reverseCollector(reverseCollector.pressed());
    }

    private void shoot(Button startShooter, Button push, Button up, Button down) {
        robot.shooter.changeState(startShooter.pressed());
        robot.shooter.pushRing(push.pressed());
        robot.shooter.Up(up.pressed());
        robot.shooter.Down(down.pressed());
    }
}
