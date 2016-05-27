/**************************************************************************
 * TypesEditFragment.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Types;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.widget.MultiEntityWidget;
import com.kerhomjarnoin.pokemon.harmony.widget.SingleEntityWidget;
import com.kerhomjarnoin.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kerhomjarnoin.pokemon.provider.TypesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.TypesProviderUtils;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/** Types create fragment.
 *
 * This fragment gives you an interface to edit a Types.
 *
 * @see android.app.Fragment
 */
public class TypesEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Types model = new Types();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.types_nom);
        this.faibleContreAdapter =
                new MultiEntityWidget.EntityAdapter<Types>() {
            @Override
            public String entityToString(Types item) {
                return String.valueOf(item.getId());
            }
        };
        this.faibleContreWidget = (MultiEntityWidget) view.findViewById(
                        R.id.types_faiblecontre_button);
        this.faibleContreWidget.setAdapter(this.faibleContreAdapter);
        this.faibleContreWidget.setTitle(R.string.types_faiblecontre_dialog_title);
        this.fortContreAdapter =
                new MultiEntityWidget.EntityAdapter<Types>() {
            @Override
            public String entityToString(Types item) {
                return String.valueOf(item.getId());
            }
        };
        this.fortContreWidget = (MultiEntityWidget) view.findViewById(
                        R.id.types_fortcontre_button);
        this.fortContreWidget.setAdapter(this.fortContreAdapter);
        this.fortContreWidget.setTitle(R.string.types_fortcontre_dialog_title);
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
        final View view =
                inflater.inflate(R.layout.fragment_types_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Types) intent.getParcelableExtra(
                TypesContract.PARCEL);

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
        private final Types entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypesEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypesProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypesEditFragment", e.getMessage());
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
                        R.string.types_error_edit));
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
        private TypesEditFragment fragment;
        /** faibleContre list. */
        private ArrayList<Types> faibleContreList;
    /** faibleContre list. */
        private ArrayList<Types> associatedFaibleContreList;
        /** fortContre list. */
        private ArrayList<Types> fortContreList;
    /** fortContre list. */
        private ArrayList<Types> associatedFortContreList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypesEditFragment fragment) {
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
            Uri faibleContreUri = TypesProviderAdapter.TYPES_URI;
            faibleContreUri = Uri.withAppendedPath(faibleContreUri, 
                                    String.valueOf(this.fragment.model.getId()));
            faibleContreUri = Uri.withAppendedPath(faibleContreUri, "faibleContre");
            android.database.Cursor faibleContreCursor = 
                    this.ctx.getContentResolver().query(
                            faibleContreUri,
                            new String[]{TypesContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedFaibleContreList = new ArrayList<Types>();
            if (faibleContreCursor != null && faibleContreCursor.getCount() > 0) {
                while (faibleContreCursor.moveToNext()) {
                    int faibleContreId = faibleContreCursor.getInt(
                            faibleContreCursor.getColumnIndex(TypesContract.COL_ID));
                    for (Types faibleContre : this.faibleContreList) {
                        if (faibleContre.getId() ==  faibleContreId) {
                            this.associatedFaibleContreList.add(faibleContre);
                        }
                    }
                }
                faibleContreCursor.close();
            }
            this.fortContreList = 
                new TypesProviderUtils(this.ctx).queryAll();
            Uri fortContreUri = TypesProviderAdapter.TYPES_URI;
            fortContreUri = Uri.withAppendedPath(fortContreUri, 
                                    String.valueOf(this.fragment.model.getId()));
            fortContreUri = Uri.withAppendedPath(fortContreUri, "fortContre");
            android.database.Cursor fortContreCursor = 
                    this.ctx.getContentResolver().query(
                            fortContreUri,
                            new String[]{TypesContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedFortContreList = new ArrayList<Types>();
            if (fortContreCursor != null && fortContreCursor.getCount() > 0) {
                while (fortContreCursor.moveToNext()) {
                    int fortContreId = fortContreCursor.getInt(
                            fortContreCursor.getColumnIndex(TypesContract.COL_ID));
                    for (Types fortContre : this.fortContreList) {
                        if (fortContre.getId() ==  fortContreId) {
                            this.associatedFortContreList.add(fortContre);
                        }
                    }
                }
                fortContreCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setFaibleContre(this.associatedFaibleContreList);
            this.fragment.onFaibleContreLoaded(this.faibleContreList);
            this.fragment.model.setFortContre(this.associatedFortContreList);
            this.fragment.onFortContreLoaded(this.fortContreList);

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
     * Called when faibleContre have been loaded.
     * @param items The loaded items
     */
    protected void onFaibleContreLoaded(ArrayList<Types> items) {
        this.faibleContreAdapter.loadData(items);
        this.faibleContreAdapter.setCheckedItems(this.model.getFaibleContre());
    }
    /**
     * Called when fortContre have been loaded.
     * @param items The loaded items
     */
    protected void onFortContreLoaded(ArrayList<Types> items) {
        this.fortContreAdapter.loadData(items);
        this.fortContreAdapter.setCheckedItems(this.model.getFortContre());
    }
}
