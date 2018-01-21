package com.example.zach.memorygame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zach on 1/17/2018.
 */

public class stage3_select extends stage_select_base {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] keys = new String[]{getString(R.string.shared_pref_S3L1_key),"","",getString(R.string.shared_pref_S3L2_key),
                "","",getString(R.string.shared_pref_S3L3_key),"","",getString(R.string.shared_pref_S3L4_key)};
        ArrayList<Intent> intents = new ArrayList<>(Arrays.asList(new Intent(getActivity(),S3L1.class),new Intent(getActivity(),S3L2.class),
                new Intent(getActivity(),S3L3.class),new Intent(getActivity(),S3L4.class)));

       return super.onCreateView(inflater,container,savedInstanceState,keys,intents);
    }

    @Override
    protected String getStageNum() {
        return "Stage 3";
    }

    @Override
    protected String getNextStageNum() {
        return "Stage 4";
    }

    @Override
    protected String getUnlockStatusKey() {
        return getActivity().getString(R.string.shared_pref_stage3_lock_status_key);
    }

    @Override
    protected String getNextStageUnlockStatusKey() {
        return getActivity().getString(R.string.shared_pref_stage4_lock_status_key);
    }
}
