/**************************************************************************
 * PokemonsShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.pokemons;


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
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.harmony.util.DateUtils;
import com.kerhomjarnoin.pokemon.harmony.view.DeleteDialog;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kerhomjarnoin.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kerhomjarnoin.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.PokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.PokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;

/** Pokemons show fragment.
 *
 * This fragment gives you an interface to show a Pokemons.
 * 
 * @see android.app.Fragment
 */
public class PokemonsShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Pokemons model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** surnom View. */
    protected TextView surnomView;
    /** niveau View. */
    protected TextView niveauView;
    /** capture View. */
    protected TextView captureView;
    /** typePokemon View. */
    protected TextView typePokemonView;
    /** attaque1 View. */
    protected TextView attaque1View;
    /** attaque2 View. */
    protected TextView attaque2View;
    /** attaque3 View. */
    protected TextView attaque3View;
    /** attaque4 View. */
    protected TextView attaque4View;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Pokemons. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.surnomView =
            (TextView) view.findViewById(
                    R.id.pokemons_surnom);
        this.niveauView =
            (TextView) view.findViewById(
                    R.id.pokemons_niveau);
        this.captureView =
            (TextView) view.findViewById(
                    R.id.pokemons_capture);
        this.typePokemonView =
            (TextView) view.findViewById(
                    R.id.pokemons_typepokemon);
        this.attaque1View =
            (TextView) view.findViewById(
                    R.id.pokemons_attaque1);
        this.attaque2View =
            (TextView) view.findViewById(
                    R.id.pokemons_attaque2);
        this.attaque3View =
            (TextView) view.findViewById(
                    R.id.pokemons_attaque3);
        this.attaque4View =
            (TextView) view.findViewById(
                    R.id.pokemons_attaque4);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokemons_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokemons_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getSurnom() != null) {
            this.surnomView.setText(this.model.getSurnom());
        }
        this.niveauView.setText(String.valueOf(this.model.getNiveau()));
        if (this.model.getCapture() != null) {
            this.captureView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getCapture()));
        }
        if (this.model.getTypePokemon() != null) {
            this.typePokemonView.setText(
                    String.valueOf(this.model.getTypePokemon().getId()));
        }
        if (this.model.getAttaque1() != null) {
            this.attaque1View.setText(
                    String.valueOf(this.model.getAttaque1().getId()));
        }
        if (this.model.getAttaque2() != null) {
            this.attaque2View.setText(
                    String.valueOf(this.model.getAttaque2().getId()));
        }
        if (this.model.getAttaque3() != null) {
            this.attaque3View.setText(
                    String.valueOf(this.model.getAttaque3().getId()));
        }
        if (this.model.getAttaque4() != null) {
            this.attaque4View.setText(
                    String.valueOf(this.model.getAttaque4().getId()));
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
                        R.layout.fragment_pokemons_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Pokemons) intent.getParcelableExtra(PokemonsContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Pokemons to get the data from.
     */
    public void update(Pokemons item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokemonsProviderAdapter.POKEMONS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonsShowFragment.this.onPokemonsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/typepokemon"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonsShowFragment.this.onTypePokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonsShowFragment.this.onAttaque1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque2"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonsShowFragment.this.onAttaque2Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque3"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonsShowFragment.this.onAttaque3Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque4"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokemonsShowFragment.this.onAttaque4Loaded(c);
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
    public void onPokemonsLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokemonsContract.cursorToItem(
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
    public void onTypePokemonLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setTypePokemon(TypeDePokemonsContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setTypePokemon(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque1(AttaquesContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque2Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque2(AttaquesContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque2(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque3Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque3(AttaquesContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque3(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque4Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque4(AttaquesContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque4(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokemonsEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokemonsEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokemonsContract.PARCEL, this.model);
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
        private Pokemons item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokemonsSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Pokemons item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokemonsProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokemonsShowFragment.this.onPostDelete();
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

