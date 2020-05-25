package dijkstra;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Launcherv2 {

	public static void main(String[] args) throws IOException {

		// Knoten und Kanten werden in jeweils 2 unterschiedlichen Arraylists
		// gespeichert
		ArrayList<Kante> kanten = new ArrayList<Kante>();
		ArrayList<Knoten> knoten = new ArrayList<Knoten>();
		
		//die sortierten und gewichteten Knoten werden im gewichteten Graph gespeichert
		ArrayList<Knoten> gewichteterGraph = new ArrayList<Knoten>();

		// Einlesen der Kanten aus einer übergebenen Datei als Kommdozeilenargument
		String filename = (args[0]);
		
		//Einlesen der Datei aus Stream
		FileInputStream fis = new FileInputStream(filename);
		BufferedInputStream bin = new BufferedInputStream(fis);
		BufferedReader r = new BufferedReader(new InputStreamReader(bin));

		int data = r.read();

		// Einlesen der Kanten aus dem Dokument
		while (data != -1) {
			String read = (char) data + r.readLine();
			
			//Splitten der ints anhand der Leerzeichen
			String readArr[] = read.split(" ");

			//Erzeugen der Kante
			Kante k = new Kante(Integer.parseInt(readArr[0]), Integer.parseInt(readArr[1]),
					Integer.parseInt(readArr[2]));

			
			// Hinzufügen der Kanten zur Arraylist Kanten
			kanten.add(k);
			
			if (knoten.size() == 0) {
				// Hinzufügen des erstens Knotens um eine Iteration zu ermöglichen
				Knoten k1 = new Knoten(k.knoten1);

				knoten.add(k1);

			}
			//Erzeugen der Knoten anhand des Kanten Arrays
			
			// Zusätzlich wird kontrolliert ob Kanten bereits als Knoten vorhanden sind
			// Kontrolle über einen Bool wert da eine die Manipulation des knoten arrays
			// nicht in der Schleife möglich ist(java.util.ConcurrentModificationException)

			boolean vorhanden = true;
			int index = 0;
			for (Knoten knoten2 : knoten) {

				if (k.knoten1 != knoten2.index) {

					vorhanden = false;

				} else if (k.knoten1 == knoten2.index) {
					vorhanden = true;
					// Falls vorhanden wird der gefundene Index zu speichern, um dem Knotenarray
					// eine Verbindung hinzuzufügen
					index = knoten2.index;
				}
			}
			// Falls kein Knoten gefunden wird, wird ein neuer Knoten erzeugt. Es wird
			// ebenfalls die Kante hinzugefügt.
			// Der Verweis der Kante erfolgt über die Integers
			if (vorhanden == false) {

				Knoten k1 = new Knoten(k.knoten1);

				knoten.add(k1);

				knoten.get(k1.index).addKante(k.knoten2, k.gewicht);

			} // Falls der Knoten bereits vorhanden ist, wird nur die neue Kante hinzugefügt.
			if (vorhanden == true) {
				Knoten kNachbar = new Knoten(k.knoten2);
				knoten.get(k.knoten1).addKante(k.knoten2, k.gewicht);
			}
			
			//Einlesen der nächsten Zeile
			data = r.read();

		}
		//Alle Knoden mit Index:1 wurden erfasst
		
		//prüfen Knoten übergeben wurden die keine ausgehenden Kanten haben
		/* z.B. 0 1 5
				0 2 2
				1 2 -10*/
		//2 hat keine ausgehende Verbindung und existiert noch nicht als Kante
		
		int indexSackgasse = 0;
		for (Kante kanten2 : kanten) {
			boolean sackgasse = true;
				for (Knoten knoten2 : knoten) {
				indexSackgasse=  kanten2.knoten2;
								
				if(kanten2.knoten2 == knoten2.index) {
					sackgasse=false;				
					
				}
							
			}
			//Fals in den Kanten ein Knoten2 gefunden wurde, der nicht als Indexknoten existiert, wird dieser angelegt
			if (sackgasse == true) {
				Knoten k3 = new Knoten(indexSackgasse);
				knoten.add(k3);
				sackgasse = false;
			}
			
		}		
		
		System.out.println("Folgende Kanten wurden erfasst");
		
		System.out.println(kanten.toString());

		// Die eingelesenen Kanten wurden in einem Graphen gespeichert
		System.out.println("Folgender Graph wird bearbeitet");
		System.out.println(knoten.toString());
		

		//schließen des Dateistreams
		r.close();

		// Bearbeite das Knoten Arrays und implementierung des Algorithmus
		while (!knoten.isEmpty()) {

			// Finde den niedrigsten Knoten. 
			// Der niedrigste Knoten wird vom Algorithmus als Fixpunkt bearbeitet
			Knoten bearbeitenderKnoten = niedrigsterKnoten(knoten);

			//entferne den Knoten aus dem Array mit den ungewichteten Knoten
			knoten.remove(bearbeitenderKnoten);
			
			// finde die Nachbarknoten
			for (int i = 0; i < bearbeitenderKnoten.verbindungen.size(); i++) {
				
				//Finde den index der Nachbarknoten
				Integer indexNachbar = bearbeitenderKnoten.verbindungen.get(i).getKnoten();
				
				//Finde das Objekt zum zugehörigen Index
				Knoten nachbarKnoten = indexInKnoten(indexNachbar, knoten);
				
				//Finde das Kantengewicht zu den Nachbarknoten
				Integer streckenGewicht = bearbeitenderKnoten.verbindungen.get(i).getStreckenGewicht();

				// bearbeite NachbarKnoten nur, wenn er noch nicht bearbeitet wurde
				if (!gewichteterGraph.contains(nachbarKnoten)) {

					//Abfangen möglicher Nullpointer 
					if(knoten.size()==0) {
						break;
					}
					//kalkuliere die Distanz zu den Verbindungen
					kalkuliereDistanz(nachbarKnoten, streckenGewicht, bearbeitenderKnoten);

				}

			}
			//Füge den Fixpunkt zum gewichteten Graph hinzu
			gewichteterGraph.add(bearbeitenderKnoten);

		}
		//Ausgabe der Lösung
		System.out.println();
		System.out.println("Folgende Wege wurden gefunden");
		for (Knoten knoten2 : gewichteterGraph) {
			System.out.println(knoten2.printResult());
		}

	}

	//Umwandlung eines Knotenindexes in das Objekt (direkt am Objekt sind die Verbindungen gespeichert)
	public static Knoten indexInKnoten(int i, ArrayList<Knoten> knoten) {
		Knoten resultKnoten = null;
		for (Knoten knoten2 : knoten) {
			if (knoten2.index == i) {
				resultKnoten = knoten2;
			}
			if (resultKnoten == null) {
				resultKnoten = new Knoten(i);

			}

		}

		return resultKnoten;
	}

	// Methode zum Finden des niedrigsten Knotens
	public static Knoten niedrigsterKnoten(ArrayList<Knoten> knoten) {
		Knoten niedrigsterKnoten = null;
		int niedrigsteDistanz = Integer.MAX_VALUE;
		for (Knoten knoten2 : knoten) {
			int distanz = knoten2.distance;
			//niedrigster Knoten wird solange aktualisiert, bis keiner mit einer geringeren Distanz gefunden wird
			if (distanz < niedrigsteDistanz) {
				niedrigsteDistanz = distanz;
				niedrigsterKnoten = knoten2;
			}
		}

		return niedrigsterKnoten;
	}

	// Berechnung zwischen Knoten
	public static void kalkuliereDistanz(Knoten zielKnoten, int gewicht, Knoten startKnoten) {
		int distanzKnoten = startKnoten.distance;

		// Kontrolle ob die neue Distanz kürzer ist als die Vorhandene
		if (gewicht + distanzKnoten < zielKnoten.distance) {
			// Fals ja, aktualisiere Knotenbewertung mit kürzerer Distanz
			zielKnoten.distance = gewicht + distanzKnoten;
			
			// speichere den gefundenen Weg im markierten Knoten
			LinkedList<Knoten> gefundenerWeg = new LinkedList<>(startKnoten.gefundenerWeg);
			gefundenerWeg.add(zielKnoten);
			zielKnoten.gefundenerWeg = gefundenerWeg;
		}

	}
}
