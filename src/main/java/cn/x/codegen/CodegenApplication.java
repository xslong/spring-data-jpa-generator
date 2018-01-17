package cn.x.codegen;

import cn.x.codegen.db.AnalysisDB;
import cn.x.codegen.db.ColumnMeta;
import cn.x.codegen.db.TableMeta;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main
 * @author xslong
 * @time 2017/11/6 10:45
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("cn.x.codegen")
public class CodegenApplication implements CommandLineRunner {

    @Value("${codegen.tableSchema}")
    private String tableSchema;

    @Value("${codegen.tables}")
    private String tables;

    @Autowired
    private Connection connection;

    @Autowired
    private CodeGenerator codeGenerator;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CodegenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final Set<String> tableSet = new HashSet<>();
        if (StringUtils.isNoneBlank(tables)) {
            tableSet.addAll(Arrays.asList(tables.split(",")));
        }
        AnalysisDB an = new AnalysisDB(connection);
        List<TableMeta> tables = an.allTable(tableSchema);
        tables.forEach(t -> {
            if (!tableSet.isEmpty() && !tableSet.contains(t.getTableName())) {
                return;
            }
            List<ColumnMeta> columns = an.allColumn(tableSchema, t.getTableName());
            t.setColumns(columns);
            t.setPk(an.pk(columns));
            t.setIndexs(an.allIndex(tableSchema,t.getTableName()));
            try {
                codeGenerator.process(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}