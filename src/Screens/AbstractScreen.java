package Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public abstract class AbstractScreen {
    protected Scene scene;
    protected VBox vbox;
    protected final int screenHeight;
    protected final int screenWidth;
    protected Dictionary<String, Button> btns;
    protected Dictionary<Integer, TextField> txts;
    protected Label messageErreur;


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
