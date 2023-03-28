package com.msys.esm.core.util.mapper.abstracts;

import org.modelmapper.ModelMapper;
public interface IModelService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}
