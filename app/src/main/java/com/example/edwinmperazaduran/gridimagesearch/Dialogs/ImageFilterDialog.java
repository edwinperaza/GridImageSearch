package com.example.edwinmperazaduran.gridimagesearch.Dialogs;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.edwinmperazaduran.gridimagesearch.R;

public class ImageFilterDialog extends DialogFragment{

    private Spinner sp_imgsize;
    private Spinner sp_imgcolor;
    private Spinner sp_img_type;
    private EditText et_img_site;
    private String imgcolor;
    private String imgsize;
    private String imgtype;


    public ImageFilterDialog() {}


    public interface ImageFilterDialogListener {
        //void onFinishDialog(GoogleFilter googleFilter);
    }

    public static ImageFilterDialog newInstance(String title) {
        ImageFilterDialog dialog = new ImageFilterDialog();
        Bundle args = new Bundle();
        args.putString("title",title);
        //args.putSerializable("google_filter", googleFilter);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_filter, container);
        sp_imgsize = (Spinner) view.findViewById(R.id.sp_imgsize);
        sp_imgcolor = (Spinner) view.findViewById(R.id.sp_imgcolor);
        sp_img_type = (Spinner) view.findViewById(R.id.sp_img_type);
        et_img_site = (EditText) view.findViewById(R.id.et_img_site);

        setupSpAdapter(view);
        setupSpSpinnerListener();

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishCreating(v);
//            }
//        });
        return view;
    }

    public void setupSpAdapter(View view){

        ArrayAdapter<CharSequence> adaptercolor = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_color, android.R.layout.simple_spinner_item);
        adaptercolor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_imgcolor.setAdapter(adaptercolor);

        ArrayAdapter<CharSequence> adaptersize = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_sz, android.R.layout.simple_spinner_item);
        adaptersize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_imgsize.setAdapter(adaptersize);

        ArrayAdapter<CharSequence> adaptertype = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sp_img_type, android.R.layout.simple_spinner_item);
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_img_type.setAdapter(adaptertype);

    }

    public void setupSpSpinnerListener (){

        sp_imgcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgcolor = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_imgsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgsize = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sp_img_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgtype = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
