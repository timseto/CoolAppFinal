package com.timboat.coolapp;


import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    TextView textView;
    ListView wishView;
    ListView haveView;
    TextView wishListTitle;
    FloatingActionButton bubbleStart, bubble1, bubble2, bubble3;
    Animation bubbleOpen, bubbleClose, rotateClockwise, rotateCounterClockwise;
    RelativeLayout entireFragment;
    adapterList wishAdapter;
    adapterList haveAdapter;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Marker locationOneMarker;
    Marker locationTwoMarker;
    Marker locationThreeMarker;

    boolean isOpen = false;
    int type;

    public PageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.page_fragment_layout, container, false);
        Bundle bundle = getArguments();
        textView = (TextView) view.findViewById(R.id.text_view);
        wishView = (ListView) view.findViewById(R.id.item_list);
        haveView = (ListView) view.findViewById(R.id.have);

        ArrayList<Item> wishlist = new ArrayList<Item>();
        for (String[] item : WebUtility.wishList)
            wishlist.add(new Item(item[0], Integer.parseInt(item[1]), 10, null));
        wishAdapter = new adapterList(this.getContext(), wishlist);
        wishView.setAdapter(wishAdapter);

        ArrayList<Item> have = new ArrayList<Item>();
        for (WebUtility.Store store : WebUtility.stores)
            for (String[] item : store.items) {
                boolean want = false;
                for (String desired[] : WebUtility.wishList)
                    if (desired[0].equals(item[0])) want = true;
                if (want)//change to want
                    have.add(new Item(item[0], Integer.parseInt(item[1]), 10, store.name));
            }
        haveAdapter = new adapterList(this.getContext(), have);
        haveView.setAdapter(haveAdapter);

        wishListTitle = (TextView) view.findViewById(R.id.wish_list_title);
        bubbleStart = (FloatingActionButton) view.findViewById(R.id.bubble_open_start);
        bubble1 = (FloatingActionButton) view.findViewById(R.id.bubble_element1);
        bubble2 = (FloatingActionButton) view.findViewById(R.id.bubble_element2);
        bubble3 = (FloatingActionButton) view.findViewById(R.id.bubble_element3);
        entireFragment = (RelativeLayout) view.findViewById(R.id.entire_fragment);

        bubbleOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.bubble_open);
        bubbleClose = AnimationUtils.loadAnimation(getActivity(), R.anim.bubble_close);
        rotateClockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_clockwise);
        rotateCounterClockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_counter_clockwise);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getView().setVisibility(View.GONE);

        if (bundle.getInt("count") == 1) {
            if (googlePlayServicesAvailable()) {
                mapFragment.getView().setVisibility(View.VISIBLE);
                if(mGoogleMap == null)
                    mapFragment.getMapAsync(this);
            }
            haveView.setVisibility(View.VISIBLE);
        } else if (bundle.getInt("count") == 2) {
            bubbleStart.setVisibility(View.VISIBLE);
            wishView.setVisibility(View.VISIBLE);

            wishListTitle.setVisibility(View.VISIBLE);

            bubbleStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isOpen) {
                        bubble1.startAnimation(bubbleClose);
                        bubble2.startAnimation(bubbleClose);
                        bubble3.startAnimation(bubbleClose);
                        bubbleStart.startAnimation(rotateCounterClockwise);
                        bubble1.setClickable(false);
                        bubble2.setClickable(false);
                        bubble3.setClickable(false);
                        isOpen = false;
                    } else {
                        bubble1.startAnimation(bubbleOpen);
                        bubble2.startAnimation(bubbleOpen);
                        bubble3.startAnimation(bubbleOpen);
                        bubbleStart.startAnimation(rotateClockwise);
                        bubble1.setClickable(true);
                        bubble2.setClickable(true);
                        bubble3.setClickable(true);

                        bubble1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                type = 0;
                                openAdder(type);
                            }
                        });
                        bubble2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                type = 1;
                                openAdder(type);
                            }
                        });
                        bubble3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                type = 2;
                                openAdder(type);
                            }
                        });
                        isOpen = true;
                    }
                }
            });
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        return view;
    }

    private void openAdder(int type) {
        Intent a = new Intent(getActivity(), itemAdder.class);
        a.putExtra("typeInt", type);
        startActivity(a);
        bubble1.startAnimation(bubbleClose);
        bubble2.startAnimation(bubbleClose);
        bubble3.startAnimation(bubbleClose);
        bubbleStart.startAnimation(rotateCounterClockwise);
        bubble1.setClickable(false);
        bubble2.setClickable(false);
        bubble3.setClickable(false);
        isOpen = false;
    }

    public boolean googlePlayServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this.getContext());
        Log.d("Connection", Boolean.toString(isAvailable == ConnectionResult.SUCCESS));
        if (isAvailable == ConnectionResult.SUCCESS)
            return true;
        else
            return false;
    }

    private void buildApi() {
        //while(mGoogleMap == null);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildApi();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildApi();
            mGoogleMap.setMyLocationEnabled(true);
        }

        //Place current location marker
        LatLng locationOne = new LatLng(43.468,-80.540 );
        MarkerOptions markerOptionsOne = new MarkerOptions();
        markerOptionsOne.position(locationOne);
        markerOptionsOne.title("Clothes Store");
        markerOptionsOne.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        locationOneMarker = mGoogleMap.addMarker(markerOptionsOne);

        LatLng locationTwo = new LatLng(43.470,-80.550 );
        MarkerOptions markerOptionsTwo = new MarkerOptions();
        markerOptionsTwo.position(locationTwo);
        markerOptionsTwo.title("Computer Store");
        markerOptionsTwo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        locationTwoMarker = mGoogleMap.addMarker(markerOptionsTwo);

        LatLng locationThree = new LatLng(43.473,-80.545 );
        MarkerOptions markerOptionsThree = new MarkerOptions();
        markerOptionsThree.position(locationThree);
        markerOptionsThree.title("Grocery Store");
        markerOptionsThree.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        locationThreeMarker = mGoogleMap.addMarker(markerOptionsThree);
        //move map camera
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        //move map camera
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    public void onProviderEnabled(String s) {

    }


    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        boolean tried = false;
        while(!mGoogleApiClient.isConnected())
        {
            if(!tried) {
                mGoogleApiClient.connect();
                tried = true;
            }
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildApi();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity().getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

}

