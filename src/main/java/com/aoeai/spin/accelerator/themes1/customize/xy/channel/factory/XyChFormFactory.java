package com.aoeai.spin.accelerator.themes1.customize.xy.channel.factory;

import com.aoeai.spin.accelerator.generate.persistent.bean.Po;
import com.aoeai.spin.accelerator.generate1.AbstractJavaFileFactory;
import com.aoeai.spin.accelerator.generate1.IPoFactory;
import com.aoeai.spin.accelerator.themes1.customize.xy.channel.bean.XyChForm;

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
        builder.setPo(po);
    }
}
