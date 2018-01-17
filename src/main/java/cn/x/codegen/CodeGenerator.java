package cn.x.codegen;

import cn.x.codegen.db.TableMeta;
import cn.x.codegen.utils.NameUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;

/**
 * 生成代码
 * @author xslong
 * @time 2017/11/6 16:58
 */
@Component
public class CodeGenerator implements InitializingBean {

    private final static String CHARSET = "UTF-8";

    @Autowired
    private Configuration freemarkerConfiguration;

    @Value("${codegen.tablePrefix}")
    private String tablePrefix;

    @Value("${codegen.output.entity.overwrite}")
    private boolean entityOverwrite;
    @Value("${codegen.output.entity.path}")
    private String entityPath;
    @Value("${codegen.output.entity.package}")
    private String entityPackage;


    @Value("${codegen.output.repository.overwrite}")
    private boolean repositoryOverwrite;
    @Value("${codegen.output.repository.path}")
    private String repositoryPath;
    @Value("${codegen.output.repository.package}")
    private String repositoryPackage;


    @Value("${codegen.output.service.overwrite}")
    private boolean serviceOverwrite;
    @Value("${codegen.output.service.path}")
    private String servicePath;
    @Value("${codegen.output.service.package}")
    private String servicePackage;


    @Value("${codegen.output.serviceimpl.overwrite}")
    private boolean serviceimplOverwrite;
    @Value("${codegen.output.serviceimpl.path}")
    private String serviceimplPath;
    @Value("${codegen.output.serviceimpl.package}")
    private String serviceimplPackage;


    @Override
    public void afterPropertiesSet() throws Exception {
        checkOutputDir(entityPath, entityPackage);
        checkOutputDir(repositoryPath, repositoryPackage);
        checkOutputDir(servicePath, servicePackage);
        checkOutputDir(serviceimplPath, serviceimplPackage);
    }

    private String checkOutputDir(String path, String pack) {
        String fullpath = path + File.separator + StringUtils.join(pack.split("\\."), File.separator);
        File output = new File(fullpath);
        if (!output.exists()) {
            output.mkdirs();
        }
        return fullpath;
    }


    public void process(TableMeta tableMeta) throws Exception {
        String tableName = tableMeta.getTableName();
        if (StringUtils.isNoneBlank(tablePrefix) && tableName.startsWith(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        String entityName = NameUtils.upperCamelCase(tableName);

        Template entityTpl = freemarkerConfiguration.getTemplate("entity.ftl", CHARSET);
        String entityCode = FreeMarkerTemplateUtils.processTemplateIntoString(entityTpl, tableMeta);
        String entityCodePath = checkOutputDir(entityPath, entityPackage) + File.separator + entityName + ".java";
        writeFile(entityCodePath, entityCode, entityOverwrite);

        Template repositoryTpl = freemarkerConfiguration.getTemplate("repository.ftl", CHARSET);
        String repositoryCode = FreeMarkerTemplateUtils.processTemplateIntoString(repositoryTpl, tableMeta);
        String repositoryCodePath = checkOutputDir(repositoryPath, repositoryPackage) + File.separator + entityName + "Repository.java";
        writeFile(repositoryCodePath, repositoryCode, repositoryOverwrite);


        Template serviceTpl = freemarkerConfiguration.getTemplate("service.ftl", CHARSET);
        String serviceCode = FreeMarkerTemplateUtils.processTemplateIntoString(serviceTpl, tableMeta);
        String serviceCodePath = checkOutputDir(servicePath, servicePackage) + File.separator + entityName + "Service.java";
        writeFile(serviceCodePath, serviceCode, serviceOverwrite);


        Template serviceImplTpl = freemarkerConfiguration.getTemplate("serviceImpl.ftl", CHARSET);
        String serviceImplCode = FreeMarkerTemplateUtils.processTemplateIntoString(serviceImplTpl, tableMeta);
        String serviceImplCodePath = checkOutputDir(serviceimplPath, serviceimplPackage) + File.separator + entityName + "ServiceImpl.java";
        writeFile(serviceImplCodePath, serviceImplCode, serviceimplOverwrite);
    }

    private void writeFile(String path, String code, boolean overwrite) throws IOException {
        File file = new File(path);
        if (!file.exists() || overwrite) {
            FileUtils.writeStringToFile(file, code, CHARSET);
        }
    }


}
