/********************************************************************************************************************
 ********************************************************************************************************************
 *****                                 Chapter 15, Part 2: Problem 6 Part B                                     *****
 *****__________________________________________________________________________________________________________*****
 *****         6. Add to your bouncing ball program from #5 by drawing a "paddle" at the bottom edge.           *****
 *****                 This should be a rectangle that is not very tall but can be wide.                        *****
 *****               Use the arrow keys and a KeyListener to change the paddle's direction.                     *****
 *****       The paddle will have an x-velocity but not y (it will always remain at the same y coordinate).     *****
 *****     Use a pdx for determining how far to move the paddle and px for the paddle's current x coordinate.   *****
 *****                                 You can move the paddle in two ways:                                     *****
 *****----------------------------------------------------------------------------------------------------------*****
 *****                 b. on <-, subtract 1 from pdx but have a minimum pdx of -3 (or -4) and                   *****
 *****                          on ->, add 1 to pdx, and on down arrow, set pdx to 0.                           *****
 ********************************************************************************************************************
 ********************************************************************************************************************/

// IMPORTS of needed tools and plug-ins
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.util.Duration;
import java.util.Random;


public class Chapter15_Part2_6_B extends Application {

    // CLASS VARIABLE(s) declaration(s)
    private Pane pane;
    private Circle ball;
    private Rectangle paddle;
    private double bx, by, bdx, bdy;
    private double px = 225;
    private double py = 475;
    private int paddleWidth = 50;
    private int paddleHeight = 10;
    private double pdx;
    private int coinToss = 0;
    private int radius = 25;
    private Random random;


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage){

        // Initialize the Random variable
        random = new Random();

        // Get the initialize random position of the ball (Circle)
        bx = (random.nextInt(475) + 25);
        by = (random.nextInt(475) + 25);

        // Initialize the Circle and add it to the Pane
        ball = new Circle(bx, by, radius);

        // Initialize the Rectangle and add it to the Pane
        paddle = new Rectangle(px, py, paddleWidth, paddleHeight);

        // Initialize the Pane and add the ball and paddle
        pane = new Pane();
        pane.getChildren().add(ball);
        pane.getChildren().add(paddle);

        // Create and initialize the Scene
        Scene scene = new Scene(pane, 500, 500);

        // Get and set the initial velocity of the ball
        bdx = (random.nextInt(6) + 1);
        bdy = (random.nextInt(6) + 1);

        // Set the initial velocity of the paddle
        pdx = 0;

        // Get the horizontal direction of the initial movement
        coinToss = random.nextInt(2);
        if(coinToss == 0){
            bdx *= -1;
        }

        // Get the vertical direction of the initial movement
        coinToss = random.nextInt(2);
        if(coinToss == 0){
            bdy *= -1;
        }

        // Animation EventHandler
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(50), e-> {
            // remove the old ball and paddle
            pane.getChildren().remove(ball);
            pane.getChildren().remove(paddle);

            // if and else if block to check if the ball hits the left or right side of the Stage and
                // reverse direction if so (commented out code was to try to change the velocity to a new random number)
            if(bx >= 480){
                bdx *= -1;
            } else if(bx <= 20){
                bdx *= -1;
            }

            // if and else if block to check if the ball hits the top or bottom side of the Stage and
                // reverse direction if so (commented out code was to try to change the velocity to a new random number)
            if(by >= 480){
                by -= 10;
                bdy *= -1;
            } else if(by <= 20){
                by += 10;
                bdy *= -1;
            }

            // Move the ball at the associated current Velocity
            bx += bdx;
            by += bdy;

            // Create a new ball (Circle) and add it to the Pane
            ball = new Circle(bx, by, radius);
            pane.getChildren().add(ball);

            // if block to check if the paddle has almost hit either the left or right wall and
                // stop the paddle if it has
            if(px < 5){
                px = 5;
                pdx = 0;
            } else if(px > 450){
                px = 445;
                pdx = 0;
            }

            // Move the paddle at the associated current Velocity
            px += pdx;

            // Create a new paddle (Rectangle) and add it to the Pane
            paddle = new Rectangle(px, py, paddleWidth, paddleHeight);
            pane.getChildren().add(paddle);
        }));

        // Set the CycleCount of the Animation to INDEFINITE (animation doesn't end) and Play the animation
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        // KeyPressed EventHandler for paddle
        scene.setOnKeyPressed(e -> {
            if((e.getCode() == KeyCode.LEFT) && (pdx > -5)){
                pdx--;
            } else if((e.getCode() == KeyCode.RIGHT) && (pdx < 5)){
                pdx++;
            } else if(e.getCode() == KeyCode.DOWN){
                pdx = 0;
            }
        });

        // Set the Title of the Stage, Set the Scene to the Stage, Show the Stage
        primaryStage.setTitle("Chapter 15, Part 2: Problem 6 - B");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}