package mx.androidtitlan.GDGHackup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	private Button button;
	private final int flag = 1;
	private ImageView imageView;
	private Bitmap imagenCapturada;
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		this.button = (Button) findViewById(R.id.tomar_foto);
		imageView = (ImageView) findViewById(R.id.imagen_capturada);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tomarFoto(flag);
			}
		});
	}

	private void tomarFoto(int flag) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		this.file = new File(Environment.getExternalStorageDirectory(),
				"imagen.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

		startActivityForResult(intent, flag);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		AlertDialog dialog = null;
		Uri uri = null;

		if (resultCode == RESULT_CANCELED) {

			dialog = crearDialogo("FOTO", "Fallo al tomar la foto", "OK");
			dialog.show();

		} else {

			uri = data.getData();
			try {
				imagenCapturada = Media.getBitmap(this.getContentResolver(),
						uri);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// imagenCapturada = (Bitmap) data.getExtras().get("data");
			dialog = crearDialogo("FOTO", "ÉXITO", "OK");
			dialog.show();

		}

	}

	private AlertDialog crearDialogo(String title, String msg, String buttonText) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		AlertDialog msgDialog = dialogBuilder.create();
		msgDialog.setTitle(title);
		msgDialog.setMessage(msg);
		msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int idx) {
				dialog.dismiss();
				imageView.setImageBitmap(imagenCapturada);
				try {
					ExifInterface exifInterface = new ExifInterface(Environment
							.getExternalStorageDirectory() + "/imagen.jpg");
					Log.i("TAG_DATE", exifInterface
							.getAttribute(ExifInterface.TAG_DATETIME));
					Log.i("TAG_LATITUD",
							""
									+ exifInterface
											.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
					Log.i("TAG_LONGITUD",
							""
									+ exifInterface
											.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		return msgDialog;
	}

}
