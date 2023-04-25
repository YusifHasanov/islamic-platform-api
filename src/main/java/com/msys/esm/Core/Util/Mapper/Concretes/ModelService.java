package com.msys.esm.Core.Util.Mapper.Concretes;

import com.msys.esm.Core.Util.Mapper.Abstracts.IModelService;
import com.msys.esm.Model.Book;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ModelService implements IModelService {
    private ModelMapper modelMapper;

    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

    @Override
    public ModelMapper setIntegerTosetBook() {
        Converter<Set<Book>, Set<Integer>> idsToBooksConverter = new AbstractConverter<Set<Book>, Set<Integer>>() {
            @Override
            protected Set<Integer> convert(Set<Book> books) {
                List<Integer> ids = new ArrayList<>();
                for (Book book : books) {
                    Integer bookId = book.getId();
                    ids.add(bookId);
                }
                return Set.copyOf(ids);
            }
        };

        this.modelMapper.addConverter(idsToBooksConverter);
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return this.modelMapper;
    }

    @Override
    public ModelMapper getInstanceOfModelMapper() {
        return new ModelMapper();
    }
}
