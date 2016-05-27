/**************************************************************************
 * PokemonsCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.pokemons;

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
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Attaques;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.widget.DateTimeWidget;

import com.kerhomjarnoin.pokemon.harmony.widget.SingleEntityWidget;
import com.kerhomjarnoin.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.PokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.AttaquesProviderUtils;

/**
 * Pokemons create fragment.
 *
 * This fragment gives you an interface to create a Pokemons.
 */
public class PokemonsCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Pokemons model = new Pokemons();

    /** Fields View. */
    /** surnom View. */
    protected EditText surnomView;
    /** niveau View. */
    protected EditText niveauView;
    /** capture DateTime View. */
    protected DateTimeWidget captureView;
    /** The typePokemon chooser component. */
    protected SingleEntityWidget typePokemonWidget;
    /** The typePokemon Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeDePokemons> 
                typePokemonAdapter;
    /** The attaque1 chooser component. */
    protected SingleEntityWidget attaque1Widget;
    /** The attaque1 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaques> 
                attaque1Adapter;
    /** The attaque2 chooser component. */
    protected SingleEntityWidget attaque2Widget;
    /** The attaque2 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaques> 
                attaque2Adapter;
    /** The attaque3 chooser component. */
    protected SingleEntityWidget attaque3Widget;
    /** The attaque3 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaques> 
                attaque3Adapter;
    /** The attaque4 chooser component. */
    protected SingleEntityWidget attaque4Widget;
    /** The attaque4 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Attaques> 
                attaque4Adapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.surnomView =
            (EditText) view.findViewById(R.id.pokemons_surnom);
        this.niveauView =
            (EditText) view.findViewById(R.id.pokemons_niveau);
        this.captureView =
                (DateTimeWidget) view.findViewById(R.id.pokemons_capture);
        this.typePokemonAdapter = 
                new SingleEntityWidget.EntityAdapter<TypeDePokemons>() {
            @Override
            public String entityToString(TypeDePokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.typePokemonWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokemons_typepokemon_button);
        this.typePokemonWidget.setAdapter(this.typePokemonAdapter);
        this.typePokemonWidget.setTitle(R.string.pokemons_typepokemon_dialog_title);
        this.attaque1Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaques>() {
            @Override
            public String entityToString(Attaques item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque1Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemons_attaque1_button);
        this.attaque1Widget.setAdapter(this.attaque1Adapter);
        this.attaque1Widget.setTitle(R.string.pokemons_attaque1_dialog_title);
        this.attaque2Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaques>() {
            @Override
            public String entityToString(Attaques item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque2Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemons_attaque2_button);
        this.attaque2Widget.setAdapter(this.attaque2Adapter);
        this.attaque2Widget.setTitle(R.string.pokemons_attaque2_dialog_title);
        this.attaque3Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaques>() {
            @Override
            public String entityToString(Attaques item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque3Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemons_attaque3_button);
        this.attaque3Widget.setAdapter(this.attaque3Adapter);
        this.attaque3Widget.setTitle(R.string.pokemons_attaque3_dialog_title);
        this.attaque4Adapter = 
                new SingleEntityWidget.EntityAdapter<Attaques>() {
            @Override
            public String entityToString(Attaques item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque4Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokemons_attaque4_button);
        this.attaque4Widget.setAdapter(this.attaque4Adapter);
        this.attaque4Widget.setTitle(R.string.pokemons_attaque4_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getSurnom() != null) {
            this.surnomView.setText(this.model.getSurnom());
        }
        this.niveauView.setText(String.valueOf(this.model.getNiveau()));
        if (this.model.getCapture() != null) {
            this.captureView.setDateTime(this.model.getCapture());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setSurnom(this.surnomView.getEditableText().toString());

        this.model.setNiveau(Integer.parseInt(
                    this.niveauView.getEditableText().toString()));

        this.model.setCapture(this.captureView.getDateTime());

        this.model.setTypePokemon(this.typePokemonAdapter.getSelectedItem());

        this.model.setAttaque1(this.attaque1Adapter.getSelectedItem());

        this.model.setAttaque2(this.attaque2Adapter.getSelectedItem());

        this.model.setAttaque3(this.attaque3Adapter.getSelectedItem());

        this.model.setAttaque4(this.attaque4Adapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.surnomView.getText().toString().trim())) {
            error = R.string.pokemons_surnom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.niveauView.getText().toString().trim())) {
            error = R.string.pokemons_niveau_invalid_field_error;
        }
        if (this.captureView.getDateTime() == null) {
            error = R.string.pokemons_capture_invalid_field_error;
        }
        if (this.typePokemonAdapter.getSelectedItem() == null) {
            error = R.string.pokemons_typepokemon_invalid_field_error;
        }
        if (this.attaque1Adapter.getSelectedItem() == null) {
            error = R.string.pokemons_attaque1_invalid_field_error;
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
                R.layout.fragment_pokemons_create,
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
        private final Pokemons entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokemonsCreateFragment fragment,
                final Pokemons entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokemons_progress_save_title),
                    this.ctx.getString(
                            R.string.pokemons_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokemonsProviderUtils(this.ctx).insert(
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
                                R.string.pokemons_error_create));
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
        private PokemonsCreateFragment fragment;
        /** typePokemon list. */
        private ArrayList<TypeDePokemons> typePokemonList;
        /** attaque1 list. */
        private ArrayList<Attaques> attaque1List;
        /** attaque2 list. */
        private ArrayList<Attaques> attaque2List;
        /** attaque3 list. */
        private ArrayList<Attaques> attaque3List;
        /** attaque4 list. */
        private ArrayList<Attaques> attaque4List;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokemonsCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokemons_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.pokemons_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typePokemonList = 
                new TypeDePokemonsProviderUtils(this.ctx).queryAll();
            this.attaque1List = 
                new AttaquesProviderUtils(this.ctx).queryAll();
            this.attaque2List = 
                new AttaquesProviderUtils(this.ctx).queryAll();
            this.attaque3List = 
                new AttaquesProviderUtils(this.ctx).queryAll();
            this.attaque4List = 
                new AttaquesProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.typePokemonAdapter.loadData(this.typePokemonList);
            this.fragment.attaque1Adapter.loadData(this.attaque1List);
            this.fragment.attaque2Adapter.loadData(this.attaque2List);
            this.fragment.attaque3Adapter.loadData(this.attaque3List);
            this.fragment.attaque4Adapter.loadData(this.attaque4List);
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
