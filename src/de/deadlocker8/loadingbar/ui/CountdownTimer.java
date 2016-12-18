package de.deadlocker8.loadingbar.ui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class CountdownTimer
{
	private double count;	
	private double time;
	private Controller controller;
	private Timer timer;
	private TimerTask task;	

	public CountdownTimer(double seconds,  Controller controller)
	{
		this.count = seconds;
		this.controller = controller;	
		
		start();
	}
	
	public void start()
	{	
		time = 0.0;
		
		task = new TimerTask()
		{
			@Override
			public void run()
			{
				Platform.runLater(()->{
					try
					{
						controller.updateProgress(time/count);						
					}
					catch(Exception e)
					{
						
					}
				});			
				if(time < count)
				{
					time += 0.01;
				}

				if(Math.abs(time - count) <= 0.0001)
				{
					Platform.runLater(()->{
						controller.stop(1.0);
					});
					timer.cancel();
				}				
			}
		};
		timer = new Timer();		
		timer.schedule(task, 0, 10);
	}
	
	public double stop()
	{
		timer.cancel();		
		timer.purge();
		
		
		return time / count;
	}
}