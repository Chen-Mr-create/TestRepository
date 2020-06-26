package com.gec.web.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

//这个类相当于以前的web.xml
public class WebInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //1.指定spring的配置类(实际上就是加载spring的容器)
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringMVCConfig.class);

        //中文编码处理过滤器
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        FilterRegistration.Dynamic charFilter = servletContext.addFilter("charFilter",encodingFilter);
        charFilter.addMappingForUrlPatterns(null,false,"/*");

        //2.配置springmvc的中央控制器（前端控制器）
        ServletRegistration.Dynamic springmvc = servletContext.addServlet("springmvc",new DispatcherServlet(context));
        springmvc.addMapping("/");//不带后缀的访问路径
        springmvc.setLoadOnStartup(1);
    }
}
