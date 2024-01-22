
package entita;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;



public class ItemContenitore extends Item {

    private Set<Item> itemsContenuti = new TreeSet<>();
    

    public ItemContenitore(String id, String nome, String descrizione, boolean prendibile, boolean apribile, boolean aperto) {
        super(id, nome, descrizione, prendibile, apribile, aperto);
    }
    
    
    public void aggiungiItem(final Item item) {
        this.itemsContenuti.add(item);
    }
    
    public void togliItem(final Item item) {
        this.itemsContenuti.remove(item);
    }
    
    
    public Iterator<Item> iteratore() {
        return this.itemsContenuti.iterator();
    }
}
