
/**************************************************************************
 * PokemonSQLiteOpenHelperBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.data.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.kerhomjarnoin.pokemon.data.PokemonSQLiteOpenHelper;
import com.kerhomjarnoin.pokemon.data.NpctoBadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.data.ArenesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;
import com.kerhomjarnoin.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;
import com.kerhomjarnoin.pokemon.data.DresseursSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.data.ZonesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ZonesContract;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.PokemonApplication;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


import com.kerhomjarnoin.pokemon.fixture.DataLoader;


/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class PokemonSQLiteOpenHelperBase extends SQLiteOpenHelper {
    /** TAG for debug purpose. */
    protected static final String TAG = "DatabaseHelper";
    /** Context. */
    protected android.content.Context ctx;

    /** Android's default system path of the database. */
    private static String DB_PATH;
    /** database name. */
    private static String DB_NAME;
    /** is assets exist.*/
    private static boolean assetsExist;
    /** Are we in a JUnit context ?*/
    public static boolean isJUnit = false;

    /**
     * Constructor.
     * @param ctx Context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public PokemonSQLiteOpenHelperBase(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
        this.ctx = ctx;
        DB_NAME = name;
        DB_PATH = ctx.getDatabasePath(DB_NAME).getAbsolutePath();

        try {
            this.ctx.getAssets().open(DB_NAME);
            assetsExist = true;
        } catch (IOException e) {
            assetsExist = false;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Activation of SQLiteConstraints
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Create database..");

        if (!assetsExist) {
            /// Create Schema

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : NpctoBadges");
            }
            db.execSQL(NpctoBadgesSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Types");
            }
            db.execSQL(TypesSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Arenes");
            }
            db.execSQL(ArenesSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeDePokemons");
            }
            db.execSQL(TypeDePokemonsSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Badges");
            }
            db.execSQL(BadgesSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeObjet");
            }
            db.execSQL(TypeObjetSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Dresseurs");
            }
            db.execSQL(DresseursSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Objets");
            }
            db.execSQL(ObjetsSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Npc");
            }
            db.execSQL(NpcSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Attaques");
            }
            db.execSQL(AttaquesSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Positions");
            }
            db.execSQL(PositionsSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Zones");
            }
            db.execSQL(ZonesSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Pokemons");
            }
            db.execSQL(PokemonsSQLiteAdapter.getSchema());
            db.execSQL("PRAGMA foreign_keys = ON;");
            if (!PokemonSQLiteOpenHelper.isJUnit) {
                this.loadData(db);
            }
        }

    }

    /**
     * Clear the database given in parameters.
     * @param db The database to clear
     */
    public static void clearDatabase(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Clearing database...");

        db.delete(NpctoBadgesContract.TABLE_NAME,
                null,
                null);
        db.delete(TypesContract.TABLE_NAME,
                null,
                null);
        db.delete(ArenesContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeDePokemonsContract.TABLE_NAME,
                null,
                null);
        db.delete(BadgesContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeObjetContract.TABLE_NAME,
                null,
                null);
        db.delete(DresseursContract.TABLE_NAME,
                null,
                null);
        db.delete(ObjetsContract.TABLE_NAME,
                null,
                null);
        db.delete(NpcContract.TABLE_NAME,
                null,
                null);
        db.delete(AttaquesContract.TABLE_NAME,
                null,
                null);
        db.delete(PositionsContract.TABLE_NAME,
                null,
                null);
        db.delete(ZonesContract.TABLE_NAME,
                null,
                null);
        db.delete(PokemonsContract.TABLE_NAME,
                null,
                null);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        android.util.Log.i(TAG, "Update database..");

        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Upgrading database from version " + oldVersion
                       + " to " + newVersion);
        }

        // TODO : Upgrade your tables !
    }

    /**
     * Loads data from the fixture files.
     * @param db The database to populate with fixtures
     */
    private void loadData(final SQLiteDatabase db) {
        final DataLoader dataLoader = new DataLoader(this.ctx);
        dataLoader.clean();
        int mode = DataLoader.MODE_APP;
        if (PokemonApplication.DEBUG) {
            mode = DataLoader.MODE_APP | DataLoader.MODE_DEBUG;
        }
        dataLoader.loadData(db, mode);
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * @throws IOException if error has occured while copying files
     */
    public void createDataBase() throws IOException {
        if (assetsExist && !checkDataBase()) {
            // By calling this method and empty database will be created into
            // the default system path
            // so we're gonna be able to overwrite that database with ours
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        boolean result;

        SQLiteDatabase checkDB = null;
        try {
            final String myPath = DB_PATH + DB_NAME;
            // NOTE : the system throw error message : "Database is locked"
            // when the Database is not found (incorrect path)
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            result = true;
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            result = false;
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return result;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * @throws IOException if error has occured while copying files
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        final InputStream myInput = this.ctx.getAssets().open(DB_NAME);

        // Path to the just created empty db
        final String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        final OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        final byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
