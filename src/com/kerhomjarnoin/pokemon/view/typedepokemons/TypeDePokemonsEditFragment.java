/**************************************************************************
 * TypeDePokemonsEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.typedepokemons;

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
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Types;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.widget.MultiEntityWidget;
import com.kerhomjarnoin.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kerhomjarnoin.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.utils.TypesProviderUtils;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/** TypeDePokemons create fragment.
 *
 * This fragment gives you an interface to edit a TypeDePokemons.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonsEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected TypeDePokemons model = new TypeDePokemons();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** attaque View. */
    protected EditText attaqueView;
    /** attaque_spe View. */
    protected EditText attaque_speView;
    /** defence View. */
    protected EditText defenceView;
    /** defence_spe View. */
    protected EditText defence_speView;
    /** vitesse View. */
    protected EditText vitesseView;
    /** pv View. */
    protected EditText pvView;
    /** The types chooser component. */
    protected MultiEntityWidget typesWidget;
    /** The types Adapter. */
    protected MultiEntityWidget.EntityAdapter<Types>
            typesAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.typedepokemons_nom);
        this.attaqueView = (EditText) view.findViewById(
                R.id.typedepokemons_attaque);
        this.attaque_speView = (EditText) view.findViewById(
                R.id.typedepokemons_attaque_spe);
        this.defenceView = (EditText) view.findViewById(
                R.id.typedepokemons_defence);
        this.defence_speView = (EditText) view.findViewById(
                R.id.typedepokemons_defence_spe);
        this.vitesseView = (EditText) view.findViewById(
                R.id.typedepokemons_vitesse);
        this.pvView = (EditText) view.findViewById(
                R.id.typedepokemons_pv);
        this.typesAdapter =
                new MultiEntityWidget.EntityAdapter<Types>() {
            @Override
            public String entityToString(Types item) {
                return String.valueOf(item.getId());
            }
        };
        this.typesWidget = (MultiEntityWidget) view.findViewById(
                        R.id.typedepokemons_types_button);
        this.typesWidget.setAdapter(this.typesAdapter);
        this.typesWidget.setTitle(R.string.typedepokemons_types_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.attaqueView.setText(String.valueOf(this.model.getAttaque()));
        this.attaque_speView.setText(String.valueOf(this.model.getAttaque_spe()));
        this.defenceView.setText(String.valueOf(this.model.getDefence()));
        this.defence_speView.setText(String.valueOf(this.model.getDefence_spe()));
        this.vitesseView.setText(String.valueOf(this.model.getVitesse()));
        this.pvView.setText(String.valueOf(this.model.getPv()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setAttaque(Integer.parseInt(
                    this.attaqueView.getEditableText().toString()));

        this.model.setAttaque_spe(Integer.parseInt(
                    this.attaque_speView.getEditableText().toString()));

        this.model.setDefence(Integer.parseInt(
                    this.defenceView.getEditableText().toString()));

        this.model.setDefence_spe(Integer.parseInt(
                    this.defence_speView.getEditableText().toString()));

        this.model.setVitesse(Integer.parseInt(
                    this.vitesseView.getEditableText().toString()));

        this.model.setPv(Integer.parseInt(
                    this.pvView.getEditableText().toString()));

        this.model.setTypes(this.typesAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.typedepokemons_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaqueView.getText().toString().trim())) {
            error = R.string.typedepokemons_attaque_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaque_speView.getText().toString().trim())) {
            error = R.string.typedepokemons_attaque_spe_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defenceView.getText().toString().trim())) {
            error = R.string.typedepokemons_defence_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defence_speView.getText().toString().trim())) {
            error = R.string.typedepokemons_defence_spe_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.vitesseView.getText().toString().trim())) {
            error = R.string.typedepokemons_vitesse_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.pvView.getText().toString().trim())) {
            error = R.string.typedepokemons_pv_invalid_field_error;
        }
        if (this.typesAdapter.getCheckedItems().isEmpty()) {
            error = R.string.typedepokemons_types_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_typedepokemons_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (TypeDePokemons) intent.getParcelableExtra(
                TypeDePokemonsContract.PARCEL);

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
        private final TypeDePokemons entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypeDePokemonsEditFragment fragment,
                    final TypeDePokemons entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.typedepokemons_progress_save_title),
                    this.ctx.getString(
                            R.string.typedepokemons_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypeDePokemonsProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypeDePokemonsEditFragment", e.getMessage());
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
                        R.string.typedepokemons_error_edit));
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
        private TypeDePokemonsEditFragment fragment;
        /** types list. */
        private ArrayList<Types> typesList;
    /** types list. */
        private ArrayList<Types> associatedTypesList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypeDePokemonsEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.typedepokemons_progress_load_relations_title),
                this.ctx.getString(
                    R.string.typedepokemons_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typesList = 
                new TypesProviderUtils(this.ctx).queryAll();
            Uri typesUri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
            typesUri = Uri.withAppendedPath(typesUri, 
                                    String.valueOf(this.fragment.model.getId()));
            typesUri = Uri.withAppendedPath(typesUri, "types");
            android.database.Cursor typesCursor = 
                    this.ctx.getContentResolver().query(
                            typesUri,
                            new String[]{TypesContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedTypesList = new ArrayList<Types>();
            if (typesCursor != null && typesCursor.getCount() > 0) {
                while (typesCursor.moveToNext()) {
                    int typesId = typesCursor.getInt(
                            typesCursor.getColumnIndex(TypesContract.COL_ID));
                    for (Types types : this.typesList) {
                        if (types.getId() ==  typesId) {
                            this.associatedTypesList.add(types);
                        }
                    }
                }
                typesCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setTypes(this.associatedTypesList);
            this.fragment.onTypesLoaded(this.typesList);

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
     * Called when types have been loaded.
     * @param items The loaded items
     */
    protected void onTypesLoaded(ArrayList<Types> items) {
        this.typesAdapter.loadData(items);
        this.typesAdapter.setCheckedItems(this.model.getTypes());
    }
}
