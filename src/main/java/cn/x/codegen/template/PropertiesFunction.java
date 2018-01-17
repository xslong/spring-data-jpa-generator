package cn.x.codegen.template;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.context.EnvironmentAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 在模板中可以读取 application.properties的值
 * @author xslong
 * @time 2017/11/6 17:58
 */
@Component
public class PropertiesFunction implements TemplateMethodModelEx ,EnvironmentAware {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return getValue(((SimpleScalar)arguments.get(0)).getAsString());
    }

    private org.springframework.core.env.Environment springEnvironment;

    public String getValue(String name) {
        return springEnvironment.getProperty(name);
    }

    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {
        this.springEnvironment = environment;
    }
}
