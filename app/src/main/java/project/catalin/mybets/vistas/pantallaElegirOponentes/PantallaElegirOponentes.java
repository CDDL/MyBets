package project.catalin.mybets.vistas.pantallaElegirOponentes;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.R;

public class PantallaElegirOponentes extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());


        setContentView(R.layout.pantalla_elegir_oponentes);

        RowEjemploAdapter adapter = new RowEjemploAdapter(this, Arrays.asList(null,null,null,null));

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.mybets_action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onListItemClick(ListView l,
                                View v, int position, long id) {
        // call detail activity for clicked entry
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query =
                    intent.getStringExtra(SearchManager.QUERY);
            realizarBusqueda(query);
        }
    }

    private void realizarBusqueda(String queryStr) {
        // get a Cursor, prepare the ListAdapter
        // and set it
    }

    public class RowEjemploAdapter extends BaseAdapter {

        private Context mContext;
        private List<Object> elements;

        public RowEjemploAdapter (Context mContext, List<Object> elements) {
            this.mContext = mContext;
            this.elements = elements;
        }

        public int getCount() {
            return elements.size();
        }

        public Entity getItem(int position) {
            return (Entity)elements.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(mContext, R.layout.pantalla_elegir_oponentes_item_xml, null);
            return v;
        }

    }

}
