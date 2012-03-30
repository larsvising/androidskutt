package skutthyllan.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import skutthyllan.data.Skuttbok;

import android.util.Log;

public class JsonUtils {
	
	/**
	 * @param url
	 * @return
	 */
	public static JSONArray getJSONfromUrl(String url) {

		InputStream is = null;
		String result = "";
		JSONArray jArray = new JSONArray();

		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("ERROR","Error HTTP "+e.toString());
		}

		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			 Log.i("DATA","RESULT: "+result);
		}catch(Exception e){
			Log.e("ERROR","Error to string "+e.toString());
		}

		try{
			JSONObject jobject = new JSONObject(result);
			 jArray = jobject.getJSONArray("data");
		//	jArray = new JSONArray(result);
			 Log.i("DATA","Some JSON "+jArray.toString());
		}catch(JSONException e){
			Log.e("ERROR","Error parsing "+e.toString());
		}    

		return jArray;

	}
	
	public static JSONObject getGoogleBooksJSON(String isbn) {

		InputStream is = null;
		String result = "";
		JSONObject jArray = new JSONObject();
		 String urlPre = "http://books.google.com/books?bibkeys=ISBN:";
	     String urlIsbn = isbn.replaceAll("-", "");
//	      urlIsbn ="0451526538";
		  String urlPost = "&jscmd=viewapi";
		String url = urlPre+urlIsbn+urlPost;

		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("ERROR","Error HTTP "+e.toString());
		}

		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
			result = result.replaceAll("var _GBSBookInfo = ", "");
			 Log.i("DATA","RESULT: "+result);
		}catch(Exception e){
			Log.e("ERROR","Error to string "+e.toString());
		}

		try{
			JSONObject jobject = new JSONObject(result);
			 jArray = jobject.getJSONObject("ISBN:"+urlIsbn);
		//	jArray = new JSONArray(result);
			 Log.i("DATA","Some JSON "+jArray.toString());
		}catch(JSONException e){
			Log.e("ERROR","Error parsing "+e.toString());
		}    

		return jArray;

	}
	
	/*
	
	 */
	/**
	 * @param url
	 * @return
	 */
	public static List<JSONObject> hamtaBoklista(String url) {
		return getListFromArray(getJSONfromUrl(url));
	}

	/**
	 * @param jArray
	 * @return
	 */
	public static List<JSONObject> getListFromArray(JSONArray jArray) {
		List<JSONObject> listan =  new ArrayList<JSONObject>(); 

		try {
			if (jArray != null) {
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jsonObj = jArray.getJSONObject(i);
					if (jsonObj != null) {
						listan.add(jsonObj);
					}
				}
			}
		} catch (JSONException e) {
			Log.e("ERROR","JSON "+e.toString());
		} 
		return listan;
	}
	
	public static List<Skuttbok> skuttbockerFranJSON(List<JSONObject> listan) {
		 List<Skuttbok> skuttbocker = new ArrayList<Skuttbok>();
		try {
			for (JSONObject jsonObject : listan) {
				Skuttbok bok = new Skuttbok();
				bok.setId(jsonObject.getInt("id"));
				bok.setTitel(jsonObject.getString("title"));
				bok.setIsbn(jsonObject.getString("isbn"));
				bok.setCreatedDate(SkuttbokUtils.ejNullTrim(jsonObject.getString("dateCreated")));
				
				JSONArray reviews = jsonObject.getJSONArray("reviews");
				ArrayList<Integer> reviewIds = new ArrayList<Integer>();
				if (reviews != null) {
					for (int i = 0; i < reviews.length(); i++) {
						 Integer id = (Integer) reviews.get(i);
						 reviewIds.add(id);
					}
					
				}
				 bok.setaReviewId(reviewIds);
				skuttbocker.add(bok);
			}
		} catch (JSONException e) {
			Log.e("ERROR","JSON "+e.toString()+e.toString());
			
		}
		
		return skuttbocker;
		
	}
	
	/**
	 * @param url
	 * @return
	 */
	private List<String> boklistan(String url) {
		List<String> sBoklista = new ArrayList<String>();
		try {
			List<JSONObject> listan = JsonUtils.hamtaBoklista(url);
			int i = 1;
			for (JSONObject jsonObject : listan) {
				String tmpItem = (String) jsonObject.get("title");
				String tmpsItem = i + ", " + tmpItem + "  ";
				sBoklista.add(tmpsItem);
				i++;
			}
		} catch (JSONException e) {
			Log.e("ERROR", "JSON " + e.toString());
			return new ArrayList<String>();
		}
		return sBoklista;

	}

}
