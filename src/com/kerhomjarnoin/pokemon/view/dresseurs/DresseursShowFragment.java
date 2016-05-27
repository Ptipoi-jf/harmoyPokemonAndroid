/**************************************************************************
 * DresseursShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.dresseurs;


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
import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.harmony.view.DeleteDialog;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kerhomjarnoin.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kerhomjarnoin.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.DresseursProviderUtils;
import com.kerhomjarnoin.pokemon.provider.DresseursProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;

/** Dresseurs show fragment.
 *
 * This fragment gives you an interface to show a Dresseurs.
 * 
 * @see android.app.Fragment
 */
public class DresseursShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Dresseurs model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** npc View. */
    protected TextView npcView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Dresseurs. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.dresseurs_nom);
        this.npcView =
            (TextView) view.findViewById(
                    R.id.dresseurs_npc);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.dresseurs_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.dresseurs_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getNpc() != null) {
            this.npcView.setText(
                    String.valueOf(this.model.getNpc().getId()));
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
                        R.layout.fragment_dresseurs_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Dresseurs) intent.getParcelableExtra(DresseursContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Dresseurs to get the data from.
     */
    public void update(Dresseurs item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    DresseursProviderAdapter.DRESSEURS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    DresseursShowFragment.this.onDresseursLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/npc"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    DresseursShowFragment.this.onNpcLoaded(c);
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
    public void onDresseursLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            DresseursContract.cursorToItem(
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
    public void onNpcLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setNpc(NpcContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setNpc(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the DresseursEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    DresseursEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(DresseursContract.PARCEL, this.model);
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
        private Dresseurs item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build DresseursSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Dresseurs item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new DresseursProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                DresseursShowFragment.this.onPostDelete();
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

