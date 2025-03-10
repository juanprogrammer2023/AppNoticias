package com.example.tiendaecci;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        fab = findViewById(R.id.fab);

        setupViewPager();
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getTabTitle(position))
        ).attach();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                int categoryId = currentItem + 1; // Ajusta según los IDs reales de las categorías

                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                intent.putExtra("categoryId", categoryId);
                startActivity(intent);
            }
        });

    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

// Agregar fragmentos para cada categoría
        adapter.addFragment(PostListFragment.newInstance(1), "Deportes");
        adapter.addFragment(PostListFragment.newInstance(2), "Noticias");
        adapter.addFragment(PostListFragment.newInstance(3), "Tecnología");
        adapter.addFragment(PostListFragment.newInstance(4), "Alimentación");

// Configurar el ViewPager
        viewPager.setAdapter(adapter);

    }

    private String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "Deportes";
            case 1:
                return "Noticias";
            case 2:
                return "Tecnología";
            case 3:
                return "Alimentación";
            default:
                return "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}