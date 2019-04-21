package org.firstinspires.ftc.teamcode.ChampsCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Courtney", group="Teleop")

public class WORLDTELE extends LinearOpMode {

    //calls the hardware map
    RovHardwareMap robot = new RovHardwareMap(hardwareMap, telemetry);

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        telemetry.addData("Say", "Welcome Statik!");

        waitForStart();

        //actual start of the program
        while (opModeIsActive()) {

            //DRIVE TRAIN!!
            double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            robot.FLDrive.setPower(v1);
            robot.FRDrive.setPower(v2);
            robot.BLDrive.setPower(v3);
            robot.BRDrive.setPower(-v4);


            //LIFT!!
            //go up
            if (gamepad2.left_bumper){
                robot.Lift.setPower(1);
            }
            //go down
            else if (gamepad2.right_bumper){
                robot.Lift.setPower(-1);
            }
            //stop
            else {
                robot.Lift.setPower(0);
            }

            //INTAKE!!
            //inward to robot
            if (gamepad2.dpad_down)
                robot.Intake.setPower(-0.5);
            //outward to ground
            else if (gamepad2.dpad_up)
                robot.Intake.setPower(0.5);
            else
                robot.Intake.setPower(0);


            //INTAKE BUCKET!!
            //spins in
            if (gamepad2.a) {
                robot.IntakeCube.setPower(-1);
            }
            //spits out
            else if (gamepad2.b) {
                robot.IntakeCube.setPower(1);
            }
            //stops motion
            else {
                robot.IntakeCube.setPower(0);
            }


            //HORIZONTAL LIFT!!
            //pull in
            if (gamepad2.right_trigger > 0.5) {
                robot.HLift.setPower(-0.5);
            }
            //push out
            else if (gamepad2.left_trigger > 0.5) {
                robot.HLift.setPower(0.5);
            }
            //keep 0.1 power (pull in)
            else {
                robot.HLift.setPower(-0.1);
            }

            //DUMP!!
            //up to lander
            if (gamepad2.left_stick_y < -0.5) {
                robot.Dump.setPower(0.5);
            }
            //down back into robot
            else if (gamepad2.left_stick_y > 0.7) {
                robot.Dump.setPower(-0.3);
            }
            //no motion
            else{
                 robot.Dump.setPower(0);
                }

            }
        }
    }



