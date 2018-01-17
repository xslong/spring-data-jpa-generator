package cn.x.codegen.template;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 在模板中把mysql类型转为java类型
 * @author xslong
 * @time 2017/11/7 08:30
 */
@Component
@ConfigurationProperties("codegen")
@Configuration
public class TypeFunction implements TemplateMethodModelEx {

    private Map<String, String> columnTypeMapping;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String mysqlType = ((SimpleScalar) arguments.get(0)).getAsString();
        String javaType = columnTypeMapping.get(mysqlType);
        if (javaType == null) {
            throw new RuntimeException("No binding type :"+mysqlType);
        }
        return javaType;
    }

    public Map<String, String> getColumnTypeMapping() {
        return columnTypeMapping;
    }

    public void setColumnTypeMapping(Map<String, String> columnTypeMapping) {
        this.columnTypeMapping = columnTypeMapping;
    }
}
