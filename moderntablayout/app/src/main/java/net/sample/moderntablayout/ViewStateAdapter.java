package net.sample.moderntablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewStateAdapter extends FragmentStateAdapter {
    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return new FirstFragment();
        }else if (position == 1){
            return new SecondFragment();
        }else if (position == 2) {
            return new ThirdFragment();
        }return new FourthFragment();
    }

    @Override
    public int getItemCount() {
        // Hardcoded, use lists
        return 4;
    }

    public void Refresh() {
        notifyDataSetChanged();
    }
    }
