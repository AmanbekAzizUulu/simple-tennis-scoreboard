package com.dandaev.edu.service.entities;

import lombok.Builder;

@Builder
public record User(String name, Long id) {
}
