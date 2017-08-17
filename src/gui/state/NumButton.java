package gui.state;

import javafx.scene.control.Button;
import model.SudokuModel;

/** Represents each of the cells in the Sudoku puzzle on the game page */
public class NumButton extends Button{
    SudokuModel model;
    private int row;
    private int col;
    private int num;

    NumButton(int row, int col, int num, SudokuModel model){
        this.model = model; //todo - set images to numbers if text isn't enough
        this.row = row;
        this.col = col;
        this.num = num;
        switch(num){
                /*case 1:
                    //Image pillarImg = new Image(getClass().getResourceAsStream("resources/pillarX.png"));
                    //ImageView pillarIcon = new ImageView(pillarImg);
                    //this.setGraphic(pillarIcon);
                    setButtonBackground(this, "white.png");
                    break;
                case 2:
                    //Image beamImg = new Image(getClass().getResourceAsStream("resources/beam.png"));
                    //ImageView beamIcon = new ImageView(beamImg);
                    //this.setGraphic(beamIcon);
                    setButtonBackground(this, "yellow.png");
                    break;
                case 3:
                    //Image laserImg = new Image(getClass().getResourceAsStream("resources/laser.png"));
                    //ImageView laserIcon = new ImageView(laserImg);
                    //this.setGraphic(laserIcon);
                    setButtonBackground(this, "yellow.png");
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;*/
            default:
                //Image whiteImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                //ImageView whiteIcon = new ImageView(whiteImg);
                //this.setGraphic(whiteIcon);
                //setButtonBackground(this, "white.png");
        }
        this.setOnAction(e -> this.pressed());
    }

    private void pressed(){
        //todo - increase num and cycle to empty
    }
}
