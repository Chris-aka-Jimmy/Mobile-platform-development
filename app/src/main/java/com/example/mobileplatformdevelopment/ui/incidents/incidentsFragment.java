package com.example.mobileplatformdevelopment.ui.incidents;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mobileplatformdevelopment.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class incidentsFragment extends androidx.fragment.app.Fragment {

    ArrayList<String> Arraylist = new ArrayList<String>();

    ListView ListView;
    EditText EditText;

    ArrayAdapter<String> ArrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_incidents, container, false);

        new AsyncTask().execute();

        ListView = (ListView) root.findViewById(R.id.incidents_feed);
        EditText = (EditText) root.findViewById(R.id.searchFilter);

        EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (incidentsFragment.this).ArrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return root;
    }

    private class AsyncTask extends android.os.AsyncTask<Integer, Integer, ArrayList<String>> {
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

                        String title, description;

                        if (Element.getElementsByTagName("title").item(0) != null) {
                            title = Element.getElementsByTagName("title").item(0).getTextContent();
                        } else {
                            title = "No Data";
                        }

                        if (Element.getElementsByTagName("description").item(0) != null) {
                            description = Element.getElementsByTagName("description").item(0).getTextContent();
                        } else {
                            description = "No Data";
                        }

                        String data = "\n" + title + "\n\n" + description;

                        Arraylist.add(data);


                    } return Arraylist;

                }

            } catch (Exception Exception) {
                System.out.println((Exception.getMessage()));


            }


            return null;
        }
            protected void onPostExecute(ArrayList<String> result) {


                ArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        result
                );

                ListView.setAdapter(ArrayAdapter);

            }
        }

        protected void onPreExecute(String result) {
            ArrayAdapter = (ArrayAdapter<String>) ListView.getAdapter();
        }
    }



