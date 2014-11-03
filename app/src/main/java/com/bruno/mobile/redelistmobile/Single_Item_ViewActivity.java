package com.bruno.mobile.redelistmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bruno on 30/09/2014.
 */
public class Single_Item_ViewActivity extends Activity{

    String nome;
    String telefone;
    String logo_listview;

    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_view);

        Intent i = getIntent();

        nome = i.getStringExtra("nome");
        telefone = i.getStringExtra("telefone");
        logo_listview = i.getStringExtra("logo_listview");

        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtTelefone = (TextView) findViewById(R.id.txtTelefone);

        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);

        txtNome.setText(nome);
        txtTelefone.setText(telefone);
        imageLoader.DisplayImage(logo_listview,imgLogo);
    }
}
