
package entita;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


public class Room {
    
    private final String id;
    private final String nome;
    private final String descrizione;
    private String osserva;
    private final String benvenuto;
    private Set<Item> itemPresenti = new TreeSet<>();

    public Room(String id, String nome, String descrizione, String osserva, String benvenuto) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.osserva = osserva;
        this.benvenuto = benvenuto;
    }

    public String getOsserva() {
        return osserva;
    }

    public void setOsserva(String osserva) {
        this.osserva = osserva;
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

    public void aggiungiItem(final Item item) {
        this.itemPresenti.add(item);
    }
    
    public void togliItem(final Item item) {
        this.itemPresenti.remove(item);
    }
    
    public Iterator<Item> iteratore() {
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
