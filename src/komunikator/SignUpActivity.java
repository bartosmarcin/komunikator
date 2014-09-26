package komunikator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.komunikator.R;

import WebService.WebService;
import WebService.WebServiceRequest;
import WebService.WebServiceResponse;

public class SignUpActivity extends WebServiceActivity {

	private Button signupButton;
	private EditText emailField;
	private EditText passwordField;
	private EditText passwordRepeatField;
	private ProgressDialog progressDialog;
	private WebService webService;

	private final static int MIN_PASS_LENGTH = 8;
	private final static int MAX_PASS_LENGTH = 60;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		signupButton = (Button) findViewById(R.id.signup_button);
		emailField = (EditText) findViewById(R.id.signup_email);
		passwordField = (EditText) findViewById(R.id.signup_password);
		passwordRepeatField = (EditText) findViewById(R.id.signup_password_repeat);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		webService = new WebService(this);
		if(webService.isSignedIn()){
			Intent intent =  new Intent(this, ProfileEditActivity.class);
			startActivity(intent);
		}			
	}

	public void signUp(View view) {
//		if (!validateForm())
//			return;
		progressDialog = ProgressDialog.show(this, "Signing up",
				"Please wait.", true);
		progressDialog.setCancelable(true);
		String email = emailField.getText().toString();
		String password = passwordField.getText().toString();
		final WebServiceRequest request = webService.signUp(email, password, this);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				request.cancel();
			}
		});
	}

	private void showError(final String message) {
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

	private boolean validateForm() {
		if (!isValidEmail())
			emailField.setError("Enter valid email");
		else if (!passwordsMatch())
			passwordField.setError("Passwords do not match");
		else if (!passwordLengthCorrect())
			passwordField
					.setError("Password length must be between 8-60 characters");
		else
			return true;
		return false;
	}

	private boolean passwordsMatch() {
		String password = passwordField.getText().toString();
		String passwordRepeat = passwordRepeatField.getText().toString();
		return password.equals(passwordRepeat);
	}

	private boolean passwordLengthCorrect() {
		int passLen = passwordField.getText().length();
		return passLen >= MIN_PASS_LENGTH && passLen <= MAX_PASS_LENGTH;
	}

	private boolean isValidEmail() {
		return android.util.Patterns.EMAIL_ADDRESS
				.matcher(emailField.getText()).matches();
	}

	@Override
	public void onRequestSuccess(WebServiceResponse response) {
		progressDialog.dismiss();
		if (!response.isIsSuccess()){
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
