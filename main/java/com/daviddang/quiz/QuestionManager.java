package com.daviddang.quiz;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;
import java.util.*;

public class QuestionManager {

    @FXML
    private Button buttonUkoncitHru;

    @FXML
    private Pane option1;

    @FXML
    private Pane option2;

    @FXML
    private Pane option3;

    @FXML
    private Pane option4;

    @FXML
    private Text txtQuestion;

    @FXML
    private Text txtOption1;

    @FXML
    private Text txtOption2;

    @FXML
    private Text txtOption3;

    @FXML
    private Text txtOption4;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane4;

    @FXML
    private Pane pane5;

    @FXML
    private Pane pane6;

    @FXML
    private Pane pane7;

    @FXML
    private Pane pane8;

    @FXML
    private Pane pane9;

    @FXML
    private Pane pane10;

    @FXML
    private Pane pane11;

    @FXML
    private Pane pane12;

    @FXML
    private Pane pane13;

    @FXML
    private Pane pane14;

    @FXML Pane paneBox1;


    private final List<Question> questions;
    private final List<Question> usedQuestions;
    private final Random random;
    private Question currentQuestion;  // Uchovává aktuální otázku

    ArrayList<Hrac> seznamHracu = nactiHraceZeSouboru();
    ArrayList<Team> seznamTeamu = nactiTeamyZeSouboru();
    ArrayList<Pane> seznamPanelu = new ArrayList<>();
    private int currentPlayerIndex = 0;
    boolean roundCompleted = false;
    private int bodyVAktualnimKole;





    @FXML
    public void initialize() {


        seznamPanelu.add(pane1);
        seznamPanelu.add(pane2);
        seznamPanelu.add(pane3);
        seznamPanelu.add(pane4);
        seznamPanelu.add(pane5);
        seznamPanelu.add(pane6);
        seznamPanelu.add(pane7);
        seznamPanelu.add(pane8);
        seznamPanelu.add(pane9);
        seznamPanelu.add(pane10);
        seznamPanelu.add(pane11);
        seznamPanelu.add(pane12);
        seznamPanelu.add(pane13);
        seznamPanelu.add(pane14);
        option1.setDisable(false);
        option2.setDisable(false);
        option3.setDisable(false);
        option4.setDisable(false);


        if (!seznamHracu.isEmpty()) {


            for (int i = 0; i < Math.min(seznamHracu.size(), seznamPanelu.size()); i++) {
                Hrac hrac = seznamHracu.get(i); // Získáme hráče
                Pane panel = seznamPanelu.get(i); // Získáme panel

                // Nastavíme text do panelu
                Text jmenoText = (Text) panel.lookup("#paneJmeno" + (i + 1)); // Získáme Text element pro jméno
                Text bodyText = (Text) panel.lookup("#paneBody" + (i + 1)); // Získáme Text element pro body

                if (jmenoText != null) {
                    jmenoText.setText(hrac.getJmeno());
                }

                if (bodyText != null) {
                    bodyText.setText(String.valueOf(hrac.getBody()));
                }

                // Nastavte panel jako viditelný, pokud byl původně skrytý
                panel.setVisible(true);
            }
        } else {
            System.out.println("Zadni hrac nebude zobrazen");
        }

        if (!seznamTeamu.isEmpty()) {

            // Projdeme seznam hráčů a seznam panelů
            for (int i = 0; i < Math.min(seznamTeamu.size(), seznamPanelu.size()); i++) {
                Team team = seznamTeamu.get(i); // Získáme hráče
                Pane panel = seznamPanelu.get(i); // Získáme panel

                // Nastavíme text do panelu
                Text jmenoText = (Text) panel.lookup("#paneJmeno" + (i + 1)); // Získáme Text element pro jméno
                Text bodyText = (Text) panel.lookup("#paneBody" + (i + 1)); // Získáme Text element pro body

                if (jmenoText != null) {
                    jmenoText.setText(team.getName());
                }

                if (bodyText != null) {
                    bodyText.setText(String.valueOf(team.getScore()));
                }

                // Nastavte panel jako viditelný, pokud byl původně skrytý
                panel.setVisible(true);
            }
        } else {
            System.out.println();
        }

        currentPlayerIndex = 0;
        markCurrentPlayer();


        buttonUkoncitHru.setOnAction(this::ukoncitHru);

        txtQuestion.setText(showNextQuestion());

        System.out.println("===================================");
        System.out.println("Otazka: " + txtQuestion.getText());
        System.out.println("Odpoved: " + showAnswer());
        System.out.println("===================================");

        txtOption1.setText(showOption1());
        option1.setStyle("-fx-background-color:  #1c1d1d;");

        txtOption2.setText(showOption2());
        option2.setStyle("-fx-background-color:  #1c1d1d;");

        txtOption3.setText(showOption3());
        option3.setStyle("-fx-background-color:  #1c1d1d;");

        txtOption4.setText(showOption4());
        option4.setStyle("-fx-background-color:  #1c1d1d;");

        option1.setOnMouseClicked(event -> {
            if (!roundCompleted) { // Přidávání bodů bude provedeno pouze jednou za kolo
                String optionText = txtOption1.getText();
                String correctAnswer = showAnswer();
                if (Objects.equals(optionText, correctAnswer)) {
                    option1.setStyle("-fx-background-color: #67eb34;");
                    option1.setDisable(true);
                    option2.setDisable(true);
                    option3.setDisable(true);
                    option4.setDisable(true);

                    // Přidání bodů hráči nebo týmu
                    int aktualniHracIndex = currentPlayerIndex;
                    int bodyZaSpravnouOdpoved = 10;
                    bodyVAktualnimKole += bodyZaSpravnouOdpoved;
                    try {
                        if (loadGameMode().equals("solo")) {
                            int aktualniBodyHrace = seznamHracu.get(aktualniHracIndex).getBody();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamHracu.get(aktualniHracIndex).setBody(zapisBodu);
                        } else {
                            int aktualniBodyHrace = seznamTeamu.get(aktualniHracIndex).getScore();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamTeamu.get(aktualniHracIndex).setScore(zapisBodu);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    roundCompleted = true; // Kolo bylo dokončeno
                } else {
                    option1.setStyle("-fx-background-color: red;");
                    markCorrectOption();
                }

                refreshPoints();
            }
        });



        option2.setOnMouseClicked(event -> {
            if (!roundCompleted) { // Přidávání bodů bude provedeno pouze jednou za kolo
                String optionText = txtOption2.getText();
                String correctAnswer = showAnswer();
                if (Objects.equals(optionText, correctAnswer)) {
                    option2.setStyle("-fx-background-color: #67eb34;");
                    option1.setDisable(true);
                    option2.setDisable(true);
                    option3.setDisable(true);
                    option4.setDisable(true);

                    // Přidání bodů hráči nebo týmu
                    int aktualniHracIndex = currentPlayerIndex;
                    int bodyZaSpravnouOdpoved = 10;
                    bodyVAktualnimKole += bodyZaSpravnouOdpoved;
                    try {
                        if (loadGameMode().equals("solo")) {
                            int aktualniBodyHrace = seznamHracu.get(aktualniHracIndex).getBody();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamHracu.get(aktualniHracIndex).setBody(zapisBodu);
                        } else {
                            int aktualniBodyHrace = seznamTeamu.get(aktualniHracIndex).getScore();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamTeamu.get(aktualniHracIndex).setScore(zapisBodu);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    roundCompleted = true; // Kolo bylo dokončeno
                } else {
                    option2.setStyle("-fx-background-color: red;");
                    markCorrectOption();
                }

                refreshPoints();
            }
        });

        option3.setOnMouseClicked(event -> {
            if (!roundCompleted) { // Přidávání bodů bude provedeno pouze jednou za kolo
                String optionText = txtOption3.getText();
                String correctAnswer = showAnswer();
                if (Objects.equals(optionText, correctAnswer)) {
                    option3.setStyle("-fx-background-color: #67eb34;");
                    option1.setDisable(true);
                    option2.setDisable(true);
                    option3.setDisable(true);
                    option4.setDisable(true);

                    // Přidání bodů hráči nebo týmu
                    int aktualniHracIndex = currentPlayerIndex;
                    int bodyZaSpravnouOdpoved = 10;
                    bodyVAktualnimKole += bodyZaSpravnouOdpoved;
                    try {
                        if (loadGameMode().equals("solo")) {
                            int aktualniBodyHrace = seznamHracu.get(aktualniHracIndex).getBody();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamHracu.get(aktualniHracIndex).setBody(zapisBodu);
                        } else {
                            int aktualniBodyHrace = seznamTeamu.get(aktualniHracIndex).getScore();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamTeamu.get(aktualniHracIndex).setScore(zapisBodu);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    roundCompleted = true; // Kolo bylo dokončeno
                } else {
                    option3.setStyle("-fx-background-color: red;");
                    markCorrectOption();
                }

                refreshPoints();
            }
        });

        option4.setOnMouseClicked(event -> {
            if (!roundCompleted) { // Přidávání bodů bude provedeno pouze jednou za kolo
                String optionText = txtOption4.getText();
                String correctAnswer = showAnswer();
                if (Objects.equals(optionText, correctAnswer)) {
                    option4.setStyle("-fx-background-color: #67eb34;");
                    option1.setDisable(true);
                    option2.setDisable(true);
                    option3.setDisable(true);
                    option4.setDisable(true);

                    // Přidání bodů hráči nebo týmu
                    int aktualniHracIndex = currentPlayerIndex;
                    int bodyZaSpravnouOdpoved = 10;
                    bodyVAktualnimKole += bodyZaSpravnouOdpoved;
                    try {
                        if (loadGameMode().equals("solo")) {
                            int aktualniBodyHrace = seznamHracu.get(aktualniHracIndex).getBody();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamHracu.get(aktualniHracIndex).setBody(zapisBodu);
                        } else {
                            int aktualniBodyHrace = seznamTeamu.get(aktualniHracIndex).getScore();
                            int zapisBodu = bodyZaSpravnouOdpoved + aktualniBodyHrace;
                            seznamTeamu.get(aktualniHracIndex).setScore(zapisBodu);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    roundCompleted = true; // Kolo bylo dokončeno
                } else {
                    option4.setStyle("-fx-background-color: red;");
                    markCorrectOption();
                }

                refreshPoints();
            }
        });
    }

    private void markCorrectOption() {
        if (txtOption1.getText().equals(showAnswer())) {
            option1.setStyle("-fx-background-color: #67eb34;");
        } else if (txtOption2.getText().equals(showAnswer())) {
            option2.setStyle("-fx-background-color: #67eb34;");
        } else if (txtOption3.getText().equals(showAnswer())) {
            option3.setStyle("-fx-background-color: #67eb34;");
        } else if (txtOption4.getText().equals(showAnswer())) {
            option4.setStyle("-fx-background-color: #67eb34;");
        } else {
            System.out.println("Chyba marku");
        }
    }

    private void markCurrentPlayer() {
        // Smažte podtržení předchozího hráče (pokud je to implementováno)
        seznamPanelu.get(currentPlayerIndex).getStyleClass().remove("player-marked");

        // Podtrhněte aktuálního hráče
        seznamPanelu.get(currentPlayerIndex).getStyleClass().add("player-marked");
    }


    private void ukoncitHru(ActionEvent event) {
        // Získání reference na aktuální okno (Stage)
        Stage currentStage = (Stage) buttonUkoncitHru.getScene().getWindow();

        try {
            // Načtení nového okna "Quiz.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Vytvoření nového okna (Stage)
            Stage menuStage = new Stage();
            menuStage.setScene(scene);

            // Zavření aktuálního okna "Menu.fxml"
            currentStage.close();

            // Zobrazte nové okno "Quiz.fxml"
            menuStage.initStyle(StageStyle.UNDECORATED);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCloseIconClick() {
        vyprazdnitSoubor();
        Platform.exit(); // Uzavře aplikaci
    }

    @FXML
    private void nextQuestion() {
        roundCompleted = false;
        bodyVAktualnimKole = 0;


        // Odznačení předchozího hráče
        seznamPanelu.get(currentPlayerIndex).getStyleClass().remove("player-marked");

        int visiblePlayerCount = 0; // Počet viditelných hráčů

        // Zjistěte, kolik hráčů je viditelných
        for (Pane pane : seznamPanelu) {
            if (pane.isVisible()) {
                visiblePlayerCount++;
            }
        }

        if (visiblePlayerCount > 0) {
            if (currentPlayerIndex == visiblePlayerCount - 1) {
                // Pokud jsme na posledním viditelném hráči, nastavíme currentPlayerIndex na 0
                currentPlayerIndex = 0;
            } else {
                // Jinak přejdeme na dalšího viditelného hráče
                currentPlayerIndex++;
            }

            // Označení aktuálního hráče
            markCurrentPlayer();
        }

        // Inicializace pro novou otázku
        txtQuestion.setText(showNextQuestion());
        txtOption1.setText(showOption1());
        option1.setStyle("-fx-background-color:  #1c1d1d;");
        txtOption2.setText(showOption2());
        option2.setStyle("-fx-background-color:  #1c1d1d;");
        txtOption3.setText(showOption3());
        option3.setStyle("-fx-background-color:  #1c1d1d;");
        txtOption4.setText(showOption4());
        option4.setStyle("-fx-background-color:  #1c1d1d;");
        option1.setDisable(false);
        option2.setDisable(false);
        option3.setDisable(false);
        option4.setDisable(false);
        System.out.println("===================================");
        System.out.println("Otazka: " + txtQuestion.getText());
        System.out.println("Odpoved: " + showAnswer());
        System.out.println("===================================");
    }


    public QuestionManager() {
        questions = new ArrayList<>();
        usedQuestions = new ArrayList<>();
        random = new Random();
        loadQuestions();
    }

    public String showNextQuestion() {

        if (questions.isEmpty()) {
            return "KONEC HRY";
        }

        int randomIndex = random.nextInt(questions.size());
        currentQuestion = questions.get(randomIndex);

        usedQuestions.add(currentQuestion);
        questions.remove(currentQuestion);

        return currentQuestion.getQuestion();
    }

    public String showOption1() {
        if (currentQuestion == null) {
            return "Zatím nebyla zobrazena žádná otázka.";
        }
        return currentQuestion.getOptions()[0];
    }

    public String showOption2() {
        if (currentQuestion == null) {
            return "Zatím nebyla zobrazena žádná otázka.";
        }
        return currentQuestion.getOptions()[1];
    }

    public String showOption3() {
        if (currentQuestion == null) {
            return "Zatím nebyla zobrazena žádná otázka.";
        }
        return currentQuestion.getOptions()[2];
    }

    public String showOption4() {
        if (currentQuestion == null) {
            return "Zatím nebyla zobrazena žádná otázka.";
        }
        return currentQuestion.getOptions()[3];
    }

    public String showAnswer() {
        if (currentQuestion == null) {
            return "Zatím nebyla zobrazena žádná otázka.";
        }
        return currentQuestion.getCorrectAnswer();
    }

    private void loadQuestions() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\data.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String question = parts[1];
                    String correctAnswer = parts[2];
                    String[] options = new String[]{parts[3], parts[4], parts[5], parts[6]};
                    Question q = new Question(id, question, correctAnswer, options);
                    questions.add(q);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vyprazdnitSoubor() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\hraci.txt"))) {
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

    private String loadGameMode() throws FileNotFoundException {
        String valueFromFile;
        File file = new File("src\\main\\resources\\gameMode.txt");
        Scanner scanner = new Scanner(file);
        if (scanner.hasNextLine()) {
            valueFromFile = scanner.nextLine();
            scanner.close();
            return valueFromFile;
        } else {
            return "";
        }
    }

    public ArrayList<Hrac> nactiHraceZeSouboru() {
        ArrayList<Hrac> seznamHracu = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\hraci.txt"))) {
            String radek;
            while ((radek = br.readLine()) != null) {
                String[] polozky = radek.split(";");
                if (polozky.length == 3) {
                    int id = Integer.parseInt(polozky[0]);
                    String jmeno = polozky[1];
                    int body = Integer.parseInt(polozky[2]);

                    Hrac hrac = new Hrac(id, jmeno, body);
                    seznamHracu.add(hrac);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return seznamHracu;
    }

    public ArrayList<Team> nactiTeamyZeSouboru() {
        ArrayList<Team> seznamTeamu = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\teamy.txt"))) {
            String radek;
            while ((radek = br.readLine()) != null) {
                String[] polozky = radek.split(";");
                if (polozky.length == 3) {
                    int id = Integer.parseInt(polozky[0]);
                    String jmeno = polozky[1];
                    int body = Integer.parseInt(polozky[2]);

                    Team team = new Team(jmeno, body);
                    seznamTeamu.add(team);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return seznamTeamu;
    }

    public void refreshPoints(){
        if (!seznamHracu.isEmpty()) {


            for (int i = 0; i < Math.min(seznamHracu.size(), seznamPanelu.size()); i++) {
                Hrac hrac = seznamHracu.get(i); // Získáme hráče
                Pane panel = seznamPanelu.get(i); // Získáme panel

                // Nastavíme text do panelu
                Text jmenoText = (Text) panel.lookup("#paneJmeno" + (i + 1)); // Získáme Text element pro jméno
                Text bodyText = (Text) panel.lookup("#paneBody" + (i + 1)); // Získáme Text element pro body

                if (jmenoText != null) {
                    jmenoText.setText(hrac.getJmeno());
                }

                if (bodyText != null) {
                    bodyText.setText(String.valueOf(hrac.getBody()));
                }

                // Nastavte panel jako viditelný, pokud byl původně skrytý
                panel.setVisible(true);
            }
        } else {
            System.out.println();
        }

        if (!seznamTeamu.isEmpty()) {

            // Projdeme seznam hráčů a seznam panelů
            for (int i = 0; i < Math.min(seznamTeamu.size(), seznamPanelu.size()); i++) {
                Team team = seznamTeamu.get(i); // Získáme hráče
                Pane panel = seznamPanelu.get(i); // Získáme panel

                // Nastavíme text do panelu
                Text jmenoText = (Text) panel.lookup("#paneJmeno" + (i + 1)); // Získáme Text element pro jméno
                Text bodyText = (Text) panel.lookup("#paneBody" + (i + 1)); // Získáme Text element pro body

                if (jmenoText != null) {
                    jmenoText.setText(team.getName());
                }

                if (bodyText != null) {
                    bodyText.setText(String.valueOf(team.getScore()));
                }

                // Nastavte panel jako viditelný, pokud byl původně skrytý
                panel.setVisible(true);
            }
        } else {
            System.out.println();
        }
    }
}