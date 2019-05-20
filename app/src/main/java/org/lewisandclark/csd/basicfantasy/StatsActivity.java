package org.lewisandclark.csd.basicfantasy;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lewisandclark.csd.basicfantasy.dialogs.XPDialog;
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

public class StatsActivity extends AppCompatActivity implements XPDialog.XPDialogListener {

    static final String TAG = "StatsActivity";

    private CharacterList sCharacters = CharacterList.getPlayerCharacterList(this);
    private PlayerCharacter mCurrentCharacter;

    private TextView mTextViewCharacterLevel;
    private TextView mTextViewCharacterXP;

    public static Intent newIntent(Context packageContext){
        //Intent Extras go here
        return new Intent(packageContext, StatsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        setTitle(R.string.title_activity_stats);
        mCurrentCharacter = sCharacters.getPlayerCharacter(sCurrentCharacterIndex);

        TextView mTextViewCharacterName = findViewById(R.id.character_name);
        TextView mTextViewCharacterClass = findViewById(R.id.character_class);
        mTextViewCharacterLevel = findViewById(R.id.character_level);
        mTextViewCharacterXP = findViewById(R.id.character_xp);
        TextView mTextViewStrengthScore = findViewById(R.id.strength_score);
        TextView mTextViewStrengthMod = findViewById(R.id.strength_mod);
        TextView mTextViewDexterityScore = findViewById(R.id.dexterity_score);
        TextView mTextViewDexterityMod = findViewById(R.id.dexterity_mod);
        TextView mTextViewIntelligenceScore = findViewById(R.id.intelligence_score);
        TextView mTextViewIntelligenceMod = findViewById(R.id.intelligence_mod);
        TextView mTextViewConstitutionScore = findViewById(R.id.constitution_score);
        TextView mTextViewConstitutionMod = findViewById(R.id.constitution_mod);
        TextView mTextViewWisdomScore = findViewById(R.id.wisdom_score);
        TextView mTextViewWisdomMod = findViewById(R.id.wisdom_mod);
        TextView mTextViewCharismaScore = findViewById(R.id.charisma_score);
        TextView mTextViewCharismaMod = findViewById(R.id.charisma_mod);

        LinearLayout mLayoutStrength = findViewById(R.id.strength_layout);
        LinearLayout mLayoutDexterity = findViewById(R.id.dexterity_layout);
        LinearLayout mLayoutIntelligence = findViewById(R.id.intelligence_layout);
        LinearLayout mLayoutConstitution = findViewById(R.id.constitution_layout);
        LinearLayout mLayoutWisdom = findViewById(R.id.wisdom_layout);
        LinearLayout mLayoutCharisma = findViewById(R.id.charisma_layout);

        TextView mLinearLayoutDeathSave = findViewById(R.id.death_save);
        TextView mLinearLayoutMagicSave = findViewById(R.id.magic_save);
        TextView mLinearLayoutParalysisSave = findViewById(R.id.paralysis_save);
        TextView mLinearLayoutDragonSave = findViewById(R.id.dragon_save);
        TextView mLinearLayoutSpellSave = findViewById(R.id.spell_save);

        TextView mTextViewLeftNavigate = findViewById(R.id.left_button);
        TextView mTextViewRightNavigate = findViewById(R.id.right_button);

        mTextViewCharacterName.setText(mCurrentCharacter.getName());
        mTextViewCharacterClass.setText(mCurrentCharacter.getCharacterClass().getResId());
        mTextViewCharacterXP.setText(getString(R.string.xp_string, mCurrentCharacter.getXP()));
        mTextViewCharacterLevel.setText(getString(R.string.level_string, mCurrentCharacter.getLevel()));
        mTextViewStrengthScore.setText(mCurrentCharacter.getStatArray()[STR.ordinal()].getScoreString());
        mTextViewStrengthMod.setText(mCurrentCharacter.getStatArray()[STR.ordinal()].getModifierString());
        mTextViewDexterityScore.setText(mCurrentCharacter.getStatArray()[DEX.ordinal()].getScoreString());
        mTextViewDexterityMod.setText(mCurrentCharacter.getStatArray()[DEX.ordinal()].getModifierString());
        mTextViewIntelligenceScore.setText(mCurrentCharacter.getStatArray()[INT.ordinal()].getScoreString());
        mTextViewIntelligenceMod.setText(mCurrentCharacter.getStatArray()[INT.ordinal()].getModifierString());
        mTextViewConstitutionScore.setText(mCurrentCharacter.getStatArray()[CON.ordinal()].getScoreString());
        mTextViewConstitutionMod.setText(mCurrentCharacter.getStatArray()[CON.ordinal()].getModifierString());
        mTextViewWisdomScore.setText(mCurrentCharacter.getStatArray()[WIS.ordinal()].getScoreString());
        mTextViewWisdomMod.setText(mCurrentCharacter.getStatArray()[WIS.ordinal()].getModifierString());
        mTextViewCharismaScore.setText(mCurrentCharacter.getStatArray()[CHA.ordinal()].getScoreString());
        mTextViewCharismaMod.setText(mCurrentCharacter.getStatArray()[CHA.ordinal()].getModifierString());

        mTextViewCharacterXP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatsActivity.this.openXPDialog();
            }
        });

        mLinearLayoutDeathSave.setOnClickListener(view -> openDialog("Death Ray or Poison", mCurrentCharacter.getDeathRayPosionMod(),
                mCurrentCharacter.getDeathRayPoisonSave()));

        mLinearLayoutMagicSave.setOnClickListener(view -> openDialog("Magic Wands", mCurrentCharacter.getWandMod(),
                mCurrentCharacter.getWandSave()));

        mLinearLayoutParalysisSave.setOnClickListener(view -> openDialog("Paralysis or Turn to Stone", mCurrentCharacter.getParalysisStoneMod(),
                mCurrentCharacter.getParalysisStoneSave()));

        mLinearLayoutDragonSave.setOnClickListener(view -> openDialog("Dragon Breath", mCurrentCharacter.getDragonBreathMod(),
                mCurrentCharacter.getDragonBreathSave()));


        mLinearLayoutSpellSave.setOnClickListener(view -> openDialog("Rods, Staves, and Spells", mCurrentCharacter.getRodStaveSpellMod(),
                mCurrentCharacter.getRodStaveSpellSave()));

        mLayoutStrength.setOnClickListener(view -> openDialog("Strength", mCurrentCharacter.getStatArray()[STR.ordinal()].getModifier(),
                mCurrentCharacter.getAbilityRoll()));

        mLayoutDexterity.setOnClickListener(view -> openDialog("Dexterity", mCurrentCharacter.getStatArray()[DEX.ordinal()].getModifier(),
                mCurrentCharacter.getAbilityRoll()));

        mLayoutIntelligence.setOnClickListener(view -> openDialog("Intelligence", mCurrentCharacter.getStatArray()[INT.ordinal()].getModifier(),
                mCurrentCharacter.getAbilityRoll()));

        mLayoutConstitution.setOnClickListener(view -> openDialog("Constitution", mCurrentCharacter.getStatArray()[CON.ordinal()].getModifier(),
                mCurrentCharacter.getAbilityRoll()));

        mLayoutWisdom.setOnClickListener(view -> openDialog("Wisdom", mCurrentCharacter.getStatArray()[WIS.ordinal()].getModifier(),
                mCurrentCharacter.getAbilityRoll()));

        mLayoutCharisma.setOnClickListener(view -> openDialog("Charisma", mCurrentCharacter.getStatArray()[CHA.ordinal()].getModifier(),
                mCurrentCharacter.getAbilityRoll()));

        mTextViewLeftNavigate.setOnClickListener(view -> {
            //open left screen
            Intent i = TreasureActivity.newIntent(StatsActivity.this);
            startActivity(i);
            overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
        });

        mTextViewRightNavigate.setOnClickListener(view -> {
            //open right screen
            Intent i = CombatActivity.newIntent(StatsActivity.this);
            startActivity(i);
            overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
        });
    }

    public void onBackPressed(){
        Log.d("BUTTON", "pressed BACK button.");
        Intent theIntent = HomeActivity.newIntent(this, sCurrentCharacterIndex);
        startActivity(theIntent);
    }


    public void openDialog(String title, int mod, int target){

        int roll = DieRoller.d20();
        String modString;

        if (mod > -1){
            modString = "+";
        }
        else {
            modString = "-";
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText(title);
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        alertDialog.setCustomTitle(mTextViewTitle);

        TextView msg = new TextView(this);

        msg.setText(getString(R.string.saving_throw_msg, target, roll, modString, abs(mod), roll+mod));
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        msg.setTextSize(18);
        alertDialog.setView(msg);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialogInterface, i) -> {
            //no action to perform
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        //set properties of the OK button
        final Button okButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralButtonLP = (LinearLayout.LayoutParams) okButton.getLayoutParams();
        okButton.setTextColor(Color.BLUE);
        okButton.setLayoutParams(neutralButtonLP);
    }

    public void openXPDialog(){
        XPDialog xpDialog = new XPDialog();
        xpDialog.show(getSupportFragmentManager(), "XP dialog");
    }

    @Override
    public void applyXP(int xp) {
        Resources r = getResources();
        int currentXP = mCurrentCharacter.getXP();
        //needed for later: the rule of not allowing more than 1 level gain at a time
        //int currentLevel = mCurrentCharacter.getLevel();
        int newLevel = 0;
        currentXP += xp;
        mCurrentCharacter.setXP(currentXP);
        mTextViewCharacterXP.setText(getString(R.string.xp_string, mCurrentCharacter.getXP()));
        Log.d(TAG, "applyXP: " + String.valueOf(currentXP));
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
            if(currentXP >= levelArray[i] && currentXP < levelArray[i+1]){
                    newLevel = i;
                    break;
            }
        }
        mCurrentCharacter.setLevel(newLevel);
        mTextViewCharacterLevel.setText(String.valueOf(newLevel));
    }
}
