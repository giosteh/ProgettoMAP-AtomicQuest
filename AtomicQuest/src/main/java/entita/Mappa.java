
package entita;

import grafo.GrafoMap;
import grafo.NodoInesistenteException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Mappa {
    
    private final GrafoMap<Stanza, Collegamento> grafo = new GrafoMap<>();
    private final Set<Stanza> stanze = new TreeSet<>();

    private class Collegamento {

        private final Direzione direzione;
        private ModalitaDiAccesso modalita;

        private Collegamento(final Direzione direzione, final ModalitaDiAccesso modalita) {
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

    public Mappa() {
    }

    public void initMappa() {

        Stanza cortile = new Stanza("001", "Cortile", "", "", LivelloRadioattivita.BASSO);
        Stanza atrio = new Stanza("002", "Atrio", "", "", LivelloRadioattivita.BASSO);
        Stanza spogliatoio = new Stanza("003", "Spogliatoio", "", "", LivelloRadioattivita.BASSO);
        Stanza anticameraSalaPompe = new Stanza("004", "Anticamera Sala Pompe", "", "", LivelloRadioattivita.BASSO);
        Stanza salaPompe = new Stanza("005", "Sala Pompe", "", "", LivelloRadioattivita.BASSO);
        Stanza anticameraSalaVapore = new Stanza("006", "Anticamera Sala Vapore", "", "", LivelloRadioattivita.BASSO);
        Stanza salaVapore = new Stanza("007", "Sala Vapore", "", "", LivelloRadioattivita.BASSO);
        Stanza anticameraSalaMacchine = new Stanza("008", "Anticamera Sala Macchine", "", "", LivelloRadioattivita.BASSO);
        Stanza salaMacchine = new Stanza("009", "Sala Macchine", "", "", LivelloRadioattivita.BASSO);
        Stanza corridoio = new Stanza("010", "Corridoio", "", "", LivelloRadioattivita.BASSO);
        Stanza anticameraSalaReattore = new Stanza("011", "Anticamera Sala Reattore", "", "", LivelloRadioattivita.MEDIO);
        Stanza salaReattore = new Stanza("012", "Sala Reattore", "", "", LivelloRadioattivita.MEDIO);
        Stanza salaControllo = new Stanza("013", "Sala Controllo", "", "", LivelloRadioattivita.MEDIO);
        Stanza anticameraDeposito = new Stanza("014", "Anticamera Deposito", "", "", LivelloRadioattivita.MEDIO);
        Stanza depositoCombustibile = new Stanza("015", "Deposito Combustibile", "", "", LivelloRadioattivita.ELEVATO);

        this.grafo.inserisciNodi(cortile, atrio, spogliatoio, anticameraSalaPompe, salaPompe, anticameraSalaVapore,
                salaVapore, anticameraSalaMacchine, corridoio, anticameraSalaReattore, salaReattore, salaControllo,
                anticameraDeposito, depositoCombustibile);
        try {
            this.grafo.inserisciArcoConEtichetta(cortile, atrio, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(atrio, cortile, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(atrio, spogliatoio, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(spogliatoio, atrio, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(atrio, anticameraSalaPompe, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaPompe, atrio, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaPompe, salaPompe, new Collegamento(Direzione.NORD, ModalitaDiAccesso.CONDOTTO));
            this.grafo.inserisciArcoConEtichetta(salaPompe, anticameraSalaPompe, new Collegamento(Direzione.SUD, ModalitaDiAccesso.CONDOTTO) );
            this.grafo.inserisciArcoConEtichetta(anticameraSalaPompe, cortile, new Collegamento(Direzione.SUD, ModalitaDiAccesso.CONDOTTO));
            this.grafo.inserisciArcoConEtichetta(atrio, anticameraSalaVapore, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTACONTESSERINO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaVapore, atrio, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaVapore, salaVapore, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(salaVapore, anticameraSalaVapore, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaVapore, anticameraSalaMacchine, new Collegamento(Direzione.GIU, ModalitaDiAccesso.ASCENSORE));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, anticameraSalaVapore, new Collegamento(Direzione.SU, ModalitaDiAccesso.ASCENSORE));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, salaMacchine, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(salaMacchine, anticameraSalaMacchine, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, corridoio, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(corridoio, anticameraSalaMacchine, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(corridoio, anticameraSalaReattore, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTACONTESSERINO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaReattore, corridoio, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaReattore, salaReattore, new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(salaReattore, anticameraSalaReattore, new Collegamento(Direzione.OVEST, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(corridoio, salaControllo, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(salaControllo, corridoio, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
            this.grafo.inserisciArcoConEtichetta(salaControllo, anticameraDeposito, new Collegamento(Direzione.GIU, ModalitaDiAccesso.SCALE));
            this.grafo.inserisciArcoConEtichetta(anticameraDeposito, salaControllo, new Collegamento(Direzione.SU, ModalitaDiAccesso.SCALE));
            this.grafo.inserisciArcoConEtichetta(anticameraDeposito, depositoCombustibile, new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTACONCODICE));
            this.grafo.inserisciArcoConEtichetta(depositoCombustibile, anticameraDeposito, new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTAAPERTA));
        } catch (NodoInesistenteException e) {
            System.err.println(e.getMessage());
        }   
    }

    public Iterator<Stanza> getStanzeAdiacenti(final Stanza stanza) throws NodoInesistenteException {
        return this.grafo.adiacenti(stanza).iterator();
    }

    public Stanza getStanzaPerDirezione(final Stanza stanza, final Direzione direzione) throws NodoInesistenteException {
        Set<Map.Entry<Stanza, Collegamento>> adj = this.grafo.adiacentiEntries(stanza);
        Stanza stanzaSucc = null;
        for (Map.Entry<Stanza, Collegamento> entry : adj) {
            if (entry.getValue().getDirezione() == direzione) {
                stanzaSucc = entry.getKey();
            }
        }
        return stanzaSucc;
    }

    public void cambiaModalitaDiAccesso(final Stanza stanza1, final Stanza stanza2, final ModalitaDiAccesso modalita) {
        this.grafo.leggiArco(stanza1, stanza2).setModalita(modalita);
    }
}
