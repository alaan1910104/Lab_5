package Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Dictionary;
import java.util.Hashtable;


public abstract class AbstractScreen {

    //Class abstracte pour les different screens
    protected Scene scene; // scene base
    protected VBox vbox; // Vbox base pour l'scene
    protected final int screenHeight;
    protected final int screenWidth;
    protected Dictionary<String, Button> btns; //dic de bouttons
    protected Dictionary<Integer, TextField> txts; //dic de textfields
    protected Label messageErreur; // message d'erreur qui est seulemnt afficher quand il y a un erreur


    public AbstractScreen(){
        txts = new Hashtable<>();
        btns = new Hashtable<>();
        screenWidth = 600;
        screenHeight = 500;
    }

    public abstract void create();

    public abstract VBox getVbox();

    public abstract Scene getScene();

    public abstract Dictionary<String, Button> getBtns();
}
