package com.example.edwinmperazaduran.gridimagesearch.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.edwinmperazaduran.gridimagesearch.R;
import com.example.edwinmperazaduran.gridimagesearch.models.ImageFilter;

public class ImageFilterDialog extends DialogFragment{

    private Spinner sp_imgsize;
    private Spinner sp_imgcolor;
    private Spinner sp_img_type;
    private EditText et_img_site;
    private Button btn_save_filter;
    private ImageFilter imageFilter;

    private ImageFilterDialogListener listener;


    public ImageFilterDialog() {}


    public interface ImageFilterDialogListener {
        public void onFinishDialog(ImageFilter imageFilter);
    }

    public static ImageFilterDialog newInstance(String title, ImageFilter imageFilter) {
        ImageFilterDialog dialog = new ImageFilterDialog();
        Bundle args = new Bundle();
        args.putString("title",title);
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

        sp_imgsize = (Spinner) view.findViewById(R.id.sp_imgsize);
        sp_imgcolor = (Spinner) view.findViewById(R.id.sp_imgcolor);
        sp_img_type = (Spinner) view.findViewById(R.id.sp_img_type);
        et_img_site = (EditText) view.findViewById(R.id.et_img_site);
        btn_save_filter = (Button) view.findViewById(R.id.btn_save_filter);

        if (getArguments().getSerializable("imageFilter") != null) {
            imageFilter = (ImageFilter) getArguments().getSerializable("imageFilter");
        }

        setupSpAdapter(view);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btn_save_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishFilter(v);
            }
        });

        return view;
    }

    public void finishFilter(View view){

        String site = et_img_site.getText().toString();
        String size = sp_imgsize.getSelectedItem().toString();
        String color = sp_imgcolor.getSelectedItem().toString();
        String type = sp_img_type.getSelectedItem().toString();

        imageFilter.setSize(size);
        imageFilter.setType(type);
        imageFilter.setColor(color);
        imageFilter.setSite(site);

        listener.onFinishDialog(imageFilter);
        dismiss();
    }

    public void setupSpAdapter(View view){

        ArrayAdapter<CharSequence> adaptercolor = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_color, android.R.layout.simple_spinner_item);
        sp_imgcolor.setAdapter(adaptercolor);

        ArrayAdapter<CharSequence> adaptersize = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_sz, android.R.layout.simple_spinner_item);
        sp_imgsize.setAdapter(adaptersize);

        ArrayAdapter<CharSequence> adaptertype = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_type, android.R.layout.simple_spinner_item);
        sp_img_type.setAdapter(adaptertype);

        if (imageFilter != null){
            for (int i = 0; i < adaptercolor.getCount(); i++) {
                if (imageFilter.getColor().equals(adaptercolor.getItem(i).toString())) {
                    sp_imgcolor.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < adaptersize.getCount(); i++) {
                if (imageFilter.getSize().equals(adaptersize.getItem(i).toString())) {
                    sp_imgsize.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < adaptertype.getCount(); i++) {
                if (imageFilter.getType().equals(adaptertype.getItem(i).toString())) {
                    sp_img_type.setSelection(i);
                    break;
                }
            }
            et_img_site.setText(imageFilter.getSite());
        }


    }
}
