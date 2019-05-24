package org.lewisandclark.csd.basicfantasy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.lewisandclark.csd.basicfantasy.dialogs.CoinDialog;
import org.lewisandclark.csd.basicfantasy.model.CharacterList;
import org.lewisandclark.csd.basicfantasy.model.Money;
import org.lewisandclark.csd.basicfantasy.model.PlayerCharacter;

import static org.lewisandclark.csd.basicfantasy.HomeActivity.sCurrentCharacterIndex;

public class TreasureActivity extends AppCompatActivity implements CoinDialog.CoinDialogListener {
    static final String TAG = "";

    private CharacterList sCharacters = CharacterList.getPlayerCharacterList(this);
    private PlayerCharacter mCurrentCharacter;

    private TextView mTextViewCharacterName;
    private TextView mTextViewCharacterClass;

    //stuff specific to the layout goes here
    private TextView mTextViewPP;
    private TextView mTextViewGP;
    private TextView mTextViewEP;
    private TextView mTextViewSP;
    private TextView mTextViewCP;

    private LinearLayout mLayoutPP;
    private LinearLayout mLayoutGP;
    private LinearLayout mLayoutEP;
    private LinearLayout mLayoutSP;
    private LinearLayout mLayoutCP;

    private EditText mTreasureDetails;

    //navigation buttons
    private TextView mTextViewLeftNavigate;
    private TextView mTextViewRightNavigate;

    public static Intent newIntent(Context packageContext){
        Intent theIntent = new Intent(packageContext, TreasureActivity.class);
        //Intent Extras go here
        return theIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure);
        setTitle(R.string.title_activity_treasure);
        mCurrentCharacter = sCharacters.getPlayerCharacter(sCurrentCharacterIndex);

        mTextViewCharacterName = findViewById(R.id.character_name);
        mTextViewCharacterName.setText(mCurrentCharacter.getName());

        mTextViewCharacterClass = findViewById(R.id.character_class);
        mTextViewCharacterClass.setText(mCurrentCharacter.getCharacterClass().toString());

        //stuff specific to the layout goes here
        mLayoutPP = findViewById(R.id.platinum);
        mLayoutPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TreasureActivity.this, "Platinum clicked", Toast.LENGTH_SHORT).show();
                openCoinDialog(Money.PP, mCurrentCharacter.getPP());
            }
        });
        mTextViewPP = findViewById(R.id.content_platinum);
        mTextViewPP.setText(Integer.toString(mCurrentCharacter.getPP()));

        mLayoutGP = findViewById(R.id.gold);
        mLayoutGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TreasureActivity.this, "Gold clicked", Toast.LENGTH_SHORT).show();
                openCoinDialog(Money.GP, mCurrentCharacter.getGP());
            }
        });
        mTextViewGP = findViewById(R.id.content_gold);
        mTextViewGP.setText(Integer.toString(mCurrentCharacter.getGP()));

        mLayoutEP = findViewById(R.id.electrum);
        mLayoutEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TreasureActivity.this, "Electrum clicked", Toast.LENGTH_SHORT).show();
                openCoinDialog(Money.EP, mCurrentCharacter.getEP());
            }
        });
        mTextViewEP = findViewById(R.id.content_electrum);
        mTextViewEP.setText(Integer.toString(mCurrentCharacter.getEP()));

        mLayoutSP = findViewById(R.id.silver);
        mLayoutSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TreasureActivity.this, "Silver clicked", Toast.LENGTH_SHORT).show();
                openCoinDialog(Money.SP, mCurrentCharacter.getSP());
            }
        });
        mTextViewSP = findViewById(R.id.content_silver);
        mTextViewSP.setText(Integer.toString(mCurrentCharacter.getSP()));

        mLayoutCP = findViewById(R.id.copper);
        mLayoutCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TreasureActivity.this, "Copper clicked", Toast.LENGTH_SHORT).show();
                openCoinDialog(Money.CP, mCurrentCharacter.getCP());
            }
        });
        mTextViewCP = findViewById(R.id.content_copper);
        mTextViewCP.setText(Integer.toString(mCurrentCharacter.getCP()));


        //navigation buttons

        mTextViewLeftNavigate = findViewById(R.id.left_button);
        mTextViewRightNavigate = findViewById(R.id.right_button);

        //functions specific to the layout go here


        //navigation functions
        mTextViewLeftNavigate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //open left screen
                Intent i = DetailsActivity.newIntent(TreasureActivity.this);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
            }
        });

        mTextViewRightNavigate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //open right screen
                Intent i = StatsActivity.newIntent(TreasureActivity.this);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
            }
        });
    }

    public void onBackPressed(){
        Log.d("BUTTON", "pressed BACK button.");
        Intent theIntent = HomeActivity.newIntent(this, sCurrentCharacterIndex);
        startActivity(theIntent);

        return;
    }


    public void openDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText("Generic Title");
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        alertDialog.setCustomTitle(mTextViewTitle);

        TextView msg = new TextView(this);

        msg.setText("Generic Message");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        msg.setTextSize(18);
        alertDialog.setView(msg);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //no action to perform
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        //set properties of the OK button
        final Button okButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralButtonLP = (LinearLayout.LayoutParams) okButton.getLayoutParams();
        okButton.setTextColor(Color.BLUE);
        okButton.setLayoutParams(neutralButtonLP);
    }

    public void openCoinDialog(Money coinType, int currentAmount){
        CoinDialog coinDialog = new CoinDialog().newInstance(coinType, currentAmount);
        coinDialog.show(getSupportFragmentManager(), "Coin dialog");
    }

    @Override
    public void adjustCoins(Money coinType, int newAmount) {
        mCurrentCharacter.setMoney(coinType, newAmount);
        mTextViewPP.setText(Integer.toString(mCurrentCharacter.getPP()));
        mTextViewGP.setText(Integer.toString(mCurrentCharacter.getGP()));
        mTextViewEP.setText(Integer.toString(mCurrentCharacter.getEP()));
        mTextViewSP.setText(Integer.toString(mCurrentCharacter.getSP()));
        mTextViewCP.setText(Integer.toString(mCurrentCharacter.getCP()));
    }

}
