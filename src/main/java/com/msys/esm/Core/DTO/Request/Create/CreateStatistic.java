package com.msys.esm.Core.DTO.Request.Create;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStatistic {
    @NotBlank(message = "View Count is required")
    String viewCount;
    @NotBlank(message = "Subscriber Count  is required")
    String subscriberCount;
    boolean hiddenSubscriber;
    @NotBlank(message = "Video Count is required")
    String videoCount;
}
