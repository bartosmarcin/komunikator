package komunikator;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.komunikator.R;

import WebService.WebService;
import WebService.WebServiceResponse;

public class ProfileEditActivity extends WebServiceActivity {
	private final int CHOOSE_PICTURE_CODE = 1;
	private final int CROP_PICTURE = 2;
    private Button contactsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
        contactsButton = (Button) findViewById(R.id.signup_button);
	}

	public void choosePhoto(View view) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, CHOOSE_PICTURE_CODE);
		WebService webService  = new WebService(this);
		webService.sendMessage("wiad test", this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if( resultCode != RESULT_OK ){
			Toast toast = Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		if (requestCode == CHOOSE_PICTURE_CODE)
		{
			Uri selectedImage = intent.getData();
			cropPhoto(selectedImage);
		}

		if (requestCode == CROP_PICTURE){
			Bundle extras = intent.getExtras();
			Bitmap bitmap = extras.getParcelable("data");
			RoundedAvatarDrawable avatar = new RoundedAvatarDrawable(bitmap);
			ImageView profileImageView = (ImageView) findViewById(R.id.profile_picture);
			profileImageView.setImageDrawable(avatar);
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
	
	private Bitmap readBitmapFromIntent(Intent intent){
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Uri selectedImage = intent.getData();
		Cursor cursor = getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		return BitmapFactory.decodeFile(filePath);
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
