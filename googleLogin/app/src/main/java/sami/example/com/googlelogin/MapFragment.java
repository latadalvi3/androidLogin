package sami.example.com.googlelogin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;

import sami.example.com.googlelogin.dummy.ConnectionDetector;

import static sami.example.com.googlelogin.R.layout.fragment_map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    Location location;
    double longitude;
    double latitude;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final LatLng KALEWADI = new LatLng(18.613821, 73.784624);
    private static final LatLng PUNE = new LatLng(18.5204, 73.8567);

    private static final LatLng DANGECHAUK = new LatLng(18.604324, 73.777908);
    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;
    private Marker currentMarker;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        return rootView;
    }

    public void getLocation() {
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            try {
                locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            } catch (SecurityException e) {
                System.out.println("Security error....");
            }

        } else {
            Toast.makeText(getActivity(), "Internet Connection Error..!!!", Toast.LENGTH_SHORT).show();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getActivity().getApplicationContext(),"The app does not have permission to get location",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
//            Toast.makeText(getActivity().getApplicationContext(),"Got Location !",Toast.LENGTH_SHORT).show();
            this.location = location;
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //  System.out.println("latitude="+latitude+", longitude="+longitude);
        }

        LatLng currentLocation;
        if (location != null) {
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            currentLocation = PUNE;
        }
        if (currentMarker != null && currentMarker.isVisible()) {
            currentMarker.remove();
        }
        currentMarker = googleMap.addMarker(new MarkerOptions().position(currentLocation));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        // Add some markers to the map, and add a data object to each marker.
        mPerth = googleMap.addMarker(new MarkerOptions()
                .position(KALEWADI)
                .title("Kalewadi D-Mart")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));

        mSydney = googleMap.addMarker(new MarkerOptions()
                .position(PUNE)
                .title("Pune")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));

        mBrisbane = googleMap.addMarker(new MarkerOptions()
                .position(DANGECHAUK)
                .title("Kalewadi Fata")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));

        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        getLocation();
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        Context context = getActivity().getApplicationContext();
        CharSequence text = marker.getTitle() + " has been clicked ";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
