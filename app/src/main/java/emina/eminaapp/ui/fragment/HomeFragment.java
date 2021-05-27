package emina.eminaapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import emina.eminaapp.R;

public class HomeFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewPager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            public void run() {
                HomeFragment.tabLayout.setupWithViewPager(HomeFragment.viewPager);
            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    static class MyAdapter extends FragmentStatePagerAdapter {
        @Nullable
        public CharSequence getPageTitle(int i) {
            switch (i) {
                case 0:
                    return "Skin Care";
                case 1:
                    return "Make Up";
                case 2:
                    return "Body Care";
                default:
                    return null;
            }
        }

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TrendingProductFragment("1");
                case 1:
                    return new TrendingProductFragment("2");
                case 2:
                    return new TrendingProductFragment("3");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}