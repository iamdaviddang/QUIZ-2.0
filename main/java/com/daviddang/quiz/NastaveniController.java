package com.daviddang.quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert.AlertType;


public class NastaveniController {
    @FXML
    private BorderPane bpNastaveni;

    @FXML
    private Button btnSolo;

    @FXML
    private Button btnTeam;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;

    @FXML
    private TextField textField5;

    @FXML
    private TextField textField6;

    @FXML
    private TextField textField7;

    @FXML
    private TextField textField8;

    @FXML
    private TextField textField9;

    @FXML
    private TextField textField10;

    @FXML
    private TextField textField11;

    @FXML
    private TextField textField12;

    @FXML
    private TextField textField13;

    @FXML
    private TextField textField14;

    @FXML
    public TextField[] textFields = new TextField[14];

    @FXML
    private TextField teamField1;

    @FXML
    private TextField teamField2;

    @FXML
    private TextField teamField3;

    @FXML
    private TextField teamField4;

    @FXML
    private TextField teamField5;

    @FXML
    private TextField teamField6;

    @FXML
    private TextField teamField7;

    @FXML
    private TextField teamField8;

    @FXML
    public TextField[] teamFields = new TextField[8];

    @FXML
    public void initialize() {
        textFields = new TextField[]{textField1, textField2, textField3, textField4, textField5, textField6, textField7, textField8, textField9, textField10, textField11, textField12, textField13, textField14};
        teamFields = new TextField[]{teamField1, teamField2, teamField3, teamField4, teamField5, teamField6, teamField7, teamField8};
    }


    @FXML
    private void modeButtonSolo(ActionEvent event) throws IOException {
        btnSolo.setStyle("-fx-background-color: #1f3b61;");
        btnTeam.setStyle("-fx-background-color: transparent;");
        vyprazdnitSoubor("teamy");
        loadPageNastaveni("paneSolo");
    }

    @FXML
    private void modeButtonTeam(ActionEvent event) throws IOException {
        btnTeam.setStyle("-fx-background-color: #1f3b61;");
        btnSolo.setStyle("-fx-background-color: transparent;");
        vyprazdnitSoubor("hraci");
        loadPageNastaveni("paneTeam");
    }

    @FXML
    private void loadPageNastaveni(String page) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(page + ".fxml"));

        bpNastaveni.setCenter(root);
    }

    @FXML
    private void ulozitJmenaDoSouboru() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\hraci.txt"))) {
            for (int i = 0; i < textFields.length; i++) {
                String jmeno = textFields[i].getText();
                if (!jmeno.isEmpty()) {
                    int hracID = i + 1;
                    writer.write(hracID + ";" + jmeno + ";0");
                    writer.newLine();
                }
            }

            // Zobrazte informační alert po úspěšném uložení.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Uloženo");
            alert.setHeaderText(null);
            alert.setContentText("Jména byla úspěšně uložena. Mužeš spustit hru tlačítkem START");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Zde by měl být zobrazen alert o chybě, pokud uložení selže.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText(null);
            alert.setContentText("Nastala chyba při ukládání jmen.");
            alert.showAndWait();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\gameMode.txt"))) {
            writer.write("solo"); // herni mod zapsan do souboru
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ulozitTeamyDoSouboru() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\teamy.txt"))) {
            for (int i = 0; i < teamFields.length; i++) {
                String team = teamFields[i].getText();
                if (!team.isEmpty()) {
                    int hracID = i + 1;
                    writer.write(hracID + ";" + team + ";0");
                    writer.newLine();
                }
            }

            // Zobrazte informační alert po úspěšném uložení.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Uloženo");
            alert.setHeaderText(null);
            alert.setContentText("Teamy byly úspěšně uložena. Mužeš spustit hru tlačítkem START");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Zde by měl být zobrazen alert o chybě, pokud uložení selže.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText(null);
            alert.setContentText("Nastala chyba při ukládání teamu.");
            alert.showAndWait();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\gameMode.txt"))) {
            writer.write("team"); // herni mod zapsan do souboru
            writer.newLine();
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
