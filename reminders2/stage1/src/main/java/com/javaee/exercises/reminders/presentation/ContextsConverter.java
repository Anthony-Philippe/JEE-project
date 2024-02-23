package com.javaee.exercises.reminders.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("contextsConverter")
public class ContextsConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty())
            return new ArrayList<>();
        
        List<String> contexts =
                Arrays.asList(value.split(",")).stream()
                .map(s -> s.trim().toLowerCase())                
                .collect(Collectors.toList());

        return contexts;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        List<String> contexts = (List<String>) value;
        return contexts.stream().sorted().collect(Collectors.joining( ", " ));
    }
    
}
