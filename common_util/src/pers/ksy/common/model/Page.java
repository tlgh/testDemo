package pers.ksy.common.model;

import java.util.List;

public class Page<T> extends Result<PageInfo<T>> {

	public Page(int pageIndex, long total, int pageSize, List<T> list) {
		super(null, null, true, new PageInfo<T>(pageIndex, total, pageSize,
				list));
	}

	public T getItem(int index) {
		return getBody().getList().get(index);
	}
}
