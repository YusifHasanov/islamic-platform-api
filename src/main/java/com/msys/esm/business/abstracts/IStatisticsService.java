package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateStatistic;
import com.msys.esm.core.dto.Request.Update.UpdateStatistic;
import com.msys.esm.core.dto.Response.CategoryResponse;
import com.msys.esm.core.dto.Response.StatisticResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStatisticsService {
    ResponseEntity<List<StatisticResponse>> getAll();

    ResponseEntity<StatisticResponse> getById(int id);

    ResponseEntity<CreateStatistic> add(CreateStatistic statisticResponse);
    ResponseEntity<UpdateStatistic> update(UpdateStatistic statisticResponse,int id);
    ResponseEntity<StatisticResponse> delete(int id);
}
