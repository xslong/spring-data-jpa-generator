package ${pv("codegen.output.service.package")};
import ${pv("codegen.output.entity.package")}.${nf("upperCamelCase",tableName)};
import cn.xxxx.service.IService;

/**
 * ${tableComment}
 * table :${tableName}
 * @author spring-data-jpa-generator
 * code generate time is ${.now}
 */
public interface ${nf("upperCamelCase",tableName)}Service extends IService<${nf("upperCamelCase",tableName)}, ${tf(pk.dataType)}> {

}

