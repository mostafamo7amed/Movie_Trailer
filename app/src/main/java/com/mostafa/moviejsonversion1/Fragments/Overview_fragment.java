package com.mostafa.moviejsonversion1.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mostafa.moviejsonversion1.R;

public class Overview_fragment extends Fragment {
    TextView overview;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overview = getActivity().findViewById(R.id.tx_des_overview);

        Bundle bundle = this.getArguments();
        String data = bundle.getString("overView");
        overview.setText(data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview_fragment, container, false);
    }
}