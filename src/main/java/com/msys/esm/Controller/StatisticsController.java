package com.msys.esm.Controller;
import com.msys.esm.Service.Concretes.StatisticsService;
import com.msys.esm.Core.DTO.Request.Create.CreateStatistic;
import com.msys.esm.Core.DTO.Request.Update.UpdateStatistic;
import com.msys.esm.Core.DTO.Response.StatisticResponse;
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

    @PutMapping
    public void updateStatistic(){
        service.updateStatistic();
    }
}
