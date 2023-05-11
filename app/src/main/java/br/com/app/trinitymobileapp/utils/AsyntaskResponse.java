package br.com.app.trinitymobileapp.utils;

import java.io.Serializable;

public interface AsyntaskResponse<T extends Serializable> {
       void processSaveFinish(T object);
}
