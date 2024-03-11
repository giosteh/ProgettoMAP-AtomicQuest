
package controller;

import entita.Direzione;
import entita.Giocatore;
import entita.LivelloRadioattivita;
import entita.Mappa;
import entita.ModalitaDiAccesso;
import entita.Stanza;
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
    private final RiconoscitoreComando riconoscitoreComandoSpostamento = (a) -> (a >= 1 && a <= 10);
    private final RiconoscitoreComando riconoscitoreComandoOsserva = (a) -> (a == 41);
    private final RiconoscitoreComando riconoscitoreComandoUso = (a) -> (a >= 11 && a <= 20);
    private final RiconoscitoreComando riconoscitoreComandoInventario = (a) -> (a >= 21 && a <= 40);
    private final RiconoscitoreComando riconoscitoreComandoFinale = (a) -> (a == 42);


    public Parser(Giocatore giocatore) {
        this.giocatore = giocatore;
        // caricamento delle hashmap
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
            }
        }
        return outputComando;
    }

    private void gestisciSpostamento(final int tipoComando, final OutputParser outputComando) {
        Direzione direzione = null;
        Stanza stanzaCorrente = this.giocatore.getStanzaCorrente();
        LivelloRadioattivita livelloRadioattivitaPrecedente = stanzaCorrente.getEsposizRadioattiva();
        Mappa mappa = this.giocatore.getMappa();
        if (tipoComando >=1 && tipoComando <= 6) {
            direzione = Direzione.values()[tipoComando - 1];
        } else if (tipoComando == 7) {
            if (stanzaCorrente.getId().equals("006")) {
                direzione = Direzione.GIU;
            } else if (stanzaCorrente.getId().equals("008")) {
                direzione = Direzione.SU;
            } else {
                outputComando.setStringaDaStampare("Non c'è nessun ascensore qui.\n\n");
                return;
            }
        } else if (tipoComando == 8) {
            if (stanzaCorrente.getId().equals("013")) {
                direzione = Direzione.GIU;
            } else if (stanzaCorrente.getId().equals("014")) {
                direzione = Direzione.SU;
            } else {
                outputComando.setStringaDaStampare("Non ci sono scale qui.\n\n");
                return;
            }
        }
        if (!mappa.esisteStanzaSuccessiva(stanzaCorrente, direzione)) {
            outputComando.setStringaDaStampare("Non puoi andare in quella direzione.\n\n");
            return;
        }
        if (!mappa.verificaModalitaAccesso(stanzaCorrente, direzione, ModalitaDiAccesso.APERTO)) {
            outputComando.setStringaDaStampare("Non puoi accedere a questa stanza.\n\n");
            return;
        }
        if (!this.giocatore.isTutaIntegra() && mappa.getStanzaPerDirezione(stanzaCorrente, direzione).isRadioattiva()) {
            outputComando.setStringaDaStampare("Non puoi entrare in una stanza radioattiva con la tuta danneggiata.\n\n");
            return;
        }
        this.giocatore.spostatiVerso(direzione);
        stanzaCorrente = this.giocatore.getStanzaCorrente();
        if (stanzaCorrente.getId().equals("005") && !stanzaCorrente.isVisitata()) {
            this.giocatore.setTutaIntegra(false);
            outputComando.setStringaDaStampare("La tua tuta si è danneggiata.\n\n");
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
        outputComando.setStringaDaStampare(this.giocatore.getStanzaCorrente().getOsserva());
    }

    private void gestisciUso(final int tipoComando, final OutputParser outputComando) {
        
    }

    private void gestisciInventario(final int tipoComando, final OutputParser outputComando) {
        
    }

    private void gestisciFinale(final int tipoComando, final OutputParser outputComando) {
        
    }
    
    private String ottieniCodiceComando(final String comando) {
        String codiceComando = "";
        Scanner scanComando = new Scanner(comando.replaceAll("\\p{Punct}", " ").trim());
        scanComando.useDelimiter("\\s+");
        
        Function<String, String> codiceMapper = this.vocabolario::get;
        codiceComando = scanComando.tokens().map(codiceMapper)
                .collect(Collectors.joining("", codiceComando, ""));
        return codiceComando;
    }
}
