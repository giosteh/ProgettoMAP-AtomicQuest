
package entita;

import grafo.ArcoInesistenteException;
import grafo.GrafoMap;
import grafo.NodoInesistenteException;
import controller.GestioneFile;
import controller.Output;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe che rappresenta la mappa del gioco.
 */
public class Mappa implements Serializable {
    
    private final GrafoMap<Stanza, Collegamento> grafo = new GrafoMap<>();
    private final List<Stanza> stanzePresenti = new ArrayList<>();

    /**
     * Classe che rappresenta il collegamento tra due stanze.
     */
    private class Collegamento implements Serializable {

        private final Direzione direzione;
        private ModalitaDiAccesso modalita;

        /**
         * Costruttore di default della classe Collegamento.
         * @param direzione la direzione del collegamento
         * @param modalita la modalità di accesso del collegamento
         */
        private Collegamento(final Direzione direzione, final ModalitaDiAccesso modalita) {
            this.direzione = direzione;
            this.modalita = modalita;
        }

        /**
         * Metodo che restituisce la direzione del collegamento.
         * @return direzione
         */
        public Direzione getDirezione() {
            return this.direzione;
        }

        /**
         * Metodo che restituisce la modalità di accesso del collegamento.
         * @return modalita
         */
        public ModalitaDiAccesso getModalita() {
            return this.modalita;
        }

        /**
         * Metodo che imposta la modalità di accesso del collegamento.
         * @param modalita la modalità di accesso del collegamento
         */
        public void setModalita(final ModalitaDiAccesso modalita) {
            this.modalita = modalita;
        }
    }

    /**
     * Costruttore di default della classe Mappa.
     */
    public Mappa() {
        this.initMappa();
    }

    /**
     * Metodo che inizializza la mappa del gioco.
     */
    public void initMappa() {
        List<String> stringhe = GestioneFile.caricaList("./risorse/files/stringhe.dat");
        
        Stanza cortile = new Stanza(Stanze.CORTILE, stringhe.get(Output.DESCRIZIONECORTILE.ordinal()),
                stringhe.get(Output.OSSERVACORTILE.ordinal()), stringhe.get(Output.BENVENUTOCORTILE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza atrio = new Stanza(Stanze.ATRIO, stringhe.get(Output.DESCRIZIONEATRIO.ordinal()),
                stringhe.get(Output.OSSERVAATRIO.ordinal()), stringhe.get(Output.BENVENUTOATRIO.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza spogliatoio = new Stanza(Stanze.SPOGLIATOIO, stringhe.get(Output.DESCRIZIONESPOGLIATOIO.ordinal()),
                stringhe.get(Output.OSSERVASPOGLIATOIO.ordinal()), stringhe.get(Output.BENVENUTOSPOGLIATOIO.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza anticameraSalaPompe = new Stanza(Stanze.ANTICAMERASALAPOMPE, stringhe.get(Output.DESCRIZIONEANTICAMERASALAPOMPE.ordinal()),
                stringhe.get(Output.OSSERVAANTICAMERASALAPOMPE.ordinal()), stringhe.get(Output.BENVENUTOANTICAMERASALAPOMPE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza salaPompe = new Stanza(Stanze.SALAPOMPE, stringhe.get(Output.DESCRIZIONESALAPOMPE.ordinal()),
                stringhe.get(Output.OSSERVASALAPOMPE.ordinal()), stringhe.get(Output.BENVENUTOSALAPOMPE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza anticameraSalaVapore = new Stanza(Stanze.ANTICAMERASALAVAPORE, stringhe.get(Output.DESCRIZIONEANTICAMERASALAVAPORE.ordinal()),
                stringhe.get(Output.OSSERVAANTICAMERASALAVAPORE.ordinal()), stringhe.get(Output.BENVENUTOANTICAMERASALAVAPORE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza salaVapore = new Stanza(Stanze.SALAVAPORE, stringhe.get(Output.DESCRIZIONESALAVAPORE.ordinal()),
                stringhe.get(Output.OSSERVASALAVAPORE.ordinal()), stringhe.get(Output.BENVENUTOSALAVAPORE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza anticameraSalaMacchine = new Stanza(Stanze.ANTICAMERASALAMACCHINE, stringhe.get(Output.DESCRIZIONEANTICAMERASALAMACCHINE.ordinal()),
                stringhe.get(Output.OSSERVAANTICAMERASALAMACCHINE.ordinal()), stringhe.get(Output.BENVENUTOANTICAMERASALAMACCHINE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza salaMacchine = new Stanza(Stanze.SALAMACCHINE, stringhe.get(Output.DESCRIZIONESALAMACCHINE.ordinal()),
                stringhe.get(Output.OSSERVASALAMACCHINE.ordinal()), stringhe.get(Output.BENVENUTOSALAMACCHINE.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza corridoio = new Stanza(Stanze.CORRIDOIO, stringhe.get(Output.DESCRIZIONECORRIDOIO.ordinal()),
                stringhe.get(Output.OSSERVACORRIDOIO.ordinal()), stringhe.get(Output.BENVENUTOCORRIDOIO.ordinal()),
                LivelloRadioattivita.BASSO);
        Stanza anticameraSalaReattore = new Stanza(Stanze.ANTICAMERASALAREATTORE, stringhe.get(Output.DESCRIZIONEANTICAMERASALAREATTORE.ordinal()),
                stringhe.get(Output.OSSERVAANTICAMERASALAREATTORE.ordinal()), stringhe.get(Output.BENVENUTOANTICAMERASALAREATTORE.ordinal()),
                LivelloRadioattivita.MEDIO);
        Stanza salaReattore = new Stanza(Stanze.SALAREATTORE, stringhe.get(Output.DESCRIZIONESALAREATTORE.ordinal()),
                stringhe.get(Output.OSSERVASALAREATTORE.ordinal()), stringhe.get(Output.BENVENUTOSALAREATTORE.ordinal()),
                LivelloRadioattivita.MEDIO);
        Stanza salaControllo = new Stanza(Stanze.SALACONTROLLO, stringhe.get(Output.DESCRIZIONESALACONTROLLO.ordinal()),
                stringhe.get(Output.OSSERVASALACONTROLLO.ordinal()), stringhe.get(Output.BENVENUTOSALACONTROLLO.ordinal()),
                LivelloRadioattivita.MEDIO);
        Stanza anticameraDeposito = new Stanza(Stanze.ANTICAMERADEPOSITO, stringhe.get(Output.DESCRIZIONEANTICAMERADEPOSITO.ordinal()),
                stringhe.get(Output.OSSERVAANTICAMERADEPOSITO.ordinal()), stringhe.get(Output.BENVENUTOANTICAMERADEPOSITO.ordinal()),
                LivelloRadioattivita.MEDIO);
        Stanza deposito = new Stanza(Stanze.DEPOSITO, stringhe.get(Output.DESCRIZIONEDEPOSITO.ordinal()),
                stringhe.get(Output.OSSERVADEPOSITO.ordinal()), stringhe.get(Output.BENVENUTODEPOSITO.ordinal()),
                LivelloRadioattivita.ELEVATO);

        ItemContenitore armadiettoSinistro = new ItemContenitore(Items.ARMADIETTOSINISTRO,
                "Armadietto sinistro" , stringhe.get(Output.OSSERVAARMADIETTO.ordinal()), false, false);
        armadiettoSinistro.aggiungiItem(new Item(Items.TUTA,"Tuta", stringhe.get(Output.OSSERVATUTA.ordinal()), true));
        ItemContenitore armadiettoDestro = new ItemContenitore(Items.ARMADIETTODESTRO,
                "Armadietto destro", stringhe.get(Output.OSSERVAARMADIETTO.ordinal()), false, false);
        armadiettoDestro.aggiungiItem(new Item(Items.TESSERINO, "Tesserino", stringhe.get(Output.OSSERVATESSERINO.ordinal()) , true));
        spogliatoio.aggiungiItemContenitore(armadiettoSinistro);
        spogliatoio.aggiungiItemContenitore(armadiettoDestro);
        salaVapore.aggiungiItem(new Item(Items.CACCIAVITE, "Cacciavite", stringhe.get(Output.OSSERVACACCIAVITE.ordinal()) , true));
        salaPompe.aggiungiItem(new Item(Items.TELECOMANDO, "Telecomando", stringhe.get(Output.OSSERVATELECOMANDO.ordinal()), true));
        salaMacchine.aggiungiItem(new Item(Items.FOGLIO, "Foglio", stringhe.get(Output.OSSERVAFOGLIO.ordinal()), true));
        salaMacchine.aggiungiItem(new Item(Items.CHIAVE, "Chiave", stringhe.get(Output.OSSERVACHIAVE.ordinal()), true));
        salaReattore.aggiungiItem(new Item(Items.TORCIA, "Torcia", stringhe.get(Output.OSSERVACHIAVE.ordinal()), true));
        

        this.grafo.inserisciNodo(cortile);
        this.grafo.inserisciNodo(atrio);
        this.grafo.inserisciNodo(spogliatoio);
        this.grafo.inserisciNodo(anticameraSalaPompe);
        this.grafo.inserisciNodo(salaPompe);
        this.grafo.inserisciNodo(anticameraSalaVapore);
        this.grafo.inserisciNodo(salaVapore);
        this.grafo.inserisciNodo(anticameraSalaMacchine);
        this.grafo.inserisciNodo(salaMacchine);
        this.grafo.inserisciNodo(corridoio);
        this.grafo.inserisciNodo(anticameraSalaReattore);
        this.grafo.inserisciNodo(salaReattore);
        this.grafo.inserisciNodo(salaControllo);
        this.grafo.inserisciNodo(anticameraDeposito);
        this.grafo.inserisciNodo(deposito);
        
        this.stanzePresenti.add(cortile);
        this.stanzePresenti.add(atrio);
        this.stanzePresenti.add(spogliatoio);
        this.stanzePresenti.add(anticameraSalaPompe);
        this.stanzePresenti.add(salaPompe);
        this.stanzePresenti.add(anticameraSalaVapore);
        this.stanzePresenti.add(salaVapore);
        this.stanzePresenti.add(anticameraSalaMacchine);
        this.stanzePresenti.add(salaMacchine);
        this.stanzePresenti.add(corridoio);
        this.stanzePresenti.add(anticameraSalaReattore);
        this.stanzePresenti.add(salaReattore);
        this.stanzePresenti.add(salaControllo);
        this.stanzePresenti.add(anticameraDeposito);
        this.stanzePresenti.add(deposito);
        
        try {
            this.grafo.inserisciArcoConEtichetta(cortile, atrio,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(atrio, cortile,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(atrio, spogliatoio,
                    new Collegamento(Direzione.OVEST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(spogliatoio, atrio,
                    new Collegamento(Direzione.EST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(atrio, anticameraSalaPompe,
                    new Collegamento(Direzione.EST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaPompe, atrio,
                    new Collegamento(Direzione.OVEST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaPompe, salaPompe,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.CONDOTTO));
            this.grafo.inserisciArcoConEtichetta(salaPompe, anticameraSalaPompe,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaPompe, cortile,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(atrio, anticameraSalaVapore,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.PORTACONTESSERINO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaVapore, atrio,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaVapore, salaVapore,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(salaVapore, anticameraSalaVapore,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaVapore, anticameraSalaMacchine,
                    new Collegamento(Direzione.GIU, ModalitaDiAccesso.ASCENSORE));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, anticameraSalaVapore,
                    new Collegamento(Direzione.SU, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, salaMacchine,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(salaMacchine, anticameraSalaMacchine,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaMacchine, corridoio,
                    new Collegamento(Direzione.EST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(corridoio, anticameraSalaMacchine,
                    new Collegamento(Direzione.OVEST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(corridoio, anticameraSalaReattore,
                    new Collegamento(Direzione.EST, ModalitaDiAccesso.PORTACONTESSERINO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaReattore, corridoio,
                    new Collegamento(Direzione.OVEST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(anticameraSalaReattore, salaReattore,
                    new Collegamento(Direzione.EST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(salaReattore, anticameraSalaReattore,
                    new Collegamento(Direzione.OVEST, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(corridoio, salaControllo,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(salaControllo, corridoio,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.APERTO));
            this.grafo.inserisciArcoConEtichetta(salaControllo, anticameraDeposito,
                    new Collegamento(Direzione.GIU, ModalitaDiAccesso.SCALE));
            this.grafo.inserisciArcoConEtichetta(anticameraDeposito, salaControllo,
                    new Collegamento(Direzione.SU, ModalitaDiAccesso.SCALE));
            this.grafo.inserisciArcoConEtichetta(anticameraDeposito, deposito,
                    new Collegamento(Direzione.SUD, ModalitaDiAccesso.PORTACONCODICE));
            this.grafo.inserisciArcoConEtichetta(deposito, anticameraDeposito,
                    new Collegamento(Direzione.NORD, ModalitaDiAccesso.APERTO));
        } catch (NodoInesistenteException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Metodo che restituisce le stanze adiacenti a una stanza.
     * @param stanza la stanza di cui si vogliono conoscere le stanze adiacenti
     * @return l'iteratore sugli adiacenti
     */
    public Iterator<Stanza> getStanzeAdiacenti(final Stanza stanza) {
        try {
            return this.grafo.adiacenti(stanza).iterator();
        } catch (NodoInesistenteException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Metodo che restituisce la stanza per un id.
     * @param id l'id della stanza
     * @return la stanza cercata
     */
    public Stanza getStanzaPerId(final Stanze id) {
        return this.stanzePresenti.get(id.ordinal());
    }

    /**
     * Metodo che restituisce la stanza per una direzione.
     * @param stanza la stanza di partenza
     * @param direzione la direzione
     * @return la stanza cercata
     */
    public Stanza getStanzaPerDirezione(final Stanza stanza, final Direzione direzione) {
        try {
            Set<Map.Entry<Stanza, Collegamento>> adj = this.grafo.adiacentiEntries(stanza);
            Stanza stanzaSucc = null;
            for (Map.Entry<Stanza, Collegamento> entry : adj) {
                if (entry.getValue().getDirezione() == direzione) {
                    stanzaSucc = entry.getKey();
                }
            }
            return stanzaSucc;
        } catch (NodoInesistenteException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Metodo che restituisce true se esiste una stanza successiva, false altrimenti.
     * @param stanza la stanza di partenza
     * @param direzione la direzione
     * @return true se la stanza esiste, false altrimenti
     */
    public boolean esisteStanzaSuccessiva(final Stanza stanza, final Direzione direzione) {
        return this.getStanzaPerDirezione(stanza, direzione) != null;
    }

    /**
     * Metodo che imposta la modalità di accesso di un collegamento.
     * @param stanza la stanza di partenza
     * @param direzione la direzione
     * @param modalita la modalità di accesso
     */
    public void cambiaModalitaDiAccesso(final Stanza stanza, final Direzione direzione, final ModalitaDiAccesso modalita) {
        try {
            this.grafo.leggiArco(stanza, this.getStanzaPerDirezione(stanza, direzione)).setModalita(modalita);
        } catch (NodoInesistenteException | ArcoInesistenteException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Metodo che restituisce true se la modalità di accesso di un collegamento è ugale a quella passata, false altrimenti.
     * @param stanza la stanza di partenza
     * @param direzione la direzione
     * @param modalita la modalità di accesso
     * @return true se la modalità è quella indicata, false altrimenti
     */
    public boolean verificaModalitaAccesso(final Stanza stanza, final Direzione direzione, final ModalitaDiAccesso modalita) {
        try {
            return this.grafo.leggiArco(stanza, this.getStanzaPerDirezione(stanza, direzione)).getModalita() == modalita;
        } catch (NodoInesistenteException | ArcoInesistenteException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
