/**************************************************************************
 * TypesShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.types;


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
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.harmony.view.DeleteDialog;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kerhomjarnoin.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kerhomjarnoin.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.TypesProviderUtils;
import com.kerhomjarnoin.pokemon.provider.TypesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/** Types show fragment.
 *
 * This fragment gives you an interface to show a Types.
 * 
 * @see android.app.Fragment
 */
public class TypesShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Types model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** faibleContre View. */
    protected TextView faibleContreView;
    /** fortContre View. */
    protected TextView fortContreView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Types. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.types_nom);
        this.faibleContreView =
            (TextView) view.findViewById(
                    R.id.types_faiblecontre);
        this.fortContreView =
            (TextView) view.findViewById(
                    R.id.types_fortcontre);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.types_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.types_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getFaibleContre() != null) {
            String faibleContreValue = "";
            for (Types item : this.model.getFaibleContre()) {
                faibleContreValue += item.getId() + ",";
            }
            this.faibleContreView.setText(faibleContreValue);
        }
        if (this.model.getFortContre() != null) {
            String fortContreValue = "";
            for (Types item : this.model.getFortContre()) {
                fortContreValue += item.getId() + ",";
            }
            this.fortContreView.setText(fortContreValue);
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
                        R.layout.fragment_types_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Types) intent.getParcelableExtra(TypesContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Types to get the data from.
     */
    public void update(Types item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    TypesProviderAdapter.TYPES_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypesShowFragment.this.onTypesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/faiblecontre"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypesShowFragment.this.onFaibleContreLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/fortcontre"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypesShowFragment.this.onFortContreLoaded(c);
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
    public void onTypesLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            TypesContract.cursorToItem(
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
    public void onFaibleContreLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setFaibleContre(TypesContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setFaibleContre(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onFortContreLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setFortContre(TypesContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setFortContre(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the TypesEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    TypesEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(TypesContract.PARCEL, this.model);
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
        private Types item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build TypesSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Types item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new TypesProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                TypesShowFragment.this.onPostDelete();
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

