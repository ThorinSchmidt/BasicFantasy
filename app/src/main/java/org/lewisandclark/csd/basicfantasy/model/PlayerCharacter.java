package org.lewisandclark.csd.basicfantasy.model;

import org.lewisandclark.csd.basicfantasy.utils.DieRoller;

import java.util.ArrayList;

import static org.lewisandclark.csd.basicfantasy.model.GameConstants.ATTACK_BONUS_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.GameConstants.CLERIC_SAVE_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.GameConstants.FIGHTER_MU_SAVE_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.GameConstants.FIGHTER_SAVE_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.GameConstants.MU_SAVE_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.GameConstants.MU_THIEF_SAVE_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.GameConstants.THIEF_SAVE_MATRIX;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.CHA;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.CON;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.DEX;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.INT;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.STR;
import static org.lewisandclark.csd.basicfantasy.model.Attribute.WIS;

/**
 * Created by Thorin Schmidt on 2/22/2018.
 */

public class PlayerCharacter {

    private int ID;
    private String mName;
    private Sex mSex;
    private String mGender;
    private int mHeight;
    private int mWeight;
    private int mAge;
    private String mEyeColor;
    private String mHairColor;
    private String mAppearanceDetails;

    private int mLevel;
    private int mXP;
    private Race mRace;
    private CharacterClass mPlayerClass;


    private int mStatRollCounter;
    private AttributeScore[] mStatArray = new AttributeScore[6]; //[STR,INT,WIS,DEX,CON,CHA]

    private int mHitDie;
    private int mTotalHitPoints;
    private int mCurrentHitPoints;
    private int mArmorClass;
    private Armor mEquippedArmor;
    private Shield mEquippedShield;
    private Weapon mEquippedWeapon;

    private int mBaseAttackBonus;
    private int mMeleeAttackBonus;
    private int mRangedAttackBonus;
    private int mMeleeDamageBonus;

    private int mBaseMovement;
    private int mCurrentMovement;

    private int mAbilityRoll;

    private int mDeathRayPoisonSave;
    private int mDeathRayPosionMod;
    private int mWandSave;
    private int mWandMod;
    private int mParalysisStoneSave;
    private int mParalysisStoneMod;
    private int mDragonBreathSave;
    private int mDragonBreathMod;
    private int mRodStaveSpellSave;
    private int mRodStaveSpellMod;

    private int[] mMoneyArray = new int[5]; //[PP,GP,EP,SP,CP]
    private ArrayList<Treasure> mTreasureList = new ArrayList<>();
    private ArrayList<Item> mEquipmentList = new ArrayList<>();

    public PlayerCharacter(int id) {

        this.ID = id;
        this.mStatRollCounter = 0;
    }

    /**
     * Pregen Constructor
     */
    public PlayerCharacter(String name, Race race, Sex sex, CharacterClass characterClass,
                           int str, int intelligence, int wis, int dex, int con, int cha, int hp,
                           int gp){
        this.mName = name;
        this.mRace = race;
        this.mSex = sex;
        this.mPlayerClass = characterClass;
        this.mLevel = 1;
        this.mXP = 0;
        this.mTotalHitPoints = hp;
        this.mCurrentHitPoints = hp;
        this.mAbilityRoll = 18;
        this.mMoneyArray[Money.PP.ordinal()] = 0;
        this.mMoneyArray[Money.GP.ordinal()] = gp;
        this.mMoneyArray[Money.EP.ordinal()] = 0;
        this.mMoneyArray[Money.CP.ordinal()] = 0;


        switch (mPlayerClass){
            case FIGHTER:  this.mHitDie = 8;
                            if (mRace == Race.ELF || mRace ==  Race.HALFLING){
                                this.mHitDie = 6;
                            }
                            break;
            case FIGHTER_MU:
            case CLERIC:    this.mHitDie = 6;
                            break;
            case MAGIC_USER:
            case MU_THIEF:
            case THIEF:     this.mHitDie = 4;
                            break;
        }

        this.mStatRollCounter = 1;
        this.mStatArray[STR.ordinal()] = new AttributeScore(str);
        this.mStatArray[INT.ordinal()] = new AttributeScore(intelligence);
        this.mStatArray[WIS.ordinal()] = new AttributeScore(wis);
        this.mStatArray[DEX.ordinal()] = new AttributeScore(dex);
        this.mStatArray[CON.ordinal()] = new AttributeScore(con);
        this.mStatArray[CHA.ordinal()] = new AttributeScore(cha);
        this.autoCalc();
    }


    /**
     * Generic Constructor for testing
     */
    public PlayerCharacter(){
        this.mName = "Fenton Falomar";
        this.mSex = Sex.MALE;
        this.mRace = Race.HUMAN;
        this.mPlayerClass = CharacterClass.FIGHTER;
        this.mAge = 20;
        this.mHeight = 76;
        this.mWeight = 220;
        this.mEyeColor = "Green";
        this.mLevel = 1;
        this.mXP = 0;
        this.mHitDie = 8;
        this.mTotalHitPoints = 8;
        this.mCurrentHitPoints = 8;

        this.mAbilityRoll = 18;

        this.mStatRollCounter = 1;
        this.mStatArray[STR.ordinal()] = new AttributeScore(18);
        this.mStatArray[INT.ordinal()] = new AttributeScore(16);
        this.mStatArray[WIS.ordinal()] = new AttributeScore(9);
        this.mStatArray[DEX.ordinal()] = new AttributeScore(12);
        this.mStatArray[CON.ordinal()] = new AttributeScore(8);
        this.mStatArray[CHA.ordinal()] = new AttributeScore(5);
        this.autoCalc();
    }

    /**
     * Other Generic Constructor for testing
     */
    public PlayerCharacter(String name){
        this.mName = name;
        this.mSex = Sex.FEMALE;
        this.mRace = Race.ELF;
        this.mPlayerClass = CharacterClass.MAGIC_USER;
        this.mAge = 120;
        this.mHeight = 65;
        this.mWeight = 100;
        this.mEyeColor = "Blue";
        this.mHairColor = "Brown";
        this.mLevel = 1;
        this.mXP = 0;
        this.mHitDie = 4;
        this.mTotalHitPoints = 0;
        this.mCurrentHitPoints = 0;

        this.mAbilityRoll = 18;

        this.mStatRollCounter = 1;
        this.mStatArray[STR.ordinal()] = new AttributeScore(7);
        this.mStatArray[INT.ordinal()] = new AttributeScore(17);
        this.mStatArray[WIS.ordinal()] = new AttributeScore(12);
        this.mStatArray[DEX.ordinal()] = new AttributeScore(15);
        this.mStatArray[CON.ordinal()] = new AttributeScore(11);
        this.mStatArray[CHA.ordinal()] = new AttributeScore(12);
        this.autoCalc();
    }

    private void autoCalc(){
        int[] saveArray;
        //total hit points
        if(this.mTotalHitPoints == 0){
            this.mTotalHitPoints += DieRoller.roll(this.mHitDie) +
                    Integer.valueOf(this.mStatArray[CON.ordinal()].getModifier());
        }
        this.mCurrentHitPoints = this.mTotalHitPoints;

        // Attack Bonus and weapons
        mBaseAttackBonus = ATTACK_BONUS_MATRIX[mPlayerClass.ordinal()][mLevel];
        mMeleeAttackBonus = mBaseAttackBonus + mStatArray[STR.ordinal()].getModifier();
        mRangedAttackBonus = mBaseAttackBonus + mStatArray[DEX.ordinal()].getModifier();
        mMeleeDamageBonus = mStatArray[STR.ordinal()].getModifier();
        this.mEquipmentList.add(new Weapon()); //adds entry to "Fists"
        this.mEquippedWeapon = (Weapon) this.mEquipmentList.get(0);

        //Armor Class and Armor
        this.mEquipmentList.add(new Armor());  //adds entry for "No Armor"
        this.mEquipmentList.add(new Shield()); // adds entry for "No Shield"
        this.mEquippedArmor = (Armor) this.mEquipmentList.get(1);
        this.mEquippedShield = (Shield) this.mEquipmentList.get(2);

        //Saves
        switch (this.mPlayerClass){
            case CLERIC: saveArray = CLERIC_SAVE_MATRIX[mLevel];
            break;
            case FIGHTER: saveArray = FIGHTER_SAVE_MATRIX[mLevel];
            break;
            case MAGIC_USER: saveArray = MU_SAVE_MATRIX[mLevel];
            break;
            case THIEF: saveArray = THIEF_SAVE_MATRIX[mLevel];
            break;
            case FIGHTER_MU: saveArray = FIGHTER_MU_SAVE_MATRIX[mLevel];
            break;
            case MU_THIEF: saveArray = MU_THIEF_SAVE_MATRIX[mLevel];
            break;
            default: saveArray = new int[]{20, 20, 20, 20, 20};
        }
        mDeathRayPoisonSave = saveArray[0];
        mWandSave = saveArray[1];
        mParalysisStoneSave = saveArray[2];
        mDragonBreathSave = saveArray[3];
        mRodStaveSpellSave = saveArray[3];

        //Save bonuses
        switch (mRace) {
            case DWARF:
                mDeathRayPosionMod = 4;
                mWandMod = 4;
                mParalysisStoneMod = 4;
                mDragonBreathMod = 3;
                mRodStaveSpellMod = 4;
                break;
            case ELF:
                mDeathRayPosionMod = 0;
                mWandMod = 2;
                mParalysisStoneMod = 0;
                mDragonBreathMod = 0;
                mRodStaveSpellMod = 2;
                break;
            case HALFLING:
                mDeathRayPosionMod = 4;
                mWandMod = 4;
                mParalysisStoneMod = 4;
                mDragonBreathMod = 3;
                mRodStaveSpellMod = 4;
                break;
            default: //human
                mDeathRayPosionMod = 0;
                mWandMod = 0;
                mParalysisStoneMod = 0;
                mDragonBreathMod = 0;
                mRodStaveSpellMod = 0;
                break;
        }

        

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Sex getSex() {
        return mSex;
    }

    public String getSexString() {
        if(mSex!=null){
            return this.mSex.toString();
        }
        else {
            return "Null";
        }
    }

    public void setSex(Sex mSex) {
        this.mSex = mSex;
    }

    public String getGender() {
        return mGender;
    }

    public String getGenderString() {
        if (mGender != null) {
            return mGender.toString();
        }
        else{
            return "Null";
        }
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getEyeColor() {return mEyeColor;}

    public void setEyeColor(String eyeColor) {mEyeColor = eyeColor;}

    public int getAge() {
        return mAge;
    }

    public int getHeight() {
        return mHeight;
    }

    public String getHeightString() {
        int feet = mHeight/12;
        int inches = mHeight%12;
        return Integer.toString(feet)+"' "+ Integer.toString(inches)+'"';
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int weight) {
        mWeight = weight;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getAppearanceDetails() {
        return mAppearanceDetails;
    }

    public void setAppearanceDetails(String mAppearanceDetails) {
        this.mAppearanceDetails = mAppearanceDetails;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getXP() {
        return mXP;
    }

    public void setXP(int XP) {
        mXP = XP;
    }

    public int getStatRollCounter() {
        return mStatRollCounter;
    }

    public void setStatRollCounter(int statRollCounter) {
        mStatRollCounter = statRollCounter;
    }

    public void incrementStatRollCounter(){ mStatRollCounter++;}

    public AttributeScore[] getStatArray() {
        return mStatArray;
    }

    public void setStatArray(AttributeScore[] statArray) {
        mStatArray = statArray;
    }

    public int[] getMoneyArray() {
        return mMoneyArray;
    }

    public void setMoneyArray(int[] moneyArray) {
        mMoneyArray = moneyArray;
    }

    public ArrayList<Treasure> getTreasureList() {
        return mTreasureList;
    }

    public void setTreasureList(ArrayList<Treasure> treasureList) {
        mTreasureList = treasureList;
    }

    public int getTotalHitPoints() {
        return mTotalHitPoints;
    }

    public void setTotalHitPoints(int totalHitPoints) {
        mTotalHitPoints = totalHitPoints;
    }

    public int getCurrentHitPoints() {
        return mCurrentHitPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        mCurrentHitPoints = currentHitPoints;
    }

    public Race getRace() {
        return mRace;
    }

    public void setRace(Race race) {
        mRace = race;
    }

    public CharacterClass getCharacterClass() {
        return mPlayerClass;
    }

    public void setCharacterClass(CharacterClass aClass) {
        mPlayerClass = aClass;
    }

    public int getArmorClass() {
        return mArmorClass;
    }

    public void setArmorClass(int armorClass) {
        mArmorClass = armorClass;
    }

    public int getBaseAttackBonus() {
        return mBaseAttackBonus;
    }

    public void setBaseAttackBonus(int baseAttackBonus) {
        mBaseAttackBonus = baseAttackBonus;
    }

    public int getMeleeAttackBonus() {
        return mMeleeAttackBonus;
    }

    public void setMeleeAttackBonus(int meleeAttackBonus) {
        mMeleeAttackBonus = meleeAttackBonus;
    }

    public int getRangedAttackBonus() {
        return mRangedAttackBonus;
    }

    public void setRangedAttackBonus(int rangedAttackBonus) {
        mRangedAttackBonus = rangedAttackBonus;
    }

    public int getMeleeDamageBonus() {
        return mMeleeDamageBonus;
    }

    public void setMeleeDamageBonus(int mMeleeDamageBonus) {
        this.mMeleeDamageBonus = mMeleeDamageBonus;
    }

    public int getBaseMovement() {
        return mBaseMovement;
    }

    public void setBaseMovement(int baseMovement) {
        mBaseMovement = baseMovement;
    }

    public int getCurrentMovement() {
        return mCurrentMovement;
    }

    public void setCurrentMovement(int currentMovement) {
        mCurrentMovement = currentMovement;
    }

    public int getAbilityRoll() {
        return mAbilityRoll;
    }

    public void setAbilityRoll(int abilityRoll) {
        mAbilityRoll = abilityRoll;
    }

    public int getDeathRayPoisonSave() {
        return mDeathRayPoisonSave;
    }

    public String getDeathRayPoisonSaveString() {return String.valueOf(mDeathRayPoisonSave);}

    public void setDeathRayPoisonSave(int deathRayPoisonSave) {
        mDeathRayPoisonSave = deathRayPoisonSave;
    }

    public int getDeathRayPosionMod() {
        return mDeathRayPosionMod;
    }

    public void setDeathRayPosionMod(int deathRayPosionMod) {
        mDeathRayPosionMod = deathRayPosionMod;
    }

    public int getWandSave() {
        return mWandSave;
    }

    public String getWandSaveString() {return String.valueOf(mWandSave);}

    public void setWandSave(int wandSave) {
        mWandSave = wandSave;
    }

    public int getWandMod() {
        return mWandMod;
    }

    public void setWandMod(int wandMod) {
        mWandMod = wandMod;
    }

    public int getParalysisStoneSave() {
        return mParalysisStoneSave;
    }

    public String getParalysisStoneSaveString() {return String.valueOf(mParalysisStoneSave);}

    public void setParalysisStoneSave(int paralysisStoneSave) {
        mParalysisStoneSave = paralysisStoneSave;
    }

    public int getParalysisStoneMod() {
        return mParalysisStoneMod;
    }

    public void setParalysisStoneMod(int paralysisStoneMod) {
        mParalysisStoneMod = paralysisStoneMod;
    }

    public int getDragonBreathSave() {
        return mDragonBreathSave;
    }

    public String getDragonBreathSaveString() {return String.valueOf(mDragonBreathSave);}

    public void setDragonBreathSave(int dragonBreathSave) {
        mDragonBreathSave = dragonBreathSave;
    }

    public int getDragonBreathMod() {
        return mDragonBreathMod;
    }

    public void setDragonBreathMod(int dragonBreathMod) {
        mDragonBreathMod = dragonBreathMod;
    }

    public int getRodStaveSpellSave() {
        return mRodStaveSpellSave;
    }

    public String getRodStaveSpellSaveString() {return String.valueOf(mRodStaveSpellSave);}

    public void setRodStaveSpellSave(int rodStaveSpellSave) {
        mRodStaveSpellSave = rodStaveSpellSave;
    }

    public int getRodStaveSpellMod() {
        return mRodStaveSpellMod;
    }

    public void setRodStaveSpellMod(int rodStaveSpellMod) {
        mRodStaveSpellMod = rodStaveSpellMod;
    }

    public int getHitDie() {
        return mHitDie;
    }

    public void setHitDie(int hitDie) {
        mHitDie = hitDie;
    }

    public ArrayList<Item> getEquipmentList() {
        return mEquipmentList;
    }

    public void setEquipmentList(ArrayList<Item> equipmentList) {
        mEquipmentList = equipmentList;
    }

    public void addEquipment(Item item, boolean isEquipped){
        item.setEquipped(isEquipped);
        this.mEquipmentList.add(item);
    }

    public ArrayList<Item> findEquipmentByType(ItemType type){

        ArrayList<Item> items = null;

        return items;
    }

    public String getHairColor() {
        return mHairColor;
    }

    public void setHairColor(String mHairColor) {
        this.mHairColor = mHairColor;
    }

}
