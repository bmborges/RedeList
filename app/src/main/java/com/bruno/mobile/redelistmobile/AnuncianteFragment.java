package com.bruno.mobile.redelistmobile;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;


public class AnuncianteFragment extends Fragment{

    ListView listview;
    Button bt_plus_empresa;
    ListViewAdapter adapter;
    String anunciante = "S";
    String filtro = "Todos";
    int offset = 0;
    int addOffset = 10;
    int limit  = 10;
    View view;
    public View loadMoreView;
    boolean isLoading = false;

    public AnuncianteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(Splash.arraylistTelefoneA.size() <= 0){
            new DownloadTelefoneJSON(view.getContext(),anunciante,offset,limit,filtro).execute();
        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_anunciante,container,false);

        listview = (ListView) view.findViewById(R.id.listView);

        /*loadMoreView = ((LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.loading_footer, null, false);*/

        bt_plus_empresa = (Button) view.findViewById(R.id.bt_plus_empresas);

        bt_plus_empresa.setOnClickListener(new bt_plus_click());
        listview.setOnScrollListener(new onScrollListView());

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
                //listview.addFooterView(loadMoreView);
                bt_plus_empresa.setText("Pesquisar +");
                offset += addOffset;
                isLoading = true;
            }

        }
    }
    public final class bt_plus_click implements View.OnClickListener{

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
    }

}
