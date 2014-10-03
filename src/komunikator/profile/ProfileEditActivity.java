package komunikator.profile;

import komunikator.BitmapRounder;
import komunikator.WebServiceActivity;
import komunikator.contacts.ContactsActivity;
import komunikator.utils.SharedPreferencesManager;
import WebService.WebServiceResponse;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.komunikator.R;

public class ProfileEditActivity extends WebServiceActivity {
	
	public final static String EMAIL_FLD = "EMAIL_FLD";
	
	private final int CHOOSE_PICTURE_CODE = 1;
	private final int CROP_PICTURE = 2;

	private EditText firstNameFld;
	private EditText lastNameFld;
	private EditText nicknameFld;
	ImageView profileImageView;
	Bitmap profileImg;

	private String userProfileIdSharedPrefField = "user_profile_id_shared_pref_field";
	private Profile userProfile;
	private ProfileDAO profileDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		firstNameFld = (EditText) findViewById(R.id.profile_first_name_field);
		lastNameFld = (EditText) findViewById(R.id.profile_last_name_field);
		nicknameFld = (EditText) findViewById(R.id.profile_nickname_field);
		profileImageView = (ImageView) findViewById(R.id.profile_picture);
		profileDao = new ProfileDAO(this);
		userProfile = getUserProfile();
	}
	
	private void fillFieldsFromProfile(){
		if(userProfile == null)
			return;
		firstNameFld.setText(userProfile.getFirstName());
		lastNameFld.setText(userProfile.getLastName());
		nicknameFld.setText(userProfile.getNickName());
		profileImg = profileDao.getProfileImage(userProfile.getId());
		if(profileImg != null)
			profileImageView.setImageBitmap(profileImg);
	}
	
	private void fillProfileFromFields(){
		
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public void saveProfile(View view) {
		Profile profile = getUserProfile();
		if (profile == null)
			profile = new Profile();
		profile.setFirstName(firstNameFld.getText().toString());
		profile.setLastName(lastNameFld.getText().toString());
		profile.setNickName(nicknameFld.getText().toString());
		//String email = getIntent().getExtras().getString(EMAIL_FLD);
		//profile.setEmail(email);
		profile = new ProfileDAO(this).addOrUpdate(profile);
		profileDao.saveProfileImage(profileImg, profile.getId());
		SharedPreferencesManager.putLong(userProfileIdSharedPrefField,
				profile.getId(), this);
	//	getActionBar().setIcon(profileImageView.getDrawable());
	}

	private Profile getUserProfile() {
		Long userProfileId = SharedPreferencesManager.getLong(
				userProfileIdSharedPrefField, this);
		if (userProfileId == null)
			return null;
		return profileDao.getById(userProfileId);
	}

	public void choosePhoto(View view) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, CHOOSE_PICTURE_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == RESULT_CANCELED)
			return;
		if (resultCode != RESULT_OK) {
			Toast toast = Toast.makeText(this, "Something went wrong!",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}

		if (requestCode == CHOOSE_PICTURE_CODE) {
			Uri selectedImage = intent.getData();
			cropPhoto(selectedImage);
		}

		if (requestCode == CROP_PICTURE) {
			Bundle extras = intent.getExtras();
			Bitmap croped = extras.getParcelable("data");
			profileImg = BitmapRounder.toCircle(croped);
			profileImageView.setImageBitmap(profileImg);
		}
	}

	private void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setData(uri);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 96);
		intent.putExtra("outputY", 96);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_PICTURE);
	}

	@Override
	public void onRequestSuccess(WebServiceResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRequestFailure() {
		// TODO Auto-generated method stub

	}

	public void chooseContactsButton(View view) {
		Intent intent = new Intent(this, ContactsActivity.class);
		startActivity(intent);
	}
}
