package com.gbsys.informe;

import java.io.File;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("com.gbsys.informe.Messages");
    private static final ObservableList<String> REPORTS = FXCollections.observableArrayList("CENDEISSS", "SISA");

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(MESSAGES.getString("app.title"));
        primaryStage.setScene(new Scene(createMainPane()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Pane createMainPane() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(createFormPane(), new Separator(), createButtonBar());
        return vbox;
    }

    private Node createFormPane() {
        //Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        //Nombre
        grid.add(new Label(MESSAGES.getString("name.label")), 0, 0);
        TextField name = new TextField();
        grid.add(name, 1, 0, 3, 1);
        //Mes
        grid.add(new Label(MESSAGES.getString("month.label")), 0, 1);
        ObservableList<Month> months = FXCollections.observableArrayList(Month.values());
        ComboBox<Month> month = new ComboBox<>(months);
        month.setConverter(new MonthConverter());
        month.setValue(Month.JANUARY);
        grid.add(month, 1, 1);
        //Reporte
        grid.add(new Label(MESSAGES.getString("report.label")), 2, 1);

        ComboBox<String> report = new ComboBox<>(REPORTS);
        report.setValue("SISA");
        grid.add(report, 3, 1);
        //Observaciones
        grid.add(new Label(MESSAGES.getString("observation.label")), 0, 2);
        TextArea observations = new TextArea(MESSAGES.getString("observation.placeholder"));
        observations.setPrefColumnCount(30);
        observations.setPrefRowCount(5);
        grid.add(observations, 1, 2, 3, 1);

        return grid;
    }

    private Node createButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(10));

        Button generateButton = new Button(MESSAGES.getString("generate.label"));
        generateButton.setOnAction(this::generate);
        Button exitButton = new Button(MESSAGES.getString("exit.label"));
        exitButton.setOnAction(ev -> Platform.exit());

        ButtonBar.setButtonData(generateButton, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(exitButton, ButtonBar.ButtonData.CANCEL_CLOSE);

        buttonBar.getButtons().addAll(generateButton, exitButton);

        return buttonBar;
    }

    private void generate(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window currentStage = source.getScene().getWindow();

        FileChooser openFileChooser = new FileChooser();
        openFileChooser.setTitle(MESSAGES.getString("openfile.title"));
        openFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        MESSAGES.getString("openfile.csv.label"),
                        MESSAGES.getString("openfile.csv.extension"))
        );
        File openFile = openFileChooser.showOpenDialog(currentStage);
        
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle(MESSAGES.getString("savefile.title"));
        saveFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        MESSAGES.getString("savefile.xlsx.label"),
                        MESSAGES.getString("savefile.xlsx.extension"))
        );
        saveFileChooser.setInitialFileName("REPORTE X.xlsx");
        File saveFile = saveFileChooser.showSaveDialog(currentStage);
    }
}
