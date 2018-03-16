package org.lewisandclark.csd.basicfantasy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class RollAttributesActivity extends AppCompatActivity {

    public static final String TAG = "RollAttributesActivity";
    public static final String EXTRA_CURRENT_CHARACTER = "current character index";

    private Button mRollStats;
    private Button mAcceptStats;
    private TextView mStrTextView;
    private TextView mIntTextView;
    private TextView mWisTextView;
    private TextView mDexTextView;
    private TextView mConTextView;
    private TextView mChaTextView;

    private AttributeScore[] mStatArray = { new AttributeScore(0),
                                            new AttributeScore(0),
                                            new AttributeScore(0),
                                            new AttributeScore(0),
                                            new AttributeScore(0),
                                            new AttributeScore(0)};
    private int mCurrentCharacterIndex;
    private HashMap<Attribute, AttributeScore> tempMap = new HashMap<Attribute, AttributeScore>();

    public static Intent newIntent(Context packageContext, int currentCharacterIndex){
        Intent theIntent = new Intent(packageContext, RollAttributesActivity.class);
        theIntent.putExtra(EXTRA_CURRENT_CHARACTER, currentCharacterIndex);
        return theIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_attributes);

        mCurrentCharacterIndex = getIntent().getIntExtra(EXTRA_CURRENT_CHARACTER, 0);

        mStrTextView = findViewById(R.id.str_score);
        mStrTextView.setText(Integer.toString(mStatArray[0].getScore()));

        mIntTextView = findViewById(R.id.int_score);
        mIntTextView.setText(Integer.toString(mStatArray[1].getScore()));

        mWisTextView = findViewById(R.id.wis_score);
        mWisTextView.setText(Integer.toString(mStatArray[2].getScore()));

        mDexTextView = findViewById(R.id.dex_score);
        mDexTextView.setText(Integer.toString(mStatArray[3].getScore()));

        mConTextView = findViewById(R.id.con_score);
        mConTextView.setText(Integer.toString(mStatArray[4].getScore()));

        mChaTextView = findViewById(R.id.cha_score);
        mChaTextView.setText(Integer.toString(mStatArray[5].getScore()));

        mRollStats = findViewById(R.id.roll_stats_button);
        mRollStats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //generate the random stats
                Log.d(TAG, "RollStats clicked.");
                for (int i=0; i<mStatArray.length; i++){
                    mStatArray[i].setScore(DieRoller.statRoll());
                }

                mStrTextView.setText(Integer.toString(mStatArray[Attribute.STR.ordinal()].getScore()));
                mIntTextView.setText(Integer.toString(mStatArray[Attribute.INT.ordinal()].getScore()));
                mWisTextView.setText(Integer.toString(mStatArray[Attribute.WIS.ordinal()].getScore()));
                mDexTextView.setText(Integer.toString(mStatArray[Attribute.DEX.ordinal()].getScore()));
                mConTextView.setText(Integer.toString(mStatArray[Attribute.CON.ordinal()].getScore()));
                mChaTextView.setText(Integer.toString(mStatArray[Attribute.CHA.ordinal()].getScore()));

            }
        });

        mAcceptStats = findViewById(R.id.accept_stats_button);
        mAcceptStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put stats into player character record.
                Log.d(TAG, "AcceptStats clicked.");

                //save data to character record
                /**tempMap.put(Attribute.STR, new AttributeScore(mStatArray[0]));
                tempMap.put(Attribute.INT, new AttributeScore(mStatArray[1]));
                tempMap.put(Attribute.WIS, new AttributeScore(mStatArray[2]));
                tempMap.put(Attribute.DEX, new AttributeScore(mStatArray[3]));
                tempMap.put(Attribute.CON, new AttributeScore(mStatArray[4]));
                tempMap.put(Attribute.CHA, new AttributeScore(mStatArray[5]));
                 */
                HomeActivity.sCharacters.get(mCurrentCharacterIndex).setStatArray(mStatArray);

                Intent intent = ChooseRaceActivity.newIntent(RollAttributesActivity.this, mCurrentCharacterIndex);
                startActivity(intent);
            }
        });




    }
}
