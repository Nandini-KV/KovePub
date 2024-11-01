package com.kognivera.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  @JsonProperty("idUser")
  private String id;

  @JsonProperty("prnId")
  private Integer prnId;

  @JsonProperty("loginEmail")
  private String loginEmail;

  @JsonProperty("notificationEmail")
  private String notificationEmail;

  @JsonProperty("name")
  private String name;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("motherLastName")
  private String motherLastName;

  @JsonProperty("gender")
  private SexEnum gender;

  @JsonProperty("isActive")
  private Boolean isActive;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
  @JsonProperty("dateOfBirth")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  @JsonSerialize(using = ZonedDateTimeSerializer.class)
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
  @JsonProperty("createdAt")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime createdAt;
}
