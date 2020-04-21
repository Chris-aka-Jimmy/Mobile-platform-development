package com.example.mobileplatformdevelopment.ui.map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.mobileplatformdevelopment.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class mapsFragment extends androidx.fragment.app.Fragment {

   private MapView MapView;
   private GoogleMap GoogleMap;

   ArrayList<String> ArrayList = new ArrayList<String>();

   ArrayAdapter ArrayAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        MapView = (MapView) root.findViewById(R.id.mapView);
        MapView.onCreate(savedInstanceState);
        MapView.onResume();

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception Exception){
            Exception.printStackTrace();
        }

        MapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {

                try{
                    GoogleMap = gMap;

                    GoogleMap.setMyLocationEnabled(true);

                   new mapsFragment.AsyncTask().execute();
                   new mapsFragment.AsyncTask2().execute();


                }catch (Exception Exception){
                    System.out.println(Exception.getMessage());
                }

            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        MapView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        MapView.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        MapView.onLowMemory();
    }

    private class AsyncTask extends android.os.AsyncTask<Integer, Integer, ArrayList<String >> {
        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            String Url = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

            try {

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder Builder = Factory.newDocumentBuilder();
                Document Doc = Builder.parse(Url);

                Doc.getDocumentElement().normalize();

                NodeList Nodelist = Doc.getElementsByTagName("item");

                for (int counter = 0; counter < Nodelist.getLength(); counter++) {
                    Node Node = Nodelist.item(counter);

                    if (Node.getNodeType() == Node.ELEMENT_NODE && Node != null) {

                        Element Element = (Element) Node;

                        String title, description, georss;

                        title = " road incident";

                        if (Element.getElementsByTagName("description").item(0) != null) {
                            description = Element.getElementsByTagName("description").item(0).getTextContent();
                        } else {
                            description = "No Description";
                        }

                        if (Element.getElementsByTagName("georss:point").item(0) != null) {
                            georss = Element.getElementsByTagName("georss:point").item(0).getTextContent();
                        } else {
                            georss = "No Data";
                        }

                        ArrayList.add(title + "/###/" + description + "/###/" + georss);


                    }

                }
                return ArrayList;

            } catch (Exception Exception) {
                System.out.println((Exception.getMessage()));
            }


            return null;
        }

        protected void onPostExecute(ArrayList<String> result) {

            try {
                for (String strTemp : result){

                    String splitString = strTemp.toString();

                    String[] arrOfString = splitString.split("/###/");

                    String splitstring2 = arrOfString[2].toString();
                    String[] latlong2 = splitstring2.split(" ");

                    String latlong3=latlong2[1].replace(",","");

                    LatLng LatLng = new LatLng(Double.parseDouble(latlong2[0]), Double.parseDouble(latlong3));
                    GoogleMap.addMarker(new MarkerOptions().position(LatLng).title(arrOfString[0]).snippet(arrOfString[1]));
                }
            }catch (Exception Exception){
                System.out.println((Exception.getMessage()));
            }

        }
    }


    private class AsyncTask2 extends android.os.AsyncTask<Integer, Integer, ArrayList<String >> {
        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            String Url = "https://trafficscotland.org/rss/feeds/roadworks.aspx";

            try {

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder Builder = Factory.newDocumentBuilder();
                Document Doc = Builder.parse(Url);

                Doc.getDocumentElement().normalize();

                NodeList Nodelist = Doc.getElementsByTagName("item");

                for (int counter = 0; counter < Nodelist.getLength(); counter++) {
                    Node Node = Nodelist.item(counter);

                    if (Node.getNodeType() == Node.ELEMENT_NODE && Node != null) {

                        Element Element = (Element) Node;

                        String title, description, georss;

                        title = " road works";

                        if (Element.getElementsByTagName("description").item(0) != null) {
                            description = Element.getElementsByTagName("description").item(0).getTextContent();
                        } else {
                            description = "No Description";
                        }

                        if (Element.getElementsByTagName("georss:point").item(0) != null) {
                            georss = Element.getElementsByTagName("georss:point").item(0).getTextContent();
                        } else {
                            georss = "No Data";
                        }

                        ArrayList.add(title + "/###/" + description + "/###/" + georss);


                    }

                }
                return ArrayList;

            } catch (Exception Exception) {
                System.out.println((Exception.getMessage()));
            }


            return null;
        }

        protected void onPostExecute(ArrayList<String> result) {

            try {
                for (String strTemp : result){

                    String splitString = strTemp.toString();

                    String[] arrOfString = splitString.split("/###/");

                    String splitstring2 = arrOfString[2].toString();
                    String[] latlong2 = splitstring2.split(" ");

                    String latlong3=latlong2[1].replace(",","");

                    LatLng LatLng = new LatLng(Double.parseDouble(latlong2[0]), Double.parseDouble(latlong3));
                    GoogleMap.addMarker(new MarkerOptions().position(LatLng).title(arrOfString[0]).snippet(arrOfString[1]));
                }
            }catch (Exception Exception){
                System.out.println((Exception.getMessage()));
            }

        }
    }
}


