package io.github.adraw.framework;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;

import io.github.adraw.framework.file.StorageProperties;


@Controller
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(StorageProperties.class)
public class Application extends SpringBootServletInitializer{
	
//	@Autowired
//	private DataSource dataSource;
//	
//	@Autowired
//	private IStudentService studentService;
//	
//	@Autowired
//	private UserMapper userMapper;
//	
////	@RequestMapping("/")
////	@ResponseBody
////	public List<User> home() {
////	    return userMapper.selectAll();
////	}
////	
//	@RequestMapping("/")
//	@RequiresRoles(value={"admin"})
//	public String home(@RequestHeader(value="X-PJAX",required=false) String pjax,Model model) {
//		model.addAttribute("pj", pjax);
//		return "index";
//	}
//	
//	@RequestMapping("/test")
//	@ResponseBody
//	public Map<String,String> test() {
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("aaa", "bbb");
//		return map;
//	}
//	
//	@RequestMapping("pages/**")
//	@RequiresRoles(value={"admin"})
//	public String page(HttpServletRequest request,@RequestHeader(value="X-PJAX",required=false) String pjax,Model model) {
//		model.addAttribute("pj", pjax);
//		request.setAttribute("aaa", "11111");
//		String requestUrl = request.getRequestURI();
//		return requestUrl.substring(1);
//	}
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(Application.class);
	}


}
