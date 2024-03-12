
package entita;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class ItemContenitore extends Item {

    private Set<Item> itemsContenuti = new TreeSet<>();
    private boolean aperto;

    public ItemContenitore(final Items id, final String descrizione,
            final boolean prendibile, boolean aperto) {
        super(id, descrizione, prendibile);
        this.aperto = aperto;
    }

    public void aggiungiItem(final Item item) {
        this.itemsContenuti.add(item);
    }

    public void rimuoviItem(final Item item) {
        this.itemsContenuti.remove(item);
    }

    public boolean isAperto() {
        return this.aperto;
    }

    public void setAperto(final boolean aperto) {
        this.aperto = aperto;
    }

    public Iterator<Item> iteratore() {
        return this.itemsContenuti.iterator();
    }
}
