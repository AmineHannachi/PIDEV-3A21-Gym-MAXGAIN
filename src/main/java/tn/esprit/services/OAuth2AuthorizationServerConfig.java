package tn.esprit.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import tn.esprit.repositories.UserRepository;

import java.util.List;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public OAuth2AuthorizationServerConfig(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Récupérer les rôles des utilisateurs depuis la base de données
        List<String> roles = userRepository.findAllRoles(); // Méthode fictive pour récupérer les rôles depuis la base de données

        // Configurer les clients OAuth en fonction des rôles récupérés
        for (String role : roles) {
            clients.inMemory()
                    .withClient(role.toLowerCase() + "-client") // Nom du client basé sur le rôle
                    .secret("{noop}client-secret") // Configurez le secret du client OAuth
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token") // Types de subventions autorisées
                    .scopes("read", "write") // Scopes autorisés
                    .authorities("ROLE_" + role.toUpperCase()) // Autorité du client basée sur le rôle
                    .accessTokenValiditySeconds(3600) // Durée de validité du jeton d'accès en secondes
                    .refreshTokenValiditySeconds(3600); // Durée de validité du jeton de rafraîchissement en secondes
        }
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    public TokenStore tokenStore() {
        return new InMemoryTokenStore(); // Stockage des tokens en mémoire pour cet exemple
    }
}
