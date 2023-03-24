package com.example.api.core.util.mapper.abstracts;

import org.modelmapper.ModelMapper;
public interface IModelService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}
