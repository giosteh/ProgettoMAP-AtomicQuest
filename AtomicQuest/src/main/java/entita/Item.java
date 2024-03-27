
package entita;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe che rappresenta un item.
 */
public class Item implements Comparable, Serializable {

    private final Items id;
    private String descrizione;
    private final boolean prendibile;
    private String nomeItem;

    /**
     * Costruttore di default.
     * @param id l'id dell'item
     * @param nomeItem il nome dell'item
     * @param descrizione la descrizione dell'item
     * @param prendibile true se l'item è prendibile, false altrimenti
     */
    public Item(final Items id, final String nomeItem,
            final String descrizione, final boolean prendibile) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.descrizione = descrizione;
        this.prendibile = prendibile;
    }

    /**
     * Metodo che restituisce l'id dell'item.
     */
    public Items getId() {
        return this.id;
    }

    /**
     * Metodo che restituisce la descrizione dell'item.
     */
    public String getDescrizione() {
        return this.descrizione;
    }

    /**
     * Metodo che imposta la descrizione dell'item.
     * @param descrizione la descrizione dell'item
     */
    public void setDescrizione(final String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Metodo che restituisce true se l'item è prendibile, false altrimenti.
     */
    public boolean isPrendibile() {
        return this.prendibile;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Item other = (Item) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int compareTo(Object o) {
        Item other = (Item) o;
        return this.id.compareTo(other.id);
    }
    
    /**
     * Metodo che restituisce la stringa rappresentante l'item.
     */
    public String toString() {
        return this.nomeItem;
    }
    
}
