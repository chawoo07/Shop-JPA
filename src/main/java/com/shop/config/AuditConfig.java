package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  //jpaのauditing機能
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvide(){   //登録者と修正者を処理するAuditorAwareをBeanに登録
        return new AuditorAwareImpl();
    }


}
