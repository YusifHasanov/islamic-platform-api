package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateQuestion;
import com.msys.esm.Model.Question;
import com.msys.esm.Repository.CategoryRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
public interface IModelService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}
