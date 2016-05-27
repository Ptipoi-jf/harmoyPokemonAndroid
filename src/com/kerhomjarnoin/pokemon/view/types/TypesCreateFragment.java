/**************************************************************************
 * TypesCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.types;

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
import com.kerhomjarnoin.pokemon.entity.Types;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.widget.MultiEntityWidget;
import com.kerhomjarnoin.pokemon.harmony.widget.SingleEntityWidget;
import com.kerhomjarnoin.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.TypesProviderUtils;

/**
 * Types create fragment.
 *
 * This fragment gives you an interface to create a Types.
 */
public class TypesCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Types model = new Types();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** The faibleContre chooser component. */
    protected MultiEntityWidget faibleContreWidget;
    /** The faibleContre Adapter. */
    protected MultiEntityWidget.EntityAdapter<Types> 
                faibleContreAdapter;
    /** The fortContre chooser component. */
    protected MultiEntityWidget fortContreWidget;
    /** The fortContre Adapter. */
    protected MultiEntityWidget.EntityAdapter<Types> 
                fortContreAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.types_nom);
        this.faibleContreAdapter = 
                new MultiEntityWidget.EntityAdapter<Types>() {
            @Override
            public String entityToString(Types item) {
                return String.valueOf(item.getId());
            }
        };
        this.faibleContreWidget =
            (MultiEntityWidget) view.findViewById(R.id.types_faiblecontre_button);
        this.faibleContreWidget.setAdapter(this.faibleContreAdapter);
        this.faibleContreWidget.setTitle(R.string.types_faiblecontre_dialog_title);
        this.fortContreAdapter = 
                new MultiEntityWidget.EntityAdapter<Types>() {
            @Override
            public String entityToString(Types item) {
                return String.valueOf(item.getId());
            }
        };
        this.fortContreWidget =
            (MultiEntityWidget) view.findViewById(R.id.types_fortcontre_button);
        this.fortContreWidget.setAdapter(this.fortContreAdapter);
        this.fortContreWidget.setTitle(R.string.types_fortcontre_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setFaibleContre(this.faibleContreAdapter.getCheckedItems());

        this.model.setFortContre(this.fortContreAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.types_nom_invalid_field_error;
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
                R.layout.fragment_types_create,
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
        private final Types entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final TypesCreateFragment fragment,
                final Types entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.types_progress_save_title),
                    this.ctx.getString(
                            R.string.types_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new TypesProviderUtils(this.ctx).insert(
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
                                R.string.types_error_create));
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
        private TypesCreateFragment fragment;
        /** faibleContre list. */
        private ArrayList<Types> faibleContreList;
        /** fortContre list. */
        private ArrayList<Types> fortContreList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypesCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.types_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.types_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.faibleContreList = 
                new TypesProviderUtils(this.ctx).queryAll();
            this.fortContreList = 
                new TypesProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.faibleContreAdapter.loadData(this.faibleContreList);
            this.fragment.fortContreAdapter.loadData(this.fortContreList);
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
