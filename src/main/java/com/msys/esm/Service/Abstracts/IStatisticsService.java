package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateStatistic;
import com.msys.esm.Core.DTO.Request.Update.UpdateStatistic;
import com.msys.esm.Core.DTO.Response.StatisticResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStatisticsService {
    ResponseEntity<List<StatisticResponse>> getAll();

    ResponseEntity<StatisticResponse> getById(int id);

    ResponseEntity<CreateStatistic> add(CreateStatistic statisticResponse);
    ResponseEntity<UpdateStatistic> update(UpdateStatistic statisticResponse,int id);
    ResponseEntity<StatisticResponse> delete(int id);
}