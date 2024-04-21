package com.example.vuesecurity.config;

import com.example.vuesecurity.filter.JwtAuthenticationTokenFilter;
import com.example.vuesecurity.service.impl.MyUserDetailServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyServiceConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private LogoutSuccess logoutSuccess;

    /*
     * security的过滤器链
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((auth) ->
                auth.requestMatchers("/getCaptcha", "user/login", "user/register").permitAll().anyRequest().authenticated()
        );
        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });
//自定义过滤器放在UsernamePasswordAuthenticationFilter过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> {
            logout.logoutUrl("user/logout")  //退出路径
                    .logoutSuccessHandler(logoutSuccess); //退出成功处理器
        });


        return http.build();
    }

    @Autowired
    private MyUserDetailServerImpl myUserDetailsService;


    /*
     * 验证管理器
     * */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//将编写的UserDetailsService注入进来
        provider.setUserDetailsService(myUserDetailsService);
//将使用的密码编译器加入进来
        provider.setPasswordEncoder(passwordEncoder);
//将provider放置到AuthenticationManager 中
        ProviderManager providerManager = new ProviderManager(provider);
        return providerManager;
    }


    //跨域配置
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*
     * 密码加密器*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
