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
import android.widget.TextView;

import org.lewisandclark.csd.basicfantasy.R;
import org.lewisandclark.csd.basicfantasy.model.Money;

/**
 * Created by Thorin Schmidt on 2/2/2019.
 */
public class CoinDialog extends AppCompatDialogFragment {
    private TextView mCoinType1;
    private TextView mCoinType2;
    private TextView mCurrentAmount;
    private EditText mNewAmount;

    private CoinDialogListener listener;

    public static CoinDialog newInstance(Money coinType, int amt){
        CoinDialog fragment = new CoinDialog();
        Bundle bundle =  new Bundle();
        bundle.putSerializable("COIN", coinType);
        bundle.putInt("AMOUNT", amt);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_coin, null);

        Money coinType = (Money) getArguments().get("COIN");
        int amount = getArguments().getInt("AMOUNT");

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
                        int newAmount = Integer.valueOf(mNewAmount.getText().toString());
                        listener.adjustCoins(coinType, newAmount);

                    }
                });
        mCoinType1 = view.findViewById(R.id.coin_label1);
        mCoinType1.setText(coinType.name());

        mCoinType2 = view.findViewById(R.id.coin_label2);
        mCoinType2.setText(coinType.name());

        mCurrentAmount = view.findViewById(R.id.current_amount);
        mCurrentAmount.setText(Integer.toString(amount));

        mNewAmount = view.findViewById(R.id.new_amount);

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
        void adjustCoins(Money coinType, int newAmount);
    }
}
