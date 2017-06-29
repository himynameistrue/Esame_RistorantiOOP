package restaurantChain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
	private String nome;
	private int tavoli;
	
	private List<Menu> carta = new ArrayList<>();
	private Map<String, Integer> prenotazioni = new HashMap<>();
	private int personeRifiutate = 0;
	private int tavoliOccupati = 0;
	
	public Restaurant(String name, int tables){
		this.nome = name;
		this.tavoli = tables;
	}
	
	public String getName(){
		return nome;
	}
	
	public void addMenu(String name, double price) throws InvalidName{
		Menu menu = new Menu(name, price);
		if(carta.contains(menu)){
			throw new InvalidName("menu già presente");
		}
		carta.add(menu);
	}
	
	public int reserve(String name, int persons) throws InvalidName{
		if(prenotazioni.containsKey(name)){
			throw new InvalidName("Prenotazione già presente");
		}
		if((tavoli*4) > persons){ // tavoli*4 = numero posti disponibili
			prenotazioni.put(name, persons);
			tavoliOccupati += (int)Math.ceil((double)persons/4);
			return (int)Math.ceil((double)persons/4);
		}
		personeRifiutate += persons;
		return 0;
	}
	
	public int getRefused(){
		return personeRifiutate;
	}
	
	public int getUnusedTables(){
		return tavoli - tavoliOccupati;
	}
	
	public boolean order(String name, String... menu) throws InvalidName{
		return false;
	}
	
	public List<String> getUnordered(){
		return null;
	}
	
	public double pay(String name) throws InvalidName{
		return -1.0;
	}
	
	public List<String> getUnpaid(){
		return null;
	}
	
	public double getIncome(){
		return -1.0;
	}

}
