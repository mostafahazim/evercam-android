package io.evercam.androidapp.tasks;

import io.evercam.androidapp.ScanActivity;
import io.evercam.androidapp.utils.NetInfo;
import io.evercam.network.EvercamDiscover;
import io.evercam.network.discovery.DiscoveredCamera;
import io.evercam.network.discovery.ScanRange;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

public class ScanForCameraTask extends AsyncTask<Void, Void, ArrayList<DiscoveredCamera>>
{
	private final String TAG = "evercamplay-ScanForCameraTask";
	private ScanActivity scanActivity;
	private NetInfo netInfo;

	public ScanForCameraTask(ScanActivity scanActivity)
	{
		this.scanActivity = scanActivity;
		netInfo = new NetInfo(scanActivity);
	}
	
	@Override
	protected void onPreExecute()
	{
		
	}

	@Override
	protected ArrayList<DiscoveredCamera> doInBackground(Void... params)
	{
		ArrayList<DiscoveredCamera> cameraList = null;
		try
		{
			EvercamDiscover evercamDiscover = new EvercamDiscover();
			ScanRange scanRange = new ScanRange(netInfo.getLocalIp(), netInfo.getNetmaskIp());
			cameraList = evercamDiscover.discoverAllCamerasAndroid(scanRange, netInfo.getGatewayIp());
		}
		catch (Exception e)
		{
			Log.e(TAG, e.toString());
		}
		return cameraList;
	}

	@Override
	protected void onPostExecute(ArrayList<DiscoveredCamera> cameraList)
	{
		scanActivity.showProgress(false);
		
		scanActivity.showScanResults(cameraList);
	}
}