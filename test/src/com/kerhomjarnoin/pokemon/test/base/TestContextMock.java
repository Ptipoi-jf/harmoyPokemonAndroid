/**************************************************************************
 * TestContextMock.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.test.base;

import java.io.File;

import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.PokemonApplication;
import com.kerhomjarnoin.pokemon.fixture.DataLoader;
import com.kerhomjarnoin.pokemon.harmony.util.DatabaseUtil;
import com.kerhomjarnoin.pokemon.data.PokemonSQLiteOpenHelper;
import com.kerhomjarnoin.pokemon.data.base.SQLiteAdapterBase;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;


/** android.content.Context mock for tests.<br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public class TestContextMock {
    private final static String CONTEXT_PREFIX = "test.";
    private final static String PROVIDER_AUTHORITY =
                    "com.kerhomjarnoin.pokemon.provider";
    private final static Class<? extends ContentProvider> PROVIDER_CLASS =
                    PokemonProvider.class;

    private static android.content.Context context = null;
    private AndroidTestCase androidTestCase;
    private android.content.Context baseContext;

    public TestContextMock(AndroidTestCase androidTestCase) {
        this.androidTestCase = androidTestCase;
    }

    /**
     * Get the original context
     * @return unmocked android.content.Context
     */
    protected android.content.Context getBaseContext() {
        return this.baseContext;
    }

    /**
     * Get the mock for ContentResolver
     * @return MockContentResolver
     */
    protected MockContentResolver getMockContentResolver() {
        return new MockContentResolver();
    }

    /**
     * Get the mock for android.content.Context
     * @return MockContext
     */
    protected android.content.Context getMockContext() {
            return this.androidTestCase.getContext();
    }

    /**
     * Initialize the mock android.content.Context
     * @throws Exception
     */
    protected void setMockContext() throws Exception {
        if (this.baseContext == null) {
            this.baseContext = this.androidTestCase.getContext();
        }

        if (context == null) {
            ContentProvider provider = PROVIDER_CLASS.newInstance();
            MockContentResolver resolver = this.getMockContentResolver();

            RenamingDelegatingContext targetContextWrapper
                = new RenamingDelegatingContext(
                    // The context that most methods are delegated to:
                    this.getMockContext(),
                    // The context that file methods are delegated to:
                    this.baseContext,
                    // Prefix database
                    CONTEXT_PREFIX);

            context = new TestContextIsolatedBase(
                    resolver,
                    targetContextWrapper);

            PackageManager packageManager = this.baseContext.getPackageManager();
            ProviderInfo providerInfo = packageManager.resolveContentProvider(
                    PokemonProvider.class.getPackage().getName(), 0);

            provider.attachInfo(context, providerInfo);

            resolver.addProvider(PROVIDER_AUTHORITY, provider);

            // Call INITIALIZE_DATABASE to create adapters and database (if needed)
            provider.call("INITIALIZE_DATABASE", null, null);
        }

        this.androidTestCase.setContext(context);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        PokemonSQLiteOpenHelper.isJUnit = true;
        this.setMockContext();

        String dbPath =
                this.androidTestCase.getContext()
                        .getDatabasePath(SQLiteAdapterBase.DB_NAME)
                        .getAbsolutePath() + ".test";

        File cacheDbFile = new File(dbPath);

        if (!cacheDbFile.exists() || !DataLoader.hasFixturesBeenLoaded) {
            if (PokemonApplication.DEBUG) {
                android.util.Log.d("TEST", "Create new Database cache");
            }

            // Create initial database
            PokemonSQLiteOpenHelper helper =
                    new PokemonSQLiteOpenHelper(
                        this.getMockContext(),
                        SQLiteAdapterBase.DB_NAME,
                        null,
                        PokemonApplication.getVersionCode(
                                this.getMockContext()));

            SQLiteDatabase db = helper.getWritableDatabase();
            PokemonSQLiteOpenHelper.clearDatabase(db);

            db.beginTransaction();
            DataLoader dataLoader = new DataLoader(this.getMockContext());
            dataLoader.clean();
            dataLoader.loadData(db,
                        DataLoader.MODE_APP |
                        DataLoader.MODE_DEBUG |
                        DataLoader.MODE_TEST);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

            DatabaseUtil.exportDB(this.getMockContext(),
                    cacheDbFile,
                    SQLiteAdapterBase.DB_NAME);
        } else {
            if (PokemonApplication.DEBUG) {
                android.util.Log.d("TEST", "Re use old Database cache");
            }
            DatabaseUtil.importDB(this.getMockContext(),
                    cacheDbFile,
                    SQLiteAdapterBase.DB_NAME,
                    false);
        }
    }
}
