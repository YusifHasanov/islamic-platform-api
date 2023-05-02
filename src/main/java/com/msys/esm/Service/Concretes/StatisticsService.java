package com.msys.esm.Service.Concretes;

import com.msys.esm.Service.Abstracts.IStatistic;
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

    IStatistic statistic;

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
    public void updateStatistic() {
        List<Statistic> statistics = repository.findAll();
        for (Statistic updatedStatistic: statistics) {
            updatedStatistic.setSubscriberCount(String.valueOf(statistic.getSubscriberCount()));
            updatedStatistic.setViewCount(String.valueOf(statistic.getViewCount()));
            updatedStatistic.setVideoCount(String.valueOf(statistic.getVideoCount()));
            updatedStatistic.setPlatformName(statistic.getPlatform().toString());
            repository.save(updatedStatistic);
        }
    }

}
