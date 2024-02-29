
package entita;

import grafo.GrafoMap;
import grafo.NodoInesistenteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Mappa {
    
    private final List<Stanza> stanze = new ArrayList<>();
    private final GrafoMap<Room, Direzione> grafo = new GrafoMap<>();

    
    public void initStanze() {
        Stanza cortile = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "1","Cortile","","","");
        this.stanze.add(cortile);
        Stanza atrio = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "2", "Atrio","","","");
        this.stanze.add(atrio);
        Stanza spogliatoio = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "3", "Spogliatoio","","","");
        this.stanze.add(spogliatoio);
        Stanza anticameraSalaPompe = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "4", "Anticamera sala pompe", "", "", "");
        this.stanze.add(anticameraSalaPompe);
        Stanza salaPompe = new Stanza(ModalitaDiAccesso.CONDOTTO, LivelloRadioattivita.BASSO,
        "5", "Sala pompe", "", "", "");
        this.stanze.add(salaPompe);
        Stanza anticameraSalaVapore = new Stanza(ModalitaDiAccesso.PORTACONTESSERINO, LivelloRadioattivita.BASSO,
        "6", "Anticamera sala vapore", "", "", "");
        this.stanze.add(anticameraSalaVapore);
        Stanza salaVapore = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "7", "Sala vapore", "", "", "");
        this.stanze.add(salaVapore);
        Stanza anticameraSalaMacchine = new Stanza(ModalitaDiAccesso.ASCENSORE, LivelloRadioattivita.BASSO,
        "8", "Anticamera sala macchine", "", "", "");
        this.stanze.add(anticameraSalaMacchine);
        Stanza salaMacchine = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "9", "Sala macchine", "", "", "");
        this.stanze.add(salaMacchine);
        Stanza corridoio = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "10", "Corridoio", "", "", "");
        this.stanze.add(corridoio);
        Stanza anticameraSalaReattore = new Stanza (ModalitaDiAccesso.PORTACONTESSERINO, LivelloRadioattivita.MEDIO, 
        "11", "Anticamera sala reattore", "", "", "");
        this.stanze.add(anticameraSalaReattore);
        Stanza salaReattore = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.MEDIO,
        "12", "Sala reattore", "", "", "");
        this.stanze.add(salaReattore);
        Stanza salaControllo = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.MEDIO,
        "13", "Sala controllo", "", "", "");
        this.stanze.add(salaControllo);
        Stanza anticameraDeposito = new Stanza(ModalitaDiAccesso.SCALE, LivelloRadioattivita.MEDIO,
        "14", "Anticamera deposito", "", "", "");
        this.stanze.add(anticameraDeposito);
        Stanza depositoCombustibile = new Stanza(ModalitaDiAccesso.PORTACONCODICE, LivelloRadioattivita.ELEVATO,
        "15", "Deposito combustibile", "", "", "");
        this.stanze.add(depositoCombustibile);

    }

    public void initMappa() {

        initStanze();

        for (Stanza stanza : this.stanze) {
            this.grafo.inserisciNodo(stanza);
        }
        
        try {
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(0), this.stanze.get(1), Direzione.NORD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(1), this.stanze.get(0), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(1), this.stanze.get(2), Direzione.OVEST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(2), this.stanze.get(1), Direzione.EST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(1), this.stanze.get(3), Direzione.EST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(3), this.stanze.get(1), Direzione.OVEST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(3), this.stanze.get(4), Direzione.NORD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(4), this.stanze.get(3), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(1), this.stanze.get(5), Direzione.NORD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(5), this.stanze.get(1), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(5), this.stanze.get(6), Direzione.NORD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(6), this.stanze.get(5), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(5), this.stanze.get(7), Direzione.GIU);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(7), this.stanze.get(5), Direzione.SU);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(7), this.stanze.get(8), Direzione.NORD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(8), this.stanze.get(7), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(7), this.stanze.get(9), Direzione.EST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(9), this.stanze.get(7), Direzione.OVEST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(9), this.stanze.get(10), Direzione.EST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(10), this.stanze.get(9), Direzione.OVEST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(10), this.stanze.get(11), Direzione.EST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(11), this.stanze.get(10), Direzione.OVEST);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(9), this.stanze.get(12), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(12), this.stanze.get(9), Direzione.NORD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(12), this.stanze.get(13), Direzione.GIU);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(13), this.stanze.get(12), Direzione.SU);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(13), this.stanze.get(14), Direzione.SUD);
            this.grafo.inserisciArcoConEtichetta(this.stanze.get(14), this.stanze.get(13), Direzione.NORD);
        } catch (NodoInesistenteException e) {
            System.err.print(e.getMessage());
    }
}
    
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
