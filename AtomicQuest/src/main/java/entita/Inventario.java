
package entita;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class Inventario {
    
    private Set<Item> items = new TreeSet<>();

    public Inventario() {
    }

    public void aggiungiItem(final Item item) {
        this.items.add(item);
    }

    public void rimuoviItem(final Item item) {
        this.items.remove(item);
    }

    public Iterator<Item> iteratore() {
        return this.items.iterator();
    }
}
