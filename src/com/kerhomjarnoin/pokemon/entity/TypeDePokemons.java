package com.kerhomjarnoin.pokemon.entity;

import android.os.Parcel;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;

@Entity
@Rest
public class TypeDePokemons  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	@Id
	@Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.INTEGER)
	private int attaque;
	
	@Column(type = Type.INTEGER)
	private int attaque_spe;
	
	@Column(type = Type.INTEGER)
	private int defence;
	
	@Column(type = Type.INTEGER)
	private int defence_spe;
	
	@Column(type = Type.INTEGER)
	private int vitesse;
	
	@Column(type = Type.INTEGER)
	private int pv;

	@OneToMany()
	@Column()
	private ArrayList<Types> types;

    /**
     * Default constructor.
     */
    public TypeDePokemons() {

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
     * Get the Attaque.
     * @return the attaque
     */
    public int getAttaque() {
         return this.attaque;
    }
     /**
     * Set the Attaque.
     * @param value the attaque to set
     */
    public void setAttaque(final int value) {
         this.attaque = value;
    }
     /**
     * Get the Attaque_spe.
     * @return the attaque_spe
     */
    public int getAttaque_spe() {
         return this.attaque_spe;
    }
     /**
     * Set the Attaque_spe.
     * @param value the attaque_spe to set
     */
    public void setAttaque_spe(final int value) {
         this.attaque_spe = value;
    }
     /**
     * Get the Defence.
     * @return the defence
     */
    public int getDefence() {
         return this.defence;
    }
     /**
     * Set the Defence.
     * @param value the defence to set
     */
    public void setDefence(final int value) {
         this.defence = value;
    }
     /**
     * Get the Defence_spe.
     * @return the defence_spe
     */
    public int getDefence_spe() {
         return this.defence_spe;
    }
     /**
     * Set the Defence_spe.
     * @param value the defence_spe to set
     */
    public void setDefence_spe(final int value) {
         this.defence_spe = value;
    }
     /**
     * Get the Vitesse.
     * @return the vitesse
     */
    public int getVitesse() {
         return this.vitesse;
    }
     /**
     * Set the Vitesse.
     * @param value the vitesse to set
     */
    public void setVitesse(final int value) {
         this.vitesse = value;
    }
     /**
     * Get the Pv.
     * @return the pv
     */
    public int getPv() {
         return this.pv;
    }
     /**
     * Set the Pv.
     * @param value the pv to set
     */
    public void setPv(final int value) {
         this.pv = value;
    }
     /**
     * Get the Types.
     * @return the types
     */
    public ArrayList<Types> getTypes() {
         return this.types;
    }
     /**
     * Set the Types.
     * @param value the types to set
     */
    public void setTypes(final ArrayList<Types> value) {
         this.types = value;
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
        dest.writeInt(this.getAttaque());
        dest.writeInt(this.getAttaque_spe());
        dest.writeInt(this.getDefence());
        dest.writeInt(this.getDefence_spe());
        dest.writeInt(this.getVitesse());
        dest.writeInt(this.getPv());

        if (this.getTypes() != null) {
            dest.writeInt(this.getTypes().size());
            for (Types item : this.getTypes()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
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
        this.setAttaque(parc.readInt());
        this.setAttaque_spe(parc.readInt());
        this.setDefence(parc.readInt());
        this.setDefence_spe(parc.readInt());
        this.setVitesse(parc.readInt());
        this.setPv(parc.readInt());

        int nbTypes = parc.readInt();
        if (nbTypes > -1) {
            ArrayList<Types> items =
                new ArrayList<Types>();
            for (int i = 0; i < nbTypes; i++) {
                items.add((Types) parc.readParcelable(
                        Types.class.getClassLoader()));
            }
            this.setTypes(items);
        }
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public TypeDePokemons(Parcel parc) {
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
    public static final Parcelable.Creator<TypeDePokemons> CREATOR
        = new Parcelable.Creator<TypeDePokemons>() {
        public TypeDePokemons createFromParcel(Parcel in) {
            return new TypeDePokemons(in);
        }
        
        public TypeDePokemons[] newArray(int size) {
            return new TypeDePokemons[size];
        }
    };

}
