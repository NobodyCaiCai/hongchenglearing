package com.caicai.myapplication.customView4_colorTextView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.caicai.myapplication.R;

public class ItemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null);
        TextView textView = view.findViewById(R.id.text);
        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText(bundle.getString("title"));
        }
        return view;
    }

    public static ItemFragment newInstance(String title) {
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }
}
