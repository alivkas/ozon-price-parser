package com.example.ozonpriceparser.api.dto;

import com.example.ozonpriceparser.api.validation.annotations.CorrectUrlConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PageDto(@NotBlank @NotNull @CorrectUrlConstraint String url,
                      String title,
                      Integer price) {
}
