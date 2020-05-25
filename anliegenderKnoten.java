package dijkstra;

public class anliegenderKnoten {
	
	//wird erzeugt in Knoten
	//Dient zur Zuordnung von anliegenden Knoten zu Knoten
	int knoten;
	int streckenGewicht;
	
	public anliegenderKnoten(int knoten, int streckenGewicht) {
		this.knoten = knoten;
		this.streckenGewicht = streckenGewicht;
	}

	@Override
	public String toString() {
		return "anliegenderKnoten [knoten=" + knoten + ", streckenGewicht=" + streckenGewicht + "]";
	}

	public int getKnoten() {
		return knoten;
	}

	public void setKnoten(int knoten) {
		this.knoten = knoten;
	}

	public int getStreckenGewicht() {
		return streckenGewicht;
	}

	public void setStreckenGewicht(int streckenGewicht) {
		this.streckenGewicht = streckenGewicht;
	}
	
	
	
}
