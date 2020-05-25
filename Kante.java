package dijkstra;

public class Kante {
	int knoten1;
	int knoten2;
	int gewicht;
	
	//Dient zum Speichern des Datei Imports bestehend aus Kanten
	
	
	public Kante(int knoten1, int knoten2, int gewicht) {
		this.knoten1 = knoten1;
		this.knoten2 = knoten2;
		this.gewicht = gewicht;
		
	}

	@Override
	public String toString() {
		return  "[knoten1=" + knoten1 + ", knoten2=" + knoten2 + ", gewicht=" + gewicht 
				 + "]" + "\r\n";
	}

}
