package org.lewisandclark.csd.basicfantasy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import org.lewisandclark.csd.basicfantasy.R;

/**
 * Created by Thorin Schmidt on 2/2/2019.
 */
public class CurrentHPDialog extends AppCompatDialogFragment {
    private EditText mEditTextHP;
    private RadioButton mButtonHeal;
    private RadioButton mButtonDamage;
    private CurHPDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_current_hp, null);

        builder.setView(view)
                .setTitle("Current Hit Points")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .setPositiveButton("Apply Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mButtonHeal = view.findViewById(R.id.heal_button);
                        mButtonDamage = view.findViewById(R.id.damage_button);
                        int hp = 0;

                        if (mButtonHeal.isChecked()) {
                            hp = Integer.valueOf(mEditTextHP.getText().toString());
                        }

                        if (mButtonDamage.isChecked()) {
                            hp = -(Integer.valueOf(mEditTextHP.getText().toString()));
                        }

                        listener.applyHP(hp);

                    }
                })
                .setNeutralButton("Full Heatlh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.fullHP();
                    }
                });

        mEditTextHP = view.findViewById(R.id.edit_hp);
        mButtonHeal = view.findViewById(R.id.heal_button);
        mButtonDamage = view.findViewById(R.id.damage_button);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CurHPDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CCDialogListener.");
        }
    }

    public interface CurHPDialogListener{
        void applyHP(int hp);
        void fullHP();
    }
}
