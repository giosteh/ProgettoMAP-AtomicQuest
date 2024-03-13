
package entita;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


public class Room {

    private final String descrizione;
    private String osserva;
    private final String benvenuto;
    private final Set<Item> itemPresenti = new TreeSet<>();
    private final Set<ItemContenitore> itemContenitoriPresenti = new TreeSet<>();

    public Room(final String descrizione, final String osserva, final String benvenuto) {

        this.descrizione = descrizione;
        this.osserva = osserva;
        this.benvenuto = benvenuto;
    }


    public String getDescrizione() {
        return this.descrizione;
    }

    public String getBenvenuto() {
        return this.benvenuto;
    }

    public String getOsserva() {
        return osserva;
    }
    
    public void setOsserva(final String osserva) {
        this.osserva = osserva;
    }

    public void aggiungiItem(final Item item) {
        this.itemPresenti.add(item);
    }

    public void rimuoviItem(final Items id) {
        this.itemPresenti.removeIf(i -> i.getId() == id);
    }

    public Iterator<Item> iteratoreItem() {
        return this.itemPresenti.iterator();
    }
    
    public void aggiungiItemContenitore(final ItemContenitore itemContenitore) {
        this.itemContenitoriPresenti.add(itemContenitore);
    }

    public Iterator<ItemContenitore> iteratoreItemContenitore() {
        return this.itemContenitoriPresenti.iterator();
    }

    
}
