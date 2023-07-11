package it.polito.tdp.nyc.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class City implements Comparable<City>{
	
	String nome;
	LatLng posizione;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LatLng getPosizione() {
		return posizione;
	}
	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}
	@Override
	public int hashCode() {
		return Objects.hash(nome, posizione);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(posizione, other.posizione);
	}
	@Override
	public String toString() {
		return "City [nome=" + nome + ", posizione=" + posizione + "]";
	}
	public City(String nome, LatLng posizione) {
		super();
		this.nome = nome;
		this.posizione = posizione;
	}
	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		return (int) LatLngTool.distance(posizione, o.getPosizione(), LengthUnit.KILOMETER);
	}
	
	

}
