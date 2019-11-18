package org.ankarton.util.document;

/**
 * Created by Locos on 18/08/2015.
 */
public interface DocumentContainer {
    void open(String paramString) throws Exception;

    void read(DocumentContainer paramDocumentContainer);
}
