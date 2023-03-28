package com.msys.esm.core.dto.Response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticResponse {

    int id;
    String viewCount;
    String subscriberCount;
    boolean hiddenSubscriber;
    String videoCount;
}
