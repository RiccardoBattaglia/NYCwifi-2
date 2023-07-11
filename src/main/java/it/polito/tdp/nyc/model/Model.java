package it.polito.tdp.nyc.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.*;

import it.polito.tdp.nyc.db.NYCDao;



public class Model {
	
	
	private NYCDao dao;
	private Graph<Pair<String, LatLng>, DefaultWeightedEdge> grafo;
	private Map<Integer, Hotspot> hotspotMap;
	private List<Hotspot> hotspotList;
	
	public Model() {
		this.dao = new NYCDao();
		
		this.hotspotMap=new HashMap<>();
		this.hotspotList = this.dao.getAllHotspot();
		for(Hotspot i : hotspotList) {
			this.hotspotMap.put(i.getObjectId(), i);
		}
		
	}
	
	public List<String> getProvider(){
		
		List<String> result = new LinkedList<>();
		
		for(Hotspot i : hotspotList) {
			if(!result.contains(i.getProvider())) {
			result.add(i.getProvider());
			}
		}
		
		return result;
	}

	public void creaGrafo(String provider) {
//		// TODO Auto-generated method stub

	this.grafo = new SimpleWeightedGraph<Pair<String, LatLng>, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
	// Aggiunta VERTICI 
	
	List<Pair<String, LatLng>> vertici=new LinkedList<>();

	
	for(Hotspot i : hotspotList) {
		if(!vertici.contains(i.getProvider()) && i.getProvider().equals(provider)) {
		vertici.add(new Pair<>(i.getCity(),this.dao.trovaLatLngMedio(i.getCity())) );
		}
	}
	
	Graphs.addAllVertices(this.grafo, vertici);
	
	// Aggiunta ARCHI
	for (Pair<String, LatLng> v1 : vertici) {
		for (Pair<String, LatLng> v2 : vertici) {
			if(!v1.equals(v2)) {
		this.grafo.addEdge(v1,v2);
		this.grafo.setEdgeWeight(this.grafo.getEdge(v1, v2), LatLngTool.distance(v1.getSecond(), v2.getSecond(), LengthUnit.KILOMETER));
			}
	}
	}
	
	}

public int nVertici() {
	return this.grafo.vertexSet().size();
}

public int nArchi() {
	return this.grafo.edgeSet().size();
}

public List<String> getVertici(){
	
	
	List<String> vertici=new LinkedList<>();
	for(Pair<String, LatLng> i: this.grafo.vertexSet()) {
		vertici.add(i.getFirst());
	}
	
	Collections.sort(vertici);
	
	return vertici;
}

public List<Pair<String, Double>> getAdiacenti(String c){
	
	Pair<String, LatLng> city = new Pair<String, LatLng>(null, null);
	
	for(Pair<String, LatLng> i : this.grafo.vertexSet()) {
		if(i.getFirst().equals(c)) {
			city=i;
			break;
		}
	}
	
	List<Pair<String, Double>> adiacenti=new LinkedList<>();
	
	for(DefaultWeightedEdge i : this.grafo.edgesOf(city)) {
		if(this.grafo.outgoingEdgesOf(city).contains(i) && !this.grafo.getEdgeSource(i).getFirst().equals(city.getFirst())) {adiacenti.add(new Pair<>(this.grafo.getEdgeSource(i).getFirst(), this.grafo.getEdgeWeight(i)));}
		if(this.grafo.incomingEdgesOf(city).contains(i) && !this.grafo.getEdgeTarget(i).getFirst().equals(city.getFirst())) {adiacenti.add(new Pair<>(this.grafo.getEdgeTarget(i).getFirst(), this.grafo.getEdgeWeight(i)));}
	}
//	
//	adiacenti.addAll(this.grafo.outgoingEdgesOf(city))
//	for(Pair<String, LatLng> i: this.grafo.vertexSet()) {
//		adiacenti.add(i.getFirst());
//	}
//	
//	Collections.sort(vertici);
	
	System.out.println(adiacenti);
//	
	return adiacenti;
}
//
//public List<Set<Track>> getComponente() {
//	ConnectivityInspector<Track, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo) ;
//	return ci.connectedSets() ;
//}
//
//public Integer getNPlaylistFromTrack(Integer trackId){
//	return this.dao.getNPlaylistFromTrack(trackId);
//}
//	
	
}
