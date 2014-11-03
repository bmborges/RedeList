package com.bruno.mobile.redelistmobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//http://www.javacodegeeks.com/2013/10/android-listview-endless-adapter.html
//http://unitid.nl/androidpatterns/
//http://xamarin.com/
public class HomeFragment extends Fragment{

    ListView listview;
    ListViewAdapter adapter;
    String anunciante = "S";
    String filtro = "Todos";
    int offset = 0;
    int addOffset = 10;
    int limit  = 10;
    View loadMoreView;
    View view;
    boolean isLoading = false;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home,container,false);

        listview = (ListView) view.findViewById(R.id.listView);

        /*loadMoreView = ((LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.loading_footer, null, false);*/

        listview.setOnScrollListener(new onScrollListView());

        final Button bt_plus_empresa = (Button) view.findViewById(R.id.bt_plus_empresas);

        bt_plus_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anunciante.equals("S")){
                    offset = 0;
                    anunciante = "N";
                    if (Splash.arraylistTelefone.size() <= 0) {
                        new DownloadTelefoneJSON(view.getContext(), anunciante, offset, limit, filtro).execute();
                    } else {
                        adapter = new ListViewAdapter(view.getContext(), Splash.arraylistTelefone);
                        listview.setAdapter(adapter);
                    }
                    ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mais Empresas");
                    bt_plus_empresa.setText("Anunciantes");
                } else {
                    offset = 0;
                    anunciante = "S";
                    //new DownloadTelefoneJSON(view.getContext(),anunciante,offset,limit,filtro).execute();
                    adapter = new ListViewAdapter(view.getContext(), Splash.arraylistTelefoneA);
                    listview.setAdapter(adapter);
                    ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Anunciantes");
                    bt_plus_empresa.setText("Mais Empresas");
                }
            }
        });

        return view;
    }

private final class onScrollListView implements AbsListView.OnScrollListener{

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        int l = visibleItemCount + firstVisibleItem;

        if (l >= totalItemCount && !isLoading) {
            // It is time to add new data. We call the listener
            listview.addFooterView(loadMoreView);
            new DownloadTelefoneJSON(view.getContext(),anunciante,offset,limit,filtro).execute();
            isLoading = true;
        }

    }
}


}
