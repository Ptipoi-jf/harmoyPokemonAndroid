/**************************************************************************
 * ArenesShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.arenes;


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
import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.harmony.view.DeleteDialog;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kerhomjarnoin.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kerhomjarnoin.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.ArenesProviderUtils;
import com.kerhomjarnoin.pokemon.provider.ArenesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;

/** Arenes show fragment.
 *
 * This fragment gives you an interface to show a Arenes.
 * 
 * @see android.app.Fragment
 */
public class ArenesShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Arenes model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** position View. */
    protected TextView positionView;
    /** badge View. */
    protected TextView badgeView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Arenes. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.arenes_nom);
        this.positionView =
            (TextView) view.findViewById(
                    R.id.arenes_position);
        this.badgeView =
            (TextView) view.findViewById(
                    R.id.arenes_badge);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.arenes_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.arenes_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getPosition() != null) {
            this.positionView.setText(
                    String.valueOf(this.model.getPosition().getId()));
        }
        if (this.model.getBadge() != null) {
            this.badgeView.setText(
                    String.valueOf(this.model.getBadge().getId()));
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
                        R.layout.fragment_arenes_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Arenes) intent.getParcelableExtra(ArenesContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Arenes to get the data from.
     */
    public void update(Arenes item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ArenesProviderAdapter.ARENES_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ArenesShowFragment.this.onArenesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/position"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ArenesShowFragment.this.onPositionLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badge"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ArenesShowFragment.this.onBadgeLoaded(c);
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
    public void onArenesLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ArenesContract.cursorToItem(
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
    public void onPositionLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPosition(PositionsContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPosition(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBadgeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setBadge(BadgesContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setBadge(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the ArenesEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ArenesEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ArenesContract.PARCEL, this.model);
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
        private Arenes item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ArenesSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Arenes item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ArenesProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ArenesShowFragment.this.onPostDelete();
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

