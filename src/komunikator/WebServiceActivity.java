package komunikator;

import com.example.komunikator.R;

import WebService.WebService;
import WebService.WebServiceResponse;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public abstract class WebServiceActivity extends Activity {
	
	protected WebService webService;

	public abstract void onRequestSuccess(WebServiceResponse response);
	
	public abstract void onRequestFailure();
	
	@Override
	public void onStart(){
		super.onStart();
		webService = new WebService(this);
	}	

	protected void showError(final String message) {
		final TextView errorTextView = (TextView) findViewById(R.id.error_text_view);
		errorTextView.setText(message);
		errorTextView.setVisibility(View.VISIBLE);
		errorTextView.setAlpha(0);
		errorTextView.animate().alpha(1).withLayer();
		errorTextView.postDelayed(new Runnable() {
			public void run() {
				errorTextView.animate().alpha(0);
			}
		}, 3000);
	}
	
	
}
