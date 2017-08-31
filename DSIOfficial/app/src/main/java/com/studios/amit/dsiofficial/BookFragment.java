package com.studios.amit.dsiofficial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


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
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<BookNotification> bookNotifications;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    String notificationURL = UrlStrings.bookUrl;
    SwipeRefreshLayout swipeLayout;

    private OnFragmentInteractionListener mListener;

    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        //MenuItem item = menu.findItem(R.id.top_navigation_search);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_book_navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i("ALERT !!", "OPTIONS SELECTED");

        if(id == R.id.top_book_profile){
            Intent intent = new Intent(getActivity(), MyProfileActivity.class);
            startActivity(intent);
        }

        if(id == R.id.top_book_about){
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        }

        if(id == R.id.top_book_logout)
        {
            //Toast.makeText(this, "DSH BRD", Toast.LENGTH_LONG).show();
            User.setIsLoggedin(false);
            User.removeAllCredentials();

            SharedPreferences sp= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putBoolean("isLoggedIn", false);
            Ed.commit();

            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            return true;
        }

        if(id == R.id.top_book_mybooks){
            sendAndPrintResponse("myBooks");
            return true;
        }

        if(id == R.id.sortMathematics) {

            sendAndPrintResponse("Math");
          //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortPhysics) {
            sendAndPrintResponse("Physics");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortChemicalEngg) {
            sendAndPrintResponse("ChemicalEngg");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortAeronautical) {
            sendAndPrintResponse("Aeronautical");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortAutomobile) {
            sendAndPrintResponse("Automobile");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortBiology) {
            sendAndPrintResponse("Biology");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortBiotechnology) {
            sendAndPrintResponse("Biotechnology");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortCivilEngg) {
            sendAndPrintResponse("CivilEngg");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortComputerScience) {
            sendAndPrintResponse("ComputerSc");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortTelecomunication) {
            sendAndPrintResponse("Telecommunication");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortElectronics) {
            sendAndPrintResponse("Electronics");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortElectrical) {
            sendAndPrintResponse("Electrical");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.sortChemistry) {
            sendAndPrintResponse("Chemistry");
            //  Toast.makeText(getActivity(), "MATH CLICKED !!", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
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
        View view =  inflater.inflate(R.layout.fragment_book, container, false);
        setHasOptionsMenu(true);
        //Initializing Views
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewBook);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshbook);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                sendAndPrintResponse("");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabBook);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), NewBookActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(), "FAB CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        bookNotifications = new ArrayList<>();
        sendAndPrintResponse("");
        return view;
    }

    private void sendAndPrintResponse(final String subject)
    {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this.getContext(),"Loading Data", "Please wait...",false,false);
        requestQueue = VolleySingleton.getInstance(this.getContext()).getRequestQueue(this.getContext());

        Log.d("ALERT !!", notificationURL);
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, notificationURL + "?Subject=" + subject + "&Name=" + User.getUserName(), null, new Response.Listener<JSONArray>() {
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
        Toast.makeText(getContext(), "Could not connect to Database.\nSwipe down to refresh.", Toast.LENGTH_LONG).show();
        swipeLayout.setRefreshing(false);
    }

    private void parseJsonArrayResponse(JSONArray jsonArray)
    {
        bookNotifications.clear();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            BookNotification bookNotification = new BookNotification();
            JSONObject jsonObject = null;
            try{
                jsonObject = jsonArray.getJSONObject(i);

                bookNotification.setTitle(jsonObject.getString("Title"));
                bookNotification.setAuthor(jsonObject.getString("Author"));
                bookNotification.setDescription(jsonObject.getString("Description"));
                bookNotification.setPrice(jsonObject.getString("Price"));
                bookNotification.setSellerEmail(jsonObject.getString("Email"));
                bookNotification.setSellerName(jsonObject.getString("Name"));
                bookNotification.setContactNumber(jsonObject.getString("ContactNumber"));
                bookNotification.setSubjet(jsonObject.getString("Subject"));

            }catch (JSONException e) {
                e.printStackTrace();
            }
            bookNotifications.add(bookNotification);
        }

        //Finally initializing our adapter
        adapter = new BookAdapter(bookNotifications, this.getContext());
        //Adding adapter to recyclerView
        recyclerView.setAdapter(adapter);

        swipeLayout.setRefreshing(false);
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
