package com.msys.esm.api;

import com.msys.esm.business.concretes.VideoService;
import com.msys.esm.core.dto.Response.VideoResponse;
import com.msys.esm.core.util.Exceptions.Global.ErrorResponse;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.dataAccess.VideoRepository;
import com.msys.esm.entities.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoControllerTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController videoController;
    @Mock
    ModelService mapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {

    }

    @Test
    public void testGetAllWhenNoVideos() {

    }

    @Test
    void getById() {



    }

    @Test
    void getByWrongId() {

    }

    @Test
    void getByPlaylistId() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void updateVideos() {
    }

    @Test
    void getAllSorted() {
    }

    @Test
    void getPlayListVideosSorted() {
    }

    @Test
    void getAllPaged() {
    }
}