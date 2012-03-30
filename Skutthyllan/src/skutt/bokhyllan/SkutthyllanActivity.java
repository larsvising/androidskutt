package skutt.bokhyllan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import skutthyllan.data.Skuttbok;
import skutthyllan.utils.JsonUtils;
import skutthyllan.utils.SkuttbokUtils;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SkutthyllanActivity extends ListActivity {

	private Resources res;
	private String url;
	private ListView listView;
	private List<Skuttbok> boklista;
	private ProgressDialog progressDialog;
    private SkuttbokAdapter skuttbokAdapter;
	
	private final Handler handler = new Handler() {
		public void handleMessage(final Message msg) {
			progressDialog.dismiss();
			//setListAdapter(new ArrayAdapter<Skuttbok>(SkutthyllanActivity.this,R.layout.listbook, boklista));
		   skuttbokAdapter = new SkuttbokAdapter(SkutthyllanActivity.this, R.layout.listbook, boklista);
		   setListAdapter(skuttbokAdapter);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		res = this.getResources();
		url = (String) res.getText(R.string.skutt_url);
		//this.setContentView(R.layout.listbook);
		listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setClickable(true);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(),
						SkuttbokUtils.getSkuttbokInfo(boklista.get(position)), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 Intent bokIntent = new Intent(view.getContext(), BokActivity.class);
				 bokIntent.putExtra("id", String.valueOf(boklista.get(position).getId()));
				 bokIntent.putExtra("isbn", boklista.get(position).getIsbn());
	             startActivityForResult(bokIntent, 0);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		threadBoklistan(url);
	}

	

	/**
	 * @param url
	 * @return
	 */
	private List<Skuttbok> skuttboklistan(String url) {
		List<Skuttbok> sBoklista = new ArrayList<Skuttbok>();
		List<JSONObject> listan = JsonUtils.hamtaBoklista(url);
		sBoklista = JsonUtils.skuttbockerFranJSON(listan);
		return sBoklista;
	}

	/**
	 * @param url
	 * @return
	 */
	private void threadBoklistan(final String url) {
		this.progressDialog = ProgressDialog.show(this, "Vänta...", "Hämtar böcker från SKUTT.");
		new Thread() {
			public void run() {
				//SkutthyllanActivity.this.sBoklista = boklistan(url);
				SkutthyllanActivity.this.boklista = skuttboklistan(url);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
}