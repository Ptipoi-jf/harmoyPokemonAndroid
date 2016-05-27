/**************************************************************************
 * NpcCreateFragment.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.provider.utils.NpcProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.ObjetsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.PokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.BadgesProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.PositionsProviderUtils;

/**
 * Npc create fragment.
 *
 * This fragment gives you an interface to create a Npc.
 */
public class NpcCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Npc model = new Npc();

    /** Fields View. */
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

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.npc_nom);
        this.professionView =
            (EditText) view.findViewById(R.id.npc_profession);
        this.texteView =
            (EditText) view.findViewById(R.id.npc_texte);
        this.objetsAdapter = 
                new MultiEntityWidget.EntityAdapter<Objets>() {
            @Override
            public String entityToString(Objets item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetsWidget =
            (MultiEntityWidget) view.findViewById(R.id.npc_objets_button);
        this.objetsWidget.setAdapter(this.objetsAdapter);
        this.objetsWidget.setTitle(R.string.npc_objets_dialog_title);
        this.pokemonsAdapter = 
                new MultiEntityWidget.EntityAdapter<Pokemons>() {
            @Override
            public String entityToString(Pokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonsWidget =
            (MultiEntityWidget) view.findViewById(R.id.npc_pokemons_button);
        this.pokemonsWidget.setAdapter(this.pokemonsAdapter);
        this.pokemonsWidget.setTitle(R.string.npc_pokemons_dialog_title);
        this.badgesAdapter = 
                new MultiEntityWidget.EntityAdapter<Badges>() {
            @Override
            public String entityToString(Badges item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgesWidget =
            (MultiEntityWidget) view.findViewById(R.id.npc_badges_button);
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

    /** Load data from model to fields view. */
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

    /** Save data from fields view to model. */
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
        final View view = inflater.inflate(
                R.layout.fragment_npc_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Npc entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final NpcCreateFragment fragment,
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
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new NpcProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.npc_error_create));
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
        private NpcCreateFragment fragment;
        /** objets list. */
        private ArrayList<Objets> objetsList;
        /** pokemons list. */
        private ArrayList<Pokemons> pokemonsList;
        /** badges list. */
        private ArrayList<Badges> badgesList;
        /** position list. */
        private ArrayList<Positions> positionList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final NpcCreateFragment fragment) {
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
            this.pokemonsList = 
                new PokemonsProviderUtils(this.ctx).queryAll();
            this.badgesList = 
                new BadgesProviderUtils(this.ctx).queryAll();
            this.positionList = 
                new PositionsProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.objetsAdapter.loadData(this.objetsList);
            this.fragment.pokemonsAdapter.loadData(this.pokemonsList);
            this.fragment.badgesAdapter.loadData(this.badgesList);
            this.fragment.positionAdapter.loadData(this.positionList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
