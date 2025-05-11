package com.olxseller.olx.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponetWidget {
  @Bean
  public StringSpliter StringSpliter(){
    return new StringSpliter();
  }

} 
