package com.arvatosystems.t9t.doc.services;

import de.jpaw.bonaparte.pojos.api.media.MediaData;

/** Interface for plugins to provide a conversion into a specified format.
 * The selection of the implementation is done via Qualifier.
 *
 * This implementation targets conversions of components, not full documents.
 *
 */
public interface IDocComponentConverter {
    String convertFrom(MediaData source);
}
