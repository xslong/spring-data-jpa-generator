package ${pv("codegen.output.repository.package")};

import ${pv("codegen.output.entity.package")}.${nf("upperCamelCase",tableName)};
import cn.xxxx.repository.IRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * ${tableComment}
 * table :${tableName}
 * @author spring-data-jpa-generator
 * code generate time is ${.now}
 */
@Repository
public interface ${nf("upperCamelCase",tableName)}Repository extends IRepository<${nf("upperCamelCase",tableName)}, ${tf(pk.dataType)}> {
}

