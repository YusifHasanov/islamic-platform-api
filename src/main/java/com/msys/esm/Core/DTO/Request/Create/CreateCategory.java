package com.msys.esm.Core.DTO.Request.Create;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCategory  {

    @NotBlank(message = "Name is required")
    String name;

    int parentId;

    Set<Integer> subCategories;

}
