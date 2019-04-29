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

import org.lewisandclark.csd.basicfantasy.R;

/**
 * Created by Thorin Schmidt on 2/2/2019.
 */
public class ChooseCharacterDialog extends AppCompatDialogFragment {
    private EditText mEditTextXP;
    private CCDialogListener listener;

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
                        int num = 0;
                        listener.changeCharacter(num);

                    }
                });
        mEditTextXP = view.findViewById(R.id.edit_xp);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CCDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CCDialogListener.");
        }
    }

    public interface CCDialogListener {
        void changeCharacter(int num);
    }
}
