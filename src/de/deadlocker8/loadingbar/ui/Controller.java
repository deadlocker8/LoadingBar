package de.deadlocker8.loadingbar.ui;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tools.AlertGenerator;

public class Controller
{
	@FXML private Label label;
	@FXML private ProgressBar progressBar;

	private Stage stage;
	private Image icon = new Image("de/deadlocker8/loadingbar/resources/icon.png");
	private final ResourceBundle bundle = ResourceBundle.getBundle("de/deadlocker8/loadingbar/main/", Locale.GERMANY);
	private CountdownTimer timer;

	public void init(Stage stage)
	{
		this.stage = stage;
	}
	
	public void buttonStart()
	{
		Random random = new Random();
		int targetPercentage = 10 + random.nextInt(90 - 10 + 1);
		label.setText(String.valueOf(targetPercentage));
		
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
	
	public void stop(double value)
	{
		int userPercentage = (int)(value * 100);
		
		label.setText(label.getText() + " - " + userPercentage);
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