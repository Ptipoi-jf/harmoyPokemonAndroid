/**************************************************************************
 * DresseursEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.dresseurs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.kerhomjarnoin.pokemon.R;
import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.entity.Npc;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;

import com.kerhomjarnoin.pokemon.harmony.widget.SingleEntityWidget;
import com.kerhomjarnoin.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;

import com.kerhomjarnoin.pokemon.provider.utils.DresseursProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.NpcProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;

/** Dresseurs create fragment.
 *
 * This fragment gives you an interface to edit a Dresseurs.
 *
 * @see android.app.Fragment
 */
public class DresseursEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Dresseurs model = new Dresseurs();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** The npc chooser component. */
    protected SingleEntityWidget npcWidget;
    /** The npc Adapter. */
    protected SingleEntityWidget.EntityAdapter<Npc>
            npcAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.dresseurs_nom);
        this.npcAdapter =
                new SingleEntityWidget.EntityAdapter<Npc>() {
            @Override
            public String entityToString(Npc item) {
                return String.valueOf(item.getId());
            }
        };
        this.npcWidget =
            (SingleEntityWidget) view.findViewById(R.id.dresseurs_npc_button);
        this.npcWidget.setAdapter(this.npcAdapter);
        this.npcWidget.setTitle(R.string.dresseurs_npc_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setNpc(this.npcAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.dresseurs_nom_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_dresseurs_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Dresseurs) intent.getParcelableExtra(
                DresseursContract.PARCEL);

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
        private final Dresseurs entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final DresseursEditFragment fragment,
                    final Dresseurs entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.dresseurs_progress_save_title),
                    this.ctx.getString(
                            R.string.dresseurs_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new DresseursProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("DresseursEditFragment", e.getMessage());
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
                        R.string.dresseurs_error_edit));
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
        private DresseursEditFragment fragment;
        /** npc list. */
        private ArrayList<Npc> npcList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final DresseursEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.dresseurs_progress_load_relations_title),
                this.ctx.getString(
                    R.string.dresseurs_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.npcList = 
                new NpcProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onNpcLoaded(this.npcList);

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
     * Called when npc have been loaded.
     * @param items The loaded items
     */
    protected void onNpcLoaded(ArrayList<Npc> items) {
        this.npcAdapter.loadData(items);
        
        if (this.model.getNpc() != null) {
            for (Npc item : items) {
                if (item.getId() == this.model.getNpc().getId()) {
                    this.npcAdapter.selectItem(item);
                }
            }
        }
    }
}
