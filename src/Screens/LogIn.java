package Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Dictionary;
import java.util.Map;

public class LogIn extends AbstractScreen{

    public LogIn(){
        create();
        this.scene = new Scene(this.vbox);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public VBox getVbox(){return this.vbox;}

    public Dictionary<Integer, TextField> getTxts(){ return this.txts;}

    public Label getMessageErreur(){return this.messageErreur;}

    @Override
    public Dictionary<String, Button> getBtns() {
        return this.btns;
    }

    @Override
    public void create() {
        // Labels
        Label lbl1 = new Label("Nom d'utilisateur");

        Label lbl2 = new Label("Mot de passe");

        this.messageErreur = new Label("Information Incorrecte!");
        this.messageErreur.setTextFill(Color.RED);
        this.messageErreur.setOpacity(0);

        //Button
        Button logInButton = new Button("Se connecter");
        this.btns.put("li", logInButton);

        Button signInButton = new Button("S'inscrire");
        this.btns.put("si", signInButton);

        //Fields
        TextField userField = new TextField();
        userField.setPromptText("Nom d'utilisateur");
        userField.setMaxWidth(screenWidth/4);
        this.txts.put(0, userField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(screenWidth/4);
        this.txts.put(1, passwordField);

        // VBox
        VBox logInVox = new VBox(10);
        logInVox.setMinSize(screenWidth, screenHeight);
        logInVox.setAlignment(Pos.CENTER);
        logInVox.getChildren().addAll(
                lbl1,
                userField,
                lbl2,
                passwordField,
                logInButton,
                signInButton,
                this.messageErreur
        );

        this.vbox = logInVox;
    }
}
