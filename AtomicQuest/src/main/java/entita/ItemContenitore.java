
package entita;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class ItemContenitore extends Item {

    private Set<Item> itemsContenuti = new TreeSet<>();

    public ItemContenitore(final String id, final String nome, final String descrizione,
            final boolean prendibile, final boolean apribile, final boolean aperto) {
        super(id, nome, descrizione, prendibile, apribile, aperto);
    }

    public void aggiungiItem(final Item item) {
        this.itemsContenuti.add(item);
    }

    public void rimuoviItem(final Item item) {
        this.itemsContenuti.remove(item);
    }

    public Iterator<Item> iteratore() {
        return this.itemsContenuti.iterator();
    }
}
