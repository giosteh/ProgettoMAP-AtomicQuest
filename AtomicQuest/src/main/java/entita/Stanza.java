
package entita;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

/**
 * Classe che rappresenta una stanza.
 */
public class Stanza extends Room implements Serializable {

    private final LivelloRadioattivita esposizRadioattiva;
    private boolean visitata = false;
    private final Stanze id;

    /**
     * Costruttore di default.
     * @param id l'id della stanza
     * @param descrizione la descrizione della stanza
     * @param osserva la descrizione della stanza quando si osserva
     * @param benvenuto il messaggio di benvenuto della stanza
     * @param esposizRadioattiva il livello di esposizione radioattiva della stanza
     */
    public Stanza(final Stanze id, final String descrizione, final String osserva, final String benvenuto, 
            final LivelloRadioattivita esposizRadioattiva) {
        super(descrizione, osserva, benvenuto);
        this.esposizRadioattiva = esposizRadioattiva;
        this.id = id;
    }

    /**
     * Metodo che restituisce il livello di esposizione radioattiva della stanza.
     * @return esposizRadioattiva
     */
    public LivelloRadioattivita getEsposizRadioattiva() {
        return this.esposizRadioattiva;
    }

    /**
     * Metodo che restituisce true se la stanza ha un livello di esposizione radioattiva medio o elevato, false altrimenti.
     * @return true se l'esposizione radioattiva è medio-alta, false altrimenti
     */
    public boolean isRadioattiva() {
        return this.esposizRadioattiva == LivelloRadioattivita.MEDIO || this.esposizRadioattiva == LivelloRadioattivita.ELEVATO;
    }

    /**
     * Metodo che restituisce true se la stanza è stata visitata, false altrimenti.
     * @return visitata
     */
    public boolean isVisitata() {
        return this.visitata;
    }

    /**
     * Metodo che imposta la stanza come visitata.
     * @param visitata true se la stanza è stata visitata, false altrimenti
     */
    public void setVisitata(final boolean visitata) {
        this.visitata = visitata;
    }

    /**
     * Metodo che restituisce l'id della stanza.
     * @return id
     */
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
    
    /**
     * Metodo che restituisce l'item corrispondente all'id.
     * @param id l'id dell'item
     * @return l'item cercato
     */
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

    /**
     * Metodo che restituisce true se la stanza contiene un item, false altrimenti.
     * @param id l'id dell'item
     * @return true se l'item è presente nella stanza, false altrimenti
     */
    public boolean contieneItem(final Items id) {
        return this.getItemPerId(id) != null;
    }
    
    /**
     * Metodo che restituisce l'item contenitore corrispondente all'id.
     * @param id l'id dell'item contenitore
     * @return l'item contenitore cercato
     */
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
