
package entita;

import java.io.Serializable;

/**
 * Enumerazione che rappresenta le modalità di accesso.
 */
public enum ModalitaDiAccesso implements Serializable {
    APERTO,
    PORTACONTESSERINO,
    PORTACONCODICE,
    ASCENSORE,
    CONDOTTO,
    SCALE
}
