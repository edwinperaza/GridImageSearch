package com.example.edwinmperazaduran.gridimagesearch.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.edwinmperazaduran.gridimagesearch.R;
import com.example.edwinmperazaduran.gridimagesearch.models.ImageFilter;

public class ImageFilterDialog extends DialogFragment{

    private Spinner spinnerImageSize;
    private Spinner spinnerImageColor;
    private Spinner spinnerImageType;
    private EditText et_imageSite;
    private Button btn_save_filter;
    private ImageFilter imageFilter;
    private ImageFilterDialogListener listener;

    public ImageFilterDialog() {}

    public interface ImageFilterDialogListener {
        public void onFinishDialog(ImageFilter imageFilter);
    }

    public static ImageFilterDialog newInstance(ImageFilter imageFilter) {
        ImageFilterDialog dialog = new ImageFilterDialog();
        Bundle args = new Bundle();
        args.putSerializable("imageFilter",imageFilter);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ImageFilterDialogListener) {
            listener = (ImageFilterDialogListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_filter, container);

        spinnerImageSize = (Spinner) view.findViewById(R.id.sp_imgsize);
        spinnerImageColor = (Spinner) view.findViewById(R.id.sp_imgcolor);
        spinnerImageType = (Spinner) view.findViewById(R.id.sp_img_type);
        et_imageSite = (EditText) view.findViewById(R.id.et_img_site);
        btn_save_filter = (Button) view.findViewById(R.id.btn_save_filter);
        

        if (getArguments().getSerializable("imageFilter") != null) {
            imageFilter = (ImageFilter) getArguments().getSerializable("imageFilter");
        }
        setupSpinnerAdapter(view);

        btn_save_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                finishFilter(v);
            }
        });

        return view;
    }

    public void finishFilter(View view){

        String site = et_imageSite.getText().toString();
        String size = spinnerImageSize.getSelectedItem().toString();
        String color = spinnerImageColor.getSelectedItem().toString();
        String type = spinnerImageType.getSelectedItem().toString();

        imageFilter.setSize(size);
        imageFilter.setType(type);
        imageFilter.setColor(color);
        imageFilter.setSite(site);

        listener.onFinishDialog(imageFilter);
        dismiss();
    }

    public void setupSpinnerAdapter(View view){

        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_color, android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerImageColor.setAdapter(adapterColor);

        ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_sz, android.R.layout.simple_spinner_item);
        adapterSize.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerImageSize.setAdapter(adapterSize);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerImageType.setAdapter(adapterType);

        if (!imageFilter.noSelections()){
            spinnerImageSize.setSelection(adapterSize.getPosition(imageFilter.getSize()));
            spinnerImageColor.setSelection(adapterColor.getPosition(imageFilter.getColor()));
            spinnerImageType.setSelection(adapterType.getPosition(imageFilter.getType()));
            et_imageSite.setText(imageFilter.getSite());
        }
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}