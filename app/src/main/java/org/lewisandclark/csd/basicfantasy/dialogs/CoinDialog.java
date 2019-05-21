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
import org.lewisandclark.csd.basicfantasy.model.Money;

/**
 * Created by Thorin Schmidt on 2/2/2019.
 */
public class CoinDialog extends AppCompatDialogFragment {
    private EditText mEditTextXP;
    private CoinDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_coin, null);

        builder.setView(view)
                .setTitle("Adjust Coins")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .setPositiveButton("Adjust", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Money coinType = Money.PP;
                        int amount = Integer.valueOf(mEditTextXP.getText().toString());
                        listener.adjustCoins(coinType, amount);

                    }
                });
        mEditTextXP = view.findViewById(R.id.edit_xp);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CoinDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CCDialogListener.");
        }
    }

    public interface CoinDialogListener{
        void adjustCoins(Money coinType, int amount);
    }
}
