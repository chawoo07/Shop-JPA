package com.shop.config;


import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration

@EnableWebSecurity

public class SecurityConfig {


    @Autowired

    MemberService memberService;


    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/members/login")    //ログインペイジ
                .defaultSuccessUrl("/")         //ログイン成功の時移動するURL
                .usernameParameter("email")     //ログインの時使うパラメータの名をemailに
                .failureUrl("/members/login/error") //ログイン失敗の時のURL
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher
                        ("/members/logout"))             //ログアウトのURL
                .logoutSuccessUrl("/");             //ログアウトの成功の時移動するURL


        http.authorizeRequests()    //security処理にhttpServletRequestを利用する意味

                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()//すべてのユーザーが認証しなくても接近できる

                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()

                .mvcMatchers("/admin/**").hasRole("ADMIN")  //  /adminはAdmin Roleの時に接近できるように

                .anyRequest().authenticated();  //上の以外には認証必要


        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());


        return http.build();

    }

    @Bean

    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }



}

