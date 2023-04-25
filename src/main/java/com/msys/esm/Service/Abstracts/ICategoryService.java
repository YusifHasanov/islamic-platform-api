package com.msys.esm.Service.Abstracts;
import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {
    ResponseEntity<List<CategoryResponse>> getAll();
    ResponseEntity<CategoryResponse> getById(int id);
    ResponseEntity<CreateCategory> add(CreateCategory category);
    ResponseEntity<UpdateCategory> update(UpdateCategory article,int id);
    ResponseEntity<?> delete(int id);
}
