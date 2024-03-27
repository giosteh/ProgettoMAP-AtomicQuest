
package controller;

import entita.Direzione;
import entita.Giocatore;
import entita.LivelloRadioattivita;
import entita.Mappa;
import entita.ModalitaDiAccesso;
import entita.Stanza;
import entita.Stanze;
import entita.Items;
import entita.Item;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe che si occupa del parsing dei comandi inseriti dall'utente.
 */
public class Parser {

    /**
     * Interfaccia funzionale che rappresenta un validatore di comandi.
     */
    interface ValidatoreComando {
        boolean isValido(String comando);
    }

    /**
     * Interfaccia funzionale che rappresenta un riconoscitore di comandi.
     */
    interface RiconoscitoreComando {
        boolean riconosci(int tipoComando);
    }

    private final Giocatore giocatore;
    private final Map<String, String> vocabolario;
    private final Map<String, Integer> comandi;
    private final List<String> stringhe;
    private final RiconoscitoreComando riconoscitoreComandoSpostamento = (a) -> (a >= 1 && a <= 10);
    private final RiconoscitoreComando riconoscitoreComandoOsserva = (a) -> (a == 41);
    private final RiconoscitoreComando riconoscitoreComandoUso = (a) -> (a >= 11 && a <= 20);
    private final RiconoscitoreComando riconoscitoreComandoInventario = (a) -> (a >= 21 && a <= 40);
    private final RiconoscitoreComando riconoscitoreComandoFinale = (a) -> (a >= 53 && a <= 55);
    private final RiconoscitoreComando riconoscitoreComandoOsservaItem = (a) -> (a >= 43 && a <= 52);
    private final RiconoscitoreComando riconoscitoreComandoDove = (a) -> (a == 56);
    private final RiconoscitoreComando riconoscitoreComandoStampaInventario = (a) -> (a == 57);

    /**
     * Costruttore della classe.
     * @param giocatore il giocatore
     */
    public Parser(Giocatore giocatore) {
        this.giocatore = giocatore;
        this.vocabolario = GestioneFile.caricaMap("./risorse/files/vocabolario.dat");
        this.comandi = GestioneFile.caricaMap("./risorse/files/comandi.dat");
        this.stringhe = GestioneFile.caricaList("./risorse/files/stringhe.dat");
    }

    /**
     * Metodo che restituisce l'oggetto di classe OutputParser che rappresenta l'output del comando inserito.
     * @param comando il comando inserito dall'utente
     * @return l'oggetto OutputParser costruito
     */
    public OutputParser analizzaComando(final String comando) {
        String codiceComando = this.ottieniCodiceComando(comando.toLowerCase());
        ValidatoreComando validatore = (a) -> (this.comandi.get(a) != null);
        OutputParser outputComando = new OutputParser();
        if (!validatore.isValido(codiceComando)) {
            outputComando.setStringaDaStampare("Eh??\n\n");
        } else {
            int tipoComando = this.comandi.get(codiceComando);
            if (this.riconoscitoreComandoSpostamento.riconosci(tipoComando)) {
                this.gestisciSpostamento(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoOsserva.riconosci(tipoComando)) {
                this.gestisciOsserva(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoUso.riconosci(tipoComando)) {
                this.gestisciUso(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoInventario.riconosci(tipoComando)) {
                this.gestisciInventario(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoFinale.riconosci(tipoComando)) {
                this.gestisciFinale(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoOsservaItem.riconosci(tipoComando)) {
                this.gestisciOsservaItem(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoDove.riconosci(tipoComando)) {
                this.gestisciDove(tipoComando, outputComando);
            } else if (this.riconoscitoreComandoStampaInventario.riconosci(tipoComando)) {
                this.gestisciStampaInventario(tipoComando, outputComando);
            }
        }
        return outputComando;
    }

    /**
     * Metodo che gestisce lo spostamento del giocatore.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciSpostamento(final int tipoComando, final OutputParser outputComando) {
        Direzione direzione = null;
        Stanza stanzaCorrente = this.giocatore.getStanzaCorrente();
        LivelloRadioattivita livelloRadioattivitaPrecedente = stanzaCorrente.getEsposizRadioattiva();
        Mappa mappa = this.giocatore.getMappa();
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        if (tipoComando >=1 && tipoComando <= 6) {
            direzione = Direzione.values()[tipoComando - 1];
        } else if (tipoComando == 7) {
            if (stanzaCorrente.getId() == Stanze.ANTICAMERASALAVAPORE) {
                direzione = Direzione.GIU;
            } else if (stanzaCorrente.getId() == Stanze.ANTICAMERASALAMACCHINE) {
                direzione = Direzione.SU;
            } else {
                outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICAASCENSORENONPRESENTE.ordinal()));
                return;
            }
        } else if (tipoComando == 8) {
            if (stanzaCorrente.getId() == Stanze.SALACONTROLLO) {
                direzione = Direzione.GIU;
            } else if (stanzaCorrente.getId() == Stanze.ANTICAMERADEPOSITO) {
                direzione = Direzione.SU;
            } else {
                outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCALENONPRESENTI.ordinal()));
                return;
            }
        }
        if (!mappa.esisteStanzaSuccessiva(stanzaCorrente, direzione)) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASTANZAINESISTENTE.ordinal()));
            return;
        }
        if (!mappa.verificaModalitaAccesso(stanzaCorrente, direzione, ModalitaDiAccesso.APERTO) && !mappa.verificaModalitaAccesso(stanzaCorrente, direzione, ModalitaDiAccesso.SCALE)) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASTANZAINACCESSIBILE.ordinal()));
            return;
        }
        if (!this.giocatore.isTutaIntegra() && mappa.getStanzaPerDirezione(stanzaCorrente, direzione).isRadioattiva()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASTANZARADIOATTIVA.ordinal()));
            return;
        }
        if (!this.giocatore.isCodiceScoperto() && mappa.getStanzaPerDirezione(stanzaCorrente, direzione).getId() == Stanze.DEPOSITO) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICACODICENECESSARIO.ordinal()));
            return;
        }
        this.giocatore.spostatiVerso(direzione);
        stanzaCorrente = this.giocatore.getStanzaCorrente();
        if (stanzaCorrente.getId() == Stanze.SALAPOMPE && !stanzaCorrente.isVisitata()) {
            this.giocatore.setTutaIntegra(false);
            outputComando.setStringaDaStampare(stringhe.get(Output.EVENTOTUTAROTTA.ordinal()) + stanzaCorrente.getDescrizione());
            outputComando.setAzione(AzioneSuInterfaccia.TUTAROTTA);
            stanzaCorrente.setVisitata(true);
            return;
        }
        if (!stanzaCorrente.isVisitata()){
            stanzaCorrente.setVisitata(true);
            outputComando.setStringaDaStampare(stanzaCorrente.getDescrizione());
        } else {
            outputComando.setStringaDaStampare(stanzaCorrente.getBenvenuto());
        }
        
        if (stanzaCorrente.getEsposizRadioattiva() != livelloRadioattivitaPrecedente) {
            switch (stanzaCorrente.getEsposizRadioattiva()) {
                case BASSO:
                    outputComando.setAzione(AzioneSuInterfaccia.RADIAZIONIBASSE);
                    break;
                case MEDIO:
                    outputComando.setAzione(AzioneSuInterfaccia.RADIAZIONIMEDIE);
                    break;
                case ELEVATO:
                    outputComando.setAzione(AzioneSuInterfaccia.RADIAZIONIELEVATE);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Metodo che gestisce l'osservazione della stanza corrente.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciOsserva(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getOsserva());
    }

    /**
     * Metodo che gestisce l'uso degli oggetti.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciUso(final int tipoComando, final OutputParser outputComando) {
        Stanza stanzaCorrente = this.giocatore.getStanzaCorrente();
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        switch (tipoComando) {
            case 11: // apri armadietto destro
                if (stanzaCorrente.getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).isAperto()) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAARMADIETTOGIAAPERTO.ordinal()));
                    return;
                }
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).setAperto(true);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAARMADIETTOAPERTO.ordinal())
                        + this.stringhe.get(Output.OSSERVAARMADIETTODESTROAPERTO.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASPOGLIATOIOARMADIETTOAPERTO.ordinal()));
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).setDescrizione(this.stringhe.get(Output.OSSERVAARMADIETTODESTROAPERTO.ordinal()));
                break;
            case 12: // usa tesserino
                if (stanzaCorrente.getId() != Stanze.ATRIO && stanzaCorrente.getId() != Stanze.CORRIDOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONUTILE.ordinal()));
                    return;
                }
                if (!this.isOggettoPresenteInInventario(Items.TESSERINO, outputComando)) {
                    return;
                }
                if (stanzaCorrente.getId() == Stanze.ATRIO) {
                    if (this.giocatore.getMappa().verificaModalitaAccesso(stanzaCorrente, Direzione.NORD, ModalitaDiAccesso.APERTO)) {
                        outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAPORTAGIAAPERTA.ordinal()));
                        return;
                    }
                    this.giocatore.getMappa().cambiaModalitaDiAccesso(stanzaCorrente, Direzione.NORD, ModalitaDiAccesso.APERTO);
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOPORTAAPERTA.ordinal()));
                    stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVAATRIOAPERTO.ordinal()));
                }
                if (stanzaCorrente.getId() == Stanze.CORRIDOIO){
                    if (this.giocatore.getMappa().verificaModalitaAccesso(stanzaCorrente, Direzione.EST, ModalitaDiAccesso.APERTO)) {
                        outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAPORTAGIAAPERTA.ordinal()));
                        return;
                    }
                    this.giocatore.getMappa().cambiaModalitaDiAccesso(stanzaCorrente, Direzione.EST, ModalitaDiAccesso.APERTO);
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOPORTAAPERTA.ordinal()));
                    stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVACORRIDOIOAPERTO.ordinal()));
                }
                break;
            case 13: // usa cacciavite
                if (stanzaCorrente.getId() != Stanze.ANTICAMERASALAPOMPE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONUTILE.ordinal()));
                    return;
                }
                if (!this.isOggettoPresenteInInventario(Items.CACCIAVITE, outputComando)) {
                    return;
                }
                if (this.giocatore.getMappa().verificaModalitaAccesso(stanzaCorrente, Direzione.NORD, ModalitaDiAccesso.APERTO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICACONDOTTOGIAAPERTO.ordinal()));
                    return;
                }
                this.giocatore.getMappa().cambiaModalitaDiAccesso(stanzaCorrente, Direzione.NORD, ModalitaDiAccesso.APERTO);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOGRATAAPERTA.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVAANTICAMERASALAPOMPEAPERTO.ordinal()));
                break;
            case 14: // usa telecomando
                if (stanzaCorrente.getId() != Stanze.ANTICAMERASALAVAPORE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONUTILE.ordinal()));
                    return;
                }
                if (!this.isOggettoPresenteInInventario(Items.TELECOMANDO, outputComando)) {
                    return;
                }
                if (this.giocatore.getMappa().verificaModalitaAccesso(stanzaCorrente, Direzione.GIU, ModalitaDiAccesso.APERTO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAASCENSOREGIAAPERTO.ordinal()));
                    return;
                }
                this.giocatore.getMappa().cambiaModalitaDiAccesso(stanzaCorrente, Direzione.GIU, ModalitaDiAccesso.APERTO);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAASCENSOREAPERTO.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVAANTICAMERASALAVAPOREAPERTO.ordinal()));
                break;
            case 15: // usa chiave
                if (stanzaCorrente.getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONUTILE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.CHIAVE)) {
                    outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICACHIAVENONINPOSSESSO.ordinal()));
                    return;
                }
                if (stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).isAperto()) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAARMADIETTOGIAAPERTO.ordinal()));
                    return;
                }
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).setAperto(true);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAARMADIETTOAPERTO.ordinal())
                        + this.stringhe.get(Output.OSSERVAARMADIETTOSINISTROAPERTO.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASPOGLIATOIOARMADIETTIAPERTI.ordinal()));
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).setDescrizione(this.stringhe.get(Output.OSSERVAARMADIETTOSINISTROAPERTO.ordinal()));
                break;
            case 16: // usa foglio e torcia
                if (!this.giocatore.getInventario().contieneItem(Items.FOGLIO) || !this.giocatore.getInventario().contieneItem(Items.TORCIA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTINONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOSCOPERTACODICE.ordinal()));
                this.giocatore.setCodiceScoperto(true);
                this.giocatore.getMappa().cambiaModalitaDiAccesso(this.giocatore.getMappa().getStanzaPerId(Stanze.ANTICAMERADEPOSITO), Direzione.SUD, ModalitaDiAccesso.APERTO);
                break;
            case 17: // usa torcia
                if (!this.isOggettoPresenteInInventario(Items.TORCIA, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVATORCIA.ordinal()));
                break;
            case 18: // usa foglio
                if (!this.isOggettoPresenteInInventario(Items.FOGLIO, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVAFOGLIO.ordinal()));
                break;
            case 19: // apri armadietto non specificato
                if (stanzaCorrente.getId() != Stanze.SPOGLIATOIO){
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAARMADIETTONONSPECIFICATO.ordinal()));
                break;
        }
    }

    /**
     * Metodo che gestisce l'inventario del giocatore.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciInventario(final int tipoComando, final OutputParser outputComando) {
        Stanza stanzaCorrente = this.giocatore.getStanzaCorrente();
        Item itemRaccolto = null;
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        switch(tipoComando) {
            case 21: // prendi tesserino
                if (stanzaCorrente.getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).isAperto()) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.TESSERINO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).rimuoviItem(Items.TESSERINO);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATESSERINOPRESO.ordinal()));
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).setDescrizione(this.stringhe.get(Output.OSSERVAARMADIETTO.ordinal()));
                break;
            case 22: // prendi cacciavite
                if (stanzaCorrente.getId() != Stanze.SALAVAPORE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.CACCIAVITE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.CACCIAVITE);
                stanzaCorrente.rimuoviItem(Items.CACCIAVITE);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICACACCIAVITEPRESO.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAVAPORESENZACACCIAVITE.ordinal()));
                break;
            case 23: // prendi telecomando
                if (stanzaCorrente.getId() != Stanze.SALAPOMPE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.TELECOMANDO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.TELECOMANDO);
                stanzaCorrente.rimuoviItem(Items.TELECOMANDO);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATELECOMANDOPRESO.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAPOMPESENZATELECOMANDO.ordinal()));
                break;
            case 24: // prendi chiave
                if (stanzaCorrente.getId() != Stanze.SALAMACCHINE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.CHIAVE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.CHIAVE);
                stanzaCorrente.rimuoviItem(Items.CHIAVE);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICACHIAVEPRESA.ordinal()));
                if (stanzaCorrente.contieneItem(Items.FOGLIO)) 
                    stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAMACCHINESENZACHIAVE.ordinal()));
                else
                    stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAMACCHINEVUOTA.ordinal()));
                break;
            case 25: // prendi foglio
                if (stanzaCorrente.getId() != Stanze.SALAMACCHINE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.FOGLIO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.FOGLIO);
                stanzaCorrente.rimuoviItem(Items.FOGLIO);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAFOGLIOPRESO.ordinal()));
                if (stanzaCorrente.contieneItem(Items.CHIAVE)) 
                    stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAMACCHINESENZAFOGLIO.ordinal()));
                else
                    stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAMACCHINEVUOTA.ordinal()));
                break;
            case 26: // prendi torcia
                if (stanzaCorrente.getId() != Stanze.SALAREATTORE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.TORCIA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.TORCIA);
                stanzaCorrente.rimuoviItem(Items.TORCIA);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATORCIAPRESA.ordinal()));
                stanzaCorrente.setOsserva(this.stringhe.get(Output.OSSERVASALAREATTORESENZATORCIA.ordinal()));
                break;
            case 27: // prendi tuta
                if (stanzaCorrente.getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).isAperto()) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (this.giocatore.getInventario().contieneItem(Items.TUTA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).rimuoviItem(Items.TUTA);
                this.giocatore.setTutaIntegra(true);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATUTAINDOSSATA.ordinal()));
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).setDescrizione(this.stringhe.get(Output.OSSERVAARMADIETTO.ordinal()));
                outputComando.setAzione(AzioneSuInterfaccia.TUTAINTEGRA);    
        }
    }

    /**
     * Metodo che gestisce il finale del gioco.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciFinale(final int tipoComando, final OutputParser outputComando) {
        switch (tipoComando) {
            case 53: // prendi uranio
                if (verificaUranio()) {
                    outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
                    return;
                }
                if (this.giocatore.getStanzaCorrente().getId() != Stanze.DEPOSITO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOSCELTA.ordinal()));
                this.giocatore.setUranioPreso(true);
                break;
            case 54: // utente scrive sì
                if (!verificaUranio()) {
                    outputComando.setStringaDaStampare("Eh??\n\n");
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOFINALECONURANIO.ordinal()));
                outputComando.setAzione(AzioneSuInterfaccia.FINE);
                break;
            case 55: // utente scrive no
                if (!verificaUranio()) {
                    outputComando.setStringaDaStampare("Eh??\n\n");
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOFINALESENZAURANIO.ordinal()));
                outputComando.setAzione(AzioneSuInterfaccia.FINE);
                break;
        }
    }

    /**
     * Metodo che gestisce l'osservazione degli oggetti.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciOsservaItem(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        switch(tipoComando) {
            case 43: // osserva tesserino
                if (!this.isOggettoPresenteInInventario(Items.TESSERINO, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getInventario().getItemPerId(Items.TESSERINO).getDescrizione());
                break;
            case 44: // osserva cacciavite
                if (!this.isOggettoPresenteInInventario(Items.CACCIAVITE, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getInventario().getItemPerId(Items.CACCIAVITE).getDescrizione());
                break;
            case 45: // osserva telecomando
                if (!this.isOggettoPresenteInInventario(Items.TELECOMANDO, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getInventario().getItemPerId(Items.TELECOMANDO).getDescrizione());
                break;
            case 46: // osserva chiave
                if (!this.isOggettoPresenteInInventario(Items.CHIAVE, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getInventario().getItemPerId(Items.CHIAVE).getDescrizione());
                break;
            case 47: // osserva foglio
                if (!this.isOggettoPresenteInInventario(Items.FOGLIO, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getInventario().getItemPerId(Items.FOGLIO).getDescrizione());
                break;
            case 48: // osserva torcia
                if (!this.isOggettoPresenteInInventario(Items.TORCIA, outputComando)) {
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getInventario().getItemPerId(Items.TORCIA).getDescrizione());
                break;
            case 49: // osserva tuta
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVATUTA.ordinal()));
                break;
            case 50: // osserva armadietto
                if (this.giocatore.getStanzaCorrente().getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAARMADIETTONONSPECIFICATO.ordinal()));
                break;
            case 51: // osserva armadietto destro
                if (this.giocatore.getStanzaCorrente().getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getItemContenitorePerId(Items.ARMADIETTODESTRO).getDescrizione());
                break;
            case 52: // osserva armadietto sinistro
                if (this.giocatore.getStanzaCorrente().getId() != Stanze.SPOGLIATOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getItemContenitorePerId(Items.ARMADIETTOSINISTRO).getDescrizione());
                break;
        }
    }

    /**
     * Metodo che gestisce il comando per sapere dove si trova il giocatore.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciDove(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getBenvenuto());
    }

    /**
     * Metodo che gestisce la stampa dell'inventario del giocatore.
     * @param tipoComando il tipo di comando
     * @param outputComando l'output del comando
     */
    private void gestisciStampaInventario(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        outputComando.setStringaDaStampare(this.giocatore.getInventario().toString());
    }

    /**
     * Metodo che verifica se l'oggetto è presente nell'inventario del giocatore.
     * @param oggetto l'oggetto da verificare
     * @param outputComando l'output del comando
     * @return true se l'oggetto è presente nell'inventario
     */
    private boolean isOggettoPresenteInInventario(final Items oggetto, final OutputParser outputComando) {
        if (!this.giocatore.getInventario().contieneItem(oggetto)) {
            outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
            return false;
        }
        return true;
    }
    
    /**
     * Metodo che restituisce il codice del comando inserito.
     * @param comando il comando inserito dall'utente
     * @return il codice del comando
     */
    private String ottieniCodiceComando(final String comando) {
        String codiceComando = "";
        Scanner scanComando = new Scanner(comando.replaceAll("\\p{Punct}", " ").trim());
        scanComando.useDelimiter("\\s+");
        
        Function<String, String> codiceMapper = this.vocabolario::get;
        codiceComando = scanComando.tokens().map(codiceMapper)
                .collect(Collectors.joining("", codiceComando, ""));
        scanComando.close();
        return codiceComando;
    }

    /**
     * Metodo che verifica se il giocatore ha preso l'uranio.
     * @return true se il giocatore ha preso l'uranio, false altrimenti
     */
    private boolean verificaUranio() {
        return this.giocatore.isUranioPreso();
    }

    /**
     * Metodo che resituisce l'introduzione del gioco.
     * @return l'oggetto OutputParser iniziale
     */
    public OutputParser getIntroduzione() {
        OutputParser outputComando = new OutputParser();
        outputComando.setStringaDaStampare(this.stringhe.get(Output.INTRODUZIONE.ordinal()) + this.stringhe.get(Output.DESCRIZIONECORTILE.ordinal()));
        return outputComando;
    }
}

