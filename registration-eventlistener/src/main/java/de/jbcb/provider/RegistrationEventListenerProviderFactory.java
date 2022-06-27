package de.jbcb.provider;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class RegistrationEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final String LISTENER_ID = "registration-listener";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new RegistrationEventListenerProvider(session);
    }

    @Override
    public void init(Config.Scope confi) {
        System.out.println(confi.get("username",null));
        System.out.println(confi.get("password",null));
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return LISTENER_ID;
    }
}