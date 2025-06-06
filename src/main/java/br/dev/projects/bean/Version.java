package br.dev.projects.bean;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Version(String gitVersion, Instant dateTime) {

}