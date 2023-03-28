package com.msys.esm.business.concretes;

import com.msys.esm.business.abstracts.IStatisticsService;
import com.msys.esm.core.dto.Request.Create.CreateStatistic;
import com.msys.esm.core.dto.Request.Update.UpdateStatistic;
import com.msys.esm.core.dto.Response.StatisticResponse;
import com.msys.esm.core.util.Exceptions.StatisticNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.StatisticRepository;
import com.msys.esm.entities.Statistic;
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
