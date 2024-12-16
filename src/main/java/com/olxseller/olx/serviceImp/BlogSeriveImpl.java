package com.olxseller.olx.serviceImp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.BlogRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.BlogService;

@Service 
public class BlogSeriveImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepo;
	@Autowired
	private UserRepository userRepo;

  @Override
  public Blog addBlogs(Blog b) {
    Blog result=blogRepo.save(b);
			return result;
  }

  @Override
  public Blog updateBlogByAdmin(Blog b, int id, int uid) {
    User user=userRepo.findById(uid).get();
    user.getBlog().add(b);
    b.setUser(user);
    userRepo.save(user);
    return b;
  }

  @Override
  public List<Blog> getAllBlogs() {
    List<Blog> list=(List<Blog>)this.blogRepo.findAll();
    return list;
  }

  @Override
  public Blog getBlogById(int id) {
    Blog blog=null;
			try {
		//	user = list.stream().filter(e->e.getId()==id).findFirst().get();
			Optional<Blog> findById=this.blogRepo.findById(id);
			if (findById.isPresent()) {
				blog = findById.get();
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
				return blog;
  }

  @Override
  public String getPageUrl(String title) {
    System.out.println("title:"+title);
		String url="";
		int i=blogRepo.findByCategoryAndCity(title, title);
		int m=blogRepo.findByMainCategory(title);
		int t=blogRepo.findByTitle(title);
		if(blogRepo.getCategory(title)>0) {
			url="citypage";
		}else if(m>0) {
			url="citypage";
		}else {
			if(t>0) {
			url="post";	
			}
		}
		return url;
  }

  @Override
  public void deleteBlogs(int id) {
   //list.stream().filter(user->user.getId()!=id).collect(Collectors.toList());
	 Blog blog=blogRepo.findById(id).get();
	 blog.setUser(null);
   blogRepo.deleteById(id);
  }

  @Override
  public Blog updateBlogs(Blog b, int id) { 
			Blog blog=blogRepo.findById(id).get();
			/*
			 * list = list.stream().map(u->{ if(u.getId()==id) { u.setName(user.getName());
			 * u.setEmail(user.getEmail()); u.setPhone(user.getPhone());
			 * u.setPassword(user.getPassword()); } return u;
			 * }).collect(Collectors.toList());
			 */
			if(!b.getImage().isEmpty()){
				blog.setImage(b.getImage());
				blog.setImage2(b.getImage2());
				blog.setImage3(b.getImage3());
				blog.setImage4(b.getImage4());
				blog.setImage5(b.getImage5());
				blog.setImage6(b.getImage6());
			}
			blog.setId(id);blog.setTitle(b.getTitle());blog.setAddress(b.getAddress());
			blog.setCategory(b.getCategory());blog.setCity(b.getCity());blog.setDescription(b.getDescription());
			blog.setMainCategory(b.getMainCategory());blog.setPrice(b.getPrice());blog.setRegion(b.getRegion());
			blog.setRegionState(b.getRegionState());blog.setUser(b.getUser());blog.setUpdate_at(b.getUpdate_at());
			blogRepo.save(blog);
			return blog;
    }

	@Override
	public Blog getBlogDetailByTitle(String name) {
		return blogRepo.getBlogByName(name);	
	}

	@Override
	public List<Blog> getBlogsByUserId(int uid) {
		return blogRepo.findBlogsByUId(uid);
		}
		
  
}
