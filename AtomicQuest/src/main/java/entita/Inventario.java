
package entita;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class Inventario implements Serializable {
    
    private Set<Item> items = new TreeSet<>();

    public Inventario() {
    }

    public void aggiungiItem(final Item item) {
        this.items.add(item);
    }

    public void rimuoviItem(final Items id) {
        this.items.removeIf(i -> i.getId() == id);
    }

    public Iterator<Item> iteratore() {
        return this.items.iterator();
    }

    public Item getItemPerId(final Items item) {
        for (Item i : this.items) {
            if (i.getId() == item){
                return i;
            }
        }
        return null;
    }

    public boolean contieneItem(final Items id) {
        return this.getItemPerId(id) != null;
    }

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
