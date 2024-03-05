
package entita;

import java.util.Objects;


public class Item {

    private final String id;
    private final String nome;
    private final String descrizione;
    private final boolean prendibile;
    private final boolean apribile;
    private boolean aperto;

    public Item(final String id, final String nome, final String descrizione,
            final boolean prendibile, final boolean apribile, final boolean aperto) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prendibile = prendibile;
        this.apribile = apribile;
        this.aperto = aperto;
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

    public boolean isApribile() {
        return this.apribile;
    }

    public boolean isAperto() {
        return this.aperto;
    }

    public void setAperto(final boolean aperto) {
        this.aperto = aperto;
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
