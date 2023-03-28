package com.msys.esm.business.abstracts;
import com.msys.esm.core.dto.Request.Create.CreateCategory;
import com.msys.esm.core.dto.Request.Update.UpdateCategory;
import com.msys.esm.core.dto.Response.CategoryResponse;
import com.msys.esm.entities.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {
    ResponseEntity<List<CategoryResponse>> getAll();
    ResponseEntity<CategoryResponse> getById(int id);
    ResponseEntity<CreateCategory> add(CreateCategory category);
    ResponseEntity<UpdateCategory> update(UpdateCategory article,int id);
    ResponseEntity<CategoryResponse> delete(int id);
}
