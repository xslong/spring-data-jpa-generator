package cn.x.codegen;

import cn.x.codegen.template.NameFunction;
import cn.x.codegen.template.PropertiesFunction;
import cn.x.codegen.template.TypeFunction;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xslong
 * @time 2017/11/6 17:25
 */
@Component
public class FreemarkerConfiguration implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Autowired
    private PropertiesFunction propertiesFunction;
    @Autowired
    private NameFunction nameFunction;
    @Autowired
    private TypeFunction typeFunction;

    @Override
    public void afterPropertiesSet() throws Exception {
        //设置模板自定义函数
        configuration.setSharedVariable("pv",propertiesFunction);
        configuration.setSharedVariable("nf",nameFunction);
        configuration.setSharedVariable("tf",typeFunction);
    }

}
