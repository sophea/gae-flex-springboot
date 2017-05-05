/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dmi;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.dmi.web", "com.dmi.service", "com.dmi.dao"})
public class MainApplication {
//  @RequestMapping("/")
//  public String home() {
//    return "Hello World!";
//  }

  /**
   * <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
   * App Engine health checking</a> requires responding with 200 to {@code /_ah/health}.
   */
  @RequestMapping("/_ah/health")
  public String healthy() {
    // Message body required though ignored
    return "Still surviving.";
  }

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }
  
  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder() {
      Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
      
      builder.serializationInclusion(JsonInclude.Include.NON_NULL);
      builder.failOnUnknownProperties(false);
      builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
      return builder;
  }
}
