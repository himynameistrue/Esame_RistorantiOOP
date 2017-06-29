package restaurantChain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chain {
		private Map<String, Restaurant> ristoranti = new HashMap<>();

		public void addRestaurant(String name, int tables) throws InvalidName{
			if(ristoranti.containsKey(name)){
				throw new InvalidName("ristorante già presente");
			}
			ristoranti.put(name, new Restaurant(name, tables));
		}
		
		public Restaurant getRestaurant(String name) throws InvalidName{
			if(ristoranti.get(name)== null){
				throw new InvalidName("ristorante non presente");
			}
			return ristoranti.get(name);
		}
		
		public List<Restaurant> sortByIncome(){
			return ristoranti.values().stream()
					.sorted(Comparator.comparing(Restaurant::getIncome).reversed())
					.collect(Collectors.toList());
		}
		
		public List<Restaurant> sortByRefused(){
			return ristoranti.values().stream()
					.sorted(Comparator.comparing(Restaurant::getRefused))
					.collect(Collectors.toList());
		}
		
		public List<Restaurant> sortByUnusedTables(){
			return ristoranti.values().stream()
					.sorted(Comparator.comparing(Restaurant::getUnusedTables))
					.collect(Collectors.toList());
		}
}
