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
        productLayout1.setOnClickListener(v -> showProductDialog(
                "Goldmine Healthy Brown Rice 25KG", // Update the product name
                "₱1,450.00", // Update the product price
                "Premium quality brown rice from the Philippines.", // Update description
                R.drawable.goldmine // Ensure this is the correct image
        ));

        productLayout2.setOnClickListener(v -> showProductDialog(
                "Jasmine Fragrant Rice 25KG",
                "₱1,600.00",
                "Premium quality rice from the Philippines.",
                R.drawable.jasmine
        ));

        productLayout3.setOnClickListener(v -> showProductDialog(
                "Harvester's Long Grain Rice 25KG",
                "₱2,050.00",
                "Harvester’s Long Grain Rice is a long slender variety of rice. When cooked it has a light and fluffy characteristic that people love!\n" +
                        "For Filipinos, we love that “buhaghag” type of rice.",
                R.drawable.harverst
        ));

        productLayout4.setOnClickListener(v -> showProductDialog(
                "Healthy Grains Organic Black Rice",
                "₱310.00",
                "Bio-organically grown black rice.\n" +
                        "High in fiber and antioxidants; rich in iron.\n" +
                        "Good for lactating mothers.\n" +
                        "For digestive and cardiovascular health.\n" +
                        "Helps prevent and control diabetes, obesity, and high cholesterol.\n" +
                        "Export quality.\n" +
                        "Chemical-free.\n" +
                        "Gluten-Free.",
                R.drawable.blackrice
        ));

        return view;
    }

    private void showProductDialog(String name, String price, String description, int image) {
        ProductDialogFragment dialog = ProductDialogFragment.newInstance(name, price, description, image);
        dialog.show(getChildFragmentManager(), "ProductDialog");
    }
}
