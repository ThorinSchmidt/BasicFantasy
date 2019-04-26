package org.lewisandclark.csd.basicfantasy.model;

import org.lewisandclark.csd.basicfantasy.R;

/**
 * Created by Thorin Schmidt on 3/18/2018.
 */

public enum Sex {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    NEITHER(R.string.gender_neither);

    Sex(int mResID) {
    }
}
