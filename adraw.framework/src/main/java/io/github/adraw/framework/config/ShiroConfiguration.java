package io.github.adraw.framework.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {
	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	  
	
	@Bean(name = "hashedCredentialsMatcher")  
	public HashedCredentialsMatcher hashedCredentialsMatcher() {  
	    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();  
	    credentialsMatcher.setHashAlgorithmName("MD5");  
	    credentialsMatcher.setHashIterations(2);  
	    credentialsMatcher.setStoredCredentialsHexEncoded(true);  
	    return credentialsMatcher;  
	}  
	
	
    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(EhCacheManager cacheManager) {
    	MyShiroRealm realm = new MyShiroRealm(); 
		realm.setCacheManager(cacheManager);
		return realm;
    }
 
    @Bean(name = "jwtRealm")
    public JWTRealm jwtRealm() {
    	JWTRealm realm = new JWTRealm();
    	//realm.setCredentialsMatcher(hashedCredentialsMatcher());
    	return realm;
    }
    
    @Bean(name = "formRealm")
    public FormRealm formRealm() {
    	FormRealm realm = new FormRealm();
    	realm.setCredentialsMatcher(hashedCredentialsMatcher());
    	return realm;
    }
    
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }
  
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
  
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
  
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(JWTRealm jwtRealm,FormRealm formRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(jwtRealm);
        realms.add(formRealm);
        dwsm.setRealms(realms);
        dwsm.setCacheManager(getEhCacheManager());
        DefaultSubjectDAO subjectDao = (DefaultSubjectDAO) dwsm.getSubjectDAO();
        DefaultWebSessionStorageEvaluator sse = (DefaultWebSessionStorageEvaluator) subjectDao.getSessionStorageEvaluator();
        sse.setSessionStorageEnabled(false);
        return dwsm;
    }  
  
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(defaultWebSecurityManager);
        return new AuthorizationAttributeSourceAdvisor();
    }  
  
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.postProcessBeforeInitialization(new JWTOrFormAuthenticationFilter(), "jwt");
		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl("/");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/font/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/**/*.json", "anon");
        filterChainDefinitionMap.put("/regist", "anon");
        //filterChainDefinitionMap.put("/pages/**", "anon");
        filterChainDefinitionMap.put("/**", "jwt");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
   
}