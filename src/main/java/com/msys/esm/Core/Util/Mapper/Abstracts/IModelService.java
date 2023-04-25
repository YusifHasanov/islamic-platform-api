package com.msys.esm.Core.Util.Mapper.Abstracts;

import org.modelmapper.ModelMapper;
public interface IModelService {
    ModelMapper forResponse();
    ModelMapper forRequest();
    ModelMapper setIntegerTosetBook();
    ModelMapper getInstanceOfModelMapper();
}
