package Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Dictionary;
import java.util.Map;

public class SignIn extends AbstractScreen{

    private ToggleGroup rbGroup;
    private Spinner<Integer> spinner;
    private CheckBox cb;
    private RadioButton[] radioButtons;

    public SignIn(){

        create();
        this.scene = new Scene(this.vbox);
    }

    public Scene getScene() {
        return this.scene;
    }

    public VBox getVbox(){return this.vbox;}

    public Spinner<Integer> getSpinner() {
        return spinner;
    }

    public Label getMessageErreur(){return this.messageErreur;}

    public CheckBox getCb() {
        return cb;
    }

    public RadioButton[] getRadioButtons(){ return this.radioButtons;}

    public Dictionary<Integer, TextField> getTxts(){ return this.txts;}

    @Override
    public Dictionary<String, Button> getBtns() {
        return this.btns;
    }

    @Override
    public void create() {
        //Labels
        Label lbl1 = new Label("Prénom");
        Label lbl2 = new Label("Nom de Famille");
        Label lbl3 = new Label("Nom d'utilisateur");
        Label lbl4 = new Label("Mot de passe");
        Label lbl5 = new Label("Confirmer le mote de passe");
        Label lbl6 = new Label("Genre");
        Label lbl7 = new Label("Age");

        this.messageErreur = new Label();
        this.messageErreur.setTextFill(Color.RED);
        this.messageErreur.setOpacity(0);

        //Buttons
        Button btn1 = new Button("S'inscrire");
        this.btns.put("ins", btn1);
        Button btn2 = new Button("Effacer");
        this.btns.put("ef", btn2);
        Button btn3 = new Button("Retour");
        this.btns.put("ret", btn3);

        //Fields
        TextField textField1 = new TextField();
        textField1.setPromptText("Prénom");
        textField1.setMaxWidth(screenWidth/4);
        this.txts.put(1, textField1);

        TextField textField2 = new TextField();
        textField2.setPromptText("Nom de Famille");
        textField2.setMaxWidth(screenWidth/4);
        this.txts.put(2, textField2);

        TextField textField3 = new TextField();
        textField3.setPromptText("Nom d'utilisateur");
        textField3.setMaxWidth(screenWidth/4);
        this.txts.put(0, textField3);

        PasswordField passwordField1 = new PasswordField();
        passwordField1.setPromptText("Mot de passe");
        passwordField1.setMaxWidth(screenWidth/4);
        this.txts.put(3, passwordField1);

        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Mot de passe");
        passwordField2.setMaxWidth(screenWidth/4);
        this.txts.put(4, passwordField2);

        //Spinner
        this.spinner = new Spinner<>(0,99,18);

        //checkbox
        this.cb = new CheckBox("J'accepte Les conditions");

        //Radio Buttons
        this.rbGroup = new ToggleGroup();
        radioButtons = new RadioButton[3];

        RadioButton rb1 = new RadioButton("Homme");
        rb1.setToggleGroup(this.rbGroup);
        radioButtons[0] = rb1;
        RadioButton rb2 = new RadioButton("Femme");
        rb2.setToggleGroup(this.rbGroup);
        radioButtons[1] = rb2;
        RadioButton rb3 = new RadioButton("Autre");
        rb3.setToggleGroup(this.rbGroup);
        radioButtons[2] = rb3;

        //
        VBox signInVox = new VBox(8);
        signInVox.setMinSize(screenWidth, screenHeight * 1.20);
        signInVox.setAlignment(Pos.CENTER);

        signInVox.getChildren().addAll(
                lbl1,
                textField1,
                lbl2,
                textField2,
                lbl3,
                textField3,
                lbl4,
                passwordField1,
                lbl5,
                passwordField2,
                lbl6,
                rb1,
                rb2,
                rb3,
                lbl7,
                this.spinner,
                this.cb,
                btn1,
                btn2,
                btn3,
                this.messageErreur
                );

        this.vbox = signInVox;
    }
}
