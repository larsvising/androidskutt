package skutthyllan.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import skutt.bokhyllan.SkutthyllanActivity;
import skutthyllan.data.Skuttbok;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Log;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import skutthyllan.utils.JsonUtils;
import skutthyllan.utils.SkuttbokUtils;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.text.Html.TagHandler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class SkuttbokUtils {

	public static String getSkuttbokInfo(Skuttbok bok) {
		String info = "";
		if (bok != null) {
			String[] tid = new String[2];
			tid = (ejNullTrim(bok.getCreatedDate())).split("T");
			info = bok.getTitel() + " \n" + "ISBN: " + bok.getIsbn() + " \n"
					+ "Inlagd: " + tid[0] + "\n ";
		}
		return info;
	}

	public static void logbok(List<Skuttbok> listan) {
		if (listan != null) {
			for (Skuttbok skuttbok : listan) {
				if (skuttbok != null) {
					Log.i("DATA",
							"BOK " + skuttbok.getTitel() + " "
									+ skuttbok.getIsbn());
				} else {
					Log.e("DATA", "BOK " + "NULL");
				}
			}
		}
	}

	public static String ejNullTrim(String s) {
		if ((s != null) && (s.length() > 0)) {
			return s.trim();
		} else {
			return "";
		}
	}

	public Bitmap downloadImage(String fileUrl) {
		URL myFileUrl = null;
		Bitmap bmImg = null;
		try {
			myFileUrl = new URL(fileUrl);

			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			bmImg = BitmapFactory.decodeStream(is);
			return bmImg; // sedan kan man t ex göra: imView.setImageBitmap(bmImg);
		} catch (Exception e) {
			Log.e("ERROR", "IMAGE LOADING " + e.toString());
			return null;
		}
	}
	
	/*
	 java.net.URL url; try { url = new
	  java.net.URL(thumbnailUrl); try {
	  imView.setImageDrawable(
	  android.graphics.drawable.Drawable
	  .createFromStream(url.openStream(),"test"));
	  Log.i("DATA","GOT IMG VIEW3");
	  imView.setAdjustViewBounds(true);
	  Log.i("DATA","GOT IMG3 ");
	  
	  } catch (IOException e) { Log.e("ERROR", "URL IO " +
	 e.toString()); }
	  
	  } catch (MalformedURLException e) {
	  
	  } 
	  */

}
