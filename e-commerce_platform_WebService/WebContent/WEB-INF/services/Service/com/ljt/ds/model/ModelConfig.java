package ljt.ds.model;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.digester.Digester;



public class ModelConfig {

    /**
     * Static ModelConfig
     */
    private static ModelConfig config = null;

    /**
     * modelMap
     */
    private static Map<String, Object> modelMap = null;

    /**
     * Logger
     */
    private Logger logger = null;

    /**
     * 使用系统配置.<br>
     *
     * @return Config
     */
    public static ModelConfig getInstance() {
        if (config == null) {
            synchronized (ModelConfig.class) {
                if (config == null) {
                    config = new ModelConfig();
                }
            }
        }
        return config;
    }

    /**
     * 构造函数.<br>
     *
     */
    protected ModelConfig() {
        logger = Logger.getLogger(getClass());
        modelMap = new HashMap<String, Object>();
        configuration();
    }

    /**
     * 解析处理.<br>
     *
     */
    public void configuration() {
        try {
            Digester digester = new Digester();
            digester.push(this);

            digester.addObjectCreate("models/model", Model.class);
            digester.addSetProperties("models/model");
            digester.addSetNext("models/model", "addModel");
            digester.addObjectCreate("models/model/attribute", Attribute.class);
            digester.addSetProperties("models/model/attribute");
            digester.addSetNext("models/model/attribute", "addAttribute");

            digester.parse(getClass().getResourceAsStream("/models.xml"));

        } catch (Exception e) {
            logger.fatal("", e);
            throw new RuntimeException();
        }
    }

    /**
     * 追加模块.<br>
     *
     * @param model 模块
     */
    public void addModel(Model<String, Object> model) {
        modelMap.put(model.getName(), model);
    }

    /**
     * 通过Key取得模块.<br>
     *
     * @param key 模块名
     * @return 模块
     */
    @SuppressWarnings("unchecked")
	public Model<String, Object> getModel(String key) {
        return (Model<String, Object>) modelMap.get(key);
    }

    @Override
	public String toString() {
        return modelMap.toString();
    }

}
