package com.example.mcaffeprods;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ProdDetailActivity extends Activity implements OnClickListener{
	
	ImageView imageView;
	TextView name,users,price,last_update,type,rating,description;
	RatingBar ratingBar;
	String link;
	Button appStore,sms,share;
	String nameOfApp,detailApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prod_detail);
		setTitle("AppDetail");
		 
		imageView=(ImageView) findViewById(R.id.prodImage);
		name=(TextView) findViewById(R.id.name);
		price=(TextView) findViewById(R.id.priceVal);
		rating=(TextView) findViewById(R.id.rating);
		description=(TextView) findViewById(R.id.description);
		last_update=(TextView) findViewById(R.id.updateVal);
		users=(TextView) findViewById(R.id.userVal);
		ratingBar=(RatingBar) findViewById(R.id.ratingBar);
		appStore=(Button) findViewById(R.id.appStore);
		sms=(Button) findViewById(R.id.sms);
		share=(Button) findViewById(R.id.share);
		
		
		Intent intent=getIntent();
		nameOfApp=intent.getStringExtra("name");
		name.setText(nameOfApp);
		
		detailApp=intent.getStringExtra("description");
		description.setText(detailApp);
		link=intent.getStringExtra("url");
		if(intent.getStringExtra("price").equals("0"))
			price.setText("free");
		else
		price.setText("$"+intent.getStringExtra("price"));
		users.setText(intent.getStringExtra("users"));
		last_update.setText(intent.getStringExtra("last_update"));
		rating.setText(intent.getStringExtra("rating"));
		Picasso.with(this).load(intent.getStringExtra("imagee")).centerCrop().resize(100,100).into(imageView);
		
		ratingBar.setRating(Float.parseFloat(intent.getStringExtra("rating")));
		
		appStore.setOnClickListener(this);
		sms.setOnClickListener(this);
		share.setOnClickListener(this);
	}//end Oncreate
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.share:
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		    sharingIntent.setType("text/plain");
		    String shareBody = detailApp+" "+link;//descripton of app to share
		    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,nameOfApp);
		    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		case R.id.appStore:
			Uri uri = Uri.parse(link);
			Intent intent = new Intent (Intent.ACTION_VIEW, uri); 
			startActivity(intent);
			break;
		case R.id.sms:
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		      smsIntent.setData(Uri.parse("smsto:"));
		      smsIntent.setType("vnd.android-dir/mms-sms");

		     // smsIntent.putExtra("address"  , new String ("0123456789"));
		      smsIntent.putExtra("sms_body"  ,nameOfApp +" App Link\n"+link);
		      try {
		         startActivity(smsIntent);
		         finish();
		         Log.i("Finished sending SMS...", "");
		      } catch (android.content.ActivityNotFoundException ex) {
		         Toast.makeText(getApplicationContext(), 
		         "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
		      }
			break;
		default:
			break;
		}
	}
	


}
