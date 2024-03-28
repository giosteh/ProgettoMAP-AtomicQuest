
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
    private final Set<Item> itemsPresenti = new TreeSet<>();
    private final Set<ItemContenitore> itemsContenitoriPresenti = new TreeSet<>();

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
     * @return descrizione
     */
    public String getDescrizione() {
        return this.descrizione;
    }

    /**
     * Metodo che restituisce il messaggio di benvenuto della stanza.
     * @return benvenuto
     */
    public String getBenvenuto() {
        return this.benvenuto;
    }

    /**
     * Metodo che restituisce la descrizione della stanza quando si osserva.
     * @return osserva
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
        this.itemsPresenti.add(item);
    }

    /**
     * Metodo che rimuove un item dalla stanza.
     * @param id l'id dell'item da rimuovere
     */
    public void rimuoviItem(final Items id) {
        this.itemsPresenti.removeIf(i -> i.getId() == id);
    }

    /**
     * Metodo che restituisce l'iteratore degli item presenti nella stanza.
     * @return itemsPresenti.iterator
     */
    public Iterator<Item> iteratoreItem() {
        return this.itemsPresenti.iterator();
    }
    
    /**
     * Metodo che aggiunge un item contenitore alla stanza.
     * @param itemContenitore l'item contenitore da aggiungere
     */
    public void aggiungiItemContenitore(final ItemContenitore itemContenitore) {
        this.itemsContenitoriPresenti.add(itemContenitore);
    }

    /**
     * Metodo che restituisce l'iteratore degli item contenitori presenti nella stanza.
     * @return itemsContenitoriPresenti.iterator
     */
    public Iterator<ItemContenitore> iteratoreItemContenitore() {
        return this.itemsContenitoriPresenti.iterator();
    }

}
