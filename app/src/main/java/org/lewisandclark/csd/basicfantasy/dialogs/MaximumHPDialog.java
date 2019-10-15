package org.lewisandclark.csd.basicfantasy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import org.lewisandclark.csd.basicfantasy.R;

/**
 * Created by Thorin Schmidt on 2/2/2019.
 */
public class MaximumHPDialog extends AppCompatDialogFragment {
    private EditText mEditTextXP;
    private XPDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_xp, null);

        builder.setView(view)
                .setTitle("Add Experience")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .setPositiveButton("Add XP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int xp = Integer.valueOf(mEditTextXP.getText().toString());
                        listener.applyXP(xp);

                    }
                });
        mEditTextXP = view.findViewById(R.id.edit_xp);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (XPDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CCDialogListener.");
        }
    }

    public interface XPDialogListener{
        void applyXP(int xp);
    }
}
