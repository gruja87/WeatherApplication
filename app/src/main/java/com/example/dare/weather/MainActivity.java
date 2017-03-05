package com.example.dare.weather;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dare.weather.adapter.ForecastAdapter;
import com.example.dare.weather.model.Forecast;
import com.example.dare.weather.model.Query;
import com.example.dare.weather.model.Weather;
import com.example.dare.weather.remote.Factory;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String URL_FIRST_PART = "select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22(";
    public static final String PATH_1 = "%2C";
    public static final String PATH_2 = ")%22)";
    public static final String PATH_3 = "%20and%20u%3D'C'";
    public static final String PATH_4 = "%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    private static final int MY_PERMISSION = 0;

    TextView tempWeather,placeWeather,dateUpdate,descWeather,temp_min,temp_max,country;
    ImageView image;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mManager;
    private ForecastAdapter mAdapter;

    private ArrayList<Forecast> mList;
    private SwipeRefreshLayout mRefresh;

    MaterialSearchView searchView;
    Toolbar toolbar;

    LocationManager locationManager;
    String provider;
    Location location;
    double lat, lng;
    //USPEOOOOO!!!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicComponent();
        addListener();
        showWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void addListener() {

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                String url = URL_FIRST_PART+location+PATH_2+PATH_3;
                String json = "json";
                String store = "store"+ PATH_4;
                Map<String,String> params= new HashMap<>();
                params.put("q",url);
                params.put("env",store);
                params.put("format",json);

                Factory.getInstance().getWeather(params).enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        setViews(response);
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWeather();
                mRefresh.setRefreshing(false);
            }
        });

    }

    public void inicComponent() {
        tempWeather = (TextView) findViewById(R.id.temperature_weather);
        temp_min = (TextView) findViewById(R.id.temp_min);
        temp_max = (TextView) findViewById(R.id.temp_max);
        placeWeather = (TextView) findViewById(R.id.city);
        dateUpdate = (TextView) findViewById(R.id.date_update);
        descWeather = (TextView) findViewById(R.id.description_weather);
        country = (TextView) findViewById(R.id.country);
        image = (ImageView) findViewById(R.id.image_weather);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mManager = new GridLayoutManager(getApplicationContext(), 5);
        mRecyclerView.setLayoutManager(mManager);

    }

    private void setViews(Response<Weather> response) {
        if (response.body()!=null){
            Query query = response.body().getQuery();
            tempWeather.setText(query.getResults().getChannel().getItem().getCondition().getTemp() + "°C");
            temp_min.setText(query.getResults().getChannel().getAstronomy().getSunrise());
            temp_max.setText(query.getResults().getChannel().getAstronomy().getSunset());
            placeWeather.setText(query.getResults().getChannel().getLocation().getCity());
            country.setText(query.getResults().getChannel().getLocation().getCountry());
            dateUpdate.setText(query.getResults().getChannel().getLastBuildDate());
            descWeather.setText(query.getResults().getChannel().getItem().getCondition().getText());
            int resource = getResources().getIdentifier("drawable/icon_" + query.getResults().getChannel().getItem().getCondition().getCode(), null, getPackageName());
            Picasso.with(getApplicationContext()).load(resource).into(image);
            mList = (ArrayList<Forecast>) query.getResults().getChannel().getItem().getForecast();
            mAdapter = new ForecastAdapter(getApplicationContext(), mList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setHasFixedSize(true);
        }
    }

    public void showWeather() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, MY_PERMISSION);
            return;
        }
        location = locationManager.getLastKnownLocation(provider);

        if (location!=null){
            lat = location.getLatitude();
            lng = location.getLongitude();

            String url = URL_FIRST_PART+lat+PATH_1+lng+PATH_2+PATH_3;
            String json = "json";
            String store = "store"+ PATH_4;
            Map<String,String> params= new HashMap<>();
            params.put("q",url);
            params.put("env",store);
            params.put("format",json);


            Factory.getInstance().getWeather(params).enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    setViews(response);
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Toast.makeText(this, "No location!!!", Toast.LENGTH_SHORT).show();
        }
//        Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onResponse(Call<Weather> call, Response<Weather> response) {
//                setViews(response);
//            }
//
//            @Override
//            public void onFailure(Call<Weather> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }



}
