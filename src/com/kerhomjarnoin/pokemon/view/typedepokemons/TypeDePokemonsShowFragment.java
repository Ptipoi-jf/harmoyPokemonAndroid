/**************************************************************************
 * TypeDePokemonsShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.typedepokemons;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.R;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.harmony.view.DeleteDialog;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kerhomjarnoin.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kerhomjarnoin.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/** TypeDePokemons show fragment.
 *
 * This fragment gives you an interface to show a TypeDePokemons.
 * 
 * @see android.app.Fragment
 */
public class TypeDePokemonsShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected TypeDePokemons model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** attaque View. */
    protected TextView attaqueView;
    /** attaque_spe View. */
    protected TextView attaque_speView;
    /** defence View. */
    protected TextView defenceView;
    /** defence_spe View. */
    protected TextView defence_speView;
    /** vitesse View. */
    protected TextView vitesseView;
    /** pv View. */
    protected TextView pvView;
    /** types View. */
    protected TextView typesView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no TypeDePokemons. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_nom);
        this.attaqueView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_attaque);
        this.attaque_speView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_attaque_spe);
        this.defenceView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_defence);
        this.defence_speView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_defence_spe);
        this.vitesseView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_vitesse);
        this.pvView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_pv);
        this.typesView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_types);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.typedepokemons_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.typedepokemons_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.attaqueView.setText(String.valueOf(this.model.getAttaque()));
        this.attaque_speView.setText(String.valueOf(this.model.getAttaque_spe()));
        this.defenceView.setText(String.valueOf(this.model.getDefence()));
        this.defence_speView.setText(String.valueOf(this.model.getDefence_spe()));
        this.vitesseView.setText(String.valueOf(this.model.getVitesse()));
        this.pvView.setText(String.valueOf(this.model.getPv()));
        if (this.model.getTypes() != null) {
            String typesValue = "";
            for (Types item : this.model.getTypes()) {
                typesValue += item.getId() + ",";
            }
            this.typesView.setText(typesValue);
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_typedepokemons_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((TypeDePokemons) intent.getParcelableExtra(TypeDePokemonsContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The TypeDePokemons to get the data from.
     */
    public void update(TypeDePokemons item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypeDePokemonsShowFragment.this.onTypeDePokemonsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/types"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypeDePokemonsShowFragment.this.onTypesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onTypeDePokemonsLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            TypeDePokemonsContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onTypesLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setTypes(TypesContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setTypes(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the TypeDePokemonsEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    TypeDePokemonsEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(TypeDePokemonsContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private TypeDePokemons item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build TypeDePokemonsSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final TypeDePokemons item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new TypeDePokemonsProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                TypeDePokemonsShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

