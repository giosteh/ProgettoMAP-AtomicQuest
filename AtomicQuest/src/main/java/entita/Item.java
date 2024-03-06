
package entita;

import java.util.Objects;


public class Item {

    private final String id;
    private final String nome;
    private final String descrizione;
    private final boolean prendibile;

    public Item(final String id, final String nome, final String descrizione,
            final boolean prendibile) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prendibile = prendibile;
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
}
