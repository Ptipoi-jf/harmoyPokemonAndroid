package com.kerhomjarnoin.pokemon.entity;

import android.os.Parcel;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.OneToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;

@Entity
@Rest
public class Npc  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	@Id
	@Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.STRING)
	private String profession;
	
	@Column(type = Type.STRING)
	private String texte;
	
	@Column(nullable = true)
	@OneToMany()
	private ArrayList<Objets> objets;
	
	@Column(nullable = true)
	@OneToMany()
	private ArrayList<Pokemons> pokemons;
	
	@Column(nullable = true)
	@ManyToMany()
	private ArrayList<Badges> badges;
	
	@Column(nullable = true)
	@OneToOne()
	private Positions position;

    /**
     * Default constructor.
     */
    public Npc() {

    }

     /**
     * Get the Id.
     * @return the id
     */
    public int getId() {
         return this.id;
    }
     /**
     * Set the Id.
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }
     /**
     * Get the Nom.
     * @return the nom
     */
    public String getNom() {
         return this.nom;
    }
     /**
     * Set the Nom.
     * @param value the nom to set
     */
    public void setNom(final String value) {
         this.nom = value;
    }
     /**
     * Get the Profession.
     * @return the profession
     */
    public String getProfession() {
         return this.profession;
    }
     /**
     * Set the Profession.
     * @param value the profession to set
     */
    public void setProfession(final String value) {
         this.profession = value;
    }
     /**
     * Get the Texte.
     * @return the texte
     */
    public String getTexte() {
         return this.texte;
    }
     /**
     * Set the Texte.
     * @param value the texte to set
     */
    public void setTexte(final String value) {
         this.texte = value;
    }
     /**
     * Get the Objets.
     * @return the objets
     */
    public ArrayList<Objets> getObjets() {
         return this.objets;
    }
     /**
     * Set the Objets.
     * @param value the objets to set
     */
    public void setObjets(final ArrayList<Objets> value) {
         this.objets = value;
    }
     /**
     * Get the Pokemons.
     * @return the pokemons
     */
    public ArrayList<Pokemons> getPokemons() {
         return this.pokemons;
    }
     /**
     * Set the Pokemons.
     * @param value the pokemons to set
     */
    public void setPokemons(final ArrayList<Pokemons> value) {
         this.pokemons = value;
    }
     /**
     * Get the Badges.
     * @return the badges
     */
    public ArrayList<Badges> getBadges() {
         return this.badges;
    }
     /**
     * Set the Badges.
     * @param value the badges to set
     */
    public void setBadges(final ArrayList<Badges> value) {
         this.badges = value;
    }
     /**
     * Get the Position.
     * @return the position
     */
    public Positions getPosition() {
         return this.position;
    }
     /**
     * Set the Position.
     * @param value the position to set
     */
    public void setPosition(final Positions value) {
         this.position = value;
    }
    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getId());
        if (this.getNom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getNom());
        } else {
            dest.writeInt(0);
        }
        if (this.getProfession() != null) {
            dest.writeInt(1);
            dest.writeString(this.getProfession());
        } else {
            dest.writeInt(0);
        }
        if (this.getTexte() != null) {
            dest.writeInt(1);
            dest.writeString(this.getTexte());
        } else {
            dest.writeInt(0);
        }

        if (this.getObjets() != null) {
            dest.writeInt(this.getObjets().size());
            for (Objets item : this.getObjets()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getPokemons() != null) {
            dest.writeInt(this.getPokemons().size());
            for (Pokemons item : this.getPokemons()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getBadges() != null) {
            dest.writeInt(this.getBadges().size());
            for (Badges item : this.getBadges()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        if (this.getPosition() != null
                    && !this.parcelableParents.contains(this.getPosition())) {
            this.getPosition().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
    }

    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setId(parc.readInt());
        int nomBool = parc.readInt();
        if (nomBool == 1) {
            this.setNom(parc.readString());
        }
        int professionBool = parc.readInt();
        if (professionBool == 1) {
            this.setProfession(parc.readString());
        }
        int texteBool = parc.readInt();
        if (texteBool == 1) {
            this.setTexte(parc.readString());
        }

        int nbObjets = parc.readInt();
        if (nbObjets > -1) {
            ArrayList<Objets> items =
                new ArrayList<Objets>();
            for (int i = 0; i < nbObjets; i++) {
                items.add((Objets) parc.readParcelable(
                        Objets.class.getClassLoader()));
            }
            this.setObjets(items);
        }

        int nbPokemons = parc.readInt();
        if (nbPokemons > -1) {
            ArrayList<Pokemons> items =
                new ArrayList<Pokemons>();
            for (int i = 0; i < nbPokemons; i++) {
                items.add((Pokemons) parc.readParcelable(
                        Pokemons.class.getClassLoader()));
            }
            this.setPokemons(items);
        }

        int nbBadges = parc.readInt();
        if (nbBadges > -1) {
            ArrayList<Badges> items =
                new ArrayList<Badges>();
            for (int i = 0; i < nbBadges; i++) {
                items.add((Badges) parc.readParcelable(
                        Badges.class.getClassLoader()));
            }
            this.setBadges(items);
        }
        this.setPosition((Positions) parc.readParcelable(Positions.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Npc(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parent The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<Npc> CREATOR
        = new Parcelable.Creator<Npc>() {
        public Npc createFromParcel(Parcel in) {
            return new Npc(in);
        }
        
        public Npc[] newArray(int size) {
            return new Npc[size];
        }
    };

}
