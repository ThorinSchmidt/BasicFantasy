package org.lewisandclark.csd.basicfantasy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import org.lewisandclark.csd.basicfantasy.R;
import org.lewisandclark.csd.basicfantasy.model.Sex;


/**
 * Created by Thorin Schmidt on 4/29/2019.
 */
public class SetSexDialog extends AppCompatDialogFragment {

    private RadioButton mFemaleButton;
    private RadioButton mMaleButton;
    private SetSexDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set_sex, null);

        builder.setView(view)
                .setTitle("Set Sex")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Sex sex = null;
                        if(mFemaleButton.isChecked()){
                            sex = Sex.FEMALE;
                        }
                        if(mMaleButton.isChecked()){
                            sex = Sex.MALE;
                        }

                        listener.applySexChange(sex);

                    }
                });

        mFemaleButton = view.findViewById(R.id.button_female);
        mMaleButton = view.findViewById(R.id.button_male);

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SetSexDialog.SetSexDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CCDialogListener.");
        }
    }

    public interface SetSexDialogListener{
        void applySexChange(Sex s);
    }

}
