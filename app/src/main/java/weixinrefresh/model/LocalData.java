package weixinrefresh.model;

import java.util.ArrayList;
import java.util.List;

public class LocalData {

	public static List<Communicate> getDatas(int pageNum) {
		List<Communicate> list = null;
		Communicate c = null;

		if (pageNum == 1) {
			list = new ArrayList<Communicate>();
			for (int i = 0; i < 20; i++) {
				c = new Communicate();
				c.setCommunicateName("第一页" + i);
				list.add(c);
			}

		} else if (pageNum == 2) {
			list = new ArrayList<Communicate>();
			for (int i = 0; i < 20; i++) {
				c = new Communicate();
				c.setCommunicateName("第二页" + i);
				list.add(c);
			}
		} else if (pageNum== 3) {
			list = new ArrayList<Communicate>();
			for (int i = 0; i < 10; i++) {
				c = new Communicate();
				c.setCommunicateName("第三页" + i);
				list.add(c);
			}
		}

		return list;
	}
}
