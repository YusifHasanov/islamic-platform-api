package com.msys.esm.api;
import com.msys.esm.business.concretes.StatisticsService;
import com.msys.esm.core.dto.Request.Create.CreateStatistic;
import com.msys.esm.core.dto.Request.Update.UpdateStatistic;
import com.msys.esm.core.dto.Response.StatisticResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE )
@RequestMapping("/api/statistics")
public class StatisticsController {
    StatisticsService service;

    @GetMapping
    public ResponseEntity<List<StatisticResponse>> getAll(){
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<StatisticResponse> getById(@PathVariable int id){
        return service.getById(id);
    }
    @PostMapping
    public ResponseEntity<CreateStatistic> add(@RequestBody CreateStatistic statisticRequest){
        return service.add(statisticRequest);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdateStatistic> update(@PathVariable int id, @RequestBody UpdateStatistic statisticRequest){
        return service.update(statisticRequest, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<StatisticResponse> delete(@PathVariable int id){
        return service.delete(id);
    }
}
