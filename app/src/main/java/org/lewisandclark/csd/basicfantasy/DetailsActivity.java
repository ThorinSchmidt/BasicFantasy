package org.lewisandclark.csd.basicfantasy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lewisandclark.csd.basicfantasy.model.CharacterList;
import org.lewisandclark.csd.basicfantasy.model.PlayerCharacter;
import org.lewisandclark.csd.basicfantasy.utils.DieRoller;

import static java.lang.Math.abs;
import static org.lewisandclark.csd.basicfantasy.HomeActivity.sCurrentCharacterIndex;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.CHA;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.CON;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.DEX;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.INT;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.STR;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.WIS;

public class DetailsActivity extends AppCompatActivity {

    static final String TAG = "";

    private CharacterList sCharacters = CharacterList.getPlayerCharacterList(this);
    private PlayerCharacter mCurrentCharacter;

    private TextView mTextViewCharacterName;
    private TextView mTextViewCharacterClass;

    private TextView mTextViewCharacterSex;
    private TextView mTextViewCharacterGender;

    private TextView mTextViewCharacterRace;
    private TextView mTextViewCharacterAge;

    private TextView mTextViewCharacterHeight;
    private TextView mTextViewCharacterWeight;

    private TextView mTextViewCharacterHair;
    private TextView mTextViewCharacterEyes;

    private EditText mEditTextCharacterDetails;

    //stuff specific to the layout goes here

    private TextView mTextViewLeftNavigate;
    private TextView mTextViewRightNavigate;

    public static Intent newIntent(Context packageContext){
        Intent theIntent = new Intent(packageContext, DetailsActivity.class);
        //Intent Extras go here
        return theIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mCurrentCharacter = sCharacters.getPlayerCharacter(sCurrentCharacterIndex);

        mTextViewCharacterName = findViewById(R.id.character_name);
        mTextViewCharacterName.setText(mCurrentCharacter.getName());
        mTextViewCharacterClass = findViewById(R.id.character_class);
        mTextViewCharacterClass.setText(mCurrentCharacter.getCharacterClass().toString());

        //stuff specific to the layout goes here
        mTextViewCharacterGender = findViewById(R.id.gender_view);
        String genderString = getString(R.string.gender_string,mCurrentCharacter.getGenderString());
        mTextViewCharacterGender.setText(genderString);
        mTextViewCharacterSex = findViewById(R.id.sex_view);
        String sexString = getString(R.string.sex_string,mCurrentCharacter.getSexString());
        mTextViewCharacterSex.setText(sexString);

        mTextViewCharacterRace =findViewById(R.id.race_view);
        String raceString = getString(R.string.race_string, mCurrentCharacter.getRace());
        mTextViewCharacterRace.setText(raceString);
        mTextViewCharacterAge =findViewById(R.id.age_view);
        String ageString = getString(R.string.age_string, mCurrentCharacter.getAge());
        mTextViewCharacterAge.setText(ageString);

        mTextViewCharacterHeight =findViewById(R.id.height_view);
        String heightString = getString(R.string.height_string, mCurrentCharacter.getHeightString());
        mTextViewCharacterHeight.setText(heightString);
        mTextViewCharacterWeight =findViewById(R.id.weight_view);
        String weightString = getString(R.string.weight_string, mCurrentCharacter.getWeight());
        mTextViewCharacterWeight.setText(weightString);

        mTextViewCharacterHair = findViewById(R.id.hair_view);
        String hairString = getString(R.string.hair_string, mCurrentCharacter.getHairColor());
        mTextViewCharacterHair.setText(hairString);
        mTextViewCharacterEyes =findViewById(R.id.eyes_view);
        String eyesString = getString(R.string.eyes_string, mCurrentCharacter.getEyeColor());
        mTextViewCharacterEyes.setText(eyesString);

        mEditTextCharacterDetails = findViewById(R.id.details_edit);
        mEditTextCharacterDetails.setText(mCurrentCharacter.getAppearanceDetails());

        //Navigation Buttons
        mTextViewLeftNavigate = findViewById(R.id.left_button);
        mTextViewRightNavigate = findViewById(R.id.right_button);

        //functions specific to the layout go here


        //navigation functions
        mTextViewLeftNavigate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //save contents of edittext
                String msg = mEditTextCharacterDetails.getText().toString();
                Log.d("DETAILS", msg);
                mCurrentCharacter.setAppearanceDetails(msg);

                //open left screen
                Intent i = CombatActivity.newIntent(DetailsActivity.this);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
            }
        });

        mTextViewRightNavigate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //save contents of edittext
                String msg = mEditTextCharacterDetails.getText().toString();
                Log.d("DETAILS", msg);
                mCurrentCharacter.setAppearanceDetails(msg);

                //open right screen
                Intent i = StatsActivity.newIntent(DetailsActivity.this);
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
}
