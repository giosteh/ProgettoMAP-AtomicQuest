
package entita;

import grafo.GrafoMap;
import grafo.NodoInesistenteException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Mappa {
    
    private final GrafoMap<Room, Direzione> grafo = new GrafoMap<>();
    
    public Iterator<Room> getStanzeAdiacenti(final Room stanza) throws NodoInesistenteException {
        return this.grafo.adiacenti(stanza).iterator();
    }
    
    public Room getStanzaPerDirezione(final Room stanza, final Direzione direz) throws NodoInesistenteException {
        Set<Map.Entry<Room, Direzione>> adj = this.grafo.adiacentiEntries(stanza);
        Room stanzaSucc = null;
        for (Map.Entry<Room, Direzione> entry : adj) {
            if (entry.getValue() == direz) {
                stanzaSucc = entry.getKey();
            }
        }
        return stanzaSucc;
    }
    
    

}
