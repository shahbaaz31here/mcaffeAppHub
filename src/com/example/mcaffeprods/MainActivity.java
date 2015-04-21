package com.example.mcaffeprods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity implements OnClickListener {
	
	String URL="http://mcafee.0x10.info/api/app?type=json";
	ListView prodListView;
	RequestQueue queue;
	ArrayList<ProductModel> prodObjectList;//for storing  looks object
    ProductAdapter adapter;
    TextView sortBy;
    ProgressBar loadingBar;
    /** An array of strings to populate dropdown list */
    String[] actions = new String[] {
        "Price - Low  to High",
        "Price - High to Low",
        "Rating- Min  to Max",
        "Rating- Max  to Min"
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("AppHub");
		 //For handling volley loading  request
       
		queue=Volley.newRequestQueue(this);

		prodListView=(ListView) findViewById(R.id.prodList);
		sortBy=(TextView) findViewById(R.id.sortBy);
		loadingBar=(ProgressBar)findViewById(R.id.loadingBar);
		prodObjectList=new ArrayList<ProductModel>();
		//for fetching the JSON Data
		loadingBar.setVisibility(View.VISIBLE);
		callVolleyLoader(URL);
		 adapter=new ProductAdapter(this,R.layout.prod_list_row,prodObjectList);
         prodListView.setAdapter(adapter);
         /** Create an array adapter to populate dropdownlist */
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, actions);
  
         /** Enabling dropdown list navigation for the action bar */
        // getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
  
		prodListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ProductModel tag=new ProductModel();
				//parent.getItemAtPosition(position)
				tag=prodObjectList.get(position);
				Intent in=new Intent(getApplicationContext(),ProdDetailActivity.class);
				
				in.putExtra("imagee",tag.getImageUrl());
				in.putExtra("price",tag.getPrice());
				in.putExtra("type",tag.getType());
				in.putExtra("name",tag.getName());
				in.putExtra("rating",tag.getRating());
				in.putExtra("users",tag.getUsers());
				in.putExtra("description",tag.getDescription());
				in.putExtra("url",tag.getUrl());
				in.putExtra("last_update",tag.getLast_updated());
				
				//in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        startActivity(in);
			}
		});
		sortBy.setOnClickListener(this);
		registerForContextMenu(sortBy);
		/** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener =new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub
				
				return false;
			}
		};
		/** Setting dropdown items and item navigation listener for the actionbar */
       // getActionBar().setListNavigationCallbacks(adapter, navigationListener);
	}//end Oncreate
	

	
	
	
	
	//VOLLEY LOADER for products
    private void callVolleyLoader(String url) {


       // Log.e("position",""+position);

        //Volley request
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            int len;
            ProgressBar loading;
            JSONObject object1;
            @Override
            public void onResponse(JSONArray jsonArray) {
                // Do something with the response

                try {
                    Log.e("data", "" + jsonArray);

                    //JSONArray jarray=new JSONArray(data);
                    len=jsonArray.length();

                    //JSONArray jarray=jsono.getJSONArray("result");

                    for(int i=0; i<len; i++)
                    {
                        object1=jsonArray.getJSONObject(i);

                        ProductModel result=new ProductModel();
                        result.setName(object1.getString("name"));
                        result.setImageUrl(object1.getString("imagee"));
                        result.setPrice(object1.getString("price"));
                        result.setRating(object1.getString("rating"));
                        result.setUsers(object1.getString("users"));
                        result.setType(object1.getString("type"));
                        result.setDescription(object1.getString("description"));
                        result.setLast_updated(object1.getString("last_update"));
                        result.setUrl(object1.getString("url"));
                        
                       
                       prodObjectList.add(result);

                    }//end for
                    adapter.notifyDataSetChanged();
                    loadingBar.setVisibility(View.GONE);

                }  catch (ParseException e1) {
                    e1.printStackTrace();
                    //Log.e("jarray1",jarray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Handle the error
                VolleyLog.d("Error", "Error: " + volleyError.getMessage());

            }
        });


        // Add the request to the RequestQueue.
       queue.add(jsonArrayRequest);

       // MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);


    }


    //Handling the click on SortBy
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		openContextMenu(sortBy);
		
	}

	/*Comparator for sorting the list by price low to High*/
    public static Comparator<ProductModel> sortPriceLowToHigh = new Comparator<ProductModel>() {

	public int compare(ProductModel s1, ProductModel s2) {
	   String price1 = s1.getPrice();
	   String price2 = s2.getPrice();

	  
	   //ascending order
	   return price1.compareTo(price2);

	  
    }};
    
    /*Comparator for sorting the list by price High to low*/
    public static Comparator<ProductModel> sortPriceHighToLow = new Comparator<ProductModel>() {

	public int compare(ProductModel s1, ProductModel s2) {
	   String price1 = s1.getPrice();
	   String price2 = s2.getPrice();

	   //descending order
	   return price2.compareTo(price1);

	
	  
    }};
    
    /*Comparator for sorting the list by Rating low to high*/
    public static Comparator<ProductModel> sortRatingLowToHigh = new Comparator<ProductModel>() {

	public int compare(ProductModel s1, ProductModel s2) {
	   String rate1 = s1.getRating();
	   String rate2 = s2.getRating();

	   //ascending order
	   return rate1.compareTo(rate2);

	
	  
    }};
    
    /*Comparator for sorting the list by Rating High to low*/
    public static Comparator<ProductModel> sortRatingHighToLow = new Comparator<ProductModel>() {

	public int compare(ProductModel s1, ProductModel s2) {
	   String rate1 = s1.getRating();
	   String rate2 = s2.getRating();

	   //descending order
	   return rate2.compareTo(rate1);

	
	  
    }};
    

	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }
	


	@Override
public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    
    switch (item.getItemId()) {
        case R.id.pLToH://price low to High
        	Collections.sort(prodObjectList,sortPriceLowToHigh);
    		
    		loadingBar.setVisibility(View.VISIBLE);
    		 adapter=new ProductAdapter(this,R.layout.prod_list_row,prodObjectList);
    		 adapter.notifyDataSetChanged();
    		 loadingBar.setVisibility(View.GONE);
             prodListView.setAdapter(adapter);
            return true;
        case R.id.pHToL:
        	Collections.sort(prodObjectList,sortPriceHighToLow);
    		
    		loadingBar.setVisibility(View.VISIBLE);
    		 adapter=new ProductAdapter(this,R.layout.prod_list_row,prodObjectList);
    		 adapter.notifyDataSetChanged();
    		 loadingBar.setVisibility(View.GONE);
             prodListView.setAdapter(adapter);
            return true;
        case R.id.rMaxToMin:
        	Collections.sort(prodObjectList,sortRatingHighToLow);
    		
    		loadingBar.setVisibility(View.VISIBLE);
    		 adapter=new ProductAdapter(this,R.layout.prod_list_row,prodObjectList);
    		 adapter.notifyDataSetChanged();
    		 loadingBar.setVisibility(View.GONE);
             prodListView.setAdapter(adapter);
        	return true;
        case R.id.rMintoMax:
        	Collections.sort(prodObjectList,sortRatingLowToHigh);
    		
    		loadingBar.setVisibility(View.VISIBLE);
    		 adapter=new ProductAdapter(this,R.layout.prod_list_row,prodObjectList);
    		 adapter.notifyDataSetChanged();
    		 loadingBar.setVisibility(View.GONE);
             prodListView.setAdapter(adapter);
        	return true;
        
        default:
            return super.onContextItemSelected(item);
    }
}
}
