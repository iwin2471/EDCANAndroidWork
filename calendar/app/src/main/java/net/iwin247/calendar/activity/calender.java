package net.iwin247.calendar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import 	android.support.v7.widget.LinearLayoutManager;
import 	android.support.design.widget.FloatingActionButton;

import net.iwin247.calendar.R;
import net.iwin247.calendar.db.DBManager;
import net.iwin247.calendar.utils.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;



public class calender extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    //final DBManager dbManager = new DBManager(getApplicationContext(), "summary.db", null, 2);

    private Toolbar toolbar;
    private ViewPager viewPager;
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        tx = (TextView) findViewById(R.id.one);
        //dbManager.insert("test", "test", "test", "tset");

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab);
        fab1.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isLogin", "yes");
        editor.commit();

        String email = prefs.getString("email","");
        tx.setText(email);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());setupViewPager(viewPager);

        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(this, google.class));
                break;
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((RecyclerAdapter) mAdapter).setOnItemClickListener(new RecyclerAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.wtf("wtf", " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<ContactInfo> getDataSet() {
        //String get = dbManager.getData();

        ArrayList results = new ArrayList<ContactInfo>();
        for (int index = 0; index < 10; index++) {
            ContactInfo obj = new ContactInfo("summary", "Secondary", "asdf");
            results.add(index, obj);
        }
        return results;
    }
}


