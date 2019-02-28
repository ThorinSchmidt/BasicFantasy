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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.lewisandclark.csd.basicfantasy.model.CharacterList;
import org.lewisandclark.csd.basicfantasy.model.EquipmentDatabase;
import org.lewisandclark.csd.basicfantasy.model.Item;
import org.lewisandclark.csd.basicfantasy.model.PlayerCharacter;
import org.lewisandclark.csd.basicfantasy.model.Weapon;
import org.lewisandclark.csd.basicfantasy.utils.DieRoller;

import java.util.ArrayList;

import static org.lewisandclark.csd.basicfantasy.HomeActivity.sCurrentCharacterIndex;

public class CombatActivity extends AppCompatActivity implements CurHPDialog.CurHPDialogListener {

    private CharacterList sCharacters = CharacterList.getPlayerCharacterList(this);
    private PlayerCharacter mCurrentCharacter = sCharacters.getPlayerCharacter(sCurrentCharacterIndex);
    private TextView mHPCurrentScore;
    private TextView mHPMaxScore;

    private TableRow mWeapon1;
    private TableRow mWeapon2;
    private TableRow mWeapon3;
    private TableRow mWeapon4;
    private TableRow mWeapon5;

    private TextView[] mWeaponNameArray;
    private TextView[] mWeaponToHitArray;
    private TextView[] mWeaponDamageArray;

    private int[] mWeaponDamageDieArray = {0,0,0,0,0};

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

        mHPMaxScore = findViewById(R.id.hp_max_textview);
        mHPCurrentScore = findViewById(R.id.hp_cur_textview);
        TextView mACScore = findViewById(R.id.AC_score);

        Log.d("PAGE2", "onCreate: total HP = "+ Integer.toString(totHP));
        mHPMaxScore.setText(getString(R.string.hp_max, mCurrentCharacter.getTotalHitPoints()));
        mHPCurrentScore.setText(getString(R.string.hp_cur, mCurrentCharacter.getCurrentHitPoints()));
        mHPCurrentScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("COMBAT", "Clicked Current HP");
                CombatActivity.this.openCurHPDialog();
            }
        });
        mACScore.setText(Integer.toString(mCurrentCharacter.getArmorClass()));

        mWeapon1 = findViewById(R.id.weapon_row1);
        mWeapon1.setOnClickListener(view -> weaponAttack(0));
        mWeapon2 = findViewById(R.id.weapon_row2);
        mWeapon2.setOnClickListener(view -> weaponAttack(1));
        mWeapon3 = findViewById(R.id.weapon_row3);
        mWeapon3.setOnClickListener(view -> weaponAttack(2));
        mWeapon4 = findViewById(R.id.weapon_row4);
        mWeapon4.setOnClickListener(view -> weaponAttack(3));
        mWeapon5 = findViewById(R.id.weapon_row5);
        mWeapon5.setOnClickListener(view -> weaponAttack(4));

        mWeaponNameArray = new TextView[]{
                findViewById(R.id.weapon_1_name),
                findViewById(R.id.weapon_2_name),
                findViewById(R.id.weapon_3_name),
                findViewById(R.id.weapon_4_name),
                findViewById(R.id.weapon_5_name)};

        mWeaponToHitArray = new TextView[]{
                findViewById(R.id.weapon_1_tohit),
                findViewById(R.id.weapon_2_tohit),
                findViewById(R.id.weapon_3_tohit),
                findViewById(R.id.weapon_4_tohit),
                findViewById(R.id.weapon_5_tohit)};

        mWeaponDamageArray = new TextView[]{
                findViewById(R.id.weapon_1_damage),
                findViewById(R.id.weapon_2_damage),
                findViewById(R.id.weapon_3_damage),
                findViewById(R.id.weapon_4_damage),
                findViewById(R.id.weapon_5_damage)};

        int index = 0;
        int meleeAttack = mCurrentCharacter.getMeleeAttackBonus();
        int rangedAttack = mCurrentCharacter.getRangedAttackBonus();
        int toHitBonus = 0;
        int damageBonus = 0;
        String meleeDamageSign = "+";
        String dieValues = "";
        if (mCurrentCharacter.getMeleeDamageBonus()< 0){
            meleeDamageSign = "-";
        }
        String damageString = "";
        for(Item item : mCurrentCharacter.getEquipmentList()){
            if(item instanceof Weapon && index < 5){
                mWeaponNameArray[index].setText(item.getNameID());
                if(((Weapon) item).isRanged()){
                    toHitBonus = ((Weapon) item).getAttackBonus() + rangedAttack;
                    damageString = getString(R.string.weapon_damage_string,
                             ((Weapon) item).getDamageDie(), "+",
                            ((Weapon) item).getAttackBonus());
                }
                else {
                    toHitBonus = ((Weapon) item).getAttackBonus() + meleeAttack;
                    damageString = getString(R.string.weapon_damage_string,
                            ((Weapon) item).getDamageDie(), meleeDamageSign,
                            Math.abs(((Weapon) item).getAttackBonus()+mCurrentCharacter.getMeleeDamageBonus()));
                }
                mWeaponToHitArray[index].setText(Integer.toString(toHitBonus));
                mWeaponDamageArray[index].setText(damageString);
                mWeaponDamageDieArray[index]=((Weapon) item).getDamageDie();
                dieValues += Integer.toString(mWeaponDamageDieArray[index])+":";
                index++;
                Log.d("EQUIPPED WEAPON", item.getNameID());
            }
        }

        Log.d("DAMAGE DIE ARRAY", dieValues);

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

    public void weaponAttack(int index){

    }

    public void openWeaponDialog(String title, int attackMod, int damageDie, int damageMod){

        int attackRoll = DieRoller.d20();
        int damageRoll = DieRoller.roll(damageDie);

        String attackModString;
        String damageModString;

        if (attackMod > -1){
            attackModString = "+";
        }
        else {
            attackModString = "-";
        }

        if (damageMod > -1){
            damageModString = "+";
        }
        else {
            damageModString = "-";
        }

        AlertDialog weaponDialog = new AlertDialog.Builder(this).create();

        TextView mTextViewTitle = new TextView(this);
        mTextViewTitle.setText(title);
        mTextViewTitle.setPadding(3, 3, 3, 10);
        mTextViewTitle.setGravity(Gravity.CENTER);
        mTextViewTitle.setTextColor(Color.BLUE);
        mTextViewTitle.setTextSize(20);
        weaponDialog.setCustomTitle(mTextViewTitle);

        TextView msg = new TextView(this);

        msg.setText(getString(R.string.attack_msg, attackRoll, attackModString, attackMod, attackRoll+attackMod, damageRoll, damageModString, damageMod, damageRoll+damageMod));
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        msg.setTextSize(18);
        weaponDialog.setView(msg);

        weaponDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialogInterface, i) -> {
            //no action to perform
        });

        new Dialog(getApplicationContext());
        weaponDialog.show();

        //set properties of the OK button
        final Button okButton = weaponDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralButtonLP = (LinearLayout.LayoutParams) okButton.getLayoutParams();
        okButton.setTextColor(Color.BLUE);
        okButton.setLayoutParams(neutralButtonLP);
    }


    public void onBackPressed(){
        Log.d("BUTTON", "pressed BACK button.");
        Intent theIntent = HomeActivity.newIntent(this, sCurrentCharacterIndex);
        startActivity(theIntent);

        return;
    }

    public void openCurHPDialog(){
        CurHPDialog curHPDialog = new CurHPDialog();
        curHPDialog.show(getSupportFragmentManager(), "HP dialog");
    }

    @Override
    public void applyHP(int hp) {
        Resources r = getResources();
        int currentHP = mCurrentCharacter.getCurrentHitPoints();
        //needed for later: the rule of not allowing more than 1 level gain at a time
        //int currentLevel = mCurrentCharacter.getLevel();
        currentHP += hp;
        if (currentHP > mCurrentCharacter.getTotalHitPoints()){
            currentHP = mCurrentCharacter.getTotalHitPoints();
        }
        mCurrentCharacter.setCurrentHitPoints(currentHP);
        mHPCurrentScore.setText(getString(R.string.hp_cur, mCurrentCharacter.getCurrentHitPoints()));

    }

    @Override
    public void fullHP(){
        mCurrentCharacter.setCurrentHitPoints(mCurrentCharacter.getTotalHitPoints());
        mHPCurrentScore.setText(getString(R.string.hp_cur, mCurrentCharacter.getCurrentHitPoints()));
        Toast.makeText(this, "Full Health Restored", Toast.LENGTH_SHORT).show();
    }
}
