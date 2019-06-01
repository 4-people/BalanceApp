package com.example.programmingknowledge.mybalance_v11;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.programmingknowledge.mybalance_v11.HomeFragment.subDate;
import static java.lang.Integer.parseInt;

public class StatisticsFragment extends Fragment {

    private ViewPager mViewPager;
    //FragmentManager fm;
    //FragmentTransaction tran;
    //WeekStatFragment frag1;
    //Button button;
    int page = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        final DBHelper helper = new DBHelper(container.getContext());

        /*
        //페이지 갯수 세기
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(distinct date) from tb_timeline",null);
        cursor.moveToFirst();
        page = cursor.getInt(0);
        db.close();*/
        ////////////////////////일단 위에 변수로 page수 4개로 고정해놨음

        mViewPager = (ViewPager)view.findViewById(R.id.pager_week);
        StatisticsFragment.MyPagerAdapter adapter = new StatisticsFragment.MyPagerAdapter(getChildFragmentManager(), page);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(page - 1);

        return view;
    }

    //히스토리 pagerAdapter
    private  static class MyPagerAdapter extends FragmentStatePagerAdapter {
        int page;

        public MyPagerAdapter(FragmentManager fm, int n) {
            super(fm);
            this.page = n;
        }

        //타임라인 히스토리 페이지 설정
        @Override
        public Fragment getItem(int position) {
            Fragment frag;
            //Date date = new Date();
            //SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy/MM/dd");
            //String formatDate = sdfdate.format(date);
            String curMonday = getCurMonday();

            for (int i = 0; i < page; i++) {
                if (position == i) {

                    try {
                        curMonday = subDate(curMonday, ((i + 1 - page) * 7));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    frag = WeekStatFragment.newInstance(curMonday);
                    return frag;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return page;
        }
    }

    //현재 날짜 월요일
    public static String getCurMonday(){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        return formatter.format(c.getTime());
    }

}

