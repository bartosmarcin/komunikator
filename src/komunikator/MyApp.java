package komunikator;

import android.app.Application;

public class MyApp extends Application{
	
	private static boolean activityVisible=true;
	
	public static boolean isInForeground(){
		return activityVisible;
	}
	
	public static void activityResumed() {
	    activityVisible = true;
	  }

	  public static void activityPaused() {
	    activityVisible = false;
	  }
}
