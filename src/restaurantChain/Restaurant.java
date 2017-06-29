package restaurantChain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Restaurant {
	private String nome;
	private int tavoli;
	
	private Map<String, Menu> carta = new HashMap<>();
	private Map<String, Integer> prenotazioni = new HashMap<>();
	private int personeRifiutate = 0;
	private int tavoliOccupati = 0;
	private int tavoliLiberi;
	
	private Map<String, List<Menu>> ordini = new HashMap<>();
	private Map<String, Double> pagati = new HashMap<>();
	
	public Restaurant(String name, int tables){
		this.nome = name;
		this.tavoli = tables;
		tavoliLiberi = tavoli;
	}
	
	public String getName(){
		return nome;
	}
	
	public void addMenu(String name, double price) throws InvalidName{
		Menu menu = new Menu(name, price);
		if(carta.get(name) != null){
			throw new InvalidName("menu già presente");
		}
		carta.put(name, menu);
	}
	
	public int reserve(String name, int persons) throws InvalidName{
		if(prenotazioni.containsKey(name)){
			throw new InvalidName("Prenotazione già presente");
		}
//		if((tavoli*4) >= persons){ // tavoli*4 = numero posti disponibili
//			prenotazioni.put(name, persons);
//			tavoliOccupati += (int)Math.ceil((double)persons/4);
//			//tavoliLiberi -= tavoliOccupati;
//			return (int)Math.ceil((double)persons/4);
//		}
//		personeRifiutate += persons;
//		return 0;
		int tavoliRichiesti = (int)Math.ceil((double)persons/4);
		if(tavoli - tavoliOccupati - tavoliRichiesti < 0){
			personeRifiutate += persons;
			return 0;
		}
		tavoliOccupati += tavoliRichiesti;
		prenotazioni.put(name, persons);
		return tavoliRichiesti;
	}
	
	public int getRefused(){
		return personeRifiutate;
	}
	
	public int getUnusedTables(){
		return tavoli-tavoliOccupati;
	}
	
	public boolean order(String name, String... menu) throws InvalidName{
		if(!prenotazioni.containsKey(name)){
			throw new InvalidName("Referente non presente");
		}
		int numeroOrdinazioni = 0;
		for(String s : menu){
			numeroOrdinazioni++;
		}
		if( numeroOrdinazioni < prenotazioni.get(name)){
			return false;
		}
		List<Menu> listaOrdinazioni = new ArrayList<>();
		for(String m : menu){
			if(carta.get(m)== null){
				throw new InvalidName("Menu non presente");
			}
			listaOrdinazioni.add(carta.get(m));
		}
		ordini.put(name, listaOrdinazioni);
		return true;
	}
	
	public List<String> getUnordered(){
		List<String> nonOrdinati = new ArrayList<>();
		
		for(String s : prenotazioni.keySet()){
			if(!ordini.containsKey(s)){
				nonOrdinati.add(s);
			}
		}
		return nonOrdinati;
	}
	
	public double pay(String name) throws InvalidName{
		if(!prenotazioni.containsKey(name)){
			throw new InvalidName("Nessun tavolo riservato");
		}
		if(ordini.get(name) == null){
			return 0;
		}
		double conto =  ordini.get(name).stream()
				.collect(Collectors.summingDouble(Menu::getPrezzo));
		pagati.put(name, conto);
		return conto;
	}
	
	public List<String> getUnpaid(){
		List<String> nonPagati = new ArrayList<>();
		for(String s : prenotazioni.keySet()){
			if(!pagati.keySet().contains(s)){
				nonPagati.add(s);
			}
		}
		return nonPagati.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	public double getIncome(){
		return pagati.values().stream()
				.reduce(0.0,(a, b) -> a + b);
	}

}
