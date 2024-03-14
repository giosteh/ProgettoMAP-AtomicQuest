
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


public class Parser {

    interface ValidatoreComando {
        boolean isValido(String comando);
    }

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
    private final RiconoscitoreComando riconoscitoreComandoFinale = (a) -> (a >= 51 && a <= 53);
    private final RiconoscitoreComando riconoscitoreComandoOsservaItem = (a) -> (a >= 43 && a <= 50);
    private final RiconoscitoreComando riconoscitoreComandoDove = (a) -> (a == 54);
    private final RiconoscitoreComando riconoscitoreComandoStampaInventario = (a) -> (a == 55);

    public Parser(Giocatore giocatore) {
        this.giocatore = giocatore;
        this.vocabolario = GestioneFile.caricaMap("vocabolario.dat");
        this.comandi = GestioneFile.caricaMap("comandi.dat");
        this.stringhe = GestioneFile.caricaList("stringhe.dat");
    }

    public OutputParser analizzaComando(final String comando) {
        String codiceComando = this.ottieniCodiceComando(comando.toLowerCase());
        ValidatoreComando validatore = (a) -> (this.comandi.get(a) != null);
        OutputParser outputComando = new OutputParser();
        if (!validatore.isValido(comando)) {
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
        if (!mappa.verificaModalitaAccesso(stanzaCorrente, direzione, ModalitaDiAccesso.APERTO)) {
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
            outputComando.setStringaDaStampare(stringhe.get(Output.EVENTOTUTAROTTA.ordinal()));
            outputComando.setAzione(AzioneSuInterfaccia.TUTAROTTA);
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

    private void gestisciOsserva(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getOsserva());
    }

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
                break;
            case 12: // usa tesserino
                if (stanzaCorrente.getId() != Stanze.ATRIO && stanzaCorrente.getId() != Stanze.CORRIDOIO) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONUTILE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.TESSERINO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
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
            case 13: //usa cacciavite
                if (stanzaCorrente.getId() != Stanze.ANTICAMERASALAPOMPE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONUTILE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.CACCIAVITE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
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
                if (!this.giocatore.getInventario().contieneItem(Items.TELECOMANDO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                if (this.giocatore.getMappa().verificaModalitaAccesso(stanzaCorrente, Direzione.NORD, ModalitaDiAccesso.APERTO)) {
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
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
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
                break;
            case 16: // usa foglio e torcia
                if (!this.giocatore.getInventario().contieneItem(Items.FOGLIO) || !this.giocatore.getInventario().contieneItem(Items.TORCIA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTINONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOSCOPERTACODICE.ordinal()));
                this.giocatore.setCodiceScoperto(true);
                this.giocatore.getMappa().cambiaModalitaDiAccesso(this.giocatore.getMappa().getStanzaPerId(Stanze.DEPOSITO), Direzione.SUD, ModalitaDiAccesso.APERTO);
                break;
            case 17: // usa torcia
                if (!this.giocatore.getInventario().contieneItem(Items.TORCIA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVATORCIA.ordinal()));
                break;
            case 18: // usa foglio
                if (!this.giocatore.getInventario().contieneItem(Items.FOGLIO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVAFOGLIO.ordinal()));
                break;
        }
    }

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
                if (!this.giocatore.getInventario().contieneItem(Items.TESSERINO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTODESTRO).rimuoviItem(Items.TESSERINO);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATESSERINOPRESO.ordinal()));
                break;
            case 22: // prendi cacciavite
                if (stanzaCorrente.getId() != Stanze.SALAVAPORE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.CACCIAVITE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.CACCIAVITE);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICACACCIAVITEPRESO.ordinal()));
                break;
            case 23: // prendi telecomando
                if (stanzaCorrente.getId() != Stanze.SALAPOMPE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.TELECOMANDO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.TELECOMANDO);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATELECOMANDOPRESO.ordinal()));
                break;
            case 24: // prendi chiave
                if (stanzaCorrente.getId() != Stanze.SALAMACCHINE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.CHIAVE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.CHIAVE);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICACHIAVEPRESA.ordinal()));
                break;
            case 25: // prendi foglio
                if (stanzaCorrente.getId() != Stanze.SALAMACCHINE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.FOGLIO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.FOGLIO);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAFOGLIOPRESO.ordinal()));
                break;
            case 26: // prendi torcia
                if (stanzaCorrente.getId() != Stanze.SALAREATTORE) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONPRESENTE.ordinal()));
                    return;
                }
                if (!this.giocatore.getInventario().contieneItem(Items.TORCIA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                itemRaccolto = stanzaCorrente.getItemPerId(Items.TORCIA);
                this.giocatore.getInventario().aggiungiItem(itemRaccolto);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATORCIAPRESA.ordinal()));
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
                if (!this.giocatore.getInventario().contieneItem(Items.TUTA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTOGIAPRESENTEININVENTARIO.ordinal()));
                    return;
                }
                stanzaCorrente.getItemContenitorePerId(Items.ARMADIETTOSINISTRO).rimuoviItem(Items.TUTA);
                this.giocatore.setTutaIntegra(true);
                outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICATUTAINDOSSATA.ordinal()));
                outputComando.setAzione(AzioneSuInterfaccia.TUTAINTEGRA);    
        }
    }

    private void gestisciFinale(final int tipoComando, final OutputParser outputComando) {
        switch (tipoComando) {
            case 51: // prendi uranio
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
            case 52: // utente scrive sì
                if (!verificaUranio()) {
                    outputComando.setStringaDaStampare("Eh??\n\n");
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOFINALECONURANIO.ordinal()));
                outputComando.setAzione(AzioneSuInterfaccia.FINE);
                break;
            case 53: // utente scrive no
                if (!verificaUranio()) {
                    outputComando.setStringaDaStampare("Eh??\n\n");
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.EVENTOFINALESENZAURANIO.ordinal()));
                outputComando.setAzione(AzioneSuInterfaccia.FINE);
                break;
        }
    }

    private void gestisciOsservaItem(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        switch(tipoComando) {
            case 43: // osserva tesserino
                if (!this.giocatore.getInventario().contieneItem(Items.TESSERINO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVATESSERINO.ordinal()));
                break;
            case 44: // osserva cacciavite
                if (!this.giocatore.getInventario().contieneItem(Items.CACCIAVITE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVACACCIAVITE.ordinal()));
                break;
            case 45: // osserva telecomando
                if (!this.giocatore.getInventario().contieneItem(Items.TELECOMANDO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVATELECOMANDO.ordinal()));
                break;
            case 46: // osserva chiave
                if (!this.giocatore.getInventario().contieneItem(Items.CHIAVE)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVACHIAVE.ordinal()));
                break;
            case 47: // osserva foglio
                if (!this.giocatore.getInventario().contieneItem(Items.FOGLIO)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVACHIAVE.ordinal()));
                break;
            case 48: // osserva torcia
                if (!this.giocatore.getInventario().contieneItem(Items.TORCIA)) {
                    outputComando.setStringaDaStampare(this.stringhe.get(Output.NOTIFICAOGGETTONONINPOSSESSO.ordinal()));
                    return;
                }
                outputComando.setStringaDaStampare(this.stringhe.get(Output.OSSERVATORCIA.ordinal()));
                break;
        }
    }

    private void gestisciDove(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getBenvenuto());
    }

    private void gestisciStampaInventario(final int tipoComando, final OutputParser outputComando) {
        if (verificaUranio()) {
            outputComando.setStringaDaStampare(stringhe.get(Output.NOTIFICASCELTAOBBLIGATORIA.ordinal()));
            return;
        }
        outputComando.setStringaDaStampare(this.giocatore.getInventario().toString());
    }
    
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

    private boolean verificaUranio() {
        return this.giocatore.isUranioPreso();
    }

    public OutputParser getIntroduzione() {
        OutputParser outputComando = new OutputParser();
        outputComando.setStringaDaStampare(this.stringhe.get(Output.INTRODUZIONE.ordinal()) + this.stringhe.get(Output.DESCRIZIONECORTILE.ordinal()));
        return outputComando;
    }
}

