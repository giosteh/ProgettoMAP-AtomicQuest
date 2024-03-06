
package controller;

import entita.Giocatore;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Parser {

    interface ValidatoreComando {
        boolean isValido(String comando);
    }

    private final Giocatore giocatore;
    private final Map<String, String> vocabolario;
    private final Map<String, Integer> comandi;

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
        }
        return outputComando;
    }

    private void gestisciSpostamento(final int tipoComando, final OutputParser outputComando) {

    }

    private void gestisciOsserva(final int tipoComando, final OutputParser outputComando) {
        
    }

    private void gestisciUso(final int tipoComando, final OutputParser outputComando) {
        
    }

    private void gestisciInventario(final int tipoComando, final OutputParser outputComando) {
        
    }

    private void gestisciFinale(final int tipoComando, final OutputParser outputComando) {
        
    }
    
    private String ottieniCodiceComando(final String comando) {
        String codiceComando = "";
        Scanner scanComando = new Scanner(comando.trim().replaceAll("[\\p{Punct}&&[^']]", ""));
        scanComando.useDelimiter("\\s+|'");
        
        Function<String, String> codiceMapper = this.vocabolario::get;
        codiceComando = scanComando.tokens().map(codiceMapper)
                .collect(Collectors.joining("", codiceComando, ""));
        return codiceComando;
    }
}
