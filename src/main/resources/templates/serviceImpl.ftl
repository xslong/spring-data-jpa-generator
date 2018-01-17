package ${pv("codegen.output.serviceimpl.package")};

import cn.xxxx.AbstractService;
import ${pv("codegen.output.entity.package")}.${nf("upperCamelCase",tableName)};
import cn.xxxx.repository.IRepository;
import ${pv("codegen.output.repository.package")}.${nf("upperCamelCase",tableName)}Repository;
import ${pv("codegen.output.service.package")}.${nf("upperCamelCase",tableName)}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
/**
 * ${tableComment}
 * table :${tableName}
 * @author spring-data-jpa-generator
 * Code generate time is ${.now}
 */
@Service
@Transactional
public class ${nf("upperCamelCase",tableName)}ServiceImpl extends AbstractService<${nf("upperCamelCase",tableName)},${tf(pk.dataType)}> implements
${nf("upperCamelCase",tableName)}Service {

    @Autowired
    private ${nf("upperCamelCase",tableName)}Repository ${nf("lowerCamelCase",tableName)}Repository;

    @Override
    protected IRepository<${nf("upperCamelCase",tableName)}, ${tf(pk.dataType)}> getRepository() {
        return ${nf("lowerCamelCase",tableName)}Repository;
    }

}
