
package entita;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che rappresenta una stanza generica.
 */
public class Room implements Serializable {

    private final String descrizione;
    private String osserva;
    private final String benvenuto;
    private final Set<Item> itemPresenti = new TreeSet<>();
    private final Set<ItemContenitore> itemContenitoriPresenti = new TreeSet<>();

    /**
     * Costruttore di default.
     * @param descrizione la descrizione della stanza
     * @param osserva la descrizione della stanza quando si osserva
     * @param benvenuto il messaggio di benvenuto della stanza
     */
    public Room(final String descrizione, final String osserva, final String benvenuto) {

        this.descrizione = descrizione;
        this.osserva = osserva;
        this.benvenuto = benvenuto;
    }

    /**
     * Metodo che restituisce la descrizione della stanza.
     */
    public String getDescrizione() {
        return this.descrizione;
    }

    /**
     * Metodo che restituisce il messaggio di benvenuto della stanza.
     */
    public String getBenvenuto() {
        return this.benvenuto;
    }

    /**
     * Metodo che restituisce la descrizione della stanza quando si osserva.
     */
    public String getOsserva() {
        return osserva;
    }
    
    /**
     * Metodo che imposta la descrizione della stanza quando si osserva.
     * @param osserva la descrizione della stanza quando si osserva
     */
    public void setOsserva(final String osserva) {
        this.osserva = osserva;
    }

    /**
     * Metodo che aggiunge un item alla stanza.
     * @param item l'item da aggiungere
     */
    public void aggiungiItem(final Item item) {
        this.itemPresenti.add(item);
    }

    /**
     * Metodo che rimuove un item dalla stanza.
     * @param id l'id dell'item da rimuovere
     */
    public void rimuoviItem(final Items id) {
        this.itemPresenti.removeIf(i -> i.getId() == id);
    }

    /**
     * Metodo che restituisce l'iteratore degli item presenti nella stanza.
     */
    public Iterator<Item> iteratoreItem() {
        return this.itemPresenti.iterator();
    }
    
    /**
     * Metodo che aggiunge un item contenitore alla stanza.
     * @param itemContenitore l'item contenitore da aggiungere
     */
    public void aggiungiItemContenitore(final ItemContenitore itemContenitore) {
        this.itemContenitoriPresenti.add(itemContenitore);
    }

    /**
     * Metodo che restituisce l'iteratore degli item contenitori presenti nella stanza.
     */
    public Iterator<ItemContenitore> iteratoreItemContenitore() {
        return this.itemContenitoriPresenti.iterator();
    }

}
