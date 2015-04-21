package com.example.mcaffeprods;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductAdapter  extends ArrayAdapter<ProductModel> implements OnClickListener  {
	ArrayList<ProductModel> prodObjectList;
	Context context;
	int prodListRow;
	LayoutInflater vi;
	ViewHolder holder;


	public ProductAdapter(MainActivity mainActivity, int prodListRow,
			ArrayList<ProductModel> prodObjectList) {
		// TODO Auto-generated constructor stub
		super(mainActivity,prodListRow,prodObjectList);
		this.context=mainActivity;
		this.prodObjectList=prodObjectList;
		this.prodListRow=prodListRow;
		
	}
	
	 @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
	        
			View v = convertView;
			if (v == null) {
				holder = new ViewHolder();
				LayoutInflater vi = (LayoutInflater) context  
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(this.prodListRow, null);
				holder.name=(TextView) v.findViewById(R.id.name);
				holder.price=(TextView) v.findViewById(R.id.price);
				holder.imageView=(ImageView) v.findViewById(R.id.prodImage);
				holder.layoutId=(LinearLayout) v.findViewById(R.id.layoutId);
				
					
					
				
				//holder.layoutId.setOnClickListener(this);//for handling the click that will take to detail activity
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			
			
	        //new DownloadImageTask(holder.imageview1).execute(looksResultList.get(position).getImage1());
			//Picasso.with(context).load(looksResultList.get(position).getLookImage()).fit().centerCrop().into(holder.lookImage);
			Picasso.with(context).load(prodObjectList.get(position).getImageUrl()).centerCrop().resize(100,100).into(holder.imageView);
			 if(prodObjectList.get(position).getPrice().equals("0"))
				 holder.price.setText("free");
             else
            	  holder.price.setText("$"+prodObjectList.get(position).getPrice());
			holder.name.setText(prodObjectList.get(position).getName());
	      
	       //setting a tag to fetch details for that click
	       parent.setTag(prodObjectList.get(position));



			return v;
		}

	 static class ViewHolder {
			public ImageView imageView;
	       public LinearLayout layoutId;
			public TextView name,price;

			
			
			}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*ProductModel tag=new ProductModel();
		tag=(ProductModel) v.getTag();
		Intent in=new Intent(context,ProdDetailActivity.class);
		
		in.putExtra("imagee",tag.getImageUrl());
		in.putExtra("price",tag.getPrice());
		in.putExtra("type",tag.getType());
		in.putExtra("name",tag.getName());
		in.putExtra("rating",tag.getRating());
		in.putExtra("users",tag.getUsers());
		in.putExtra("description",tag.getDescription());
		in.putExtra("url",tag.getUrl());
		in.putExtra("last_update",tag.getLast_updated());
		
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);*/

	}
		

}
