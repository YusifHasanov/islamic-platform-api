package com.msys.esm.Service.Concretes;

import com.msys.esm.Service.Abstracts.IStatistic;
import com.msys.esm.Service.Abstracts.IStatisticsService;
import com.msys.esm.Core.DTO.Response.StatisticResponse;
import com.msys.esm.Core.Util.Exceptions.StatisticNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.StatisticRepository;
import com.msys.esm.Model.Statistic;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatisticsService implements IStatisticsService {

    IStatistic statistic;

    StatisticRepository statisticRepository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<StatisticResponse>> getAll() {
        List<Statistic> statistics = statisticRepository.findAll();
        List<StatisticResponse> statisticResponses = statistics.stream().map(statistic ->
                mapper.forResponse().map(statistic, StatisticResponse.class)).toList();
        return ResponseEntity.ok(statisticResponses);
    }

    @Override
    public ResponseEntity<StatisticResponse> getByPlatformName(String platformName) {
        Statistic statistic = statisticRepository.findByPlatformName(platformName.toUpperCase().replace(" ", ""))
                .orElseThrow(() ->
                        new StatisticNotFoundException("Statistic not found with platform name: " + platformName));
        StatisticResponse statisticResponse = mapper.forResponse().map(statistic, StatisticResponse.class);
        return ResponseEntity.ok(statisticResponse);
    }


    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    @Override
    public void updateStatistic() {
        try {
            List<Statistic> statistics = statisticRepository.findAll();
            for (Statistic updatedStatistic : statistics) {
                updatedStatistic.setSubscriberCount(String.valueOf(statistic.getSubscriberCount()));
                updatedStatistic.setViewCount(String.valueOf(statistic.getViewCount()));
                updatedStatistic.setVideoCount(String.valueOf(statistic.getVideoCount()));
                updatedStatistic.setPlatformName(statistic.getPlatform().toString());
                statisticRepository.save(updatedStatistic);
            }
        } catch (Exception ignored) {
        }
    }

    @PostConstruct
    public void init() {
        statisticRepository.save(Statistic.builder()
                .platformName("YOUTUBE")
                .subscriberCount("0")
                .viewCount("0")
                .videoCount("0")
                .build());
        updateStatistic();
    }

}
