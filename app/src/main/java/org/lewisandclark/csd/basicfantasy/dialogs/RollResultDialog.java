package org.lewisandclark.csd.basicfantasy.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import org.lewisandclark.csd.basicfantasy.R;

/**
 * This fragment displays a single score roll.
 * The user can do nothing else on this fragment.
 * @author Thorin Schmidt on 4/13/2017.
 */

public class RollResultDialog extends DialogFragment {

    /**
     * Modifier of the score.
     */
    int mMod;

    /**
     * The value rolled, treated as D20.
     */
    int mRoll;

    /**
     * The name of the score that was rolled.
     */
    String mAttributeName;

    /**
     * Calculated total from mMod + mRoll.
     */
    int mTotal;

    /**
     * Create a new instance of RollResultDialog, providing "roll"
     * and "mod" as arguments.
     */
    static RollResultDialog newInstance(int roll, int mod, String name) {
        RollResultDialog f = new RollResultDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("roll", roll);
        args.putInt("mod", mod);
        args.putString("name", name);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mRoll = getArguments().getInt("roll");
        mMod = getArguments().getInt("mod");
        mAttributeName = getArguments().getString("name");
        mTotal = mRoll + mMod;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_roll_result, container, false);
        View tv = v.findViewById(R.id.die_roll_view);
        ((TextView) tv).setText(mAttributeName + " Roll: " + mRoll + " + " + mMod + " = " +
                + mTotal);

        return v;
    }

}
