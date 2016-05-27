/**************************************************************************
 * DataManager.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.fixture;


import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import com.kerhomjarnoin.pokemon.data.base.SQLiteAdapterBase;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.data.ArenesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.TypeObjet;
import com.kerhomjarnoin.pokemon.data.DresseursSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.data.ZonesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Zones;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Pokemons;

/**
 * DataManager.
 * 
 * This class is an "orm-like" manager which simplifies insertion in database
 * with sqlite adapters.
 */
public class DataManager {
    /** HashMap to join Entity Name and its SQLiteAdapterBase. */
    protected Map<String, SQLiteAdapterBase<?>> adapters =
            new HashMap<String, SQLiteAdapterBase<?>>();
    /** is successfull. */
    protected boolean isSuccessfull = true;
    /** is in internal transaction. */
    protected boolean isInInternalTransaction = false;
    /** database. */
    protected SQLiteDatabase db;
    /** Types name constant. */
    private static final String TYPES = "Types";
    /** Arenes name constant. */
    private static final String ARENES = "Arenes";
    /** TypeDePokemons name constant. */
    private static final String TYPEDEPOKEMONS = "TypeDePokemons";
    /** Badges name constant. */
    private static final String BADGES = "Badges";
    /** TypeObjet name constant. */
    private static final String TYPEOBJET = "TypeObjet";
    /** Dresseurs name constant. */
    private static final String DRESSEURS = "Dresseurs";
    /** Objets name constant. */
    private static final String OBJETS = "Objets";
    /** Npc name constant. */
    private static final String NPC = "Npc";
    /** Attaques name constant. */
    private static final String ATTAQUES = "Attaques";
    /** Positions name constant. */
    private static final String POSITIONS = "Positions";
    /** Zones name constant. */
    private static final String ZONES = "Zones";
    /** Pokemons name constant. */
    private static final String POKEMONS = "Pokemons";
    /**
     * Constructor.
     * @param ctx The context
     * @param db The DB to work in
     */
    public DataManager(final android.content.Context ctx, final SQLiteDatabase db) {
        this.db = db;
        this.adapters.put(TYPES,
                new TypesSQLiteAdapter(ctx));
        this.adapters.get(TYPES).open(this.db);
        this.adapters.put(ARENES,
                new ArenesSQLiteAdapter(ctx));
        this.adapters.get(ARENES).open(this.db);
        this.adapters.put(TYPEDEPOKEMONS,
                new TypeDePokemonsSQLiteAdapter(ctx));
        this.adapters.get(TYPEDEPOKEMONS).open(this.db);
        this.adapters.put(BADGES,
                new BadgesSQLiteAdapter(ctx));
        this.adapters.get(BADGES).open(this.db);
        this.adapters.put(TYPEOBJET,
                new TypeObjetSQLiteAdapter(ctx));
        this.adapters.get(TYPEOBJET).open(this.db);
        this.adapters.put(DRESSEURS,
                new DresseursSQLiteAdapter(ctx));
        this.adapters.get(DRESSEURS).open(this.db);
        this.adapters.put(OBJETS,
                new ObjetsSQLiteAdapter(ctx));
        this.adapters.get(OBJETS).open(this.db);
        this.adapters.put(NPC,
                new NpcSQLiteAdapter(ctx));
        this.adapters.get(NPC).open(this.db);
        this.adapters.put(ATTAQUES,
                new AttaquesSQLiteAdapter(ctx));
        this.adapters.get(ATTAQUES).open(this.db);
        this.adapters.put(POSITIONS,
                new PositionsSQLiteAdapter(ctx));
        this.adapters.get(POSITIONS).open(this.db);
        this.adapters.put(ZONES,
                new ZonesSQLiteAdapter(ctx));
        this.adapters.get(ZONES).open(this.db);
        this.adapters.put(POKEMONS,
                new PokemonsSQLiteAdapter(ctx));
        this.adapters.get(POKEMONS).open(this.db);
    }

    /**
     * Tells the ObjectManager to make an instance managed and persistent.
     *
     * The object will be entered into the database as a result of the <br />
     * flush operation.
     *
     * NOTE: The persist operation always considers objects that are not<br />
     * yet known to this ObjectManager as NEW. Do not pass detached <br />
     * objects to the persist operation.
     *
     * @param object $object The instance to make managed and persistent.
     * @return Count of objects entered into the DB
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int persist(final Object object) {
        int result;

        this.beginTransaction();
        try {
            final SQLiteAdapterBase adapter = this.getRepository(object);

            result = (int) adapter.insert(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.isSuccessfull = false;
            result = 0;
        }

        return result;
    }

    /**
     * Removes an object instance.
     *
     * A removed object will be removed from the database as a result of <br />
     * the flush operation.
     *
     * @param object $object The object instance to remove.
     */
    public void remove(final Object object) {
        this.beginTransaction();
        try {
            if (object instanceof Types) {
                ((TypesSQLiteAdapter)
                        this.adapters.get(TYPES))
                            .remove(((Types) object).getId());
            }
            if (object instanceof Arenes) {
                ((ArenesSQLiteAdapter)
                        this.adapters.get(ARENES))
                            .remove(((Arenes) object).getId());
            }
            if (object instanceof TypeDePokemons) {
                ((TypeDePokemonsSQLiteAdapter)
                        this.adapters.get(TYPEDEPOKEMONS))
                            .remove(((TypeDePokemons) object).getId());
            }
            if (object instanceof Badges) {
                ((BadgesSQLiteAdapter)
                        this.adapters.get(BADGES))
                            .remove(((Badges) object).getId());
            }
            if (object instanceof TypeObjet) {
                ((TypeObjetSQLiteAdapter)
                        this.adapters.get(TYPEOBJET))
                            .remove(((TypeObjet) object).getId());
            }
            if (object instanceof Dresseurs) {
                ((DresseursSQLiteAdapter)
                        this.adapters.get(DRESSEURS))
                            .remove(((Dresseurs) object).getId());
            }
            if (object instanceof Objets) {
                ((ObjetsSQLiteAdapter)
                        this.adapters.get(OBJETS))
                            .remove(((Objets) object).getId());
            }
            if (object instanceof Npc) {
                ((NpcSQLiteAdapter)
                        this.adapters.get(NPC))
                            .remove(((Npc) object).getId());
            }
            if (object instanceof Attaques) {
                ((AttaquesSQLiteAdapter)
                        this.adapters.get(ATTAQUES))
                            .remove(((Attaques) object).getId());
            }
            if (object instanceof Positions) {
                ((PositionsSQLiteAdapter)
                        this.adapters.get(POSITIONS))
                            .remove(((Positions) object).getId());
            }
            if (object instanceof Zones) {
                ((ZonesSQLiteAdapter)
                        this.adapters.get(ZONES))
                            .remove(((Zones) object).getId());
            }
            if (object instanceof Pokemons) {
                ((PokemonsSQLiteAdapter)
                        this.adapters.get(POKEMONS))
                            .remove(((Pokemons) object).getId());
            }
        } catch (Exception ex) {
            this.isSuccessfull = false;
        }
    }

//    /**
//     * Merges the state of a detached object into the persistence context
//     * of this ObjectManager and returns the managed copy of the object.
//     * The object passed to merge will not become associated/managed with
//       * this ObjectManager.
//     *
//     * @param object $object
//     */
//    public void merge(Object object) {
//
//    }
//
//    /**
//     * Clears the ObjectManager. All objects that are currently managed
//     * by this ObjectManager become detached.
//     *
//     * @param objectName $objectName if given, only objects of this type will
//     * get detached
//     */
//    public void clear(String objectName) {
//
//    }
//
//    /**
//     * Detaches an object from the ObjectManager, causing a managed object to
//     * become detached. Unflushed changes made to the object if any
//     * (including removal of the object), will not be synchronized to the
//     * database.
//     * Objects which previously referenced the detached object will continue
//     * to reference it.
//     *
//     * @param object $object The object to detach.
//     */
//    public void detach(Object object) {
//
//    }
//
//    /**
//     * Refreshes the persistent state of an object from the database,
//     * overriding any local changes that have not yet been persisted.
//     *
//     * @param object $object The object to refresh.
//     */
//    public void refresh(Object object) {
//
//    }

    /**
     * Flushes all changes to objects that have been queued up to now to <br />
     * the database. This effectively synchronizes the in-memory state of<br />
     * managed objects with the database.
     */
    public void flush() {
        if (this.isInInternalTransaction) {
            if (this.isSuccessfull) {
                this.db.setTransactionSuccessful();
            }
            this.db.endTransaction();
            this.isInInternalTransaction = false;
        }
    }

    /**
     * Gets the repository for a class.
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    public SQLiteAdapterBase<?> getRepository(final String className) {
        return this.adapters.get(className);
    }


    /**
     * Gets the repository for a given object.
     *
     * @param o object
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    private SQLiteAdapterBase<?> getRepository(final Object o) {
        final String className = o.getClass().getSimpleName();

        return this.getRepository(className);
    }

//    /**
//     * Returns the ClassMetadata descriptor for a class.
//     *
//     * The class name must be the fully-qualified class name without a <br />
//     * leading backslash (as it is returned by get_class($obj)).
//     *
//     * @param className $className
//     * @return \Doctrine\Common\Persistence\Mapping\ClassMetadata
//     */
//    public ClassMetadata getClassMetadata(final String className) {
//        return null;
//    }

    /**
     * Check if the object is part of the current UnitOfWork and therefore
     * managed.
     *
     * @param object $object
     * @return bool
     */
    public boolean contains(final Object object) {
        return false;
    }

    /**
     * Called before any transaction to open the DB.
     */
    private void beginTransaction() {
        // If we are not already in a transaction, begin it
        if (!this.isInInternalTransaction) {
            this.db.beginTransaction();
            this.isSuccessfull = true;
            this.isInInternalTransaction = true;
        }
    }

}
