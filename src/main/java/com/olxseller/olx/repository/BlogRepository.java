package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
	// pagination......

	@Query("from Blog as b where b.user.id=:userId")
	public Page<Blog> findBlogsByUser(@Param("userId") int userId, Pageable pageable);
    
	@Query(value="select * from blog",nativeQuery=true)
	public Page<Blog> getAllBlogs(Pageable pageable);
	
	@Query("select b from Blog b where b.Title=:t")
	public Blog getBlogByName(@Param("t") String name);
	
	@Query(value="select * from blog where main_category=:t",nativeQuery=true)
	public List<Blog> getBlogByMainCatalog(@Param("t") String name);
	
	@Query(value="select * from blog where city=:t",nativeQuery=true)
	public List<Blog> getBlogByCity(@Param("t") String city);
	
	@Query(value="select * from blog as b where b.category=:c or b.city=:ct",nativeQuery=true)
	public List<Blog> getBlogByCategory(@Param("c") String category,@Param("ct") String city);
	
	
	@Query("select b from Blog b where concat(b.id,b.Description,b.Title,b.address,b.category,b.city,b.mainCategory,b.price,b.region,b.regionState) LIKE %?1% ")
	public List<Blog> getBlogSearchByContent(String keyword);
	
	/*
	 * @Query("from Blog as b where b.user.id=:userId") public List<Blog>
	 * findBlogsByUser(@Param("userId") int userId);
	 */
	@Query(value="select count(*) from blog where category=:c or city=:ct",nativeQuery=true) 
	 public int  findByCategoryAndCity(@Param("c") String category,@Param("ct") String city);
	
	@Query(value="select count(*) from blog where main_category=:c",nativeQuery=true) 
	 public int  findByMainCategory(@Param("c") String category);
	
	@Query(value="select count(*) from blog where title=:t",nativeQuery=true) 
	 public int  findByTitle(@Param("t") String title);
	
	
	  @Query(value="select * from blog",nativeQuery=true) 
	  public List<Blog> getBlogs();
	 
}
