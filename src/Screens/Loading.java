package Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Dictionary;
import java.util.Map;

public class Loading extends AbstractScreen{

    public Loading(){
        create();
        this.scene = new Scene(this.vbox);
}

    public Scene getScene() {
        return this.scene;
    }

    public VBox getVbox(){return this.vbox;}

    @Override
    public Dictionary<String, Button> getBtns() {
        return this.btns;
    }

    @Override
    public void create() {
        // progress
        ProgressIndicator pi1 = new ProgressIndicator();

        //texte
        Label lbl1 = new Label("Chargement");

        //Vbox
        VBox loadingVox = new VBox(14);
        loadingVox.setMinSize(screenWidth, screenHeight);
        loadingVox.setAlignment(Pos.CENTER);
        loadingVox.getChildren().addAll(pi1, lbl1);

        this.vbox = loadingVox;
    }
}
