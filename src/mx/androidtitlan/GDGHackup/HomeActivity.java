package mx.androidtitlan.GDGHackup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	private Button button;
	private final int flag = 1;
	private ImageView imageView;
	private Bitmap imagenCapturada;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	private void tomarFoto(int flag) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, flag);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		AlertDialog dialog;

		if (resultCode == RESULT_CANCELED) {

			dialog = crearDialogo("FOTO", "Fallo al tomar la foto", "OK");

		} else {
			imagenCapturada = (Bitmap) data.getExtras().get("data");
			dialog = crearDialogo("FOTO", "Éxito al tomar la foto", "OK");

		}
		dialog.show();
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

			}
		});

		return msgDialog;
	}

}
