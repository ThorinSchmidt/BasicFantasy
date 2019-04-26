package org.lewisandclark.csd.basicfantasy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.lewisandclark.csd.basicfantasy.model.CharacterClass;
import org.lewisandclark.csd.basicfantasy.model.CharacterList;
import org.lewisandclark.csd.basicfantasy.model.EquipmentDatabase;
import org.lewisandclark.csd.basicfantasy.model.Sex;
import org.lewisandclark.csd.basicfantasy.model.Item;
import org.lewisandclark.csd.basicfantasy.model.PlayerCharacter;
import org.lewisandclark.csd.basicfantasy.model.Race;

public class HomeActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext, int index){
        Intent theIntent = new Intent(packageContext, HomeActivity.class);
        //Intent Extras go here
        theIntent.putExtra("HomeActivityIndex", index);
        return theIntent;
    }

    private CharacterList sCharacters = CharacterList.getPlayerCharacterList(this);
    private EquipmentDatabase sEquipmentDatabase = EquipmentDatabase.getEquipmentDatabase(this);

    public static int sCurrentCharacterIndex;

    private PlayerCharacter mCurrentCharacter;
    private TextView mCurrentCharacterName;
    private Button mChooseCharacterButton;
    private Button mCreateCharacterButton;
    private Button mOpenCharacterButton;
    private Button mDeleteCharacterButton;
    private Button mBuyEquipmentButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Item testItem = sEquipmentDatabase.getEquipment("Hand Axe");
        sCurrentCharacterIndex =
                getIntent().getIntExtra("HomeActivityIndex", 0);

        if (sCharacters.getList().size() == 0) {
            createPreGens();
        }
        mCurrentCharacter = sCharacters.getPlayerCharacter(0);
        sCharacters.updateCharacter(mCurrentCharacter,0);


        Log.d("HOME", "Current character index: " + Integer.toString(sCurrentCharacterIndex));
        Log.d("HOME", "Character Array Size: "+ Integer.toString(sCharacters.getList().size()));
        mCurrentCharacter = sCharacters.getPlayerCharacter(sCurrentCharacterIndex);

        mCurrentCharacterName = findViewById(R.id.currentCharacter);
        mCurrentCharacterName.setText(String.format(mCurrentCharacter.getName()));

        mChooseCharacterButton = (Button) findViewById(R.id.choose_button);
        mChooseCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent =
                        ChooseCurrentCharacterActivity.newIntent(HomeActivity.this,
                                sCurrentCharacterIndex);
                startActivity(theIntent);
            }
        });

        mCreateCharacterButton = findViewById(R.id.create_button);
        mCreateCharacterButton.setOnClickListener(view -> {
            //sCurrentCharacterIndex = sCharacters.sizeOf();
            //sCharacters.addCharacter(new PlayerCharacter(sCharacters.sizeOf()));
            //Intent theIntent = RollAttributesActivity
            //        .newIntent(HomeActivity.this);
            //startActivity(theIntent);
            Toast.makeText(HomeActivity.this,
                    "Create Character Button clicked.",
                    Toast.LENGTH_SHORT).show();
        });

        mOpenCharacterButton = findViewById(R.id.open_button);
        mOpenCharacterButton.setOnClickListener(view -> {
            Intent theIntent = StatsActivity.newIntent(HomeActivity.this);
            startActivity(theIntent);
        });

        mDeleteCharacterButton = findViewById(R.id.delete_button);
        mDeleteCharacterButton.setOnClickListener(view -> Toast.makeText(HomeActivity.this,
                "Delete Character Button clicked.",
                Toast.LENGTH_SHORT).show());

        /* only included for testing purposes
        mBuyEquipmentButton = findViewById(R.id.buy_equipment_button);
        mBuyEquipmentButton.setOnClickListener(view -> {
            Intent theIntent = BuyEquipmentActivity.newIntent(HomeActivity.this);
            startActivity(theIntent);
        });*/
    }

    public void onBackPressed(){
        openDialog();

        return;
    }

    public void openDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText("Exit");
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        alertDialog.setCustomTitle(mTextViewTitle);

        TextView msg = new TextView(this);

        msg.setText("Exit the Basic Fantasy app?");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        msg.setTextSize(18);
        alertDialog.setView(msg);

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                moveTaskToBack(true);
            }
        });

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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

    public void createPreGens(){
        PlayerCharacter Darion = new PlayerCharacter("Darion", Race.HUMAN, Sex.MALE, CharacterClass.FIGHTER,
                16, 9, 8, 13, 13, 11, 6, 7);
        Darion.addEquipment(sEquipmentDatabase.getEquipment("Longsword"), true);
        Darion.setAge(22);
        Darion.setWeight(220);
        Darion.setHeight(74);
        Darion.setEyeColor("Blue");
        Darion.setHairColor("Brown");
        Darion.setGender("Male");
        Darion.setAppearanceDetails("Has a nipple ring.");
        PlayerCharacter Morningstar = new PlayerCharacter("Morningstar", Race.ELF, Sex.FEMALE, CharacterClass.FIGHTER_MU,
                15, 14, 14, 7, 11, 15, 5, 3);
        Morningstar.addEquipment(sEquipmentDatabase.getEquipment("Longsword"), true);
        Morningstar.setAge(86);
        Morningstar.setWeight(98);
        Morningstar.setHeight(58);
        Morningstar.setEyeColor("Green");
        Morningstar.setHairColor("Blonde");
        Morningstar.setGender("Andro.");
        sCharacters.addCharacter(Darion);
        sCharacters.addCharacter(Morningstar);
    }
}
