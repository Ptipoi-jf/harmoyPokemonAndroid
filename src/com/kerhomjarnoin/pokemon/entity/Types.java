package com.kerhomjarnoin.pokemon.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;

@Entity
public class Types {
	@Id
	@Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;

	@Column(type = Type.STRING)
	private String nom;
	
	@OneToMany()
	@Column(nullable = true)
	private ArrayList<Types> faibleContre;
	
	@OneToMany()
	@Column(nullable = true)
	private ArrayList<Types> fortContre;
}
