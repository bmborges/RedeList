package com.bruno.mobile.redelistmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.ArrayList;
import java.util.List;

//http://www.tutecentral.com/android-custom-navigation-drawer/
//http://www.101apps.co.za/index.php/articles/using-search-in-your-apps-a-tutorial.html


public class NavigationActivity extends ActionBarActivity{

    private SearchView mSearchView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedpreferences;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private CharSequence oldTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    @Override
    public void onBackPressed() {
        Sair();
        //super.onBackPressed();
    }
    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        sharedpreferences = getSharedPreferences(Splash.MyPREFERENCES, Context.MODE_PRIVATE);

        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Anunciantes", R.drawable.logolistview,null,R.layout.custom_drawer_item));
        dataList.add(new DrawerItem("Localização",0,null,R.layout.custom_drawer_header));
        dataList.add(new DrawerItem("Pais", 0, sharedpreferences.getString(Splash.Pais,""),R.layout.custom_drawer_item_2));
        dataList.add(new DrawerItem("Cidade", 0, sharedpreferences.getString(Splash.Cidade,""),R.layout.custom_drawer_item_2));
        //dataList.add(new DrawerItem("Pesquisa",0,null,R.layout.custom_drawer_header));
        //dataList.add(new DrawerItem("Alfabeto", R.drawable.logolistview,null,R.layout.custom_drawer_item));
        //dataList.add(new DrawerItem("Alfabeto", R.drawable.logolistview,null,R.layout.custom_drawer_item));
        dataList.add(new DrawerItem("Configurações",0,null,R.layout.custom_drawer_header));
        dataList.add(new DrawerItem("Sobre", R.drawable.ic_action_about, null,R.layout.custom_drawer_item));
        dataList.add(new DrawerItem("Sair", R.drawable.ic_action_remove, null,R.layout.custom_drawer_item));

        adapter = new CustomDrawerAdapter(this, dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(oldTitle);
                supportInvalidateOptionsMenu();
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                oldTitle = getSupportActionBar().getTitle();
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        MenuItem searchItem = menu.findItem(R.id.pesquisar);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(new TextSubmit());
        return true;
    }
    private final class TextSubmit implements SearchView.OnQueryTextListener{

        @Override
        public boolean onQueryTextSubmit(String s) {
            //Log.i("Script","onQueryTextSubmit");
            EasyTracker.getInstance(getApplicationContext()).send(MapBuilder.createEvent("botao","click","Pesquisa",null).build());

            Splash.arraylistTelefoneT.clear();
            Intent intent = new Intent(getApplicationContext(),SearchListTelefonesActivity.class);
            intent.putExtra("filtro",s);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            //Log.i("Script","onQueryTextChange");
            return false;
        }
    }

    private final class SairPositiveClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            NavigationActivity.this.finish();
            Splash.arraylistTelefone.clear();
            Splash.arraylistTelefoneA.clear();
        }
    }
    private final class SairNegativeClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }
    private void Sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair");
        builder.setMessage("Deseja realmente Sair?");
        builder.setCancelable(true);
        builder.setPositiveButton("Sim", new SairPositiveClickListener());
        builder.setNegativeButton("Nao", new SairNegativeClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.pesquisar){
            mSearchView.setIconified(true);
            return true;
        }

        if(id == R.id.sair){
            EasyTracker.getInstance(this).send(MapBuilder.createEvent("botao", "click", "Sair", null).build());
            Sair();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new AnuncianteFragment();
                oldTitle = dataList.get(possition).getItemName();
                setTitle(dataList.get(possition).getItemName());
                fragment.setArguments(args);
                FragmentManager frgManager = getSupportFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                break;
            case 2:
                //pais
                Intent pais = new Intent(getApplicationContext(), PaisActivity.class);
                startActivity(pais);
                break;
            case 3:
                //cidade
                Intent cidade = new Intent(getApplicationContext(), CidadeActivity.class);
                startActivity(cidade);
                break;
            case 5:
                //sobre
                Intent sobre = new Intent(getApplicationContext(), SobreActivity.class);
                startActivity(sobre);
                break;
            case 6:
                //sair
                Sair();
                break;
            default:
                break;
        }

       /* */

        mDrawerList.setItemChecked(possition, true);

        mDrawerLayout.closeDrawer(mDrawerList);

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
