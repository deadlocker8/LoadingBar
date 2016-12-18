package de.deadlocker8.loadingbar.ui;

import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tools.AlertGenerator;

public class Controller
{
	@FXML private AnchorPane mainPane;
	@FXML private Label labelDescription;
	@FXML private Label labelTarget;
	@FXML private Label labelUser;
	@FXML private Label labelMessage;
	@FXML private ProgressBar progressBar;
	@FXML private ProgressBar progressBarTarget;
	@FXML private StackPane stackPane;
	@FXML private Button buttonStop;

	private Stage stage;
	private Image icon = new Image("de/deadlocker8/loadingbar/resources/icon.png");
	private final ResourceBundle bundle = ResourceBundle.getBundle("de/deadlocker8/loadingbar/main/", Locale.GERMANY);
	private CountdownTimer timer;
	private int targetPercentage;
	private boolean swapped;
	private final String BACKGROUND_COLOR = "#333333";

	public void init(Stage stage)
	{
		this.stage = stage;

		mainPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR);
		stackPane.setStyle("-fx-background-color: derive(" + BACKGROUND_COLOR + ", -60%)");
		progressBar.setStyle("-fx-accent: white;");	
		labelDescription.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");
		labelTarget.setStyle("-fx-text-fill: white; -fx-font-size: 40; -fx-font-weight: bold;");
		labelUser.setStyle("-fx-text-fill: white; -fx-font-size: 30; -fx-font-weight: bold;");
		labelMessage.setStyle("-fx-text-fill: white; -fx-font-size: 30; -fx-font-weight: bold;");
		buttonStop.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 3; -fx-border-radius: 0; -fx-text-fill: white;");
	}

	public void buttonStart()
	{
		if(swapped)
		{
			swapProgressBars();
			swapped = false;
		}

		Random random = new Random();
		targetPercentage = 15 + random.nextInt(90 - 15 + 1);
		labelTarget.setText(String.valueOf(targetPercentage));
		labelUser.setText("");
		labelMessage.setText("");

		progressBarTarget.setProgress(0.0);

		progressBar.setProgress(0.0);
		timer = new CountdownTimer(4.0, this);
	}

	public void buttonStop()
	{
		stop(timer.stop());
	}

	public void updateProgress(double value)
	{
		progressBar.setProgress(value);
	}

	private void swapProgressBars()
	{
		ObservableList<Node> workingCollection = FXCollections.observableArrayList(stackPane.getChildren());
		Collections.swap(workingCollection, 0, 1);
		stackPane.getChildren().setAll(workingCollection);
	}

	public void stop(double value)
	{
		int userPercentage = (int)(value * 100);
		labelUser.setText(String.valueOf(userPercentage));
		progressBar.setProgress(userPercentage / 100.0);
		progressBarTarget.setProgress(targetPercentage / 100.0);
		progressBarTarget.setStyle("-fx-accent: red");

		if(userPercentage < targetPercentage)
		{
			swapped = true;
			swapProgressBars();
		}

		int difference = Math.abs(targetPercentage - userPercentage);
		switch(difference)
		{
			case 0:
				labelMessage.setText("Awesome!");
				break;
			case 1:
				labelMessage.setText("Close One!");
				break;
			case 2:
				labelMessage.setText("Almost");
				break;
			default:
				labelMessage.setText("Missed by " + difference);
		}
	}

	public Stage getStage()
	{
		return stage;
	}

	public void about()
	{
		AlertGenerator.showAboutAlert(bundle.getString("app.name"), bundle.getString("version.name"), bundle.getString("version.code"), bundle.getString("version.date"), bundle.getString("author"), icon, stage, null, false);
	}
}