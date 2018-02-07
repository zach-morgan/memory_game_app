package com.example.zach.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zach.memorygame.LevelClasses.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zach on 2/3/2018.
 */

public class stage6_select extends stage_select_base {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] keys = new String[]{getString(R.string.shared_pref_S6L1_key),getString(R.string.shared_pref_S6L2_key),getString(R.string.shared_pref_S6L3_key),getString(R.string.shared_pref_S6L4_key)};

        ArrayList<Intent> intents = new ArrayList<>(Arrays.asList(new Intent(getActivity(),S6L1.class),new Intent(getActivity(),S6L2.class),
                new Intent(getActivity(),S6L3.class),new Intent(getActivity(),S6L4.class)));
        return super.onCreateView(inflater,container,savedInstanceState,keys,intents);
    }

    @Override
    protected String getStageNum() {
        return "Stage 6";
    }

    @Override
    protected String getNextStageNum() {
        return "Stage 7";
    }

    @Override
    protected String getUnlockStatusKey() {
        return getActivity().getString(R.string.shared_pref_stage6_lock_status_key);
    }

    @Override
    protected String getNextStageUnlockStatusKey() {
        return getActivity().getString(R.string.shared_pref_stage7_lock_status_key);
    }
}
