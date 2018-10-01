package tigerhacks.android.tigerhacksapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrizesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrizesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrizesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PrizeCardView card1;
    private View layoutView;
    private LinearLayout ll, cardLinearLayout;
    private ProgressBar progressBar;
    private PrizeCardView.Type currentType = PrizeCardView.Type.MAIN;
    private TabLayout tabLayout;
    private ArrayList<Prize> cardList;

    private OnFragmentInteractionListener mListener;

    public PrizesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrizesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrizesFragment newInstance(String param1, String param2) {
        PrizesFragment fragment = new PrizesFragment();
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

        layoutView = inflater.inflate(R.layout.fragment_prizes, container, false);
        ll = layoutView.findViewById(R.id.linearView);
        tabLayout = layoutView.findViewById(R.id.typeTabLayout);
        cardLinearLayout = layoutView.findViewById(R.id.prizeCardLinearLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0)
                {
                    currentType = PrizeCardView.Type.MAIN;
                    addCardsByType(cardList);
                }
                else if(tab.getPosition() == 1)
                {
                    currentType = PrizeCardView.Type.BEGINNER;
                    addCardsByType(cardList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        progressBar = layoutView.findViewById(R.id.progressBar);

        //create cards. This is static, and will be replaced by dynamic creation through the API


        Retrofit tigerHacksRetrofit = new Retrofit.Builder()
                .baseUrl("https://n61dynih7d.execute-api.us-east-2.amazonaws.com/production/tigerhacksPrizes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TigerHacksService service = tigerHacksRetrofit.create(TigerHacksService.class);
        Call<PrizeList> prizes = service.listPrizes("https://n61dynih7d.execute-api.us-east-2.amazonaws.com/production/tigerhacksPrizes");
        prizes.enqueue(new Callback<PrizeList>() {
            @Override
            public void onResponse(Call<PrizeList> call, Response<PrizeList> response) {
                int statusCode = response.code();
                PrizeList prizeList = response.body();
                progressBar.setVisibility(View.GONE);
                populatePrizes(prizeList);
            }

            @Override
            public void onFailure(Call<PrizeList> call, Throwable t) {
                Snackbar.make(layoutView, "TigerHacks API call failed. Make sure you are connected to the internet.", Snackbar.LENGTH_SHORT).show();
            }
        });


        return layoutView;
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

    public void populatePrizes(PrizeList prizeList)
    {
        if(prizeList == null)
        {
            return;
        }
        cardList = (ArrayList<Prize>)prizeList.getPrizes();
        addCardsByType(cardList);
    }

    public void addCardsByType(ArrayList<Prize> list)
    {
        cardLinearLayout.removeAllViews();
        for(Prize prize : list)
        {
            PrizeCardView card = (PrizeCardView)LayoutInflater.from(ll.getContext()).inflate(R.layout.prize_card_layout, ll, false);
            card.setDescription(prize.getDescription());
            card.setTitle(prize.getTitle());
            card.setPrizes(new ArrayList<String>(Arrays.asList(prize.getReward())));
            card.onClickAction(layoutView);

            if(prize.getPrizetype().equals("Beginner"))
            {
                Log.e("TEST","WORKS");
                card.setType(PrizeCardView.Type.BEGINNER);
            }
            else if(prize.getPrizetype().equals("Main"))
            {
                card.setType(PrizeCardView.Type.MAIN);
            }

            if(card.getType() == currentType)
            {
                cardLinearLayout.addView(card);
            }
        }
    }
}
