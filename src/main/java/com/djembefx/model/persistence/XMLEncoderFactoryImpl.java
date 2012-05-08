package com.djembefx.model.persistence;

import com.google.inject.Inject;

import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 18:16
 */
public class XMLEncoderFactoryImpl implements XMLEncoderFactory {

    @Inject
    Map<Class, PersistenceDelegate> delegates;

    public XMLEncoder create(String url) throws FileNotFoundException {
        XMLEncoder e = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(url)));
        for (Class clazz : delegates.keySet()) {
            e.setPersistenceDelegate(clazz, delegates.get(clazz));
        }
        return e;
    }

}
