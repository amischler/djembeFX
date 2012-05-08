package com.djembefx.controller;

import com.djembefx.ioc.IOC;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 11:55
 */
public class AbstractController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IOC.getInjector().injectMembers(this);
    }
}
