package Tools;

import Screens.Loading;
import Screens.LogIn;
import Screens.SignIn;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StateHandeler {

    // Classe pour determiner les changements de scene, les changements dans le file et autres elements
    private LogIn li;
    private SignIn sn;
    private Loading ld;
    private Stage stg;
    private Scene currentScene;
    private File file;


    //Constructeur
    public StateHandeler(Stage stage, File fl) throws IOException {
        this.li = new LogIn();
        this.sn = new SignIn();
        this.ld = new Loading();
        this.file = fl;
        this.stg = stage;

        setUp();

        this.currentScene = li.getScene();
        this.stg.setScene(this.currentScene);
    }


    // Set up initial, set les actions des buttons
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


    // Trainsition d'une scene a l'autre, si le check est reussi
    public void transition(Boolean check, Scene newScene) throws IOException {
        //ecrire dans le file seulment si nous somme sur l'scene du sign in
        if(check && this.currentScene == this.sn.getScene()){
            fileWrite();
            effacer();
        }

        // si le check est reussi, changer l'scene
        if (check){
            this.currentScene = newScene;
            this.stg.setScene(this.currentScene);
        }
    }


    // Transion sans check
    private void transition(Scene newScene){
        this.currentScene = newScene;
        this.stg.setScene(this.currentScene);
        this.li.getMessageErreur().setOpacity(0);
        this.sn.getMessageErreur().setOpacity(0);
    }


    // Grader les infos dans le file
    private void fileWrite() throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.file, true)));
        StringBuilder strgns = new StringBuilder();

        //strings des Textfields
        for(int counter = 0; counter < this.sn.getTxts().size() - 1; counter++){
            strgns.append(this.sn.getTxts().get(counter).getText()).append(",");
        }

        // genre corché
        for (RadioButton rb: this.sn.getRadioButtons()
        ) {
            if(rb.isSelected()){
                strgns.append(rb.getText()).append(",");
            }
        }

        // l'age
        strgns.append(this.sn.getSpinner().getValue());

        // write
        writer.println(strgns.toString());
        writer.flush();
        writer.close();

    }


    //checker chaque ligne du file pour determier si le nom d'utilisateur est déja utilisé
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


    //checker si le nom d'utilisater et le password sont déja dans le ficher
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


    // checker plusieurs choses, si tout est correct returner true, sinon cahnger et afficher le message d'erreur
    private boolean check() throws IOException {

        boolean result = true;

        // checker si le nom d'utilisateur et le password sont corrects
        if(this.currentScene == this.li.getScene()){
            if(!passwordCheck()){
                this.li.getMessageErreur().setOpacity(1);
                result = false;
            }
        }

        //checker que tout est correct dans le sign in
        if(this.currentScene == this.sn.getScene()){

            //checker si quelque textfield est vide
            for (int counter = 0; counter < this.sn.getTxts().size(); counter++){
                if(this.sn.getTxts().get(counter).getText().equals("")){
                    this.sn.getMessageErreur().setText(this.sn.getTxts().get(counter).getPromptText() + " incorrete.");
                    this.sn.getMessageErreur().setOpacity(1);
                    return false;
                }
            }

            //checker si la confirmation de mot de passe est reussi
            if(!this.sn.getTxts().get(3).getText().equals(this.sn.getTxts().get(4).getText())){
                this.sn.getMessageErreur().setText("les mots de passe ne sont pas les memes.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }


            //si quelque radio button est selected
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

            //checker si les condisions sont acceptes
            if(!this.sn.getCb().isSelected()){
                this.sn.getMessageErreur().setText("Vous devez confirmer.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }

            //checker si le nom d'utilisateur est deja dans le file
            if(!fileCheck()){
                this.sn.getMessageErreur().setText("Nom d'utilisateur est deja utilisé.");
                this.sn.getMessageErreur().setOpacity(1);
                return false;
            }
        }
        //returner le result
        return result;
    }


    //effacer tous les infos dans la page de Sign in
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

}
