package com.example.heyfarmers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ProductDialogFragment extends DialogFragment {

    // Argument keys for product details
    private static final String ARG_PRODUCT_NAME = "product_name";
    private static final String ARG_PRODUCT_PRICE = "product_price";
    private static final String ARG_PRODUCT_DESCRIPTION = "product_description";
    private static final String ARG_PRODUCT_IMAGE = "product_image";

    // Factory method to create a new instance of the dialog fragment with product details
    public static ProductDialogFragment newInstance(String productName, String productPrice, String productDescription, int productImage) {
        ProductDialogFragment fragment = new ProductDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_NAME, productName);
        args.putString(ARG_PRODUCT_PRICE, productPrice);
        args.putString(ARG_PRODUCT_DESCRIPTION, productDescription);
        args.putInt(ARG_PRODUCT_IMAGE, productImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_product, container, false);

        // Check if arguments were passed successfully
        if (getArguments() != null) {
            // Get data from the arguments bundle
            String productName = getArguments().getString(ARG_PRODUCT_NAME);
            String productPrice = getArguments().getString(ARG_PRODUCT_PRICE);
            String productDescription = getArguments().getString(ARG_PRODUCT_DESCRIPTION);
            int productImage = getArguments().getInt(ARG_PRODUCT_IMAGE);

            // Find the views in the dialog layout
            ImageView productImageView = view.findViewById(R.id.product_image);
            TextView productNameTextView = view.findViewById(R.id.product_name);
            TextView productPriceTextView = view.findViewById(R.id.product_price);
            TextView productDescriptionTextView = view.findViewById(R.id.product_description);
            Button closeButton = view.findViewById(R.id.close_button);

            // Set the product details in the respective views
            productImageView.setImageResource(productImage);
            productNameTextView.setText(productName);
            productPriceTextView.setText(productPrice);
            productDescriptionTextView.setText(productDescription);

            // Close the dialog when the button is clicked
            closeButton.setOnClickListener(v -> dismiss());
        } else {
            // Handle the case when no arguments are passed
            dismiss(); // Dismiss the dialog if there's no data
        }

        return view;
    }
}
