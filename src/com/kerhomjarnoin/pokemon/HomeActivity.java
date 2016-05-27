/**************************************************************************
 * HomeActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.view.types.TypesListActivity;
import com.kerhomjarnoin.pokemon.view.arenes.ArenesListActivity;
import com.kerhomjarnoin.pokemon.view.typedepokemons.TypeDePokemonsListActivity;
import com.kerhomjarnoin.pokemon.view.badges.BadgesListActivity;
import com.kerhomjarnoin.pokemon.view.typeobjet.TypeObjetListActivity;
import com.kerhomjarnoin.pokemon.view.dresseurs.DresseursListActivity;
import com.kerhomjarnoin.pokemon.view.objets.ObjetsListActivity;
import com.kerhomjarnoin.pokemon.view.npc.NpcListActivity;
import com.kerhomjarnoin.pokemon.view.attaques.AttaquesListActivity;
import com.kerhomjarnoin.pokemon.view.positions.PositionsListActivity;
import com.kerhomjarnoin.pokemon.view.zones.ZonesListActivity;
import com.kerhomjarnoin.pokemon.view.pokemons.PokemonsListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
        implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.initButtons();
    }
    
    @Override
    protected int getContentView() {
        return R.layout.main;
    }

    /**
     * Initialize the buttons click listeners.
     */
    private void initButtons() {
        this.findViewById(R.id.types_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.arenes_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typedepokemons_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.badges_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.typeobjet_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.dresseurs_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.objets_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.npc_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.attaques_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.positions_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.zones_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokemons_list_button)
                        .setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.types_list_button:
                intent = new Intent(this,
                        TypesListActivity.class);
                break;

            case R.id.arenes_list_button:
                intent = new Intent(this,
                        ArenesListActivity.class);
                break;

            case R.id.typedepokemons_list_button:
                intent = new Intent(this,
                        TypeDePokemonsListActivity.class);
                break;

            case R.id.badges_list_button:
                intent = new Intent(this,
                        BadgesListActivity.class);
                break;

            case R.id.typeobjet_list_button:
                intent = new Intent(this,
                        TypeObjetListActivity.class);
                break;

            case R.id.dresseurs_list_button:
                intent = new Intent(this,
                        DresseursListActivity.class);
                break;

            case R.id.objets_list_button:
                intent = new Intent(this,
                        ObjetsListActivity.class);
                break;

            case R.id.npc_list_button:
                intent = new Intent(this,
                        NpcListActivity.class);
                break;

            case R.id.attaques_list_button:
                intent = new Intent(this,
                        AttaquesListActivity.class);
                break;

            case R.id.positions_list_button:
                intent = new Intent(this,
                        PositionsListActivity.class);
                break;

            case R.id.zones_list_button:
                intent = new Intent(this,
                        ZonesListActivity.class);
                break;

            case R.id.pokemons_list_button:
                intent = new Intent(this,
                        PokemonsListActivity.class);
                break;

            default:
                intent = null;
                break;
        }

        if (intent != null) {
            this.startActivity(intent);
        }
    }
}
