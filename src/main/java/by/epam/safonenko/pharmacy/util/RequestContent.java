package by.epam.safonenko.pharmacy.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    public RequestContent(){
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
    }
    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry entry: requestAttributes.entrySet()){
            request.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        for (Map.Entry entry: sessionAttributes.entrySet()){
            request.getSession().setAttribute(entry.getKey().toString(), entry.getValue());
        }
    }

    public void addRequestAttridute(String key, Object value){
        requestAttributes.put(key, value);
    }

    public String getRequestParameter(String key){
        return requestParameters.get(key)[0];
    }

    public void addSessionAttridute(String key, Object value){
        sessionAttributes.put(key, value);
    }

}
