package org.lewisandclark.csd.basicfantasy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.lewisandclark.csd.basicfantasy.dialogs.SetRaceDialog;
import org.lewisandclark.csd.basicfantasy.dialogs.SetSexDialog;
import org.lewisandclark.csd.basicfantasy.model.CharacterList;
import org.lewisandclark.csd.basicfantasy.model.PlayerCharacter;
import org.lewisandclark.csd.basicfantasy.model.Race;
import org.lewisandclark.csd.basicfantasy.model.Sex;

import static java.lang.Math.abs;
import static org.lewisandclark.csd.basicfantasy.HomeActivity.sCurrentCharacterIndex;

public class DetailsActivity extends AppCompatActivity implements
        SetSexDialog.SetSexDialogListener,
        SetRaceDialog.SetRaceDialogListener {

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
        setTitle(R.string.title_activity_details);
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
        mTextViewCharacterGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGenderDialog();
            }
        });

        mTextViewCharacterSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSexDialog setSexDialog = new SetSexDialog();
                setSexDialog.show(getSupportFragmentManager(), "Sex dialog");
            }
        });

        mTextViewCharacterRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetRaceDialog setRaceDialog = new SetRaceDialog();
                setRaceDialog.show(getSupportFragmentManager(), "Race dialog");
            }
        });

        mTextViewCharacterAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgeDialog();
            }
        });

        mTextViewCharacterHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeightDialog();
            }
        });

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
                Log.d("TREASURE", msg);
                //mCurrentCharacter.setAppearanceDetails(msg);

                //open right screen
                Intent i = TreasureActivity.newIntent(DetailsActivity.this);
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

    public void openHeightDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText("Set Height");
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        alertDialog.setCustomTitle(mTextViewTitle);

        final EditText input = new EditText(this);
        input.setHint("Type your character's height in inches.");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("AGE", Integer.toString(mCurrentCharacter.getAge()));
                int newHeight = Integer.valueOf(input.getText().toString());
                mCurrentCharacter.setHeight(newHeight);
                String heightString = getString(R.string.height_string,mCurrentCharacter.getHeightString());
                mTextViewCharacterHeight.setText(heightString);

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Context t = getApplicationContext();
                Toast.makeText(t, "Value Unchanged.", Toast.LENGTH_SHORT).show();
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        //set properties of the buttons
        final Button cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negButtonLP = (LinearLayout.LayoutParams) cancelButton.getLayoutParams();
        cancelButton.setTextColor(Color.BLUE);
        cancelButton.setLayoutParams(negButtonLP);

        final Button applyButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams posButtonLP = (LinearLayout.LayoutParams) applyButton.getLayoutParams();
        applyButton.setTextColor(Color.BLUE);
        applyButton.setLayoutParams(posButtonLP);

    }


    public void openAgeDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText("Set Age");
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        alertDialog.setCustomTitle(mTextViewTitle);

        final EditText input = new EditText(this);
        input.setHint("Type your character's age in years.");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("AGE", Integer.toString(mCurrentCharacter.getAge()));
                int newAge = Integer.valueOf(input.getText().toString());
                String ageString = getString(R.string.age_string,newAge);
                mCurrentCharacter.setAge(newAge);
                mTextViewCharacterAge.setText(ageString);

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Context t = getApplicationContext();
                Toast.makeText(t, "Value Unchanged.", Toast.LENGTH_SHORT).show();
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        //set properties of the buttons
        final Button cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negButtonLP = (LinearLayout.LayoutParams) cancelButton.getLayoutParams();
        cancelButton.setTextColor(Color.BLUE);
        cancelButton.setLayoutParams(negButtonLP);

        final Button applyButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams posButtonLP = (LinearLayout.LayoutParams) applyButton.getLayoutParams();
        applyButton.setTextColor(Color.BLUE);
        applyButton.setLayoutParams(posButtonLP);

    }

    public void openGenderDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText("Set Gender");
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        alertDialog.setCustomTitle(mTextViewTitle);

        final EditText input = new EditText(this);
        input.setHint("Type how your character presents.");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("GENDER", mCurrentCharacter.getGenderString());
                String genderString = getString(R.string.gender_string,input.getText().toString());
                mCurrentCharacter.setGender(input.getText().toString());
                mTextViewCharacterGender.setText(genderString);

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Context t = getApplicationContext();
                Toast.makeText(t, "Value Unchanged.", Toast.LENGTH_SHORT).show();
        }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        //set properties of the buttons
        final Button cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negButtonLP = (LinearLayout.LayoutParams) cancelButton.getLayoutParams();
        cancelButton.setTextColor(Color.BLUE);
        cancelButton.setLayoutParams(negButtonLP);

        final Button applyButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams posButtonLP = (LinearLayout.LayoutParams) applyButton.getLayoutParams();
        applyButton.setTextColor(Color.BLUE);
        applyButton.setLayoutParams(posButtonLP);

    }

    @Override
    public void applySexChange(Sex s) {
        mCurrentCharacter.setSex(s);
        mTextViewCharacterSex.setText(getString(R.string.sex_string,s.toString()));
    }

    @Override
    public void applyRaceChange(Race r) {
        mCurrentCharacter.setRace(r);
        mTextViewCharacterRace.setText(getString(R.string.race_string,r.toString()));
    }

    public String feetInches(int f){
        int feet = f/12;
        int inches = f%12;

        return String.valueOf(feet)+"' "+ String.valueOf(inches)+'"';
    }

}
