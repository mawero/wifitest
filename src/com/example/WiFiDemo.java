package com.example;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WiFiDemo extends Activity implements OnClickListener {
	private static final String TAG = "WiFiDemo";
	WifiManager wifi;
	BroadcastReceiver receiver;

	TextView textStatus;
	Button buttonScan;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Setup UI
		textStatus = (TextView) findViewById(R.id.textStatus);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(this);

		// Setup WiFi
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		// Get WiFi status
		WifiInfo info = wifi.getConnectionInfo();
		textStatus.append("\n\nWiFi Status: " + info.toString());

		// List available networks
		List<WifiConfiguration> configs = wifi.getConfiguredNetworks();
		for (WifiConfiguration config : configs) {
			textStatus.append("\n\n" + config.toString());
		}

		// Register Broadcast Receiver
		if (receiver == null)
			receiver = new WiFiScanReceiver(this);

		registerReceiver(receiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		Log.d(TAG, "onCreate()");
	}

	@Override
	public void onStop() {
		unregisterReceiver(receiver);
	}

	public void onClick(View view) {
		Toast.makeText(this, "On Click Clicked. Toast to that!!!",
				Toast.LENGTH_LONG).show();

		if (view.getId() == R.id.buttonScan) {
			Log.d(TAG, "onClick() wifi.startScan()");
			wifi.startScan();
		}
	}

}