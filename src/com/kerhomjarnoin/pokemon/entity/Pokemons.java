package com.kerhomjarnoin.pokemon.entity;

import org.joda.time.format.ISODateTimeFormat;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class Pokemons {
	@Id
	@Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String surnom;
	
	@Column(type = Type.INTEGER)
	private int niveau;
	
	@Column(type = Type.DATETIME, locale = true)
	private DateTime capture;
	
	@OneToOne()
	@Column()
	private TypeDePokemons typePokemon;

	@OneToOne
	@Column()
	private Attaques attaque1;
	
	@OneToOne
	@Column(nullable = true)
	private Attaques attaque2;
	
	@OneToOne
	@Column(nullable = true)
	private Attaques attaque3;
	
	@OneToOne
	@Column(nullable = true)
	private Attaques attaque4;
}
