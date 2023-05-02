package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import com.msys.esm.Model.Category;

public interface ICategoryMapper {

    Category createDtoToEntity(CreateCategory createCategory);

    void updateDtoToEntity(Category category, UpdateCategory updateCategory);

    CategoryResponse entityToResponse(Category category);

}
