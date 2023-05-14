package fr.aura.markandweb.gena_server;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
