package mobile.app.webService.decorator;

import java.util.Map;

import ljt.ds.util.JsonUtils;
import mobile.app.webService.bridge.abstraction.AbstractAbstraction;
import mobile.app.webService.bridge.abstraction.decorator.DecoratorAbstraction;

public class Bean2JsonAbstraction extends DecoratorAbstraction {

	public Bean2JsonAbstraction(AbstractAbstraction abstraction) {
		super(abstraction);
	}

	@Override
	public Object execute(Map<String, Object> parameters) {
		return JsonUtils.beanToJson(super.abstraction.execute(parameters),
				"result");
	}
}
