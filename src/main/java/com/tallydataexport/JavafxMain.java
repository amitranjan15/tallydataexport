package com.tallydataexport;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

import static com.tallydataexport.common.ConstantText.*;


@Log4j2
@Controller
public class JavafxMain extends Application {
    Preferences userPreferences = Preferences.userRoot();
    @Override
    public void init() throws Exception {
        super.init();
    }


    @Override
    public void start(Stage stage) throws IOException
    {
        // Set the title of the Stage
        stage.setTitle("Hisaab");
        URL url = new File(FXML_URL.concat("Main.fxml")).toURI().toURL();
        log.info(url);
        Parent root=FXMLLoader.load(url);
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        stage.setScene(scene);

        // Display the Stage
        stage.setResizable(false);
        stage.show();
    }

    public void goToTallyPage(ActionEvent actionEvent) throws Exception
    {
        URL url = new File(FXML_URL.concat("TallyConnect.fxml")).toURI().toURL();
        Parent tallyView=FXMLLoader.load(url);

        // Create the Scene
        Scene scene = new Scene(tallyView);
        // Add the scene to the Stage
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);

        // Display the Stage
        stage.setResizable(false);
        stage.show();
    }
}
