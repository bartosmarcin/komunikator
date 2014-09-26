package komunikator;

import WebService.WebServiceRequest;
import WebService.WebServiceResponse;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.komunikator.R;

public class SignInActivity extends WebServiceActivity {

	private EditText emailField;
	private EditText passwordField;
	private Button signinButton;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		signinButton = (Button) findViewById(R.id.signin_button);
		emailField = (EditText) findViewById(R.id.signin_email);
		passwordField = (EditText) findViewById(R.id.signin_password);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (webService.isSignedIn()) {
			Intent intent = new Intent(this, ProfileEditActivity.class);
			startActivity(intent);
		}
	}

	public void signIn(View view) {
		progressDialog = ProgressDialog.show(this, "Signing in",
				"Please wait.", true);
		progressDialog.setCancelable(true);
		String email = emailField.getText().toString();
		String password = passwordField.getText().toString();
		final WebServiceRequest request = webService.signIn(email, password,
				this);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				request.cancel();
			}
		});

	}

	@Override
	public void onRequestSuccess(WebServiceResponse response) {
		progressDialog.dismiss();
		if(!response.isIsSuccess()){
			showError(response.getErrors().get(0));
			return;
		}
		Intent intent =  new Intent(this, ProfileEditActivity.class); 
		webService.setAuthToken((String)response.getResponse());
		startActivity(intent);
		return;
	}

	@Override
	public void onRequestFailure() {
		progressDialog.dismiss();
		showError("Network Error");

	}

}
