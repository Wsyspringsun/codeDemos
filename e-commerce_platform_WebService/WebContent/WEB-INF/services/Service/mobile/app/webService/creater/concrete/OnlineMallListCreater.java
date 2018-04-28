package mobile.app.webService.creater.concrete;

import mobile.app.webService.bridge.abstraction.AbstractAbstraction;
import mobile.app.webService.creater.AbstractCreater;
import mobile.app.webService.decorator.Bean2JsonAbstraction;

public class OnlineMallListCreater extends AbstractCreater {

	@Override
	public AbstractAbstraction createAbstraction() {
		return new Bean2JsonAbstraction(
				AbstractAbstraction
						.newAbstraction(
								"mobile.app.webService.bridge.abstraction.concrete.OnlineMallListAbstraction",
								"mobile.app.webService.faced.concrete.OnlineMallListFaced"));
	}
}
