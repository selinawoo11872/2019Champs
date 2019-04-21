package org.firstinspires.ftc.teamcode.ChampsCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;


import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class RovHardwareMap {

    //declaration of motors on robot
    public DcMotor FLDrive;
    public DcMotor BLDrive;
    public DcMotor FRDrive;
    public DcMotor BRDrive;
    public DcMotor Intake;
    public DcMotor HLift;
    public DcMotor Lift;
    public DcMotor Dump;


    //declarations of servos on robot
    public CRServo IntakeCube;
    public Servo TeamMaker;
    public Servo liftBlock;

    //gyro stuff
    public double heading;
    public BNO055IMU imu;

    //calling hardware map that FTC provides
    HardwareMap hwMap;


    public RovHardwareMap (HardwareMap hardwareMap, Telemetry telemetry) {
    }


    /* Initialize standard Hardware interfaces */
    public void init (HardwareMap ahwMap) {

        // save reference to hwMap
        hwMap = ahwMap;

        // green = name to put into the phones
        BLDrive = hwMap.get(DcMotor.class,"BLdrive");
        BRDrive = hwMap.get(DcMotor.class, "BRdrive");
        FLDrive = hwMap.get(DcMotor.class, "FLdrive");
        FRDrive = hwMap.get(DcMotor.class, "FRdrive");
        Lift = hwMap.get(DcMotor.class, "Lift");
        imu = hwMap.get(BNO055IMU.class, "imu");
        Intake = hwMap.get(DcMotor.class, "Intake");
        HLift = hwMap.get(DcMotor.class,"HLift");
        Dump = hwMap.get(DcMotor.class, "Dump");

        IntakeCube = hwMap.crservo.get("IntakeLeft");
        TeamMaker = hwMap.servo.get("TeamMaker");


        FLDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        FRDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors



        // Set all motors to zero power
        BLDrive.setPower(0);
        BRDrive.setPower(0);
        FLDrive.setPower(0);
        FRDrive.setPower(0);


        // Set all motors to run without encoders.
        BLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //methods for the gyro sensor
    public double resetGyro (){
        heading = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        heading =0;
        return heading;
    }

    public double updateGyro() {
        heading = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        return heading;
    }



}
