package com.example.heyfarmers; // Change this to your actual package name

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find product layouts
        LinearLayout productLayout1 = view.findViewById(R.id.productLayout1);
        LinearLayout productLayout2 = view.findViewById(R.id.productLayout2);
        LinearLayout productLayout3 = view.findViewById(R.id.productLayout3);
        LinearLayout productLayout4 = view.findViewById(R.id.productLayout4);

        // Set click listeners for each product layout
        productLayout1.setOnClickListener(v -> showProductDialog("Item 1", "$10.99", "This is a short description of Item 1", R.drawable.sample_image1));
        productLayout2.setOnClickListener(v -> showProductDialog("Item 2", "$12.99", "This is a short description of Item 2", R.drawable.sample_image2));
        productLayout3.setOnClickListener(v -> showProductDialog("Item 3", "$14.99", "This is a short description of Item 3", R.drawable.sample_image3));
        productLayout4.setOnClickListener(v -> showProductDialog("Item 4", "$9.99", "This is a short description of Item 4", R.drawable.sample_image4));

        return view;
    }

    private void showProductDialog(String name, String price, String description, int image) {
        ProductDialogFragment dialog = ProductDialogFragment.newInstance(name, price, description, image);
        dialog.show(getChildFragmentManager(), "ProductDialog");
    }
}
