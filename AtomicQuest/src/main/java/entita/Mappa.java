
package entita;

import grafo.GrafoMap;
import grafo.NodoInesistenteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Mappa {
    
    private final GrafoMap<Stanza, Collegamento> grafo = new GrafoMap<>();

    private class Collegamento {

        private final Direzione direzione;
        private ModalitaDiAccesso modalita;

        public Collegamento(final Direzione direzione, final ModalitaDiAccesso modalita) {
            this.direzione = direzione;
            this.modalita = modalita;
        }

        public Direzione getDirezione() {
            return this.direzione;
        }

        public ModalitaDiAccesso getModalita() {
            return this.modalita;
        }

        public void setModalita(final ModalitaDiAccesso modalita) {
            this.modalita = modalita;
        }
    }

    public void initMappa() {

        Stanza cortile = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "1","Cortile","","","");
        Stanza atrio = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "2", "Atrio","","","");
        Stanza spogliatoio = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "3", "Spogliatoio","","","");
        Stanza anticameraSalaPompe = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "4", "Anticamera sala pompe", "", "", "");
        Stanza salaPompe = new Stanza(ModalitaDiAccesso.CONDOTTO, LivelloRadioattivita.BASSO,
        "5", "Sala pompe", "", "", "");
        Stanza anticameraSalaVapore = new Stanza(ModalitaDiAccesso.PORTACONTESSERINO, LivelloRadioattivita.BASSO,
        "6", "Anticamera sala vapore", "", "", "");
        Stanza salaVapore = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "7", "Sala vapore", "", "", "");
        Stanza anticameraSalaMacchine = new Stanza(ModalitaDiAccesso.ASCENSORE, LivelloRadioattivita.BASSO,
        "8", "Anticamera sala macchine", "", "", "");
        Stanza salaMacchine = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "9", "Sala macchine", "", "", "");
        Stanza corridoio = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.BASSO,
        "10", "Corridoio", "", "", "");
        Stanza anticameraSalaReattore = new Stanza (ModalitaDiAccesso.PORTACONTESSERINO, LivelloRadioattivita.MEDIO, 
        "11", "Anticamera sala reattore", "", "", "");
        Stanza salaReattore = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.MEDIO,
        "12", "Sala reattore", "", "", "");
        Stanza salaControllo = new Stanza(ModalitaDiAccesso.PORTAAPERTA, LivelloRadioattivita.MEDIO,
        "13", "Sala controllo", "", "", "");
        Stanza anticameraDeposito = new Stanza(ModalitaDiAccesso.SCALE, LivelloRadioattivita.MEDIO,
        "14", "Anticamera deposito", "", "", "");
        Stanza depositoCombustibile = new Stanza(ModalitaDiAccesso.PORTACONCODICE, LivelloRadioattivita.ELEVATO,
        "15", "Deposito combustibile", "", "", "");

        grafo.inserisciNodo(cortile);
        grafo.inserisciNodo(atrio);
        grafo.inserisciNodo(spogliatoio);
        grafo.inserisciNodo(anticameraSalaPompe);
        grafo.inserisciNodo(salaPompe);
        grafo.inserisciNodo(anticameraSalaVapore);
        grafo.inserisciNodo(salaVapore);
        grafo.inserisciNodo(anticameraSalaMacchine);
        grafo.inserisciNodo(salaMacchine);
        grafo.inserisciNodo(corridoio);
        grafo.inserisciNodo(anticameraSalaReattore);
        grafo.inserisciNodo(salaReattore);
        grafo.inserisciNodo(salaControllo);
        grafo.inserisciNodo(anticameraDeposito);
        grafo.inserisciNodo(depositoCombustibile);

        try {
        grafo.inserisciArcoConEtichetta(cortile, atrio, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(atrio, cortile, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(atrio, spogliatoio, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(spogliatoio, atrio, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(atrio, anticameraSalaPompe, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(anticameraSalaPompe, atrio, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(anticameraSalaPompe, salaPompe, new Collegamento(Direzione.NORD, ModalitaDiAccesso.CONDOTTO));
        grafo.inserisciArcoConEtichetta(salaPompe, anticameraSalaPompe, new Collegamento(Direzione.SUD, ModalitaDiAccesso.CONDOTTO) );
        grafo.inserisciArcoConEtichetta(anticameraSalaPompe, cortile, new Collegamento(Direzione.SUD, ModalitaDiAccesso.CONDOTTO));
        grafo.inserisciArcoConEtichetta(atrio, anticameraSalaVapore, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTACONTESSERINO));
        grafo.inserisciArcoConEtichetta(anticameraSalaVapore, atrio, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(anticameraSalaVapore, salaVapore, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(salaVapore, anticameraSalaVapore, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(anticameraSalaVapore, anticameraSalaMacchine, new Collegamento(Direzione.GIU, ModalitaDiAccesso.ASCENSORE));
        grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, anticameraSalaVapore, new Collegamento(Direzione.SU, ModalitaDiAccesso.ASCENSORE));
        grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, salaMacchine, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(salaMacchine, anticameraSalaMacchine, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, corridoio, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(corridoio, anticameraSalaMacchine, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(corridoio, anticameraSalaReattore, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTACONTESSERINO));
        grafo.inserisciArcoConEtichetta(anticameraSalaReattore, corridoio, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(anticameraSalaReattore, salaReattore, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(salaReattore, anticameraSalaReattore, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(corridoio, salaControllo, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(salaControllo, corridoio, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
        grafo.inserisciArcoConEtichetta(salaControllo, anticameraDeposito, new Collegamento(Direzione.GIU, ModalitaDiAccesso.SCALE));
        grafo.inserisciArcoConEtichetta(anticameraDeposito, salaControllo, new Collegamento(Direzione.SU, ModalitaDiAccesso.SCALE));
        grafo.inserisciArcoConEtichetta(anticameraDeposito, depositoCombustibile, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTACONCODICE));
        grafo.inserisciArcoConEtichetta(depositoCombustibile, anticameraDeposito, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));

        } catch (NodoInesistenteException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public Iterator<Stanza> getStanzeAdiacenti(final Stanza stanza) throws NodoInesistenteException {
        return this.grafo.adiacenti(stanza).iterator();
    }
    
    public Stanza getStanzaPerDirezione(final Stanza stanza, final Direzione direz) throws NodoInesistenteException {
        Set<Map.Entry<Stanza, Collegamento>> adj = this.grafo.adiacentiEntries(stanza);
        Stanza stanzaSucc = null;
        for (Map.Entry<Stanza, Collegamento> entry : adj) {
            if (entry.getValue().getDirezione() == direz) {
                stanzaSucc = entry.getKey();
            }
        }
        return stanzaSucc;
        
    }
    
    

}
