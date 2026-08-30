package com.cappielloantonio.tempo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;

import com.cappielloantonio.tempo.R;
import com.cappielloantonio.tempo.databinding.FragmentHomeBinding;
import com.cappielloantonio.tempo.ui.activity.MainActivity;
import com.cappielloantonio.tempo.ui.fragment.pager.HomePager;
import com.cappielloantonio.tempo.util.Preferences;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;
import java.util.Objects;

@UnstableApi
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding bind;
    private MainActivity activity;

    private MaterialToolbar materialToolbar;
    private AppBarLayout appBarLayout;
    private HorizontalScrollView chipScroll;
    private ChipGroup chipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        bind = FragmentHomeBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAppBar();
        initHomePager();
    }

    @Override
    public void onStart() {
        super.onStart();

        activity.setBottomNavigationBarVisibility(true);
        activity.setBottomSheetVisibility(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    private void initAppBar() {
        appBarLayout = bind.getRoot().findViewById(R.id.toolbar_fragment);
        materialToolbar = bind.getRoot().findViewById(R.id.toolbar);

        activity.setSupportActionBar(materialToolbar);
        Objects.requireNonNull(materialToolbar.getOverflowIcon()).setTint(requireContext().getResources().getColor(R.color.titleTextColor, null));

        TextView title = bind.getRoot().findViewById(R.id.toolbar_title);
        if (title != null) title.setText(greeting());

        chipScroll = (HorizontalScrollView) getLayoutInflater()
                .inflate(R.layout.layout_home_tab_chips, appBarLayout, false);
        chipGroup = chipScroll.findViewById(R.id.home_tab_chip_group);

        appBarLayout.addView(chipScroll);
    }

    private int greeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour < 12) return R.string.home_greeting_morning;
        if (hour < 18) return R.string.home_greeting_afternoon;

        return R.string.home_greeting_evening;
    }

    private void initHomePager() {
        HomePager pager = new HomePager(this);

        pager.addFragment(new HomeTabMusicFragment(), "Music", R.drawable.ic_home);

        if (Preferences.isPodcastSectionVisible())
            pager.addFragment(new HomeTabPodcastFragment(), "Podcast", R.drawable.ic_graphic_eq);

        if (Preferences.isRadioSectionVisible())
            pager.addFragment(new HomeTabRadioFragment(), "Radio", R.drawable.ic_play_for_work);

        bind.homeViewPager.setAdapter(pager);
        bind.homeViewPager.setOffscreenPageLimit(3);
        bind.homeViewPager.setUserInputEnabled(false);

        chipGroup.removeAllViews();

        for (int position = 0; position < pager.getItemCount(); position++) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip_home_tab, chipGroup, false);

            // ChipGroup tracks single selection by child id, so each chip needs its own.
            chip.setId(View.generateViewId());
            chip.setText(pager.getPageTitle(position));

            int page = position;
            chip.setOnClickListener(v -> bind.homeViewPager.setCurrentItem(page, false));

            chipGroup.addView(chip);

            if (position == 0) chip.setChecked(true);
        }

        chipScroll.setVisibility(pager.getItemCount() > 1 ? View.VISIBLE : View.GONE);
    }
}
