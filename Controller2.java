package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int requestItems = 0, responseItems = 0;
    @FXML
    private Label scene2Label;
    @FXML
    private Accordion accordion;
    @FXML
    private  Button backButton;
    private ArrayList<ArrayList<Fields>> fieldsArrayList;
    private ArrayList<TreeItem<String>> requestBranchItems = new ArrayList<>();
    private ArrayList<TreeItem<String>> responseBranchItems = new ArrayList<>();
    private ArrayList<TreeItem<String>> requestLeafItems = new ArrayList<>();
    private ArrayList<TreeItem<String>> responseLeafItems = new ArrayList<>();
    private ArrayList<TreeItem<String>> rootItem = new ArrayList<>();
    public Controller2 () {}
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws NullPointerException {
        UtilityClass utilityClass = new UtilityClass();
        try {
            fieldsArrayList = utilityClass.readExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene2Label.setText("Number of APIs Found: " + utilityClass.getAPINames().size());
        ArrayList<TreeView<String>> treeView = new ArrayList<>();
        for (int i = 0; i < fieldsArrayList.size(); i++) {
            requestLeafItems.add(new TreeItem<>("Request"));
            responseLeafItems.add(new TreeItem<>("Response"));
            rootItem.add(new TreeItem<>("Root"));
            rootItem.get(i).getChildren().add(requestLeafItems.get(i));
            rootItem.get(i).getChildren().add(responseLeafItems.get(i));
            treeView.add(new TreeView<>(rootItem.get(i)));
            treeView.get(i).setShowRoot(false);
            accordion.getPanes().add(new TitledPane(utilityClass.getAPINames().get(i), treeView.get(i)));
            for (int j = 0; j < fieldsArrayList.get(i).size(); j++) {
                if (fieldsArrayList.get(i).get(j).getRequest().equalsIgnoreCase("I")) {
                    if (fieldsArrayList.get(i).get(j).isObject()) {
                        requestBranchItems.add(new TreeItem<>(fieldsArrayList.get(i).get(j).getType()));
                        requestLeafItems.get(i).getChildren().addAll(requestBranchItems.get(requestItems));
                        requestItems++;
                    }
                    if (fieldsArrayList.get(i).get(j).isObject() && (!fieldsArrayList.get(i).get(j).getType().equalsIgnoreCase(fieldsArrayList.get(i).get(j).getParent()))) {
                        for (int k = 0; k < requestBranchItems.size(); k++) {
                            if (fieldsArrayList.get(i).get(j).getParent().equalsIgnoreCase(requestBranchItems.get(k).getValue())) {
                                requestBranchItems.get(k).getChildren().add(new TreeItem<>(fieldsArrayList.get(i).get(j).getType()));
                            }
                        }
                    } else if (!fieldsArrayList.get(i).get(j).isObject()) {
                        for (int fieldsItems = 0; fieldsItems < requestBranchItems.size(); fieldsItems++) {
                            if (fieldsArrayList.get(i).get(j).getParent().equalsIgnoreCase(requestBranchItems.get(fieldsItems).getValue())) {
                                requestBranchItems.get(fieldsItems).getChildren().add(new TreeItem<>((fieldsArrayList.get(i).get(j).getArrayOfStrings().get((fieldsArrayList.get(i).get(j).getArrayOfStrings().size()) - 1)) + " - Allowed Values: " + fieldsArrayList.get(i).get(j).getAllowedValues() + " - Mandatory: " + fieldsArrayList.get(i).get(j).getMandatory()));
                            }
                        }
                        if (fieldsArrayList.get(i).get(j).getParent().contains("field")) {
                            requestLeafItems.get(i).getChildren().add(new TreeItem<>((fieldsArrayList.get(i).get(j).getArrayOfStrings().get((fieldsArrayList.get(i).get(j).getArrayOfStrings().size()) - 1)) + " - Allowed Values: " + fieldsArrayList.get(i).get(j).getAllowedValues() + " - Mandatory: " + fieldsArrayList.get(i).get(j).getMandatory()));
                        }
                    }
                } else if (fieldsArrayList.get(i).get(j).getRequest().equalsIgnoreCase("O")) {
                    if (fieldsArrayList.get(i).get(j).isObject()) {
                        responseBranchItems.add(new TreeItem<>(fieldsArrayList.get(i).get(j).getType()));
                        responseLeafItems.get(i).getChildren().addAll(responseBranchItems.get(responseItems));
                        responseItems++;
                    }
                    if (fieldsArrayList.get(i).get(j).isObject() && (!fieldsArrayList.get(i).get(j).getType().equalsIgnoreCase(fieldsArrayList.get(i).get(j).getParent()))) {
                        for (int k = 0; k < responseBranchItems.size(); k++) {
                            if (fieldsArrayList.get(i).get(j).getParent().equalsIgnoreCase(responseBranchItems.get(k).getValue())) {
                                responseBranchItems.get(k).getChildren().add(new TreeItem<>(fieldsArrayList.get(i).get(j).getType()));
                            }
                        }
                    } else if (!fieldsArrayList.get(i).get(j).isObject()) {
                        for (int fieldsItems = 0; fieldsItems < responseBranchItems.size(); fieldsItems++) {
                            if (fieldsArrayList.get(i).get(j).getParent().equalsIgnoreCase(responseBranchItems.get(fieldsItems).getValue())) {
                                responseBranchItems.get(fieldsItems).getChildren().add(new TreeItem<>((fieldsArrayList.get(i).get(j).getArrayOfStrings().get((fieldsArrayList.get(i).get(j).getArrayOfStrings().size()) - 1)) + " - Allowed Values: " + fieldsArrayList.get(i).get(j).getAllowedValues() + " - Mandatory: " + fieldsArrayList.get(i).get(j).getMandatory()));
                            }
                        }
                        if (fieldsArrayList.get(i).get(j).getParent().contains("field")) {
                            responseLeafItems.get(i).getChildren().add(new TreeItem<>((fieldsArrayList.get(i).get(j).getArrayOfStrings().get((fieldsArrayList.get(i).get(j).getArrayOfStrings().size()) - 1)) + " - Allowed Values: " + fieldsArrayList.get(i).get(j).getAllowedValues() + " - Mandatory: " + fieldsArrayList.get(i).get(j).getMandatory()));
                        }
                    }
                }
            }
            requestItems = 0;
            responseItems = 0;
            requestBranchItems.clear();
            responseBranchItems.clear();
        }
    }
}
