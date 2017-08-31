package com.studios.amit.dsiofficial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeTableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String timeTableToShow;
    private FrameLayout timeTableLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeTableFragment newInstance(String param1, String param2) {
        TimeTableFragment fragment = new TimeTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.top_time_change_section){
            Intent intent = new Intent(getActivity(), ChooseSectionActivity.class);
            startActivity(intent);
        }

        if(id == R.id.top_time__about){
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        }

        if(id == R.id.top_time__logout) {
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

        return super.onOptionsItemSelected(item);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_time_table_nav, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Boolean isFirstTimeTable = sp1.getBoolean("isFirstTimeTable", false);
        User.setClassChosen(sp1.getString("yearChosen", "CSE 3A"));
        //Default view
        View view = inflater.inflate(R.layout.fragment_time_table_cse3d, container, false);
        // Inflate the layout for this fragment
        if(!isFirstTimeTable){
            Intent intent = new Intent(getActivity(), ChooseSectionActivity.class);
            startActivity(intent);
            //Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
            getActivity().getSharedPreferences("Login", MODE_PRIVATE).edit().putBoolean("isFirstTimeTable", true).commit();
            //Toast.makeText(getContext(), Boolean.toString(sp1.getBoolean("isFirstTimeTable", false)), Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return null;
        }
        if(User.getClassChosen() == null){
            getActivity().finish();
        }
        Toast.makeText(getContext(),"Tip: Choose your table from the drop-down above.", Toast.LENGTH_SHORT).show();

        switch (User.getClassChosen()){

            case "CSE 3D":
                return inflater.inflate(R.layout.fragment_time_table_cse3d, container, false);

            case "CSE 3A":
                return inflater.inflate(R.layout.fragment_time_table_cse3a, container, false);

            case "CSE 3B":
                return inflater.inflate(R.layout.fragment_timetable_cse3b, container, false);

            case "CSE 3C":
                return inflater.inflate(R.layout.fragment_timetable_cse3c, container, false);//----------------------------CHANGE !!!!

            case "CSE 5A":
                return inflater.inflate(R.layout.fragment_timetable_cse5a, container, false);

            case "CSE 5B":
                return inflater.inflate(R.layout.fragment_timetable_cse5b, container, false);

            case "CSE 5C":
                return inflater.inflate(R.layout.fragment_timetable_cse5c, container, false);

            case "CSE 5D":
                return inflater.inflate(R.layout.fragment_timetable_cse5d, container, false);
        }
        return view;

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
           // throw new RuntimeException(context.toString()
             //       + " must implement OnFragmentInteractionListener");
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
