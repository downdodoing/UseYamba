package com.example.yambama;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yambaClient.YambaClient;
import com.example.yambaClient.YambaClientException;

public class MainActivity extends Activity implements OnClickListener {

	private TextView textView;
	private Button bnt;
	private EditText editText;

	private int defultColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView);
		bnt = (Button) findViewById(R.id.bnt);
		editText = (EditText) findViewById(R.id.editText);

		bnt.setOnClickListener(this);

		defultColor = textView.getTextColors().getDefaultColor();

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int count = 10 - editText.length();
				textView.setText(Integer.toString(count));
				if (count < 5 && count >= 0) {
					textView.setTextColor(Color.GREEN);
				} else if (count >= 5) {
					textView.setTextColor(defultColor);
				} else {
					textView.setTextColor(Color.RED);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		String status = editText.getText().toString();
		new PostTast().execute(status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class PostTast extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			YambaClient yambaClient = new YambaClient("student", "password");
			try {
				yambaClient.postStatus(params[0]);
				return "Successfully posted";
			} catch (YambaClientException e) {
				e.printStackTrace();
				return "Failed to post to yama service";
			}

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT)
					.show();
		}
	}

}
