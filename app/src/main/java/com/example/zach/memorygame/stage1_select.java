package com.example.zach.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zach.memorygame.LevelClasses.S1L1;
import com.example.zach.memorygame.LevelClasses.S1L2;
import com.example.zach.memorygame.LevelClasses.S1L3;
import com.example.zach.memorygame.LevelClasses.S1L4;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zach on 1/15/2018.
 */

public class stage1_select extends stage_select_base {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] keys = new String[]{getString(R.string.shared_pref_S1L1_key),getString(R.string.shared_pref_S1L2_key),getString(R.string.shared_pref_S1L3_key),getString(R.string.shared_pref_S1L4_key)};

        ArrayList<Intent> intents = new ArrayList<>(Arrays.asList(new Intent(getActivity(),S1L1.class),new Intent(getActivity(),S1L2.class),
                new Intent(getActivity(),S1L3.class),new Intent(getActivity(),S1L4.class)));
        return super.onCreateView(inflater,container,savedInstanceState,keys,intents);
    }

    @Override
    protected String getStageNum() {
        return "Stage 1";
    }

    @Override
    protected String getNextStageNum() {
        return "Stage 2";
    }

    @Override
    protected String getUnlockStatusKey() {
        return getActivity().getString(R.string.shared_pref_stage1_lock_status_key);
    }

    @Override
    protected String getNextStageUnlockStatusKey() {
        return getActivity().getString(R.string.shared_pref_stage2_lock_status_key);
    }
}
