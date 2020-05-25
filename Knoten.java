package dijkstra;

import java.util.*;

public class Knoten {

	public int index;
	
	public int distance;
	
	// Klasse anliegenderKnoten wird genutzt um die Kanten zu Nachbarn zu speichern
	anliegenderKnoten aK ;
	// Es können mehrere Knoten anliegen, Objekte der Klasse anliegenderKnoten werden deshalb an die Liste vebrindungen übergeben
	List<anliegenderKnoten> verbindungen = new LinkedList<>();
	//Abspeicherung gefundener kurzer Wege
	List<Knoten> gefundenerWeg = new LinkedList<>();
	
	
	
	//Setze den Startknoten auf 0
	//Alle anderen Knoten werden mit Max_Value auf unendlich angenähert
	public Knoten(int index) {
		this.index = index;
		if (index == 0) {
			distance = 0;
		}
		else {
			distance = Integer.MAX_VALUE;
		}
		
	}
	//Funktion um die Nachbarknoten hinzuzufügen. 
	public void addKante(Integer zielKnoten, int gewicht) {
		//nachbarKnoten.put(zielKnoten, gewicht);
		aK = new anliegenderKnoten(zielKnoten, gewicht);
		verbindungen.add(aK);
	}

	// Ausgabe der Klasse als String zur Ausgabe der Lösung auf der Konsole
	public String toString() {
		return "Knoten [index=" + index + ", distance=" + distance + ", nachbarKnoten(Ziel=" + verbindungen.toString() + "]" + "\r\n";
	}
	public anliegenderKnoten getaK() {
		return aK;
	}
	public void setaK(anliegenderKnoten aK) {
		this.aK = aK;
	}
	public List<anliegenderKnoten> getVerbindungen() {
		return verbindungen;
	}
	public void setVerbindungen(List<anliegenderKnoten> verbindungen) {
		this.verbindungen = verbindungen;
	}
	
	public String printResult() {
		StringBuilder sb = new StringBuilder("Gefundender weg: 0");
		
		for (Knoten knoten : gefundenerWeg) {
			sb.append("->" + knoten.index );
			
		}
		
		return "Knoten [index=" + index + ", distance= " + distance + " "+ sb.toString() +"]" ;
	}
	
}
