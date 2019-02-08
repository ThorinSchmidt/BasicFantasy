package org.lewisandclark.csd.basicfantasy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.lewisandclark.csd.basicfantasy.model.CharacterList;
import org.lewisandclark.csd.basicfantasy.model.EquipmentDatabase;
import org.lewisandclark.csd.basicfantasy.model.Item;
import org.lewisandclark.csd.basicfantasy.model.PlayerCharacter;
import org.lewisandclark.csd.basicfantasy.model.Weapon;

import java.util.ArrayList;

import static org.lewisandclark.csd.basicfantasy.HomeActivity.sCurrentCharacterIndex;

public class CombatActivity extends AppCompatActivity implements CurHPDialog.CurHPDialogListener {

    private CharacterList sCharacters = CharacterList.getPlayerCharacterList(this);
    private PlayerCharacter mCurrentCharacter = sCharacters.getPlayerCharacter(sCurrentCharacterIndex);
    private TextView mHPCurrentScore;

    public static Intent newIntent(Context packageContext) {
        //Intent Extras go here
        return new Intent(packageContext, CombatActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);


        int totHP = mCurrentCharacter.getTotalHitPoints();

        TextView mTextViewCharacterName = findViewById(R.id.character_name);
        mTextViewCharacterName.setText(mCurrentCharacter.getName());

        TextView mTextViewCharacterClass = findViewById(R.id.character_class);
        mTextViewCharacterClass.setText(mCurrentCharacter.getCharacterClass().toString());

        TextView mHPMaxScore = findViewById(R.id.hp_max_score);
        mHPCurrentScore = findViewById(R.id.hp_cur_score);
        TextView mACScore = findViewById(R.id.AC_score);

        Log.d("PAGE2", "onCreate: total HP = "+ Integer.toString(totHP));
        mHPMaxScore.setText(Integer.toString(totHP));
        mHPCurrentScore.setText(Integer.toString(mCurrentCharacter.getCurrentHitPoints()));
        mHPCurrentScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("COMBAT", "Clicked Current HP");
                CombatActivity.this.openCurHPDialog();
            }
        });
        mACScore.setText(Integer.toString(mCurrentCharacter.getArmorClass()));

        TableRow mWeapon1 = findViewById(R.id.weapon_row1);
        TableRow mWeapon2 = findViewById(R.id.weapon_row2);
        TableRow mWeapon3 = findViewById(R.id.weapon_row3);
        TableRow mWeapon4 = findViewById(R.id.weapon_row4);
        TableRow mWeapon5 = findViewById(R.id.weapon_row5);

        TextView[] mWeaponNameArray = new TextView[]{
                findViewById(R.id.weapon_1_name),
                findViewById(R.id.weapon_2_name),
                findViewById(R.id.weapon_3_name),
                findViewById(R.id.weapon_4_name),
                findViewById(R.id.weapon_5_name)};

        TextView[] mWeaponToHitArray = new TextView[]{
                findViewById(R.id.weapon_1_tohit),
                findViewById(R.id.weapon_2_tohit),
                findViewById(R.id.weapon_3_tohit),
                findViewById(R.id.weapon_4_tohit),
                findViewById(R.id.weapon_5_tohit)};

        TextView[] mWeaponDamageArray = new TextView[]{
                findViewById(R.id.weapon_1_damage),
                findViewById(R.id.weapon_2_damage),
                findViewById(R.id.weapon_3_damage),
                findViewById(R.id.weapon_4_damage),
                findViewById(R.id.weapon_5_damage)};

        int index = 0;
        for(Item item : mCurrentCharacter.getEquipmentList()){
            if(item instanceof Weapon && index < 5){
                mWeaponNameArray[index].setText(item.getNameID());
                index++;
                Log.d("EQUIPPED WEAPON", item.getNameID());
            }
        }


        TextView mTextViewLeftNavigate = findViewById(R.id.left_button);
        TextView mTextViewRightNavigate = findViewById(R.id.right_button);

        mTextViewLeftNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open left screen
                Intent i = StatsActivity.newIntent(CombatActivity.this);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
            }
        });

        mTextViewRightNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open right screen
                Intent i = StatsActivity.newIntent(CombatActivity.this);
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

    public void openCurHPDialog(){
        CurHPDialog curHPDialog = new CurHPDialog();
        curHPDialog.show(getSupportFragmentManager(), "XP dialog");
    }

    @Override
    public void applyHP(int hp) {
        Resources r = getResources();
        int currentHP = mCurrentCharacter.getCurrentHitPoints();
        //needed for later: the rule of not allowing more than 1 level gain at a time
        //int currentLevel = mCurrentCharacter.getLevel();
        int newLevel = 0;
        currentHP += hp;
        mCurrentCharacter.setCurrentHitPoints(currentHP);
        //mTextViewCharacterXP.setText(getString(R.string.xp_string, mCurrentCharacter.getXP()));
        //Log.d(TAG, "applyXP: " + String.valueOf(currentXP));
        int[] levelArray = {};
        switch (mCurrentCharacter.getCharacterClass()){
            case THIEF: levelArray = r.getIntArray(R.array.THIEF_LEVELS);
                break;
            case CLERIC: levelArray = r.getIntArray(R.array.CLERIC_LEVELS);
                break;
            case FIGHTER: levelArray = r.getIntArray(R.array.FIGHTER_LEVELS);
                break;
            case MAGIC_USER: levelArray = r.getIntArray(R.array.MAGICUSER_LEVELS);
                break;
            case MU_THIEF: levelArray = r.getIntArray(R.array.MU_THIEF_LEVELS);
                break;
            case FIGHTER_MU: levelArray = r.getIntArray(R.array.FIGHTER_MU_LEVELS);
                break;
        }
        for(int i=0; i < levelArray.length-1; i++){
            newLevel = 20;

        }
        mCurrentCharacter.setLevel(newLevel);
        //mTextViewCharacterLevel.setText(String.valueOf(newLevel));
    }
}
