package my.hackday.Mirror;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpPostThread implements Runnable {
	public static final String POSTURL = "http://49.212.148.77/pusherServer/";
	public static final String POSTKEY = "json";
	HttpClient client;
	JSONObject json;
	String key, value;
	
	public HttpPostThread(){
		client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(this.client.getParams(), 3000);
		HttpConnectionParams.setSoTimeout(this.client.getParams(), 5000);
		json = new JSONObject();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		//String line = null;
		try {
			URL url = new URL(POSTURL);
			HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
			urlCon.setRequestMethod("POST");
			urlCon.setDoOutput(true);
			String postData = POSTKEY+"="+json.toString();
			PrintStream ps = new PrintStream(urlCon.getOutputStream());
			ps.print(postData);
			ps.close();
			
			//InputStream in = urlCon.getInputStream();
			//BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//String tmp = null;
			//while((tmp = br.readLine()) != null) {
			//	Log.e("response", tmp);
			//}
			
			Log.i("debun", "success");
		} catch (Exception e) {
			Log.i("error", e.getMessage());
			e.getStackTrace();
		}
	}
	
	public void setKeyValue(String key, String value) throws JSONException{
		this.value = value;
		this.key = key;
		json.put("value", value);
		json.put("key", key);
	}
}
