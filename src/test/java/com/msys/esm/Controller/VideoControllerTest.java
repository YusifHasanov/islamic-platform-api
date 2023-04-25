package com.msys.esm.Controller;

import com.msys.esm.Service.Concretes.VideoService;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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