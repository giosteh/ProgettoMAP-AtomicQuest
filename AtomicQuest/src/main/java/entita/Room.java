
package entita;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


public class Room {

    private final String id;
    private final String nome;
    private final String descrizione;
    private final List<String> osserva = new ArrayList<>();
    private final String benvenuto;
    private final Set<Item> itemPresenti = new TreeSet<>();

    public Room(final String id, final String nome, final String descrizione,
            final String benvenuto) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.benvenuto = benvenuto;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public String getBenvenuto() {
        return this.benvenuto;
    }

    public Iterator<String> iteratoreDescr() {
        return this.osserva.iterator();
    }

    public void aggiungiDescr(final String descr) {
        this.osserva.add(descr);
    }

    public void aggiungiItem(final Item item) {
        this.itemPresenti.add(item);
    }

    public void rimuoviItem(final Item item) {
        this.itemPresenti.remove(item);
    }

    public Iterator<Item> iteratoreItem() {
        return this.itemPresenti.iterator();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        return Objects.equals(this.id, other.id);
    }
}
