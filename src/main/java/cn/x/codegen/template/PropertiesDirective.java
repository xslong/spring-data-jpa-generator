package cn.x.codegen.template;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author xslong
 * @time 2017/11/6 17:16
 */
@Component
public class PropertiesDirective implements TemplateDirectiveModel, EnvironmentAware {
    private org.springframework.core.env.Environment springEnvironment;

    public String getValue(String name) {
        return springEnvironment.getProperty(name);
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        SimpleScalar simpleScalar = (SimpleScalar) params.get("name");
        String name = simpleScalar.getAsString();
        Writer out = env.getOut();
        out.write(getValue(name));
    }

    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {
        this.springEnvironment = environment;
    }
}
