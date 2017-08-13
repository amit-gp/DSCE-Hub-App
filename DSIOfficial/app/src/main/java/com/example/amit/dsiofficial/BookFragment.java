package com.example.amit.dsiofficial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<BookNotification> bookNotifications;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    String notificationURL = UrlStrings.bookUrl;

    private OnFragmentInteractionListener mListener;

    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        //Initializing Views
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        bookNotifications = new ArrayList<>();
        sendAndPrintResponse();
        return view;
    }

    private void sendAndPrintResponse()
    {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this.getContext(),"Loading Data", "Please wait...",false,false);
        requestQueue = VolleySingleton.getInstance(this.getContext()).getRequestQueue(this.getContext());

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, notificationURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("ALERT !!", response.toString());
                loading.dismiss();
                parseJsonArrayResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                addRefreshGui();
                Log.i("ALERT ERROR!!", error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void addRefreshGui()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Could not connect to Database.")
                .setCancelable(false)
                .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendAndPrintResponse();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void parseJsonArrayResponse(JSONArray jsonArray)
    {
        for (int i = 0; i < jsonArray.length(); i++)
        {
            BookNotification bookNotification = new BookNotification();
            JSONObject jsonObject = null;
            try{
                jsonObject = jsonArray.getJSONObject(i);

                bookNotification.setTitle(jsonObject.getString("Title"));
                bookNotification.setAuthor(jsonObject.getString("Author"));
                bookNotification.setDescription(jsonObject.getString("Description"));
                bookNotification.setEdition(jsonObject.getString("Edition"));
                bookNotification.setPrice(jsonObject.getString("Price"));
                bookNotification.setSellerEmail(jsonObject.getString("Email"));
                bookNotification.setSellerName(jsonObject.getString("Name"));
                bookNotification.setContactNumber(jsonObject.getString("ContactNumber"));

            }catch (Exception e){e.printStackTrace();}
            bookNotifications.add(bookNotification);
        }

        //Finally initializing our adapter
        adapter = new BookAdapter(bookNotifications, this.getContext());
        //Adding adapter to recyclerView
        recyclerView.setAdapter(adapter);
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
            //Toast.makeText(this.getContext(), "Hello", Toast.LENGTH_LONG).show();
            //throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
