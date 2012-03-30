package skutt.bokhyllan;

import java.util.ArrayList;
import java.util.List;

import skutthyllan.data.Skuttbok;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SkuttbokAdapter  extends ArrayAdapter<Skuttbok> {
	
 private final Context context;
	private final List<Skuttbok> bocker;

	public SkuttbokAdapter(Context inContext, int textViewResourceId, List<Skuttbok> inBocker ) {
         super(inContext, textViewResourceId, inBocker);
         this.context = inContext;
         this.bocker = inBocker;
     }

	@Override
	public int getCount() {
		if (this.bocker != null) {
		return this.bocker.size();
		} else return 0;
	}

	@Override
	public Skuttbok getItem(int position) {
		if (this.bocker != null) {
		 return this.bocker.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 Skuttbok bok = this.bocker.get(position);
		return new BoklistaView(this.context, bok.getTitel(), bok.getId(), bok.getIsbn(),bok.getaReviewId());
	}
	 
	private final class BoklistaView extends LinearLayout {
        private TextView titel;
        private TextView isbn;
        
		public BoklistaView(Context context, String sTitel, Integer id,String sIsbn,ArrayList<Integer> recensioner) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			this.titel = new TextView(context);
			this.titel.setText(sTitel);
			this.titel.setTextSize(20f);
			this.titel.setTextColor(Color.WHITE);
			this.addView(this.titel);
			this.isbn = new TextView(context);
			this.isbn.setTextSize(16f);
			int antal = recensioner.size();
			if (antal == 1) {
			this.isbn.setText(recensioner.size()+" recension");
			} else {
				this.isbn.setText(recensioner.size()+" recensioner");
			}
			this.addView(this.isbn);
		}
		
	}
	 

}
