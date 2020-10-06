import Tools.StateHandeler;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){ launch(args);}


    @Override
    public void start(Stage pstage) throws IOException {

        File file = new File("Infos");

        StateHandeler st = new StateHandeler(pstage, file);

        pstage.setTitle("Lab 5!");
        pstage.show();
    }
}
