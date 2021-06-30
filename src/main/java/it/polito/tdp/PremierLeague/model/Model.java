package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(int time, String m) {
		String mese = "";
		switch(m) {
		case "Gennaio":
			mese = "01";
			break;
		case "Febbraio":
			mese = "02";
			break;
		case "Marzo":
			mese = "03";
			break;
		case "Aprile":
			mese = "04";
			break;
		case "Maggio":
			mese = "05";
			break;
		case "Giugno":
			mese = "06";
			break;
		case "Luglio":
			mese = "07";
			break;
		case "Agosto":
			mese = "08";
			break;
		case "Settembre":
			mese = "09";
			break;
		case "Ottobre":
			mese = "10";
			break;
		case "Novembre":
			mese = "11";
			break;
		case "Dicembre":
			mese = "12";
			break;
		}
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Match> matches = dao.listAllMatches(mese);
		
		Graphs.addAllVertices(grafo, matches);
		
		
		for(Match m1 : grafo.vertexSet()) {
			for(Match m2 : grafo.vertexSet()) {
				if(!m1.equals(m2) && dao.listAdiacenze(m1, m2, time) != 0) {
					Graphs.addEdgeWithVertices(grafo, m1, m2, dao.listAdiacenze(m1, m2, time));
				}
			}
		}
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	public String getConnMax(int time){
	    String result = ""; 
	    int max = 0;
	    
	    if(grafo == null) {
	    	result="Grafo non creato!!";
	    	return result;
	    }
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
		
		if(grafo.getEdgeWeight(e) == max) {
		
		   result += grafo.getEdgeTarget(e).toString() + "-" + grafo.getEdgeSource(e).toString() + "(" + max + ")\n";	
		}
		if (grafo.getEdgeWeight(e) > max) {
		   max = (int) grafo.getEdgeWeight(e) ;
		   result = grafo.getEdgeTarget(e).toString() + "-" + grafo.getEdgeSource(e).toString() + "(" + max + ")\n";	
		}
		
	}
		return result;
	}
}
