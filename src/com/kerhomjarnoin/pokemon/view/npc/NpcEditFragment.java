/**************************************************************************
 * NpcEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.npc;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.kerhomjarnoin.pokemon.R;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.entity.Positions;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.widget.MultiEntityWidget;
import com.kerhomjarnoin.pokemon.harmony.widget.SingleEntityWidget;
import com.kerhomjarnoin.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kerhomjarnoin.pokemon.provider.NpcProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.NpcProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.ObjetsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.PokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.BadgesProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.PositionsProviderUtils;
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/** Npc create fragment.
 *
 * This fragment gives you an interface to edit a Npc.
 *
 * @see android.app.Fragment
 */
public class NpcEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Npc model = new Npc();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** profession View. */
    protected EditText professionView;
    /** texte View. */
    protected EditText texteView;
    /** The objets chooser component. */
    protected MultiEntityWidget objetsWidget;
    /** The objets Adapter. */
    protected MultiEntityWidget.EntityAdapter<Objets>
            objetsAdapter;
    /** The pokemons chooser component. */
    protected MultiEntityWidget pokemonsWidget;
    /** The pokemons Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokemons>
            pokemonsAdapter;
    /** The badges chooser component. */
    protected MultiEntityWidget badgesWidget;
    /** The badges Adapter. */
    protected MultiEntityWidget.EntityAdapter<Badges>
            badgesAdapter;
    /** The position chooser component. */
    protected SingleEntityWidget positionWidget;
    /** The position Adapter. */
    protected SingleEntityWidget.EntityAdapter<Positions>
            positionAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.npc_nom);
        this.professionView = (EditText) view.findViewById(
                R.id.npc_profession);
        this.texteView = (EditText) view.findViewById(
                R.id.npc_texte);
        this.objetsAdapter =
                new MultiEntityWidget.EntityAdapter<Objets>() {
            @Override
            public String entityToString(Objets item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.npc_objets_button);
        this.objetsWidget.setAdapter(this.objetsAdapter);
        this.objetsWidget.setTitle(R.string.npc_objets_dialog_title);
        this.pokemonsAdapter =
                new MultiEntityWidget.EntityAdapter<Pokemons>() {
            @Override
            public String entityToString(Pokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.npc_pokemons_button);
        this.pokemonsWidget.setAdapter(this.pokemonsAdapter);
        this.pokemonsWidget.setTitle(R.string.npc_pokemons_dialog_title);
        this.badgesAdapter =
                new MultiEntityWidget.EntityAdapter<Badges>() {
            @Override
            public String entityToString(Badges item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgesWidget = (MultiEntityWidget) view.findViewById(
                        R.id.npc_badges_button);
        this.badgesWidget.setAdapter(this.badgesAdapter);
        this.badgesWidget.setTitle(R.string.npc_badges_dialog_title);
        this.positionAdapter =
                new SingleEntityWidget.EntityAdapter<Positions>() {
            @Override
            public String entityToString(Positions item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionWidget =
            (SingleEntityWidget) view.findViewById(R.id.npc_position_button);
        this.positionWidget.setAdapter(this.positionAdapter);
        this.positionWidget.setTitle(R.string.npc_position_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setText(this.model.getProfession());
        }
        if (this.model.getTexte() != null) {
            this.texteView.setText(this.model.getTexte());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setProfession(this.professionView.getEditableText().toString());

        this.model.setTexte(this.texteView.getEditableText().toString());

        this.model.setObjets(this.objetsAdapter.getCheckedItems());

        this.model.setPokemons(this.pokemonsAdapter.getCheckedItems());

        this.model.setBadges(this.badgesAdapter.getCheckedItems());

        this.model.setPosition(this.positionAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.npc_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.professionView.getText().toString().trim())) {
            error = R.string.npc_profession_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.texteView.getText().toString().trim())) {
            error = R.string.npc_texte_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }
    @Override
    public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(R.layout.fragment_npc_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Npc) intent.getParcelableExtra(
                NpcContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Npc entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final NpcEditFragment fragment,
                    final Npc entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.npc_progress_save_title),
                    this.ctx.getString(
                            R.string.npc_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new NpcProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("NpcEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.npc_error_edit));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                                int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }


    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private NpcEditFragment fragment;
        /** objets list. */
        private ArrayList<Objets> objetsList;
    /** objets list. */
        private ArrayList<Objets> associatedObjetsList;
        /** pokemons list. */
        private ArrayList<Pokemons> pokemonsList;
    /** pokemons list. */
        private ArrayList<Pokemons> associatedPokemonsList;
        /** badges list. */
        private ArrayList<Badges> badgesList;
    /** badges list. */
        private ArrayList<Badges> associatedBadgesList;
        /** position list. */
        private ArrayList<Positions> positionList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final NpcEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.npc_progress_load_relations_title),
                this.ctx.getString(
                    R.string.npc_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.objetsList = 
                new ObjetsProviderUtils(this.ctx).queryAll();
            Uri objetsUri = NpcProviderAdapter.NPC_URI;
            objetsUri = Uri.withAppendedPath(objetsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            objetsUri = Uri.withAppendedPath(objetsUri, "objets");
            android.database.Cursor objetsCursor = 
                    this.ctx.getContentResolver().query(
                            objetsUri,
                            new String[]{ObjetsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedObjetsList = new ArrayList<Objets>();
            if (objetsCursor != null && objetsCursor.getCount() > 0) {
                while (objetsCursor.moveToNext()) {
                    int objetsId = objetsCursor.getInt(
                            objetsCursor.getColumnIndex(ObjetsContract.COL_ID));
                    for (Objets objets : this.objetsList) {
                        if (objets.getId() ==  objetsId) {
                            this.associatedObjetsList.add(objets);
                        }
                    }
                }
                objetsCursor.close();
            }
            this.pokemonsList = 
                new PokemonsProviderUtils(this.ctx).queryAll();
            Uri pokemonsUri = NpcProviderAdapter.NPC_URI;
            pokemonsUri = Uri.withAppendedPath(pokemonsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            pokemonsUri = Uri.withAppendedPath(pokemonsUri, "pokemons");
            android.database.Cursor pokemonsCursor = 
                    this.ctx.getContentResolver().query(
                            pokemonsUri,
                            new String[]{PokemonsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPokemonsList = new ArrayList<Pokemons>();
            if (pokemonsCursor != null && pokemonsCursor.getCount() > 0) {
                while (pokemonsCursor.moveToNext()) {
                    int pokemonsId = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(PokemonsContract.COL_ID));
                    for (Pokemons pokemons : this.pokemonsList) {
                        if (pokemons.getId() ==  pokemonsId) {
                            this.associatedPokemonsList.add(pokemons);
                        }
                    }
                }
                pokemonsCursor.close();
            }
            this.badgesList = 
                new BadgesProviderUtils(this.ctx).queryAll();
            Uri badgesUri = NpcProviderAdapter.NPC_URI;
            badgesUri = Uri.withAppendedPath(badgesUri, 
                                    String.valueOf(this.fragment.model.getId()));
            badgesUri = Uri.withAppendedPath(badgesUri, "badges");
            android.database.Cursor badgesCursor = 
                    this.ctx.getContentResolver().query(
                            badgesUri,
                            new String[]{BadgesContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedBadgesList = new ArrayList<Badges>();
            if (badgesCursor != null && badgesCursor.getCount() > 0) {
                while (badgesCursor.moveToNext()) {
                    int badgesId = badgesCursor.getInt(
                            badgesCursor.getColumnIndex(BadgesContract.COL_ID));
                    for (Badges badges : this.badgesList) {
                        if (badges.getId() ==  badgesId) {
                            this.associatedBadgesList.add(badges);
                        }
                    }
                }
                badgesCursor.close();
            }
            this.positionList = 
                new PositionsProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setObjets(this.associatedObjetsList);
            this.fragment.onObjetsLoaded(this.objetsList);
            this.fragment.model.setPokemons(this.associatedPokemonsList);
            this.fragment.onPokemonsLoaded(this.pokemonsList);
            this.fragment.model.setBadges(this.associatedBadgesList);
            this.fragment.onBadgesLoaded(this.badgesList);
            this.fragment.onPositionLoaded(this.positionList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when objets have been loaded.
     * @param items The loaded items
     */
    protected void onObjetsLoaded(ArrayList<Objets> items) {
        this.objetsAdapter.loadData(items);
        this.objetsAdapter.setCheckedItems(this.model.getObjets());
    }
    /**
     * Called when pokemons have been loaded.
     * @param items The loaded items
     */
    protected void onPokemonsLoaded(ArrayList<Pokemons> items) {
        this.pokemonsAdapter.loadData(items);
        this.pokemonsAdapter.setCheckedItems(this.model.getPokemons());
    }
    /**
     * Called when badges have been loaded.
     * @param items The loaded items
     */
    protected void onBadgesLoaded(ArrayList<Badges> items) {
        this.badgesAdapter.loadData(items);
        this.badgesAdapter.setCheckedItems(this.model.getBadges());
    }
    /**
     * Called when position have been loaded.
     * @param items The loaded items
     */
    protected void onPositionLoaded(ArrayList<Positions> items) {
        this.positionAdapter.loadData(items);
        
        if (this.model.getPosition() != null) {
            for (Positions item : items) {
                if (item.getId() == this.model.getPosition().getId()) {
                    this.positionAdapter.selectItem(item);
                }
            }
        }
    }
}
