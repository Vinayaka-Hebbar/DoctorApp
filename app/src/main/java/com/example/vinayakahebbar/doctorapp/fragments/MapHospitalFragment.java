package com.example.vinayakahebbar.doctorapp.fragments;


import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.interfaces.JsonListener;
import com.example.vinayakahebbar.doctorapp.interfaces.ListListener;
import com.example.vinayakahebbar.doctorapp.interfaces.NetworkListener;
import com.example.vinayakahebbar.doctorapp.interfaces.OnUpdateMap;
import com.example.vinayakahebbar.doctorapp.model.Hospital;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.model.direction.DirectionObject;
import com.example.vinayakahebbar.doctorapp.model.direction.LegsObject;
import com.example.vinayakahebbar.doctorapp.model.direction.PolylineObject;
import com.example.vinayakahebbar.doctorapp.model.direction.RouteObject;
import com.example.vinayakahebbar.doctorapp.model.direction.StepsObject;
import com.example.vinayakahebbar.doctorapp.utils.GsonRequest;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.utils.dialogs.NotifyDialog;
import com.example.vinayakahebbar.doctorapp.utils.direction.DirectionHelper;
import com.example.vinayakahebbar.doctorapp.utils.direction.MapRouteDrawer;
import com.example.vinayakahebbar.doctorapp.utils.helper.SnackBarHelper;
import com.example.vinayakahebbar.doctorapp.utils.polyline.PolyDecoder;
import com.example.vinayakahebbar.doctorapp.utils.volley.VolleySingleton;
import com.example.vinayakahebbar.doctorapp.views.ImageLoaderView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapHospitalFragment extends Fragment implements OnMapReadyCallback,LocationListener,
        OnUpdateMap, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveStartedListener, NetworkListener, JsonListener {

    GoogleMap googleMap;
    private LocationManager locationManager;
    private List<Hospital> list;
    private View view;
    private MapView mapView;
    private LatLng curLatLan;
    private Context context;
    private String curLocation;
    private LinearLayout layout;
    private boolean moveCamera;
    private ListListener listListener;
    private MapRouteDrawer mapRouteDrawer;
    public MapHospitalFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map_hospital, container, false);
        context = view.getContext();
        mapView = (MapView)view.findViewById(R.id.map);
        layout = (LinearLayout)view.findViewById(R.id.layout_map);
        mapRouteDrawer = new MapRouteDrawer();
        if (googleServicesAvailable()) {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mapView.getMapAsync(this);
            MapsInitializer.initialize(context);
            initMap();
        }
        return view;
    }

    private void initMap() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(context);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(getActivity(), isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(view.getContext(), "can't connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        final double lan = location.getLongitude();
        final double lat = location.getLatitude();
        curLatLan = new LatLng(lat,lan);
        locationManager.removeUpdates(this);
        moveCamera = true;
        loadMapDetails(lat, lan, this);
    }

    private void loadMapDetails(final double lat, final double lan, final OnUpdateMap onUpdateMap) {
        new AsyncTask<Void, Integer, List<Address>>() {

            @Override
            protected List<Address> doInBackground(Void... voids) {
                Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
                List<Address> addresses = new ArrayList<>();
                try {
                    addresses =  geocoder.getFromLocation(lat, lan, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return addresses;
            }

            @Override
            protected void onPostExecute(List<Address> addresses) {
                if(addresses.size() != 0) {
                    String detail = addresses.get(0).getSubLocality();
                    if(detail != null && !detail.equals(curLocation))
                        onUpdateMap.UpdateMap(detail);
                }
            }
        }.execute();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnCameraMoveStartedListener(this);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.setOnMarkerClickListener(this);
        checkPermissions();
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);
            else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        } else {
            NotifyDialog dialog = new NotifyDialog(view.getContext());
            dialog.setClickListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            dialog.show("Enable Location Setting", "Ok");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);
                else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null)
            mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void UpdateMap(String text) {
        curLocation = text;
        if(moveCamera) {
            googleMap.addMarker(new MarkerOptions().position(curLatLan).title(text).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curLatLan, 15));
        }
        HttpUtils httpUtils = new HttpUtils(new String[]{text})
                .setOnLoaded(this);
        httpUtils.getHospitalLocations();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Object tag = marker.getTag();
        if (tag != null) {
            showPopUp(new ModelView(tag));
        }
        return false;
    }

    public void showPopUp(ModelView tag) {
        LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popup_hospital,null);
        final PopupWindow popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Hospital hospital = tag.isValue()?(Hospital) tag:list.get(Integer.parseInt(tag.toString()));
        if(hospital.isValue())
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(hospital.getLat(), hospital.getLan())).title(hospital.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_hospital)));
        ImageButton buttonClose = (ImageButton) popUpView.findViewById(R.id.btn_popup_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button buttonDistance = (Button)popUpView.findViewById(R.id.btn_popup_distance);
        buttonDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirection(new LatLng(hospital.getLat(),hospital.getLan()));
                popupWindow.dismiss();
            }
        });
        ImageLoaderView imageLoaderView = (ImageLoaderView)popUpView.findViewById(R.id.img_popup_hospital);
        imageLoaderView.loadImageWithUri(hospital.getImgUrl());
        TextView textViewName = (TextView)popUpView.findViewById(R.id.tv_popup_name);
        textViewName.setText(hospital.getName());
        TextView textView = (TextView)popUpView.findViewById(R.id.tv_popup_address);
        textView.setText(hospital.getAddress());
        popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
    }

    private void getDirection(LatLng latLng) {
        if(curLocation != null) {
            String url = DirectionHelper.getUrl(curLatLan, latLng);
            GsonRequest<DirectionObject> gsonRequest = new GsonRequest<DirectionObject>(
                    Request.Method.GET,
                    url, DirectionObject.class,
                    createRequestSuccessListener(),
                    createRequestErrorListener()
            );
            gsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DirectionHelper.SOCKET_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            VolleySingleton.getInstance(view.getContext()).addToRequestQueue(gsonRequest);
        }
    }

    private Response.Listener<DirectionObject> createRequestSuccessListener() {
        return new Response.Listener<DirectionObject>() {
            @Override
            public void onResponse(DirectionObject response) {
                if(response.getStatus().equals("OK")){
                    List<LatLng> directions = getDirectionPolyline(response.getRoutes());
                    mapRouteDrawer.drawRouteOnMap(googleMap,directions);
                }
            }
        };
    }

    private List<LatLng> getDirectionPolyline(List<RouteObject> routes) {
        List<LatLng> directionList = new ArrayList<>();
        for (RouteObject route : routes){
            List<LegsObject> legs = route.getLegs();
            for(LegsObject leg:legs){
                List<StepsObject> steps = leg.getSteps();
                for(StepsObject step:steps){
                    PolylineObject polyline = step.getPolyline();
                    String point = polyline.getPoints();
                    List<LatLng> singlePolyline = new PolyDecoder().decodePoly(point);
                    for(LatLng direction:singlePolyline)
                        directionList.add(direction);
                }
            }
        }
        return directionList;
    }

    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            LatLng mapLatLan = googleMap.getCameraPosition().target;
            moveCamera = false;
            loadMapDetails(mapLatLan.latitude,mapLatLan.longitude,this);
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            //tap
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            //app anim
        }
    }


    public void setListListener(ListListener listListener) {
        this.listListener = listListener;
    }

    @Override
    public void onLoaded(String response) {
        JsonIO jsonIO = new JsonIO(response)
                .setOnLoaded(this);
        jsonIO.getHospitalDetailsFromJson();
    }

    @Override
    public void onNetworkError(String error) {
        new SnackBarHelper(view,error, Snackbar.LENGTH_SHORT).showError();
    }

    @Override
    public void onParsed(List<ModelView> lists) {
        if (listListener != null)
            listListener.updateList(lists, new String[]{curLocation});
        list = new ArrayList<Hospital>();
        for (ModelView view : lists) {
            Hospital hospital = (Hospital) view;
            hospital.setValue(false);
            list.add(hospital);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(hospital.getLat(), hospital.getLan())).title(hospital.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_hospital))).setTag(list.size() - 1);
        }
    }

    @Override
    public void onParseError(String error) {

    }
}

