package com.djembefx.model.persistence;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 18:20
 */
public interface XMLEncoderFactory {

    public XMLEncoder create(String url) throws FileNotFoundException;

}
