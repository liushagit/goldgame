package com.orange.goldgame.server.help;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.HelpQa;

public class HelpService {

	
	public static List<HelpQa> getHelpQqByType(int helpType){
		Map<Integer, Map<Integer, HelpQa>> helpQaMap = ResourceCache.getInstance().getHelpQaMap();
		Map<Integer, HelpQa> map = helpQaMap.get(helpType);
		List<HelpQa> list = new ArrayList<HelpQa>();
		list.addAll(map.values());
		Collections.sort(list, new Comparator<HelpQa>() {

			@Override
			public int compare(HelpQa o1, HelpQa o2) {
				return o1.getId() - o2.getId();
			}
		});
		return list;
	}
}
