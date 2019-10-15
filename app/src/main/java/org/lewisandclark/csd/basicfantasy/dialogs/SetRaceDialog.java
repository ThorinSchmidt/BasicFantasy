package org.lewisandclark.csd.basicfantasy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import org.lewisandclark.csd.basicfantasy.R;
import org.lewisandclark.csd.basicfantasy.model.Race;


/**
 * Created by Thorin Schmidt on 4/29/2019.
 */
public class SetRaceDialog extends AppCompatDialogFragment {

    private RadioButton mDwarfButton;
    private RadioButton mElfButton;
    private RadioButton mHalflingButton;
    private RadioButton mHumanButton;
    private SetRaceDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set_race, null);

        builder.setView(view)
                .setTitle("Set Race")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Race race = null;
                        if(mDwarfButton.isChecked()) {
                            listener.applyRaceChange(Race.DWARF);
                        }
                        else if(mElfButton.isChecked()) {
                            listener.applyRaceChange(Race.ELF);
                        }
                        else if(mHalflingButton.isChecked()) {
                            listener.applyRaceChange(Race.HALFLING);
                        }
                        else if(mHumanButton.isChecked()) {
                            listener.applyRaceChange(Race.HUMAN);
                        }
                        else{
                            Toast.makeText(getActivity(), "Value Unchanged", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mDwarfButton = view.findViewById(R.id.button_dwarf);
        mElfButton = view.findViewById(R.id.button_elf);
        mHalflingButton = view.findViewById(R.id.button_halfling);
        mHumanButton = view.findViewById(R.id.button_human);

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SetRaceDialog.SetRaceDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CCDialogListener.");
        }
    }

    public interface SetRaceDialogListener{
        void applyRaceChange(Race r);
    }

}
