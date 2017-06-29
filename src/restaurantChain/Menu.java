package restaurantChain;

public class Menu {
	private String nome;
	private double prezzo;
	
	
	public Menu(String nome, double prezzo) {
		super();
		this.nome = nome;
		this.prezzo = prezzo;
	}
	
	public String getName() {
		return nome;
	}

	public void setName(String name) {
		this.nome = name;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}


	
	
	

}
