package com.musicfestivals.app;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;

public class JSFParamGetter implements Serializable {

    private final Map<String, String> params;

    public int getIntParametar(String parametarName) {
        int param = params.get(parametarName) == null ? 0 : Integer.parseInt(params.get(parametarName));
        return param;
    }

    public String getStringParametar(String parametarName) {
        String param = params.get(parametarName) == null ? "" : params.get(parametarName);
        return param;
    }

    public long getLongParametar(String parametarName) {
        Long param = params.get(parametarName) == null ? 0 : Long.parseLong(params.get(parametarName));
        return param;
    }

    public boolean getBooleanParametar(String parametarName) {
        boolean param = params.get(parametarName) == null ? false : Boolean.parseBoolean(params.get(parametarName));
        return param;
    }

    public JSFParamGetter(FacesContext fc) {
        this.params = fc.getExternalContext().getRequestParameterMap();
    }

}
