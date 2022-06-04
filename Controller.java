package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Controller{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String filePath;
    @FXML
    private Button selectButton, nextButton;
    @FXML
    private Label scene1Label;
    @FXML
    private Pane scene1Pane;
    public void switchScenes (javafx.event.ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void selectFile (ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            scene1Label.setText(selectedFile.getName());
            filePath = selectedFile.getAbsolutePath();
            UtilityClass.setFilePath(filePath);
        }
        else {
            scene1Label.setText("File is not valid");
        }
    }
}
