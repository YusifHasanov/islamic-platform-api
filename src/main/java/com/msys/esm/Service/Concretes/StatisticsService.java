package com.msys.esm.Service.Concretes;

import com.msys.esm.Service.Abstracts.IStatisticsService;
import com.msys.esm.Core.DTO.Request.Create.CreateStatistic;
import com.msys.esm.Core.DTO.Request.Update.UpdateStatistic;
import com.msys.esm.Core.DTO.Response.StatisticResponse;
import com.msys.esm.Core.Util.Exceptions.StatisticNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.StatisticRepository;
import com.msys.esm.Model.Statistic;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatisticsService implements IStatisticsService {
    StatisticRepository repository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<StatisticResponse>> getAll() {
        List<Statistic> statistics = repository.findAll();
        List<StatisticResponse> statisticResponses = statistics.stream().map(statistic ->
                mapper.forResponse().map(statistic, StatisticResponse.class)).toList();
        return ResponseEntity.ok(statisticResponses);
    }

    @Override
    public ResponseEntity<StatisticResponse> getById(int id) {
        Statistic statistic = repository.findById(id).orElseThrow(() ->
                new StatisticNotFoundException("Statistic not found with id: " + id));
        CheckIds.check(statistic.getId(), id);
        StatisticResponse statisticResponse = mapper.forResponse().map(statistic, StatisticResponse.class);
        return ResponseEntity.ok(statisticResponse);
    }

    @Override
    public ResponseEntity<CreateStatistic> add(CreateStatistic statisticResponse) {
        Statistic statistic = mapper.forRequest().map(statisticResponse, Statistic.class);
        repository.save(statistic);
        return ResponseEntity.status(HttpStatus.CREATED).body(statisticResponse);
    }

    @Override
    public ResponseEntity<UpdateStatistic> update(UpdateStatistic statisticResponse, int id) {
        Statistic statistic = repository.findById(id).orElseThrow(() ->
                new StatisticNotFoundException("Statistic not found with id: " + id));
        CheckIds.check(statistic.getId(), id);
        mapper.forRequest().map(statisticResponse, statistic);
        repository.save(statistic);
        return ResponseEntity.ok(statisticResponse);
    }

    @Override
    public ResponseEntity<StatisticResponse> delete(int id) {
        Statistic statistic = repository.findById(id).orElseThrow(() ->
                new StatisticNotFoundException("Statistic not found with id: " + id));
        CheckIds.check(statistic.getId(), id);
        repository.delete(statistic);
        StatisticResponse statisticResponse = mapper.forResponse().map(statistic, StatisticResponse.class);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(statisticResponse);
    }
}
