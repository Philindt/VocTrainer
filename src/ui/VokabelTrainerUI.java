package ui;

import java.io.IOException;
import java.util.List;
import dao.WoerterBuchDAO;
import dao.WoerterBuchDAOImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.WoerterBuch;
import model.Wort;


/**
 * @author Christian Pollok
 * @author Lukas Maier
 * @author Philipp Lindt
 * 
 */
public class VokabelTrainerUI extends Application {

	private static final String PREFIX = "Die Übersetzung war richtig! Nächstes Wort wurde zufällig ausgewählt";
	private static final String PREFIX2 = "Die Übersetzung war falsch! Versuche es noch einmal!";

	// Erster Tab
	private Parent root;
	private Label titel;
	private Label zuÜbersetzendesWort;
	private Label richtung;
	private TextField eingabe;
	private Button bestätigen;
	private Button richtungÄndern;
	private Label feedback;
	
	// Zweiter Tab
	private Label neuesWort;
	private TextField deutsch;
	private TextField englisch;
	private Button hinzufügen;
	private Button speichern;
	
	
	private WoerterBuch buch;
	private WoerterBuchDAO dao;
	private Wort wort;
	private int sprachenIndex = 0;

	@Override
	public void init() throws Exception {
			
		// init dao
		Parameters params = getParameters();
		List<String> paramList = params.getRaw();
		if (paramList.size() < 1) {
			throw new IOException("No parameter defined for file name!");
		}
		dao = new WoerterBuchDAOImpl(paramList.get(0));

		
		// init model
		try {
			buch = dao.readBuch();
		} catch (IOException e) {
			buch = dao.createBuch();
		}
		
		wort = buch.zufaelligesWort();
		
		// ui controls Tab1
		feedback = new Label("");
		titel = new Label("Übersetze das Wort");

		zuÜbersetzendesWort = new Label(buch.neuesWort(wort, sprachenIndex));

		richtung = new Label("von Deutsch nach Englisch");

		eingabe = new TextField();
		eingabe.setPromptText("Übersetzung");

		bestätigen = new Button("Bestätigen");
		bestätigen.setOnAction(event -> {
			if (buch.uebersetze(wort, eingabe.getText())) {
				feedback.setText(PREFIX);
				eingabe.clear();
				wort = buch.zufaelligesWort();
				zuÜbersetzendesWort.setText(buch.neuesWort(wort, sprachenIndex));
			} else {
				feedback.setText(PREFIX2);
				eingabe.clear();
			}
		});

		richtungÄndern = new Button("Übersetzungsrichtung ändern");
		richtungÄndern.setOnAction(event -> {
			if(sprachenIndex == 0) {
				sprachenIndex = 1;
				wort = buch.zufaelligesWort();
				zuÜbersetzendesWort.setText(buch.neuesWort(wort, sprachenIndex));
				richtung.setText("von Englisch nach Deutsch");
			} else {
				sprachenIndex = 0;
				wort = buch.zufaelligesWort();
				zuÜbersetzendesWort.setText(buch.neuesWort(wort, sprachenIndex));
				richtung.setText("von Deutsch nach Englisch");
			}
		});
		
		
		// ui controls Tab2
		neuesWort = new Label("Ein neues Wort in das Wörterbuch eintragen:");

		deutsch = new TextField();
		deutsch.setPromptText("Deutsch");

		englisch = new TextField();
		englisch.setPromptText("Englisch");

		hinzufügen = new Button("Hinzufügen");
		hinzufügen.setOnAction(event -> {
			if (buch.wortHinzufuegen(deutsch.getText().trim(), englisch.getText().trim())) {
				deutsch.clear();
				englisch.clear();
				showAlert("Eintrag im Wörterbuch erfolgreich hinzugefügt", AlertType.INFORMATION);
			} else {
				showAlert("Eintrag für " + "[" + deutsch.getText().trim()+ "/" + englisch.getText().trim() +"]"
						+ " im Wörterbuch bereits vorhanden", AlertType.ERROR);
				deutsch.clear();
				englisch.clear();
			}
		});

		speichern = new Button("Wörterbuch speichern");
		speichern.setOnAction(event -> {		
			try {
				dao.updateBuch(buch);
				showAlert("Wörterbuch erfolgreich aktualisiert", AlertType.INFORMATION);
			} catch (IOException e) {
				showAlert("Can't write to File!", AlertType.ERROR);
				e.printStackTrace();
			}
		});
		
		
		// create scene graph
		root = createSceneGraph();
	}
	
	
	private Parent createSceneGraph() {

		VBox root1 = new VBox();
		root1.setSpacing(10);
		root1.setPadding(new Insets(15, 20, 10, 10));
		root1.getChildren().addAll(titel, zuÜbersetzendesWort, richtung, eingabe, bestätigen, richtungÄndern, feedback);

		VBox root2 = new VBox();
		root2.setSpacing(10);
		root2.setPadding(new Insets(15, 20, 10, 10));
		root2.getChildren().addAll(neuesWort, deutsch, englisch, hinzufügen, speichern);

		TabPane pane = new TabPane();

		Tab tab1 = new Tab("Trainieren");
		tab1.setContent(root1);
		pane.getTabs().add(tab1);

		Tab tab2 = new Tab("Wörterbuch");
		tab2.setContent(root2);
		pane.getTabs().add(tab2);

		return pane;
	}

	/**
	 * Dialog-Fenster
	 * @param message
	 * @param type
	 */
	private void showAlert(String message, AlertType type) {
		Alert alert = new Alert(type, message);
		alert.showAndWait()
	      .filter(response -> response == ButtonType.OK)
	      .ifPresent(response -> alert.close());
	}
	
	/**
	 * Abfragefenster zum Programmschließen
	 * @throws Exception
	 */
	public void programmSchließen() throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Wortschatzsicherung");

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				try {
					dao.updateBuch(buch);
					Platform.exit();
				} catch (Exception e) {
					showAlert("Can't write to File!", AlertType.ERROR);
					e.printStackTrace();
				}
			} else {
				alert.close();
			}
		});
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Vokabeltrainer");		
		Scene scene = new Scene(root, 500, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(event -> {
			event.consume();
			try {
				programmSchließen();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
