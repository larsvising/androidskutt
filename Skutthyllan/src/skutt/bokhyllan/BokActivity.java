package skutt.bokhyllan;

 
import java.io.InputStream;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import skutthyllan.utils.JsonUtils;
import skutthyllan.utils.SkuttbokUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
 

public class BokActivity extends Activity {

	String urlPre = "http://books.google.com/books?bibkeys=ISBN:";
	String urlIsbn = "";
	String urlPost = "&jscmd=viewapi";
	Bitmap bmImg;
	ImageView imView;
	String imageUrl = "";
	String thumbnailUrl = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bokactivity);

		imView = new ImageView(this);
		LayoutInflater inflater = (LayoutInflater) BokActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.main, null);
		imView = (ImageView) ll.findViewById(R.id.imview);
		// this.setContentView(imView);
		Bundle bundle = getIntent().getExtras();
		String bokId = "";
		String bokIsbn = "";

		if (bundle != null) {
			bokId = bundle.getString("id");
			bokIsbn = SkuttbokUtils.ejNullTrim(bundle.getString("isbn"));
			final String gBooksPre="http://www.google.se/search?hl=sv&tbo=p&tbm=bks&q=isbn:"+bokIsbn;
			final String gBooksPost ="&num=10";
			JSONObject jobj = JsonUtils.getGoogleBooksJSON(bokIsbn);
			if (jobj != null) {
				try {
					if (jobj.has("thumbnail_url")) {
						thumbnailUrl = SkuttbokUtils.ejNullTrim(jobj.getString("thumbnail_url"));
					}
					
					if (thumbnailUrl.length() < 1) {
						thumbnailUrl = "http://bks4.books.google.com/books?id=tVwpQdummydefault&printsec=frontcover&img=1&zoom=5";
					}

					Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(thumbnailUrl).getContent());
					imView.setImageBitmap(bitmap);
					imView.invalidate();
					imView.setOnClickListener(new View.OnClickListener(){
					    public void onClick(View v){
					        Intent intent = new Intent();
					        intent.setAction(Intent.ACTION_VIEW);
					        intent.addCategory(Intent.CATEGORY_BROWSABLE);
					        intent.setData(Uri.parse(gBooksPre+gBooksPost));
					        startActivity(intent);
					    }
					});


				} catch (JSONException e) {
					Log.e("ERROR", "JSON " + e.toString());
				} catch (Exception e) {
					Log.e("ERROR", "CRASH " + e.toString());
				} 
			}

		}

		this.setContentView(ll);


	}

}
