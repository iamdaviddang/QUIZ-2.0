package com.daviddang.quiz;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;
import javafx.scene.control.Alert.AlertType;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuController {

    @FXML
    private Button buttonStart;

    @FXML
    private Button btnPravidla;

    @FXML
    private Button btnNastaveni;

    @FXML
    private Button btnOkruhy;

    @FXML
    private BorderPane bp;

    @FXML
    public void initialize(){
        buttonStart.setText("START");
        buttonStart.setOnAction(this::openQuizWindow);
        vyprazdnitSoubor("hraci");
        vyprazdnitSoubor("teamy");
        vyprazdnitSoubor("gameMode");
    }

    private void openQuizWindow(ActionEvent event) {
        // Získání reference na aktuální okno (Stage)
        Stage currentStage = (Stage) buttonStart.getScene().getWindow();

        try {
            // Načtení nového okna "Quiz.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Quiz.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Vytvoření nového okna (Stage)
            Stage quizStage = new Stage();
            quizStage.setScene(scene);

            // Nastavení režimu celé obrazovky pro nové okno
            quizStage.setFullScreen(true);

            // Zavření aktuálního okna "Menu.fxml"
            currentStage.close();

            // Zobrazte nové okno "Quiz.fxml"
            quizStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void handleCloseIconClick(ActionEvent event) {
        vyprazdnitSoubor();
        Platform.exit(); // Uzavře aplikaci
    }

    @FXML
    private void pageNastaveni(ActionEvent event) throws IOException {
        loadPage("Nastaveni");
        btnNastaveni.setStyle("-fx-background-color: #1f3b61;");
        btnPravidla.setStyle("-fx-background-color: #264a6e;");
        btnOkruhy.setStyle("-fx-background-color: #264a6e;");

    }

    @FXML
    private void pagePravidla(ActionEvent event) throws IOException {
        loadPage("Pravidla");
        btnPravidla.setStyle("-fx-background-color: #1f3b61;");
        btnNastaveni.setStyle("-fx-background-color: #264a6e;");
        btnOkruhy.setStyle("-fx-background-color: #264a6e;");

    }

    @FXML
    private void pageOkruhy(ActionEvent event) throws IOException {
        btnOkruhy.setStyle("-fx-background-radius: 20;");
        btnOkruhy.setStyle("-fx-background-color: #1f3b61;");
        btnPravidla.setStyle("-fx-background-color: #264a6e;");
        btnNastaveni.setStyle("-fx-background-color: #264a6e;");

        loadPage("Okruhy");
    }

    @FXML
    private void loadPage(String page) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(page + ".fxml"));

        bp.setCenter(root);
    }

    @FXML
    private void vyprazdnitSoubor() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\hraci.txt"))) {
            // Pouze otevřete soubor pro zápis, což jej vyprázdní.
        } catch (IOException e) {
            e.printStackTrace();
            // Zde by měl být zobrazen alert o chybě, pokud smazání selže.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText(null);
            alert.setContentText("Nastala chyba při vyprazdňování souboru.");
            alert.showAndWait();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\teamy.txt"))) {
            // Pouze otevřete soubor pro zápis, což jej vyprázdní.
        } catch (IOException e) {
            e.printStackTrace();
            // Zde by měl být zobrazen alert o chybě, pokud smazání selže.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText(null);
            alert.setContentText("Nastala chyba při vyprazdňování souboru.");
            alert.showAndWait();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\gameMode.txt"))) {
            // Pouze otevřete soubor pro zápis, což jej vyprázdní.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vyprazdnitSoubor(String soubor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\" + soubor + ".txt"))) {
            // Pouze otevřete soubor pro zápis, což jej vyprázdní.
        } catch (IOException e) {
            e.printStackTrace();
            // Zde by měl být zobrazen alert o chybě, pokud smazání selže.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText(null);
            alert.setContentText("Nastala chyba při vyprazdňování souboru.");
            alert.showAndWait();
        }
    }
}
