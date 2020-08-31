package com.aoeai.spin.accelerator.themes1.customize.xy.channel.factory;

import cn.hutool.core.util.StrUtil;
import com.aoeai.spin.accelerator.generate.constant.JavaTypeEnum;
import com.aoeai.spin.accelerator.generate.constant.MySQLType2JavaTypeEnum;
import com.aoeai.spin.accelerator.generate.persistent.bean.POField;
import com.aoeai.spin.accelerator.generate.persistent.bean.Po;
import com.aoeai.spin.accelerator.generate1.AbstractJavaFileFactory;
import com.aoeai.spin.accelerator.generate1.IPoFactory;
import com.aoeai.spin.accelerator.refining.db.bean.Column;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.bean.XyChForm;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.bean.XyChFormField;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * 表单类工厂
 * @author aoe
 * @date 2020/8/26
 */
public class XyChFormFactory extends AbstractJavaFileFactory<XyChForm> {

    private IPoFactory poFactory;

    public XyChFormFactory(IPoFactory poFactory) {
        this.poFactory = poFactory;
    }

    /**
     * 建造模块对象
     *
     * @param tableName
     * @return
     */
    @Override
    public XyChForm build(String tableName) {
        return create(tableName, new XyChForm());
    }

    /**
     * 存放配置信息的yaml文件
     *
     * @return
     */
    @Override
    protected String configYaml() {
        return "/themes/xy/channel1/config/form.yaml";
    }

    @Override
    protected IPoFactory poFactory() {
        return poFactory;
    }

    /**
     * 手动创建逻辑
     *
     * @param tableName
     */
    @Override
    protected void manualCreate(String tableName) {
        Po po = poFactory.build(tableName);
        // 添加效验标签
        Map<String, List<String>> checkTagListMap = new HashMap<>();
        for (Column column : po.getTable().getColumns()) {
            if (column.getIsPrimaryKey()) {
                checkTagListMap.put(column.getName(), Collections.EMPTY_LIST);
                continue;
            }

            List<String> checkTagList = new ArrayList<>();
            JavaTypeEnum javaType = MySQLType2JavaTypeEnum.javaType(column.getType(), column.getDbMaxLength());
            String comment = column.getComment();
            if (JavaTypeEnum.STRING == javaType) {
                checkTagList.add(StrUtil.format("@XyNotBlank(msg = \"{}不能为空\")", comment));
            }else {
                checkTagList.add(StrUtil.format("@XyNotNull(msg = \"{}不能为空\")", comment));
            }
            checkTagListMap.put(column.getName(), checkTagList);
        }

        List<XyChFormField> formFieldList = new ArrayList<>(po.getTable().getColumns().size());
        for (POField poField : po.getFieldList()) {
            XyChFormField formField = new XyChFormField();
            BeanUtils.copyProperties(poField, formField);
            formField.setCheckTagList(checkTagListMap.get(formField.getName()));
            formFieldList.add(formField);
        }
        builder.setFieldList(formFieldList);
        builder.setImportList(po.getImportList());
    }
}
