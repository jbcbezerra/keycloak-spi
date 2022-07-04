package de.jbcb.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.user.UserLookupProvider;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class RegistrationEventListenerProvider implements EventListenerProvider {
//https://github.com/jessylenne/keycloak-event-listener-http/blob/master/src/main/java/org/softwarefactory/keycloak/providers/events/http/HTTPEventListenerProviderFactory.java
    //https://aboutbits.it/blog/2020-11-23-keycloak-custom-event-listener
    //https://dev.to/adwaitthattey/building-an-event-listener-spi-plugin-for-keycloak-2044
    private final KeycloakSession session;

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
    
    @Override
    public void onEvent(Event event) {
        if (EventType.REGISTER.equals(event.getType())) {
            log.info("Triggered <%s> EVENT", event.getType());
       
            // request accesstoken for kc client
            HttpRequest request = HttpRequest.newBuilder()
              .POST()
              .uri(new URI("kc token uri"))
              .header("Authorization", getBasicAuthenticationHeader("kc-client-username", "kc-client-password"))
              .build();

            
            RealmModel realm = session.realms().getRealm(event.getRealmId());
            UserModel newRegisteredUser = session.users().getUserById(realm,event.getUserId());
/*
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("email", newRegisteredUser.getEmail())
                        .add("username", newRegisteredUser.getUsername())
                        .add("firstName", newRegisteredUser.getFirstName())
                        .add("lastName", newRegisteredUser.getLastName())
                        .add("id", newRegisteredUser.getId())
                        .build();

                okhttp3.Request.Builder builder = new Request.Builder()
                        .url("http://localhost:8080/api/v1/users")
                        .addHeader("User-Agent", "KeycloakHttp Bot");

                if (this.username != null && this.password != null) {
                    builder.addHeader("Authorization", "Basic " + this.username + ":" + this.password.toCharArray());
                }

                Request request = builder.post(formBody)
                        .build();

                Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // Get response body
                System.out.println(response.body().string());
            } catch(Exception e) {
                // ?
                System.out.println("UH OH!! " + e.toString());
                e.printStackTrace();
                return;
            }
        }*/}
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    @Override
    public void close() {
    }
}
