package Tools;

import Screens.Loading;
import Screens.LogIn;
import Screens.SignIn;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StateHandeler {

    private LogIn li;
    private SignIn sn;
    private Loading ld;
    private Stage stg;
    private Scene currentScene;
    private File file;
    private PrintWriter writer;
    private BufferedReader reader;


    public StateHandeler(Stage stage, File fl) throws IOException {
        this.li = new LogIn();
        this.sn = new SignIn();
        this.ld = new Loading();
        this.file = fl;

        this.reader = new BufferedReader( new FileReader(this.file));

        System.out.print(this.file.canWrite() + " " + this.file.canRead());

        this.stg = stage;

        setUp();

        this.currentScene = li.getScene();
        this.stg.setScene(this.currentScene);
    }

    public void transition(Boolean check, Scene newScene) throws IOException {
        if(check && this.currentScene == this.sn.getScene()){
            fileWrite();

            effacer();
        }
        if (check){
            this.currentScene = newScene;
            this.stg.setScene(this.currentScene);
        }
    }

    private void fileWrite() throws IOException {
        this.writer = new PrintWriter( new BufferedWriter( new FileWriter(this.file, true)));
        StringBuilder strgns = new StringBuilder();

        for(int counter = 0; counter < this.sn.getTxts().size() - 1; counter++){
            strgns.append(this.sn.getTxts().get(counter).getText()).append(",");
        }

        for (RadioButton rb: this.sn.getRadioButtons()
        ) {
            if(rb.isSelected()){
                strgns.append(rb.getText()).append(",");
            }
        }

        strgns.append(this.sn.getSpinner().getValue());

        this.writer.println(strgns.toString());
        this.writer.flush();
        this.writer.close();

    }

    private boolean passwordCheck() throws IOException {
       if(!Files.readAllLines(Paths.get("Infos")).isEmpty()) {
            List<String> strings1 =
                    Files.readAllLines(Paths.get("Infos")).
                            stream().
                            map((str) -> str.split(",")).
                            map(str1 -> str1[0]).
                            filter((str) -> str.equals(this.li.getTxts().get(0).getText())).
                            collect(Collectors.toList());

           List<String> strings2 =
                   Files.readAllLines(Paths.get("Infos")).
                           stream().
                           map((str) -> str.split(",")).
                           map(str2 -> str2[3]).
                           filter((str) -> str.equals(this.li.getTxts().get(1).getText())).
                           collect(Collectors.toList());

            return !(strings1.isEmpty() && strings2.isEmpty());
        }
        return true;
    }

    private void transition(Scene newScene){
        this.currentScene = newScene;
        this.stg.setScene(this.currentScene);
        this.li.getMessageErreur().setOpacity(0);
        this.sn.getMessageErreur().setOpacity(0);
    }

    private boolean fileCheck() throws IOException {
        if(!Files.readAllLines(Paths.get("Infos")).isEmpty()) {
            List<String> strings =
                    Files.readAllLines(Paths.get("Infos")).
                            stream().
                            map((str) -> str.split(",")).
                            map(strings1 -> strings1[0]).
                            filter((str) -> str.equals(this.sn.getTxts().get(0).getText())).
                            collect(Collectors.toList());

            return strings.isEmpty();
        }
        return true;
    }

    private boolean check() throws IOException {

        boolean result = true;

        if(this.currentScene == this.li.getScene()){
            if(!passwordCheck()){
                this.li.getMessageErreur().setOpacity(1);
                result = false;
            }
        }
        if(this.currentScene == this.sn.getScene()){
            for (int counter = 0; counter < this.sn.getTxts().size(); counter++){
                if(this.sn.getTxts().get(counter).getText().equals("")){
                    this.sn.getMessageErreur().setText(this.sn.getTxts().get(counter).getPromptText() + " incorrete.");
                    this.sn.getMessageErreur().setOpacity(1);
                    return false;
                }
            }

            if(!this.sn.getTxts().get(3).getText().equals(this.sn.getTxts().get(4).getText())){
                this.sn.getMessageErreur().setText("les mots de passe ne sont pas les memes.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }

            result = false;

            for (RadioButton rb: this.sn.getRadioButtons()
                 ) {
                if(rb.isSelected()){
                    result = true;
                }
            }
            if(!result){
                this.sn.getMessageErreur().setText("Vouz devez choisir une genre.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }

            if(!this.sn.getCb().isSelected()){
                this.sn.getMessageErreur().setText("Vous devez confirmer.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }

            if(!fileCheck()){
                this.sn.getMessageErreur().setText("Nom d'utilisateur est deja utilisé.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }
        }
        return result;
    }


    private void setUp(){
        //Log In Buttons
        this.li.getBtns().get("li").setOnAction(event -> {
            try {
                transition(check(), this.ld.getScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.li.getBtns().get("si").setOnAction(event -> transition(this.sn.getScene()));

        //Sign in buttons
        this.sn.getBtns().get("ins").setOnAction(event -> {
            try {
                transition(check(), this.li.getScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.sn.getBtns().get("ef").setOnAction(event -> effacer());
        this.sn.getBtns().get("ret").setOnAction(event -> transition(this.li.getScene()));

    }

    public  void effacer(){
        //text fields
        for (int counter = 0; counter < this.sn.getTxts().size(); counter++){
            this.sn.getTxts().get(counter).setText("");
        }

        // Radio Buttons
        for (RadioButton rb: this.sn.getRadioButtons()
             ) {
            rb.setSelected(false);
        }

        //Checkbox
        this.sn.getCb().setSelected(false);

        //Spinner
        while(this.sn.getSpinner().getValue() != 18) {
            if (this.sn.getSpinner().getValue() > 18) {
                this.sn.getSpinner().decrement();
            } else {this.sn.getSpinner().increment();}
        }
    }

    public Scene getCurrentScene() {
        return this.currentScene;
    }
}
