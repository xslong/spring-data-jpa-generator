package ${pv("codegen.output.entity.package")};

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import ${pv("codegen.output.entity.baseclass")};

/**
 * ${tableComment}
 * table :${tableName}
 * @author spring-data-jpa-generator
 * code generate time is ${.now}
 */
@Getter
@Setter
@Entity
@Table(
    name = "${tableName}"<#if indexs?has_content>,
    indexes = {
    <#list indexs as idx>
        @Index(name = "${idx.indexName}", columnList = "${idx.columnNames}"<#if idx.nonUnique = 0>, unique = true</#if>),
    </#list>
    }
</#if>
)
public class ${nf("upperCamelCase",tableName)} extends ${nf("simpleClassName",pv("codegen.output.entity.baseclass"))} {
<#list columns as col>
    <#if !(("," + pv('codegen.excludeColumn') + ",") ? contains("," + col.columnName + ","))>

    <#if (col.columnComment??)>// ${col.columnComment}</#if><#if (col.columnDefault??)>
    // default: ${col.columnDefault}</#if><#if (col.columnKey=="PRI")>
    @Id</#if><#if (col.extra=="auto_increment")>
    @GeneratedValue(strategy = GenerationType.AUTO)</#if>
    @Column(name = "${col.columnName}"<#if col.isNullable=="NO">, nullable = false</#if><#if (col.characterMaximumLength??)>, length = ${col
.characterMaximumLength?replace(',', '')}</#if><#if (col.numericScale?? && col.numericScale > 0)>, scale = ${col.numericScale}</#if>)
    private ${tf(col.dataType)} ${col.columnName};
    </#if>
</#list>

}