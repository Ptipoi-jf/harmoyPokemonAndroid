/**************************************************************************
 * NpctoBadgesContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Badges;



import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class NpctoBadgesContractBase {


        /** NpcInternalId_id. */
    public static final String COL_NPCINTERNALID_ID =
            "NpcInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_NPCINTERNALID_ID =
            NpctoBadgesContract.TABLE_NAME + "." + COL_NPCINTERNALID_ID;

    /** badges_id. */
    public static final String COL_BADGES_ID =
            "badges_id";
    /** Alias. */
    public static final String ALIASED_COL_BADGES_ID =
            NpctoBadgesContract.TABLE_NAME + "." + COL_BADGES_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "NpctoBadges";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "NpctoBadges";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        NpctoBadgesContract.COL_NPCINTERNALID_ID,
        
        NpctoBadgesContract.COL_BADGES_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        NpctoBadgesContract.ALIASED_COL_NPCINTERNALID_ID,
        
        NpctoBadgesContract.ALIASED_COL_BADGES_ID
    };

}
