package my.hackday.Mirror;

import android.util.Log;

public class Pusher {	
	public Pusher() {
	}
	
	public void getRequest(String key, String value) {
		HttpPostThread post = new HttpPostThread();
		try {
			post.setKeyValue(key, value);
			Thread thread = new Thread(post);
			thread.start();
		} catch(Exception e) {
			return;
		}
	}
}
