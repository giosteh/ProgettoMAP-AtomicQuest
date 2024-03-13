
package entita;

import java.util.Iterator;
import java.util.Objects;


public class Stanza extends Room {

    private final LivelloRadioattivita esposizRadioattiva;
    private boolean visitata = false;
    private final Stanze id;

    public Stanza(final Stanze id, final String descrizione, final String osserva, final String benvenuto, 
            final LivelloRadioattivita esposizRadioattiva) {
        super(descrizione, osserva, benvenuto);
        this.esposizRadioattiva = esposizRadioattiva;
        this.id = id;
    }

    public LivelloRadioattivita getEsposizRadioattiva() {
        return this.esposizRadioattiva;
    }

    public boolean isRadioattiva() {
        return this.esposizRadioattiva == LivelloRadioattivita.MEDIO || this.esposizRadioattiva == LivelloRadioattivita.ELEVATO;
    }

    public boolean isVisitata() {
        return this.visitata;
    }

    public void setVisitata(final boolean visitata) {
        this.visitata = visitata;
    }

    public Stanze getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Stanza other = (Stanza) obj;
        return this.id == other.id;
    }
    
    public Item getItemPerId(final Items id) {
        Iterator<Item> it = this.iteratoreItem();
        Item itemCercato = null;
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getId() == id) {
                itemCercato = i;
            }
        }
        return itemCercato;
    }

    public boolean contieneItem(final Items id) {
        return this.getItemPerId(id) != null;
    }
    
    public ItemContenitore getItemContenitorePerId(final Items id) {
        Iterator<ItemContenitore> it = this.iteratoreItemContenitore();
        ItemContenitore itemCercato = null;
        while (it.hasNext()) {
            ItemContenitore i = it.next();
            if (i.getId() == id) {
                itemCercato = i;
            }
        }
        return itemCercato;
    }
    
    
}
