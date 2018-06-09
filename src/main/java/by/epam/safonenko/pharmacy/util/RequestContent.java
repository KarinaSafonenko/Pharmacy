package by.epam.safonenko.pharmacy.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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

    public void extractValues(HttpServletRequest request){
        requestParameters = request.getParameterMap();
        Enumeration sessionKeys = request.getSession().getAttributeNames();
        while (sessionKeys.hasMoreElements())
        {
            String key = (String)sessionKeys.nextElement();
            sessionAttributes.put(key, request.getSession().getAttribute(key));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry entry: requestAttributes.entrySet()){
            request.setAttribute(entry.getKey().toString(), entry.getValue());
        }
        for (Map.Entry entry: sessionAttributes.entrySet()){
            request.getSession().setAttribute(entry.getKey().toString(), entry.getValue());
        }
    }

    public void addRequestAttribute(String key, Object value){
        requestAttributes.put(key, value);
    }

    public String getRequestParameter(String key){
        return requestParameters.get(key)[0];
    }

    public void addSessionAttribute(String key, Object value){
        sessionAttributes.put(key, value);
    }

    public Object getSessionAttribute(String key){
        return sessionAttributes.get(key);
    }


}
