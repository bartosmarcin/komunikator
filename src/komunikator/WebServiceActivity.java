package komunikator;

import WebService.WebServiceResponse;
import android.app.Activity;

public abstract class WebServiceActivity extends Activity {

	public abstract void onRequestSuccess(WebServiceResponse response);
	
	public abstract void onRequestFailure();
	
	
}
