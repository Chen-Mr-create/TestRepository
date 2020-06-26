package com.gec.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gec.web.interceptor.CheckUserInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration //相当于applicationContext.xml
@ComponentScan(basePackages = "com.gec.web")//扫描包
@EnableWebMvc //相当于配置处理器适配器和映射器
@EnableTransactionManagement //启动事务管理器
public class SpringMVCConfig implements WebMvcConfigurer {

    //拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> paths = new ArrayList<>();
        paths.add("doLogin");
        paths.add("register");
        //addPathPatterns("/**").excludePathPatterns(paths)除了paths集合中的路径之外，都要拦截。
        registry.addInterceptor(new CheckUserInterceptor()).addPathPatterns("/**").excludePathPatterns(paths);
    }

    //显示静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //第一个参数：通过http访问的路径名
        //第二个参数：表示文件的物理路径
        registry.addResourceHandler("/pic/**").addResourceLocations("file:d:/upload/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(converter);
    }

    @Bean //相当于配置以前的视图解析器<bean/>
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/pages/");
        viewResolver.setSuffix(".jsp");
        return  viewResolver;
    }

    //文件上传解析器
    @Bean(name = "multipartResolver") // bean必须写name属性且必须为multipartResolver
    protected CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(5 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(0);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

    // 静态资源的处理的java配置
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //添加日期类型转换器
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

    //配置数据源
    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver"); //配置驱动
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mybatisdb?useUnicode=true&characterEncoding=UTF-8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("chen1998");
        return druidDataSource;
    }

    //配置sqlSessionFactoryBean
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("SqlMapConfig.xml"));
        return sqlSessionFactoryBean;
    }

    //配置扫描托管mapper包
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.gec.web.mapper");
        return configurer;
    }

    //配置事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }
}
