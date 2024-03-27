
package entita;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che rappresenta un item contenitore.
 */
public class ItemContenitore extends Item implements Serializable {

    private Set<Item> itemsContenuti = new TreeSet<>();
    private boolean aperto;

    /**
     * Costruttore di default.
     * @param id l'id dell'item
     * @param nome il nome dell'item
     * @param descrizione la descrizione dell'item
     * @param prendibile true se l'item è prendibile, false altrimenti
     * @param aperto true se l'item è aperto, false altrimenti
     */
    public ItemContenitore(final Items id, final String nome, final String descrizione,
            final boolean prendibile, boolean aperto) {
        super(id, nome, descrizione, prendibile);
        this.aperto = aperto;
    }

    /**
     * Metodo che aggiunge un item all'item contenitore.
     * @param item l'item da aggiungere
     */
    public void aggiungiItem(final Item item) {
        this.itemsContenuti.add(item);
    }

    /**
     * Metodo che rimuove un item dall'item contenitore.
     * @param id l'id dell'item da rimuovere
     */
    public Item rimuoviItem(final Items id) {
        Iterator<Item> it = this.itemsContenuti.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getId() == id) {
                it.remove();
                return i;
            }
        }
        return null;
    }

    /**
     * Metodo che restituisce true se l'item è aperto, false altrimenti.
     */
    public boolean isAperto() {
        return this.aperto;
    }

    /**
     * Metodo che imposta l'item come aperto.
     * @param aperto true se l'item è aperto, false altrimenti
     */
    public void setAperto(final boolean aperto) {
        this.aperto = aperto;
    }

    /**
     * Metodo che restituisce l'iteratore degli item contenuti.
     */
    public Iterator<Item> iteratore() {
        return this.itemsContenuti.iterator();
    }
}
