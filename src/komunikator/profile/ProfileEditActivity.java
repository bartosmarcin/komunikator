package komunikator.profile;

import java.io.File;

import komunikator.RoundedAvatarDrawable;
import komunikator.WebServiceActivity;
import komunikator.contacts.ContactsActivity;
import komunikator.utils.SharedPreferencesManager;
import WebService.WebServiceResponse;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.komunikator.R;

public class ProfileEditActivity extends WebServiceActivity {
	private final int CHOOSE_PICTURE_CODE = 1;
	private final int CROP_PICTURE = 2;

	private Button contactsButton;
	private EditText firstNameFld;
	private EditText lastNameFld;
	ImageView profileImageView;
	Bitmap profileImg;

	private String userProfileIdSharedPrefField = "user_profile_id_shared_pref_field";
	private Profile userProfile;
	private ProfileDAO profileDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		contactsButton = (Button) findViewById(R.id.signup_button);
		firstNameFld = (EditText) findViewById(R.id.profile_first_name_field);
		lastNameFld = (EditText) findViewById(R.id.profile_last_name_field);
		profileImageView = (ImageView) findViewById(R.id.profile_picture);
		profileDao = new ProfileDAO(this);
		userProfile = getUserProfile();
		if (userProfile != null) {
			firstNameFld.setText(userProfile.getFirstName());
			lastNameFld.setText(userProfile.getLastName());
			profileImg = profileDao.getProfileImage(userProfile.getId());
			if(profileImg != null)
				profileImageView.setImageBitmap(profileImg);
		}
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
		profile = new ProfileDAO(this).addOrUpdate(profile);

		profileImageView.buildDrawingCache();
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
			RoundedAvatarDrawable avatar = new RoundedAvatarDrawable(croped);
			profileImg = avatar.getBitmap();
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
