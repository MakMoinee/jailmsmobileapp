package com.aclc.thesis.jmsapp.ui.qr;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.databinding.FragmentSlideshowBinding;

public class QRGeneratorFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QRGeneratorViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(QRGeneratorViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        viewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
