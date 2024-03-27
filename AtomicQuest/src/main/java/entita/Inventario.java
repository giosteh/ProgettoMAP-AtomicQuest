
package entita;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che rappresenta l'inventario del giocatore.
 */
public class Inventario implements Serializable {
    
    private Set<Item> items = new TreeSet<>();

    /**
     * Costruttore di default.
     */
    public Inventario() {
    }

    /**
     * Metodo che aggiunge un item all'inventario.
     * @param item l'item da aggiungere
     */
    public void aggiungiItem(final Item item) {
        this.items.add(item);
    }

    /**
     * Metodo che rimuove un item dall'inventario.
     * @param id l'id dell'item da rimuovere
     */
    public void rimuoviItem(final Items id) {
        this.items.removeIf(i -> i.getId() == id);
    }

    /**
     * Metodo che restituisce l'iteratore degli item.
     */
    public Iterator<Item> iteratore() {
        return this.items.iterator();
    }

    /**
     * Metodo che restituisce l'item corrispondente all'id.
     * @param item l'id dell'item
     */
    public Item getItemPerId(final Items item) {
        for (Item i : this.items) {
            if (i.getId() == item){
                return i;
            }
        }
        return null;
    }

    /**
     * Metodo che restituisce true se l'inventario contiene un item, false altrimenti.
     * @param id l'id dell'item
     */
    public boolean contieneItem(final Items id) {
        return this.getItemPerId(id) != null;
    }

    /**
     * Metodo che restituisce la stringa rappresentante l'inventario.
     */
    public String toString() {
        if (this.items.isEmpty()) {
            return "Non hai nulla nel tuo inventario.\n\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nel tuo inventario hai:\n");
        for (Item i : this.items) {
            sb.append(i.toString() + ", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.append(".\n\n").toString();
    }
}
