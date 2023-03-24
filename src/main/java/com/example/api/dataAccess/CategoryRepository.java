package com.example.api.dataAccess;
import com.example.api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository  extends  JpaRepository<Category, Integer>{
}
