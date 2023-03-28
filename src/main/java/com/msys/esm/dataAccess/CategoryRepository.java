package com.msys.esm.dataAccess;
import com.msys.esm.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository  extends  JpaRepository<Category, Integer>{
}
