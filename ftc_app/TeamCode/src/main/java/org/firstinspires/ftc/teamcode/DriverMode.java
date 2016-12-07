import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * This file provides basic Telop driving for a simple robot.
 * The code is structured as an Iterative OpMode
 *
 * This particular OpMode executes a basic Tank Drive Teleop
 */

@TeleOp(name="8944: Simple Teleop", group="TeleOp")

public class DriverMode extends OpMode {
    DcMotor leftmotor = null;
    DcMotor rightmotor = null;
    Servo servo = null;
    float StickPercent = 0.5f;

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  0.4;     // Maximum rotational position
    static final double MIN_POS     =  0.1;     // Minimum rotational position
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        leftmotor = hardwareMap.dcMotor.get("left motor");
        leftmotor.setDirection(DcMotor.Direction.REVERSE);
        rightmotor = hardwareMap.dcMotor.get("right motor");
        servo = hardwareMap.servo.get("button pusher");

        servo.setPosition(position);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left;
        double right;
        float hypermode;
        float seanmode;
        float driveadjustment;
        float pushbeaconright;
        float pushbeaconleft;


        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        hypermode = gamepad1.right_trigger;
        seanmode = gamepad1.left_trigger;
        pushbeaconright = gamepad2.right_trigger;
        pushbeaconleft = gamepad2.left_trigger;

        if (pushbeaconright > 0){
            servo.setPosition(MAX_POS);
        }
        if (pushbeaconleft > 0){
            servo.setPosition(MIN_POS);
        }

        driveadjustment = StickPercent;
        if (hypermode > 0){
            driveadjustment = StickPercent * 2.0f;
        }
        if (seanmode > 0){
            driveadjustment = StickPercent * 0.5f;
        }
        leftmotor.setPower(left * driveadjustment);
        rightmotor.setPower(right * driveadjustment);

        telemetry.addData("left",  "%.2f", left * driveadjustment);
        telemetry.addData("right", "%.2f", right * driveadjustment);
        updateTelemetry(telemetry);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
