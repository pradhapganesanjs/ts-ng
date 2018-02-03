package com.citi.frontier.config;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class FrontierConfig extends WebMvcConfigurerAdapter {

	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/ng/**").addResourceLocations("classpath:/static/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/ng/index.html");
		// registry.addViewController("/frontier/**").set
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/WEB-INF/jsp/");
	    viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
		super.configureViewResolvers(registry);
	}
	/*
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		UrlFilenameViewController urlViewController = new UrlFilenameViewController();
		
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.put("/frontier/*", urlViewController);
         
        handlerMapping.setMappings(mappings);
		
		super.configurePathMatch(configurer);
	} */
	/*
	    @Bean
        public InternalResourceViewResolver getViewResolver() {
            InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
            viewResolver.setPrefix("/WEB-INF/views/");
            viewResolver.setSuffix(".jsp");
            return viewResolver;
        }         @Bean(name = "urlViewController")
        public UrlFilenameViewController getUrlViewController() {
            UrlFilenameViewController urlViewController = new UrlFilenameViewController();
            return urlViewController;
        }         @Bean
        public SimpleUrlHandlerMapping getUrlHandlerMapping() {
            SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
            Properties mappings = new Properties();
            mappings.put("/** / *.html", "urlViewController");
             
            handlerMapping.setMappings(mappings);
            return handlerMapping;
        }
    */
	/*
	 * @Bean public ViewResolver htmlViewResolver() {
	 * System.err.println("htmlViewResolver checked "); UrlBasedViewResolver
	 * resolver = new ChainUrlBasedViewResolver(); resolver.setPrefix("/static/");
	 * resolver.setSuffix(".html"); resolver.setOrder(0); return resolver; }
	 * 
	 * @Bean public ViewResolver jspViewResolver() {
	 * System.err.println("jspViewResolver checked "); InternalResourceViewResolver
	 * resolver = new InternalResourceViewResolver();
	 * resolver.setPrefix("/WEB-INF/jsp/"); resolver.setSuffix(".jsp");
	 * resolver.setOrder(1); return resolver; }
	 */

	/*
	 * @Bean public WebMvcConfigurerAdapter forwardToIndex() { return new
	 * WebMvcConfigurerAdapter() {
	 * 
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * // forward requests to /admin and /user to their index.html
	 * registry.addViewController("/").setViewName( "forward:static/index.html");
	 * registry.addViewController("/ng").setViewName( "forward:/index.html"); } }; }
	 */
	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * registry.addRedirectViewController("/", "index.html"); }
	 */
}
/*
 * @Configuration
 * 
 * @EnableWebMvc public class WebConfig extends WebMvcConfigurerAdapter {
 * 
 * @Override public void
 * configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
 * configurer.enable(); }
 * 
 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
 * registry.addResourceHandler("/app/**")
 * .addResourceLocations("classpath:/static/"); }
 * 
 * @Override public void addViewControllers(ViewControllerRegistry registry) {
 * registry.addRedirectViewController("/", "/app/index.html"); } }
 */